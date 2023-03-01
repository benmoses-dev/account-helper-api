package mosesweb.accounthelper.exceptions;

public class SaleNotFoundException extends RuntimeException {
    
    public SaleNotFoundException(Integer id)
    {
        super("Could not find sale " + id);
    }
}
