package mosesweb.AccountHelper.Exceptions;

public class CustomerNameNeededException extends RuntimeException
{
    public CustomerNameNeededException()
    {
        super("You need to supply a customer name");
    }
}
