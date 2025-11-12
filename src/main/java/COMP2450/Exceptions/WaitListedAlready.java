package COMP2450.Exceptions;

public class WaitListedAlready extends RuntimeException {
    public WaitListedAlready(String message) {
        super(message);
    }
}
