package itau.com.br.cadastrochavespix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationsRulesException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ValidationsRulesException(String ex){
        super(ex);
    }

}
