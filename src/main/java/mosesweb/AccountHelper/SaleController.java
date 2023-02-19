package mosesweb.AccountHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController
{
  
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CustomerRepository customerRepository;
  
    /**
     *
     * @param customerId the unique id of the customer associated with the invoice
     * @param date the date of the sale if cash, otherwise date of the invoice
     * @param amount the sale amount
     * @param invoiceNumber the number of the invoice. Set to 0 if the sale is cash
     * @param isCash true if the sale is a cash sale, false otherwise
     * @return String representing the new sale
     */
    @PostMapping("/sales")
    public String addNewSale (@RequestParam("customerId") Integer customerId, 
                            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, 
                            @RequestParam("amount") BigDecimal amount,
                            @RequestParam("invoiceNumber") Integer invoiceNumber,
                            @RequestParam("isCash") boolean isCash)
    {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        if (isCash) {
            invoiceNumber = 0;
        }
        Sale sale = new Sale(customer.getId(), date, amount, invoiceNumber, isCash);
        saleRepository.save(sale);
        return "Saved: customerId=" + sale.getCustomerId() + ", date=" + sale.getDate()
                  + ", amount=" + sale.getAmount() + ", invoice number=" + sale.getInvoiceNumber() + ", cash=" + sale.getIsCash();
    }

    /**
     *
     * @return all Sale objects
     */
    @GetMapping("/sales")
    public Iterable<Sale> getAllSales() {
        // This returns a JSON or XML with the users
        return saleRepository.findAll();
    }
  
    /**
     *
     * @param id the unique id of the Sale
     * @return the Sale with the given id
     */
    @GetMapping("/sales/{id}")
    public Sale getSale(@PathVariable("id") Integer id)
    {
        return saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException(id));
    }
  
    /**
     *
     * @param id the unique id of the Sale
     * @param customerId the new customer id
     * @param date the new date
     * @param amount the new amount
     * @param invoiceNumber the new invoice number
     * @param isCash true if the Sale is cash, otherwise false
     * @return a String representation of the edited Sale
     */
    @PutMapping("/sales/{id}")
    public String editSale(@PathVariable("id") Integer id, 
                            @RequestParam("customerId") Integer customerId, 
                            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, 
                            @RequestParam("amount") BigDecimal amount,
                            @RequestParam("invoiceNumber") Integer invoiceNumber,
                            @RequestParam("isCash") boolean isCash)
    {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException(id));
        Customer newCustomer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        sale.setCustomer(newCustomer.getId());
        sale.setDate(date);
        sale.setAmount(amount);
        sale.setInvoiceNumber(invoiceNumber);
        sale.setIsCash(isCash);
        saleRepository.save(sale);
        return "sale altered: customerId=" + sale.getCustomerId() + ", date=" + sale.getDate()
              + ", amount=" + sale.getAmount() + ", invoice number=" + sale.getInvoiceNumber() + ", cash=" + sale.getIsCash();
    }
  
    /**
     *
     * @param id the unique id of the Sale to delete
     * @return a String representation of the deleted Sale if successful, otherwise 'sale not deleted!'
     */
    @PostMapping("/sales/{id}")
    public String deleteSale(@PathVariable("id") Integer id)
    {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundException(id));
        String success = "deleted sale: customerId=" + sale.getCustomerId() + ", date=" + sale.getDate()
              + ", amount=" + sale.getAmount() + ", invoice number=" + sale.getInvoiceNumber() + ", cash=" + sale.getIsCash();
        saleRepository.deleteById(id);
        if (saleRepository.existsById(id)) {
            return "sale not deleted!";
        } else {
            return success;
        }
    }
}