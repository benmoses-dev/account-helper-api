package mosesweb.accounthelper.exceptions;

public class InvoiceNumberNotUniqueException extends RuntimeException
{
    public InvoiceNumberNotUniqueException(Integer invoiceNumber, Integer saleId)
    {
        super("The invoice number " + invoiceNumber + " is already in use by sale with ID " + saleId);
    }
}
