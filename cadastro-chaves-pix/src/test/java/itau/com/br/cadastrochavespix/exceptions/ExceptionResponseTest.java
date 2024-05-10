package itau.com.br.cadastrochavespix.exceptions;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionResponseTest {
    @Test
    void testConstructorAndGetters() {
        Date timestamp = new Date();
        String message = "Test message";
        String details = "Test details";

        ExceptionResponse exceptionResponse = new ExceptionResponse(timestamp, message, details);

        assertEquals(timestamp, exceptionResponse.getTimestamp());
        assertEquals(message, exceptionResponse.getMessage());
        assertEquals(details, exceptionResponse.getDetails());
    }

}