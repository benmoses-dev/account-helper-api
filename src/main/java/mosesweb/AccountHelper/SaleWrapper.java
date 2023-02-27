package mosesweb.AccountHelper;

public class SaleWrapper {

    private Sale sale;
    private Customer customer;
    private Integer invoiceNumber;

    public SaleWrapper()
    {
    }
    
    public void setSale(Sale sale)
    {
        this.sale = sale;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    public void setInvoiceNumber(Integer invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }
    
    public Sale getSale()
    {
        return sale;
    }

    public Customer getCustomer()
    {
        return customer;
    }
    
    public Integer getInvoiceNumber()
    {
        return invoiceNumber;
    }
}