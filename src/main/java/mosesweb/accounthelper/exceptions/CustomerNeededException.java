package mosesweb.accounthelper.exceptions;

public class CustomerNeededException extends RuntimeException
{
    public CustomerNeededException()
    {
        super("You need to supply a customer.");
    }
    
    public CustomerNeededException(Integer id)
    {
        super("You need to supply a valid customer ID. Supplied: " + id);
    }
}
