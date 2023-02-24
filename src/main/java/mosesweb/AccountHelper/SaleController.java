package mosesweb.AccountHelper;

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
    @GetMapping("/sales/")
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
    @GetMapping("/sales/{id}/")
    public Sale getSale(@PathVariable("id") Integer id)
    {
        return saleRepository.findById(id).orElseThrow(
                    () -> new SaleNotFoundException(id));
    }

    // ***** CONTROLLER *****
    
    /**
     *
     * Adds a new sale to the system. If the sale is not a cash sale, it must
     * have a valid, unique invoice number and customer ID. The amount of the
     * sale must be greater than zero. If the sale is a cash sale, the invoice
     * number will be set to null.
     *
     * @param sale the Sale to add to the repository
     * @return the Sale that has been added
     */
    @PostMapping(value = "/sales/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sale addNewSale(@RequestBody Sale sale)
    {
        // ** VALIDATE CLIENT INPUT **
        // Check that the customer exists
        Integer customerId = sale.getCustomerId();
        if (customerId != null && customerRepository.existsById(customerId) != true) {
            // The customer does not exist
            throw new CustomerNotFoundException(customerId);
        }
        // If the sale is a cash sale, set the invoice number to null
        if (sale.getIsCash()) {
            sale.setInvoiceNumber(null);
        }
        else {
            // If the sale is a credit sale, it needs a customer ID and a unique invoice number
            if (customerId == null) {
                // There is no provided customer ID
                throw new CustomerNeededException();
            }
            else if (sale.getInvoiceNumber() == null) {
                // There is no provided invoice number
                throw new InvoiceNumberNeededException();
            }
            else {
                Iterable<Sale> allSales = saleRepository.findAll();
                for (Sale existingSale : allSales) {
                    if (existingSale.getInvoiceNumber() != null
                        && existingSale.getInvoiceNumber().equals(
                                            sale.getInvoiceNumber())) {
                        // The invoice number is not unique
                        throw new InvoiceNumberNotUniqueException(
                                    sale.getInvoiceNumber(),
                                    existingSale.getId());
                    }
                }
            }
        }
        // The amount of the sale must be greater than zero
        if (sale.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountNotValidException(sale.getAmount());
        }
        return saleRepository.save(sale);
    }
    
    /**
     *
     * Edit an existing Sale. Throws a RuntimeException if the sale does not
     * exist. If the new sale details are not for a cash sale, they must include
     * a valid, unique invoice number and customer ID. The amount of the new
     * sale details must be greater than zero. If the new details are for a cash
     * sale, the invoice number will be set to null.
     *
     * @param id the unique id of the Sale
     * @param sale a sale object with the new details
     * @return a String representation of the edited Sale
     */
    @PutMapping(value = "/sales/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sale editSale(@PathVariable("id") Integer id,
                         @RequestBody Sale sale)
    {
        // Check that the sale with id exists
        Sale foundSale = saleRepository.findById(id).orElseThrow(
                    () -> new SaleNotFoundException(id));
        // ** VALIDATE CLIENT INPUT **
        // Check that the customer exists
        Integer customerId = sale.getCustomerId();
        if (customerId != null && customerRepository.existsById(customerId) != true) {
            // The customer does not exist
            throw new CustomerNotFoundException(customerId);
        }
        // If the sale is a cash sale, set the invoice number to null
        if (sale.getIsCash()) {
            sale.setInvoiceNumber(null);
        }
        else {
            // If the sale is a credit sale, it needs a customer ID and a unique invoice number
            if (customerId == null) {
                // There is no provided customer ID
                throw new CustomerNeededException();
            }
            else if (sale.getInvoiceNumber() == null) {
                // There is no provided invoice number
                throw new InvoiceNumberNeededException();
            }
            else if (!(sale.getInvoiceNumber().equals(
                       foundSale.getInvoiceNumber()))) {
                // The invoice number is being changed
                Iterable<Sale> allSales = saleRepository.findAll();
                for (Sale existingSale : allSales) {
                    if (existingSale.getInvoiceNumber() != null && existingSale.getInvoiceNumber().equals(
                                sale.getInvoiceNumber())) {
                        // The invoice number is not unique
                        throw new InvoiceNumberNotUniqueException(
                                    sale.getInvoiceNumber(),
                                    existingSale.getId());
                    }
                }
            }
        }
        // The amount of the sale must be greater than zero
        if (sale.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountNotValidException(sale.getAmount());
        }
        // Update the details of the target sale
        foundSale.updateDetails(sale);
        return saleRepository.save(foundSale);
    }

    /**
     *
     * Delete a sale from the system with the provided ID. Throws a
     * RuntimeException if the sale does not exist.
     *
     * @param id the unique id of the Sale to delete
     * @return a String representation of the deleted Sale if successful,
     * otherwise 'sale not deleted!'
     */
    @PostMapping("/sales/{id}/")
    public String deleteSale(@PathVariable("id") Integer id)
    {
        // Check that the sale exists, find it and then delete it
        Sale sale = saleRepository.findById(id).orElseThrow(
                    () -> new SaleNotFoundException(id));
        saleRepository.deleteById(id);

        // Validate that the sale was deleted
        if (saleRepository.existsById(id)) {
            // If a sale with id still exists, is it the same sale?
            Sale notDeleted = saleRepository.findById(id).orElseThrow(
                        () -> new SaleNotFoundException(id));
            if (notDeleted.equals(sale)) {
                // The sale was not deleted. Find the customer and return all the details of the sale
                if (notDeleted.getCustomerId() != null) {
                    Customer customer = customerRepository.findById(
                                notDeleted.getCustomerId()).orElseThrow(()
                                -> new CustomerNotFoundException(
                                            notDeleted.getCustomerId()));
                    return "sale {date: " + notDeleted.getDate() + ", amount: " + notDeleted.getAmount() + ", customer: " + customer + "} not deleted!";
                }
                return "sale {date: " + notDeleted.getDate() + ", amount: " + notDeleted.getAmount() + "} not deleted!";
            }
        }
        return "sale {date: " + sale.getDate() + ", amount: " + sale.getAmount() + "} deleted.";
    }
}
