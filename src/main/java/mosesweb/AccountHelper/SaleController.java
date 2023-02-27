package mosesweb.AccountHelper;

import mosesweb.AccountHelper.Exceptions.AmountNotValidException;
import mosesweb.AccountHelper.Exceptions.CustomerNeededException;
import mosesweb.AccountHelper.Exceptions.CustomerNotFoundException;
import mosesweb.AccountHelper.Exceptions.InvoiceNumberNeededException;
import mosesweb.AccountHelper.Exceptions.SaleNotFoundException;
import mosesweb.AccountHelper.Exceptions.SaleDoesNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

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
            Customer customer = saleWrapper.getCustomer();
            Integer invoiceNumber = saleWrapper.getInvoiceNumber();
            if (sale.getIsCash()) {
                BankDebit bd = sale.createBankDebit();
                sale.setBankDebit(bd);
            } else {
                Receivable r = sale.createReceivable(customer, invoiceNumber);
                sale.setReceivable(r);
            }
            return saleRepository.save(sale);
        }
        return saleRepository.findById(sale.getId()).orElseThrow(() -> new SaleNotFoundException(sale.getId()));
    }
}