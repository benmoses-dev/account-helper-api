package mosesweb.accounthelper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import mosesweb.accounthelper.exceptions.AmountNotValidException;
import mosesweb.accounthelper.exceptions.CustomerNeededException;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
import mosesweb.accounthelper.exceptions.DateNotValidException;
import mosesweb.accounthelper.exceptions.InvoiceNumberNeededException;
import mosesweb.accounthelper.exceptions.InvoiceNumberNotUniqueException;
import mosesweb.accounthelper.exceptions.SaleNotFoundException;
import mosesweb.accounthelper.models.Customer;
import mosesweb.accounthelper.models.Sale;
import mosesweb.accounthelper.repositories.CustomerRepository;
import mosesweb.accounthelper.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ben Moses
 */
@Service
public class SaleService
{

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    public SaleService()
    {
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    /**
     *
     * @return @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public String getAllSales() throws JsonProcessingException
    {
        return mapper.writeValueAsString(saleRepository.findAll());
    }

    /**
     *
     * @param id
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public String getSale(Integer id) throws JsonProcessingException
    {
        return mapper.writeValueAsString(saleRepository.findById(id).orElseThrow(
                    () -> new SaleNotFoundException(id)));
    }

    /**
     *
     * @param amount
     * @param date
     * @param cash
     * @param invoiceNumber
     * @param customerId
     * @return
     * @throws JsonProcessingException
     */
    public String addNewSale(BigDecimal amount, LocalDate date, boolean cash,
                             Integer invoiceNumber, Integer customerId) throws JsonProcessingException
    {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            // The amount must be non-negative
            throw new AmountNotValidException(amount);
        }
        if (date == null || date.isAfter(LocalDate.now().plusDays(1))) {
            // The date cannot be more than a day in the future
            throw new DateNotValidException(date, LocalDate.now());
        }
        if (cash) {
            Sale sale = new Sale(amount, date, true);
            return mapper.writeValueAsString(saleRepository.save(sale));
        }
        // A valid customer and invoice number is needed - this is a credit sale
        if (invoiceNumber == null || invoiceNumber.compareTo(0) < 0) {
            throw new InvoiceNumberNeededException(invoiceNumber);
        }
        if (customerId == null || customerId.compareTo(0) < 0) {
            throw new CustomerNeededException(customerId);
        }
        // The customer must already exist in the database. Let's retrieve it
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        // The invoice number must be unique
        Iterable<Sale> sales = saleRepository.findAll();
        for (Sale existingSale : sales) {
            if (existingSale.invoiceNumberMatches(invoiceNumber)) {
                throw new InvoiceNumberNotUniqueException(invoiceNumber, existingSale.getId());
            }
        }
        Sale sale = new Sale(amount, date, false, invoiceNumber, customer);
        return mapper.writeValueAsString(saleRepository.save(sale));
    }

    /**
     *
     * @param id
     * @return
     */
    public String deleteSale(Integer id)
    {
        if (!saleRepository.existsById(id)) {
            throw new SaleNotFoundException(id);
        }
        saleRepository.deleteById(id);
        return "success.";
    }

    /**
     *
     * @return
     */
    public String deleteAllSales()
    {
        saleRepository.deleteAll();
        return "success: all sales deleted. I hope you're happy...";
    }
}
