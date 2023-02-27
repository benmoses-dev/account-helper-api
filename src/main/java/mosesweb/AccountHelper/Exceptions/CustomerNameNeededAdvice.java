package mosesweb.AccountHelper.Exceptions;

import mosesweb.AccountHelper.Exceptions.CustomerNameNeededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerNameNeededAdvice
{
    @ResponseBody
    @ExceptionHandler(CustomerNameNeededException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String customerNameNeededHandler(CustomerNameNeededException e)
    {
        return e.getMessage();
    }
}
