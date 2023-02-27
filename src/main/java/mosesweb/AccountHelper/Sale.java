package mosesweb.AccountHelper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import mosesweb.AccountHelper.Exceptions.InvoiceNumberNotUniqueException;

@Entity
@Table(name="sales")
public class Sale
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    
    private LocalDate date;
    private BigDecimal amount;
    private boolean isCash;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="bank_debit_id")
    private BankDebit bankDebit;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="receivable_id")
    private Receivable receivable;

    public Sale()
    {
    }
    
    public void setId(Integer id)
    {
        this.id = id;
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
    
    public void setBankDebit(BankDebit bankDebit)
    {
        this.bankDebit = bankDebit;
    }
    
    public void setReceivable(Receivable receivable)
    {
        this.receivable = receivable;
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
     * @return true if the sale if a cash sale, otherwise false.
     */
    public boolean getIsCash()
    {
        return isCash;
    }
    
    public BankDebit getBankDebit()
    {
        return bankDebit;
    }
    
    public Receivable getReceivable()
    {
        return receivable;
    }
    
    public BankDebit createBankDebit()
    {
        BankDebit bd = new BankDebit();
        bd.setAmount(getAmount());
        bd.setDate(getDate());
        return bd;
    }
    
    public Receivable createReceivable(Customer customer, Integer invoiceNumber)
    {
        Receivable r = new Receivable();
        r.setAmount(getAmount());
        r.setDate(getDate());
        r.setInvoiceNumber(invoiceNumber);
        r.setCustomer(customer);
        return r;
    }
    
    public void checkUnique(Integer invoiceNumber)
    {
        if (!getIsCash() && receivable != null) {
            if (receivable.getInvoiceNumber().equals(invoiceNumber)) {
                throw new InvoiceNumberNotUniqueException(invoiceNumber, id);
            }
        }
    }
}
