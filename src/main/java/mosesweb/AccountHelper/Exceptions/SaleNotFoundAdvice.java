package mosesweb.AccountHelper.Exceptions;

import mosesweb.AccountHelper.Exceptions.SaleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SaleNotFoundAdvice
{
    @ResponseBody
    @ExceptionHandler(SaleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String saleNotFoundHandler(SaleNotFoundException e)
    {
        return e.getMessage();
    }
}
