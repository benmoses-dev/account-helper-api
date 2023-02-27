package mosesweb.AccountHelper.Exceptions;

public class CustomerNotFoundException extends RuntimeException
{
    public CustomerNotFoundException(Integer id)
    {
        super("Could not find customer " + id);
    }
}
