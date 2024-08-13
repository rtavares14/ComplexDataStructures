package nl.saxion.cds.collection.exceptions;

public class ValueNotFoundException extends RuntimeException {
    public ValueNotFoundException(String value) {
        super("Value \"" + value + "\" is not found.");
    }
}
