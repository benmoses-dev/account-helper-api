package mosesweb.accounthelper.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
import mosesweb.accounthelper.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerView {

    @Autowired
    private CustomerService customerService;

    /**
     *
     * Returns a collection of all Customer objects in the system.
     *
     * @return a collection of all customers
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @GetMapping(value = "/customers/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllCustomers() throws JsonProcessingException
    {
        return customerService.getAllCustomers();
    }

    /**
     *
     * Returns the Customer with the provided ID.
     *
     * @param id the unique customer id
     * @return the Customer with the given id if found, otherwise 404 error
     * @throws mosesweb.accounthelper.exceptions.CustomerNotFoundException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @GetMapping(value = "/customers/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomer(@PathVariable("id") Integer id) throws
            CustomerNotFoundException, JsonProcessingException
    {
        return customerService.getCustomer(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     * @throws mosesweb.accounthelper.exceptions.CustomerNotFoundException
     */
    @GetMapping(value = "/customers/{id}/ledger/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomerLedger(@PathVariable("id") Integer id) throws
            JsonProcessingException, CustomerNotFoundException
    {
        return customerService.getCustomerLedger(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     * @throws mosesweb.accounthelper.exceptions.CustomerNotFoundException
     */
    @GetMapping(value = "/customers/{id}/address/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomerAddress(@PathVariable("id") Integer id) throws
            JsonProcessingException, CustomerNotFoundException
    {
        return customerService.getCustomerAddress(id);
    }

}
