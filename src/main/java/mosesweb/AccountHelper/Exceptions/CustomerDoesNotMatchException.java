package mosesweb.AccountHelper.Exceptions;

public class CustomerDoesNotMatchException extends RuntimeException
{
    public CustomerDoesNotMatchException(Integer customerId, Integer id)
    {
        super("The customer with ID " + id + " is not the same as the customer with ID " + customerId);
    }
}
