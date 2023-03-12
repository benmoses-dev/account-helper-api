package mosesweb.accounthelper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * A bank debit, usually associated with a cash sale or a credit payment. Has a
 * non-negative amount and a date that is not more than a day in the future.
 *
 * @author Ben Moses
 */
@Entity
@Table(name = "bank_debit")
public class BankDebit
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    @NotNull(message = "cannot be null")
    @PositiveOrZero(message = "must not be negative")
    @Digits(integer = 9, fraction = 2, message = "maximum of two decimal places")
    private BigDecimal amount;

    @Column(name = "date")
    @NotNull(message = "cannot be null")
    @PastOrPresent(message = "date cannot be in the future")
    private LocalDate date;

    protected BankDebit()
    {
    }

    public BankDebit(BigDecimal amount, LocalDate date)
    {
        this.amount = amount;
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
}
