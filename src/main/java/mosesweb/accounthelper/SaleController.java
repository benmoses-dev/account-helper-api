package mosesweb.accounthelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import mosesweb.accounthelper.exceptions.AmountNotValidException;
import mosesweb.accounthelper.exceptions.CustomerNeededException;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
import mosesweb.accounthelper.exceptions.DateNotValidException;
import mosesweb.accounthelper.exceptions.InvoiceNumberNeededException;
import mosesweb.accounthelper.exceptions.InvoiceNumberNotUniqueException;
import mosesweb.accounthelper.models.Sale;
import mosesweb.accounthelper.exceptions.SaleNotFoundException;
import mosesweb.accounthelper.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

/**
 *
 * A REST Controller for managing client requests for Sale objects. Can get all
 * Sale objects in the system, as well as the details for any individual Sale.
 * Can add a Sale to the system. Can update a Sale that belongs to the system.
 * Can delete a Sale that belongs to the system.
 *
 * @author Ben Moses
 */
@RestController
public class SaleController
{

    @Autowired
    private SaleRepository saleRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    // ***** PRESENTER *****

    /**
     *
     * Returns a collection of all Sale objects in the system.
     *
     * @return all Sale objects
     */
    @GetMapping(value = "/sales/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Sale> getAllSales()
    {
        return saleRepository.findAll();
    }

    /**
     *
     * Returns the Sale object with the provided ID. Throws a RuntimeException
     * if the sale does not exist.
     *
     * @param id the unique id of the Sale
     * @return the Sale with the given id
     */
    @GetMapping(value = "/sales/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sale getSale(@PathVariable("id") Integer id)
    {
        return saleRepository.findById(id).orElseThrow(
                    () -> new SaleNotFoundException(id));
    }

    // ***** CONTROLLER *****
    
    /**
     *
     * Adds a new sale to the system. If the sale is a cash sale, a BankDebit
     * will be created and attached to the sale. Otherwise, a Receivable will be
     * created and attached to the sale: a customer ID and a unique invoice
     * number will need to be provided alongside the sale; both can be omitted
     * if the sale is cash.
     *
     * @param saleWrapper the amount, date, invoice number, and customer ID to
     * add. Add isCash=true to create a cash sale.
     * @return the Sale that has been added.
     */
    @PostMapping(value = "/sales/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sale addNewSale(@RequestBody SaleWrapper saleWrapper)
    {
        BigDecimal amount = saleWrapper.getAmount();
        LocalDate date = saleWrapper.getDate();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            // The amount must be non-negative
            throw new AmountNotValidException(amount);
        }
        if (date == null || date.isAfter(LocalDate.now().plusDays(1))) {
            // The date cannot be more than a day in the future
            throw new DateNotValidException(date, LocalDate.now());
        }
        if (saleWrapper.isCash()) {
            Sale sale = new Sale(amount, date, true);
            return saleRepository.save(sale);
        }
        Integer invoiceNumber = saleWrapper.getInvoiceNumber();
        Integer customerId = saleWrapper.getCustomerId();
        // A valid customer and invoice number is needed - this is a credit sale
        if (invoiceNumber == null || invoiceNumber.compareTo(0) < 0) {
            throw new InvoiceNumberNeededException(invoiceNumber);
        }
        if (customerId == null || customerId.compareTo(0) < 0) {
            throw new CustomerNeededException(customerId);
        }
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Iterable<Sale> sales = saleRepository.findAll();
        for (Sale existingSale : sales) {
            if (existingSale.invoiceNumberMatches(invoiceNumber)) {
                throw new InvoiceNumberNotUniqueException(invoiceNumber, existingSale.getId());
            }
        }
        Sale sale = new Sale(amount, date, false, invoiceNumber, customer);
        return saleRepository.save(sale);
    }
    
    /**
     *
     * Helper method for development. Should a sale be deleted in a production system?
     * 
     * @param id the id of the sale to delete.
     * @return success if the operation was successful.
     */
    @PostMapping(value = "/sales/{id}/")
    public String deleteSale(@PathVariable("id") Integer id)
    {
        if (!saleRepository.existsById(id)) {
            throw new SaleNotFoundException(id);
        }
        saleRepository.deleteById(id);
        return "success.";
    }
    
    /**
     *
     * Helper method for development. Deletes all sales.
     * 
     * @return success if successful.
     */
    @PostMapping(value = "/sales/all/")
    public String deleteAllSales()
    {
        saleRepository.deleteAll();
        return "success: all sales deleted. I hope you're happy...";
    }
}