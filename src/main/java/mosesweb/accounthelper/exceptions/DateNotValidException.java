package mosesweb.accounthelper.exceptions;

import java.time.LocalDate;

public class DateNotValidException extends RuntimeException
{
    public DateNotValidException(LocalDate target, LocalDate now)
    {
        super("The date " + target + " is not valid. Is it more than a day in the future? The current date is " + now);
    }
}
