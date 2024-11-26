package weather.rest_api.spring_boot.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import weather.rest_api.spring_boot.demo.common.dto.ErrorDetailsDTO;

/**
 * This class allows to handle global exception and provide the caller an explicit error message.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Global exception handler for WebClientException
     * 
     * @param ex the WebClientException that has been thrown
     * @return an explicit error message to the caller
     */
    @ExceptionHandler(WebClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetailsDTO handleWebClientException(WebClientException ex) {
        return new ErrorDetailsDTO(WebClientException.class.getName(), ex.getMessage());
    }

    /**
     * Global exception handler for InconsistentRawDataException
     * 
     * @param ex the InconsistentRawDataException that has been thrown
     * @return an explicit error message to the caller
     */
    @ExceptionHandler(InconsistentRawDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetailsDTO handleInconsistentRawDataException(InconsistentRawDataException ex) {
        return new ErrorDetailsDTO(InconsistentRawDataException.class.getName(), ex.getMessage());
    }
}
