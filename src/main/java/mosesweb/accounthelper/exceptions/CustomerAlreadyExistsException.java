package mosesweb.accounthelper.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException
{
    public CustomerAlreadyExistsException(Integer id)
    {
        super("A customer with id " + id + " already exists");
    }
}
