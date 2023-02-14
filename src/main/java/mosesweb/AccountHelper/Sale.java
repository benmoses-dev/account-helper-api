package mosesweb.AccountHelper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity // This tells Hibernate to make a table out of this class
public class Sale {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private Integer customerId;
  private LocalDate date;
  private BigDecimal amount;
  private Integer invoiceNumber;
  private boolean isCash;
  
  public Sale(){}
  
  public Sale(Integer customerId, LocalDate date, BigDecimal amount, Integer invoiceNumber, boolean isCash)
  {
      this.customerId = customerId;
      this.date = date;
      this.amount = amount;
      this.invoiceNumber = invoiceNumber;
      this.isCash = isCash;
  }

  public Integer getId() {
    return id;
  }

  public Integer getCustomerId() {
    return customerId;
  }
  public void setCustomer(Customer customer) {
    this.customerId = customer.getId();
  }

  public LocalDate getDate() {
    return date;
  }
  public void setDate(LocalDate date) {
    this.date = date;
  }
  
  public BigDecimal getAmount() {
    return amount;
  }
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
  
  public Integer getInvoiceNumber() {
    return invoiceNumber;
  }
  public void setInvoiceNumber(Integer invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }
  
  public boolean getIsCash() {
    return isCash;
  }
  public void setIsCash(boolean isCash) {
    this.isCash = isCash;
  }
}