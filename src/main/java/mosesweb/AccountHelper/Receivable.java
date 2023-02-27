package mosesweb.AccountHelper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="receivables")
public class Receivable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    private BigDecimal amount;
    private LocalDate date;
    private Integer invoiceNumber;
    
    @OneToOne(mappedBy="receivable")
    @JsonIgnore
    private Sale sale;
    
    @ManyToOne()
    @JoinColumn(name="customer_id")
    @JsonIgnore
    private Customer customer;

    public Receivable()
    {
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    public void setDate(LocalDate date)
    {
        this.date = date;
    }
    
    public void setInvoiceNumber(Integer invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public BigDecimal getAmount()
    {
        return amount;
    }

    public LocalDate getDate()
    {
        return date;
    }
    
    public Integer getInvoiceNumber()
    {
        return invoiceNumber;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public Sale getSale()
    {
        return sale;
    }
}