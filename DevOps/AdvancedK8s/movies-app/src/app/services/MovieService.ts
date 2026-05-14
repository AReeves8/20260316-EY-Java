import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { Movie } from "../types/Movie";
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../environments/environments";



/**
 * INJECTABLE
 *      - register the class with the Angular DI contatiner
 *      - creates a singleton instance
 *      - root: the service will be provided at the root of your application
 *          - meaning it can be injected ANYWHERE in this app
 * 
 *          - other options:
 *              - platform: higher than root. useful when you have multiple angular apps in the same project. (microfrontends)
 *              - any: deprecated in v16. each lazy-loaded instance, is a new instance instead of a singleton
 */
@Injectable({providedIn: "root"})
export class MovieService {
    /**
     * SERVICES
     *      - include business logic that will be used across many components
     * 
     *      - HTTP requests
     *          - one central location for where those requests occur
     */

    // grabbing the backend url from the environment
    private readonly URL = `${environment.baseApiUrl}/movies`

    constructor(
        private http: HttpClient    // injected Http Client to send reqs with
    ) {}



    /**
     * OBSERVABLE
     *      - from Reactive Extensions for JavaScript (RxJS)
     *      - handle async operations in Angular
     *          - similar to Promises, kinda
     *              - promises only fire once, where as, Observable are streams of events
     * 
     *      - pub/sub model
     *          - event is fired, then the result is stored in the Observable until the subscriber reads the event
     *          - observable is intermediary 
     *          - OBSERVABLES NEED A SUBSCRIBER
     *              - they will not publish any data, if there is not a subscriber
     */
    getMovies(rating?: number): Observable<Movie[]> {
        let params = new HttpParams();

        // setting a query parameter if rating is provided
        if(rating != null) {
            params = params.set("rating", rating);
        }

        return this.http.get<Movie[]>(this.URL, {params})
            
            // pipe allows you to chain multiple observabale methods together
            // provide each method as a parameter and they will run in the order they were provided
            .pipe(

                // catchError - observable method that alows for graceful error handling on the observable
                catchError(
                    () => throwError(
                        () => new Error("Failed to load movies.")
                    )
                )
            )
    }

    // POST request for movies
    createNewMovie(movie: Movie): Observable<Movie> {

        // adding movie object to body of request
        return this.http.post<Movie>(this.URL, movie)
            .pipe(
                catchError(() => throwError(() => new Error("Failed to create movie.")))
            )
    }

    // PUT request for movies
    updateMovie(id: number, movie: Movie): Observable<Movie> {
        return this.http.put<Movie>(`${this.URL}/${id}`, movie)
            .pipe(
                catchError(() => throwError(() => new Error("Failed to update movie.")))
            )
    }

    // DELETE request for movies
    deleteMovie(id: number): Observable<void> {
        return this.http.delete<void>(`${this.URL}/${id}`)
            .pipe(
                catchError(() => throwError(() => new Error("Failed to delete movie.")))
            )
    }

}