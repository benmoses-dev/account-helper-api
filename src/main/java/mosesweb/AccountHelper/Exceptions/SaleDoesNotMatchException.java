package mosesweb.AccountHelper.Exceptions;

public class SaleDoesNotMatchException extends RuntimeException
{
    public SaleDoesNotMatchException(Integer saleId, Integer id)
    {
        super("The sale with ID " + id + " is not the same as the sale with ID " + saleId);
    }
}
