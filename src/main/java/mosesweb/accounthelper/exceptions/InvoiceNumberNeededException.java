package mosesweb.accounthelper.exceptions;

public class InvoiceNumberNeededException extends Exception
{

    public InvoiceNumberNeededException(Integer invoiceNumber)
    {
        super("You need to supply a valid invoice number. Supplied: " + invoiceNumber);
    }
}
