package mosesweb.AccountHelper.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DateNotValidAdvice
{
    @ResponseBody
    @ExceptionHandler(DateNotValidException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String dateNotValidHandler(DateNotValidException e)
    {
        return e.getMessage();
    }
}
