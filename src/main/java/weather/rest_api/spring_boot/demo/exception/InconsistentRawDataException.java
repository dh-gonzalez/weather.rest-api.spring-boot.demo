package weather.rest_api.spring_boot.demo.exception;

/**
 * Exception to raise when weatherbit has provided inconsistent data
 */
public class InconsistentRawDataException extends RuntimeException {

    /**
     * Constructor
     * 
     * @param message message
     */
    public InconsistentRawDataException(String message) {
        super(message);
    }
}
