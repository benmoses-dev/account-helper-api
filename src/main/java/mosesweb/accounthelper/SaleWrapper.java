package mosesweb.accounthelper;

import mosesweb.accounthelper.models.Sale;

public class SaleWrapper {

    private Sale sale;
    private Integer customerId;
    private Integer invoiceNumber;

    public SaleWrapper()
    {
    }
    
    public void setSale(Sale sale)
    {
        this.sale = sale;
    }
    
    public void setCustomerId(Integer customerId)
    {
        this.customerId = customerId;
    }
    
    public void setInvoiceNumber(Integer invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }
    
    public Sale getSale()
    {
        return sale;
    }

    public Integer getCustomerId()
    {
        return customerId;
    }
    
    public Integer getInvoiceNumber()
    {
        return invoiceNumber;
    }
}