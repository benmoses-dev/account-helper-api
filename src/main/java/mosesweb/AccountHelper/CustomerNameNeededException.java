package mosesweb.AccountHelper;

public class CustomerNameNeededException extends RuntimeException
{
    public CustomerNameNeededException()
    {
        super("You need to supply a customer name");
    }
}
