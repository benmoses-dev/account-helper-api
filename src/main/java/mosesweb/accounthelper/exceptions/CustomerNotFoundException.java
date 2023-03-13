package mosesweb.accounthelper.exceptions;

public class CustomerNotFoundException extends Exception
{

    public CustomerNotFoundException(Integer id)
    {
        super("Could not find customer " + id);
    }
}
