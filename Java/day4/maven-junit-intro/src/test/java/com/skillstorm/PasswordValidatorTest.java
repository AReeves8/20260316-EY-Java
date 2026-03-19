package com.skillstorm;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class PasswordValidatorTest 
{
    /**
     * JUnit Annotations (@)
     *      Test        - define a method as a test method
     *      Disabled    - used with @Test, to ignore a specific test so it doesn't run with the others
     *      BeforeAll   - setup method; run once before all other methods. MUST BE STATIC
     *      BeforeEach  - setup method; run before each individual @Test method
     *      AfterAll    - teardown method; run once after all other methods. MUST BE STATIC
     *      AfterEach   - teardown method; run after each individual @Test method
     * 
     *      - there is no guarentee of order for similar annotations
     *          - make sure that if you have multiple BeforeAll methods, for example, that they don't depend on each other
     */

    @BeforeAll
    public static void classSetup() {
        /**
         * setup something the entire test class will use
         *      ex: opening a database connection
         */
        System.out.println("BEFORE ALL");
    }

    @BeforeEach
    public void methodSetup() {
        /**
         * setup things for each method to use
         *      ex: loading database with test data
         */
        System.out.println("BEFORE EACH");
    }

    @Test
    @Disabled
    public void shouldAnswerWithTrue()
    {
        // THIS TEST WILL NOT RUN SINCE IT IS DISABLED
        assertTrue( true );
    }

    @AfterEach
    public void methodTeardown() {
        /**
         * teardown things that were used in each test method
         *      ex: clear the data in the database
         */
        System.out.println("AFTER EACH");
    }

    @AfterAll
    public static void classTeardown() {
        /**
         * teardown something that was setup for the entire class
         *      ex: closing a database connection
         */
        System.out.println("AFTER ALL");
    }





    /**
     * TEST DRIVEN DEVELOPMENT (TDD)
     *      - write our tests BEFORE writing our code
     *      - forces you to write code that passes your tests, rather than writing tests that will pass your code
     *          - force you to be more objective when writing your tests
     * 
     *  Password Validation:
     *      - contains a number
     *      - minimum 8 characters
     *          - .length >= 8
     *      - no spaces
     *      - contains a special character (@, #, $)
     *      - does not contain nefarious characters (<>, (), ;)
     *      - contains a capital letter
     *      - require lowercase letters
     *      - not an empty string
     *          - null, "", blank, .trim()
     */

    @Test
    @DisplayName("Password Contains a Number")
    public void testPasswordContainsNumber() {

        assertTrue(PasswordValidator.containsNumber("abc123"));
        assertFalse(PasswordValidator.containsNumber("abcxyz"));

    }

    @Test
    @DisplayName("Password is at least 8 Characters")
    public void testPasswordLength() {

        // boundary value analysis - testing all values around some boundary
        assertTrue(PasswordValidator.passwordLength("abc123xyz"));  // 9
        assertTrue(PasswordValidator.passwordLength("abc123xz"));   // 8
        assertFalse(PasswordValidator.passwordLength("abcxyz1"));   // 7

        // equivalence partitioning - testing middle values between partitions
        //      like 4, 8, 12 in this example
    }

    @Test
    @DisplayName("Password Contains a Space")
    public void testPasswordContainsSpace() {
        assertFalse(PasswordValidator.containsSpace("abc123"));
        assertTrue(PasswordValidator.containsSpace("abc xyz"));
    }

    @Test
    @DisplayName("Password Contains a Special Character")
    public void testPasswordContainsSpecialCharacter() {
        assertTrue(PasswordValidator.containsSpecialCharacter("abc123@"));
        assertTrue(PasswordValidator.containsSpecialCharacter("abc#xyz"));
        assertTrue(PasswordValidator.containsSpecialCharacter("$abcxyz"));
        assertFalse(PasswordValidator.containsSpecialCharacter("abc123"));
    }

    @Test
    @DisplayName("Password Contains a Nefarious Character")
    public void testPasswordContainsNefariousCharacter() {
        assertTrue(PasswordValidator.containsNefariousCharacter("abc123<"));
        assertTrue(PasswordValidator.containsNefariousCharacter("abc123>"));
        assertTrue(PasswordValidator.containsNefariousCharacter("abc123("));
        assertTrue(PasswordValidator.containsNefariousCharacter("abc123)"));
        assertTrue(PasswordValidator.containsNefariousCharacter("abc123;"));
        assertFalse(PasswordValidator.containsNefariousCharacter("abc123"));
    }

    @Test
    @DisplayName("Password Contains a Capital Letter")
    public void testPasswordContainsCapitalLetter() {

        assertTrue(PasswordValidator.containsCapitalLetter("Abc123"));
        assertFalse(PasswordValidator.containsCapitalLetter("abcxyz"));

    }

    @Test
    @DisplayName("Password Contains a Lowercase Letter")
    public void testPasswordContainsLowercaseLetter() {

        assertTrue(PasswordValidator.containsLowercaseLetter("Abc123"));
        assertFalse(PasswordValidator.containsLowercaseLetter("ABC123"));

    }

    @Test
    @DisplayName("Password Is Not Empty")
    public void testPasswordIsNotEmpty() {

        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.passwordIsNotEmpty(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.passwordIsNotEmpty("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            PasswordValidator.passwordIsNotEmpty("                      ");
        });
        assertDoesNotThrow(() -> {
            PasswordValidator.passwordIsNotEmpty("Abc123@#$");
        });
        assertTrue(PasswordValidator.passwordIsNotEmpty("Abc123@#$"));
    }

}
