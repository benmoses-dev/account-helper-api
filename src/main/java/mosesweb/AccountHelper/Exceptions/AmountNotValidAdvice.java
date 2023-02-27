package mosesweb.AccountHelper.Exceptions;

import mosesweb.AccountHelper.Exceptions.AmountNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AmountNotValidAdvice
{
    @ResponseBody
    @ExceptionHandler(AmountNotValidException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String amountNotValidHandler(AmountNotValidException e)
    {
        return e.getMessage();
    }
}
