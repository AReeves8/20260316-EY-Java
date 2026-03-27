package com.skillstorm.spring_aop.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/*
 * GLOBAL EXCEPTION HANDLING IN SPRING MVC
 * =========================================
 *
 * Without a GlobalExceptionHandler, unhandled exceptions bubble up and Spring
 * returns a generic 500 error with a stack trace (in dev mode). That exposes
 * implementation details and gives the client no useful information.
 *
 * @RestControllerAdvice
 * ---------------------
 * This annotation is a combination of @ControllerAdvice and @ResponseBody.
 *
 *   - @ControllerAdvice tells Spring to apply this class globally across ALL
 *     controllers (not just one). It acts as a cross-cutting concern — similar
 *     in spirit to an AOP aspect, but scoped specifically to exception handling
 *     in the web layer.
 *
 *   - @ResponseBody means every method's return value is written directly into
 *     the HTTP response body as JSON (just like @RestController does for
 *     normal endpoints).
 *
 * Use @ControllerAdvice (without @ResponseBody) if you need to return a View
 * (Thymeleaf template) for an error page instead of JSON.
 *
 * @ExceptionHandler
 * -----------------
 * Each method annotated with @ExceptionHandler declares which exception type(s)
 * it handles. When an exception propagates out of any controller method, Spring
 * checks this class first and routes it to the matching handler.
 *
 * The resolution order:
 *   1. Spring looks for an @ExceptionHandler in the same controller first.
 *   2. If none is found, it falls back to any @ControllerAdvice class.
 *   3. If still unhandled, Spring's DefaultHandlerExceptionResolver takes over
 *      and typically returns a 500.
 *
 * HOW THIS RELATES TO AOP
 * -----------------------
 * @ControllerAdvice is actually implemented using Spring AOP under the hood.
 * Each @ExceptionHandler method is essentially an @AfterThrowing advice on all
 * controller join points — Spring intercepts the thrown exception and routes it
 * here before it reaches the client.
 *
 * The difference from a raw AOP @AfterThrowing aspect is that Spring MVC gives
 * you a cleaner, more expressive API (method signatures, HTTP status mapping,
 * response body serialization) without writing pointcut expressions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Handles Spring's ResponseStatusException.
     *
     * This is the preferred way for service/controller code to signal a
     * specific HTTP status without coupling the service layer to HTTP.
     * Example from a service: throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
     *
     * The handler extracts the status and reason phrase from the exception and
     * returns them to the client as a structured JSON body.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    /*
     * Handles Jakarta Bean Validation failures (@NotBlank, @Min, @Max, etc.).
     *
     * Spring MVC throws MethodArgumentNotValidException when a @Valid or
     * @Validated annotated parameter fails validation (e.g., the Movie posted
     * to POST /api/v1/movies has a blank title).
     *
     * BindingResult contains one FieldError per violated constraint. We collect
     * them into a map of { fieldName -> errorMessage } so the client knows
     * exactly which fields are invalid.
     *
     * Returns 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation failed");
        body.put("fields", fieldErrors);

        return ResponseEntity.badRequest().body(body);
    }

    /*
     * Catch-all handler for any unexpected exception.
     *
     * This is your safety net. Any exception that doesn't match a more specific
     * handler above will end up here. Returning a vague 500 message prevents
     * leaking stack traces or internal details to the client while still giving
     * developers something to grep for in logs.
     *
     * TIP: Log the full exception here (with a logger) so it isn't lost — the
     * client gets a sanitized message, but your logs still have the real cause.
     *
     * Returns 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
