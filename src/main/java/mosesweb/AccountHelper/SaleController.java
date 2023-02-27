package mosesweb.AccountHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import mosesweb.AccountHelper.Exceptions.AmountNotValidException;
import mosesweb.AccountHelper.Exceptions.CustomerNeededException;
import mosesweb.AccountHelper.Exceptions.CustomerNotFoundException;
import mosesweb.AccountHelper.Exceptions.DateNotValidException;
import mosesweb.AccountHelper.Exceptions.InvoiceNumberNeededException;
import mosesweb.AccountHelper.Exceptions.SaleNotFoundException;
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
     * Adds a new sale to the system.
     *
     * @param saleWrapper the Sale, Customer, and Receivable to add.
     * @return the Sale that has been added
     */
    @PostMapping(value = "/sales/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sale addNewSale(@RequestBody SaleWrapper saleWrapper)
    {
        Sale sale = saleWrapper.getSale();
        if (sale.getId() == null) {
            if (sale.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new AmountNotValidException(sale.getAmount());
            }
            if (sale.getDate().isAfter(LocalDate.now().plusDays(1))) {
                throw new DateNotValidException(sale.getDate(), LocalDate.now());
            }
            if (sale.getIsCash()) {
                BankDebit bd = sale.createBankDebit();
                sale.setBankDebit(bd);
            } else {
                Integer customerId = saleWrapper.getCustomerId();
                Integer invoiceNumber = saleWrapper.getInvoiceNumber();
                if (invoiceNumber == null || invoiceNumber.compareTo(0) < 0) {
                    throw new InvoiceNumberNeededException(invoiceNumber);
                }
                if (customerId == null || customerId.compareTo(0) < 0) {
                    throw new CustomerNeededException(customerId);
                }
                Iterable<Sale> sales = saleRepository.findAll();
                for (Sale existingSale : sales) {
                    existingSale.checkUnique(invoiceNumber);
                }
                Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
                Receivable r = sale.createReceivable(customer, invoiceNumber);
                sale.setReceivable(r);
            }
            return saleRepository.save(sale);
        }
        return saleRepository.findById(sale.getId()).orElseThrow(() -> new SaleNotFoundException(sale.getId()));
    }
    
    @PostMapping(value = "/sales/{id}/")
    public String deleteSale(@PathVariable("id") Integer id)
    {
        if (!saleRepository.existsById(id)) {
            throw new SaleNotFoundException(id);
        }
        saleRepository.deleteById(id);
        return "success.";
    }
    
    @PostMapping(value = "/sales/all/")
    public String deleteAllSales()
    {
        saleRepository.deleteAll();
        return "success: all sales deleted. I hope you're happy...";
    }
}