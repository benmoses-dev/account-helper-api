package mosesweb.accounthelper.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SaleWrapper {

    private BigDecimal amount;
    private LocalDate date;
    private boolean isCash;
    private Integer customerId;
    private Integer invoiceNumber;

    public SaleWrapper()
    {
    }
    
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public LocalDate getDate()
    {
        return date;
    }
    
    public boolean isCash()
    {
        return isCash;
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