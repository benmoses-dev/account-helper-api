package mosesweb.AccountHelper.Exceptions;

import mosesweb.AccountHelper.Exceptions.CustomerDoesNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerDoesNotMatchAdvice
{
    @ResponseBody
    @ExceptionHandler(CustomerDoesNotMatchException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String customerDoesNotMatchHandler(CustomerDoesNotMatchException e)
    {
        return e.getMessage();
    }
}
