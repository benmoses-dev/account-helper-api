package mosesweb.AccountHelper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Sale
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer customerId;
    private LocalDate date;
    private BigDecimal amount;
    private boolean isCash;
    private Integer invoiceNumber;

    public Sale()
    {
    }

//    public Sale(Integer customerId, LocalDate date, BigDecimal amount,
//                Integer invoiceNumber, boolean isCash)
//    {
//        this.customerId = customerId;
//        this.date = date;
//        this.amount = amount;
//        this.isCash = isCash;
//        this.invoiceNumber = invoiceNumber;
//    }

     /**
     *
     * @param customerId the customer ID of the new customer linked to the sale.
     */
    public void setCustomerId(Integer customerId)
    {
        this.customerId = customerId;
    }
    
    /**
     *
     * @param date the new date of the sale if cash, the date of the invoice if credit.
     */
    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    /**
     *
     * @param amount the new sale amount in GBP.
     */
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    /**
     *
     * @param isCash true if the sale is a cash sale, otherwise false.
     */
    public void setIsCash(boolean isCash)
    {
        this.isCash = isCash;
    }

    /**
     *
     * @param invoiceNumber the new invoice number linked to a credit sale.
     */
    public void setInvoiceNumber(Integer invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     *
     * @return the ID of the Sale. null if not saved in the repository.
     */
    public Integer getId()
    {
        return id;
    }

    /**
     *
     * @return the ID of the customer linked to the sale.
     */
    public Integer getCustomerId()
    {
        return customerId;
    }

    /**
     *
     * @return the date of the sale if cash, the date of the invoice if credit.
     */
    public LocalDate getDate()
    {
        return date;
    }

    /**
     *
     * @return the amount of the sale in GBP.
     */
    public BigDecimal getAmount()
    {
        return amount;
    }

    /**
     *
     * @return the invoice number linked to a credit sale. null if the sale is cash.
     */
    public Integer getInvoiceNumber()
    {
        return invoiceNumber;
    }

    /**
     *
     * @return true if the sale if a cash sale, otherwise false.
     */
    public boolean getIsCash()
    {
        return isCash;
    }
    
    /**
     *
     * @param sale a Sale object with the desired customer ID, date, amount, cash status, and invoice number. 
     * This Sale will update its state to the same values as the provided Sale.
     */
    public void updateDetails (Sale sale)
    {
        this.customerId = sale.getCustomerId();
        this.date = sale.getDate();
        this.amount = sale.getAmount();
        this.isCash = sale.getIsCash();
        this.invoiceNumber = sale.getInvoiceNumber();
    }
}
