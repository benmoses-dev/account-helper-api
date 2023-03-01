package mosesweb.accounthelper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SaleDoesNotMatchAdvice
{
    @ResponseBody
    @ExceptionHandler(SaleDoesNotMatchException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String saleDoesNotMatchHandler(SaleDoesNotMatchException e)
    {
        return e.getMessage();
    }
}
