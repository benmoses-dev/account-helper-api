package mosesweb.accounthelper.exceptions;

public class CustomerNameNeededException extends RuntimeException
{
    public CustomerNameNeededException()
    {
        super("You need to supply a customer name");
    }
}
