package br.com.company.explorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Fábio Siqueira on 12/28/15.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LandNotFoundException extends RuntimeException {

    public LandNotFoundException(Long landId) {
        super("could not find land '" + landId + "'.");
    }
}
