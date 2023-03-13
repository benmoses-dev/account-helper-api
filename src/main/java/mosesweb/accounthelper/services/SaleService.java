package mosesweb.accounthelper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import mosesweb.accounthelper.exceptions.CustomerNeededException;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
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
     * @throws mosesweb.accounthelper.exceptions.SaleNotFoundException
     */
    public String getSale(Integer id) throws JsonProcessingException,
                                             SaleNotFoundException
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
     * @throws mosesweb.accounthelper.exceptions.InvoiceNumberNeededException
     * @throws mosesweb.accounthelper.exceptions.CustomerNeededException
     * @throws mosesweb.accounthelper.exceptions.InvoiceNumberNotUniqueException
     * @throws mosesweb.accounthelper.exceptions.CustomerNotFoundException
     */
    public String addNewSale(BigDecimal amount, LocalDate date, boolean cash,
                             Integer invoiceNumber, Integer customerId) throws
            JsonProcessingException, InvoiceNumberNeededException,
            CustomerNeededException, InvoiceNumberNotUniqueException,
            CustomerNotFoundException
    {
        if (cash) {
            Sale sale = new Sale(amount, date, true);
            return mapper.writeValueAsString(saveSale(sale));
        }
        // A valid customer and invoice number is needed - this is a credit sale
        if (invoiceNumber == null) {
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
        return mapper.writeValueAsString(saveSale(sale));
    }

    /**
     *
     * @param id
     * @return
     * @throws mosesweb.accounthelper.exceptions.SaleNotFoundException
     */
    public String deleteSale(Integer id) throws SaleNotFoundException
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

    private Sale saveSale(Sale sale) throws ConstraintViolationException
    {
        MyModelValidator validator = new MyModelValidator();
        validator.validate(sale);
        return sale;
        //return saleRepository.save(sale);
    }
}
