package mosesweb.accounthelper.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import mosesweb.accounthelper.exceptions.CustomerNeededException;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
import mosesweb.accounthelper.exceptions.InvoiceNumberNeededException;
import mosesweb.accounthelper.exceptions.InvoiceNumberNotUniqueException;
import mosesweb.accounthelper.exceptions.SaleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author user
 */
@ControllerAdvice
public class ExceptionAdvice
{

    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String customerNotFoundHandler(CustomerNotFoundException e)
    {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CustomerNeededException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String customerNeededHandler(CustomerNeededException e)
    {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvoiceNumberNeededException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String invoiceNumberNeededHandler(InvoiceNumberNeededException e)
    {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvoiceNumberNotUniqueException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String invoiceNumberNotUniqueHandler(
            InvoiceNumberNotUniqueException e)
    {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String jsonProcessingHandler(JsonProcessingException e)
    {
        return "an error has occured while processing json";
    }

    @ResponseBody
    @ExceptionHandler(SaleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String saleNotFoundHandler(SaleNotFoundException e)
    {
        return e.getMessage();
    }
}
