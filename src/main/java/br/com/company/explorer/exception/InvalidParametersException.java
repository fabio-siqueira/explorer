package br.com.company.explorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Fábio Siqueira on 12/17/15.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidParametersException extends RuntimeException {

    public InvalidParametersException() {
    }

    public InvalidParametersException(String message) {
        super(message);
    }
}
