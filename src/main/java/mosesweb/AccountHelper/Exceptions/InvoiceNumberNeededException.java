package mosesweb.AccountHelper.Exceptions;

public class InvoiceNumberNeededException extends RuntimeException
{
    public InvoiceNumberNeededException()
    {
        super("You need to supply an invoice number");
    }
}
