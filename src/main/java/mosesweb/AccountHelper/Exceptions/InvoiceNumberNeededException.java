package mosesweb.AccountHelper.Exceptions;

public class InvoiceNumberNeededException extends RuntimeException
{
    public InvoiceNumberNeededException(Integer invoiceNumber)
    {
        super("You need to supply a valid invoice number. Supplied: " + invoiceNumber);
    }
}
