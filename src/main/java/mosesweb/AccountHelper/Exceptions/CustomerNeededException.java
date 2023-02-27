package mosesweb.AccountHelper.Exceptions;

public class CustomerNeededException extends RuntimeException
{
    public CustomerNeededException()
    {
        super("You need to supply a Customer ID");
    }
}
