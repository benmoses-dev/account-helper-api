package mosesweb.accounthelper.exceptions;

public class SaleNotFoundException extends Exception
{

    public SaleNotFoundException(Integer id)
    {
        super("Could not find sale " + id);
    }
}
