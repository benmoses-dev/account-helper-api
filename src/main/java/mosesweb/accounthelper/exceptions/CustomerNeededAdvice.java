package mosesweb.accounthelper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerNeededAdvice
{
    @ResponseBody
    @ExceptionHandler(CustomerNeededException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String customerNeededHandler(CustomerNeededException e)
    {
        return e.getMessage();
    }
}
