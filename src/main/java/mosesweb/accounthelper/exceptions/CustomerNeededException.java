package mosesweb.accounthelper.exceptions;

public class CustomerNeededException extends RuntimeException
{
    public CustomerNeededException(Integer id)
    {
        super("You need to supply a valid Customer ID. Supplied: " + id);
    }
}