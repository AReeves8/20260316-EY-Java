package fileIO;

/**
 * Creating Custom Exceptions
 *      - extend an existing exception class, usually Exception
 *      - common practice to name your excpetions with the word "Exception"
 */
public class InvalidProductDataException extends Exception {

    private int lineNumber;

    public InvalidProductDataException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
