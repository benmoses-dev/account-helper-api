package mosesweb.AccountHelper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="bank_debits")
public class BankDebit {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    private BigDecimal amount;
    private LocalDate date;
    
    @OneToOne(mappedBy="bankDebit")
    @JsonIgnore
    private Sale sale;

    public BankDebit()
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
    
    public Sale getSale()
    {
        return sale;
    }
}