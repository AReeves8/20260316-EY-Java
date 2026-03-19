package com.skillstorm;

/**
 * Hello world!
 *
 */
public class PasswordValidator 
{
    public static void main( String[] args )
    {
        String password = "Abc123@#$";
        // then call each individual method
    }


    public static boolean containsNumber(String input) {

        // .chars() creates a stream of characters
        // Character::isDigit is a predicate that checks if each character is a number
        return input.chars().anyMatch(Character::isDigit);
    }

    public static boolean containsSpace(String input) {
        return input.chars().anyMatch(Character::isSpaceChar);
    }

    public static boolean passwordLength(String input) {
        return input.length() >= 8;
    }

    public static boolean containsSpecialCharacter(String input) {
        return input.contains("@") || input.contains("#") || input.contains("$");
    }

    public static boolean containsNefariousCharacter(String input) {
        return input.contains("<") || input.contains(">") || input.contains("(") || input.contains(")") || input.contains(";");
    }

    public static boolean containsCapitalLetter(String input) {
        return input.chars().anyMatch(Character::isUpperCase);
    }

    public static boolean containsLowercaseLetter(String input) {
        return input.chars().anyMatch(Character::isLowerCase);
    }

    public static boolean passwordIsNotEmpty(String input) throws IllegalArgumentException {

        if(input == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        String sanitizedInput = input.trim();
        if(sanitizedInput.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return true;
    }
}
