package mosesweb.accounthelper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.validation.ConstraintViolationException;
import java.text.SimpleDateFormat;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
import mosesweb.accounthelper.models.Customer;
import mosesweb.accounthelper.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ben Moses
 */
@Service
public class CustomerService
{

    @Autowired
    private CustomerRepository customerRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    public CustomerService()
    {
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    /**
     *
     * @return a Json string of all customers
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public String getAllCustomers() throws JsonProcessingException
    {
        return mapper.writeValueAsString(customerRepository.findAll());

    }

    /**
     *
     * @param id
     * @return
     * @throws CustomerNotFoundException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public String getCustomer(Integer id) throws CustomerNotFoundException,
                                                 JsonProcessingException
    {
        return mapper.writeValueAsString(customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException(id)));
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     * @throws mosesweb.accounthelper.exceptions.CustomerNotFoundException
     */
    public String getCustomerLedger(Integer id) throws JsonProcessingException,
                                                       CustomerNotFoundException
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return mapper.writeValueAsString(customer.getReceivables());
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     * @throws mosesweb.accounthelper.exceptions.CustomerNotFoundException
     */
    public String getCustomerAddress(Integer id) throws JsonProcessingException,
                                                        CustomerNotFoundException
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return mapper.writeValueAsString(customer.getAddress());
    }

    /**
     *
     * @param name
     * @param email
     * @param houseNumber
     * @param roadName
     * @param postcode
     * @return
     * @throws JsonProcessingException
     */
    public String addNewCustomer(String name, String email, Integer houseNumber,
                                 String roadName, String postcode) throws
            JsonProcessingException
    {
        Customer customer = new Customer();

        if (name != null) {
            customer.setName(name);
        }

        if (email != null) {
            customer.setEmail(email);
        }

        if (houseNumber != null) {
            customer.setHouseNumber(houseNumber);
        }

        if (roadName != null) {
            customer.setRoadName(roadName);
        }

        if (postcode != null) {
            customer.setPostcode(postcode);
        }

        return mapper.writeValueAsString(saveCustomer(customer));
    }

    /**
     *
     * @param id
     * @param name
     * @param email
     * @param houseNumber
     * @param roadName
     * @param postcode
     * @return
     * @throws JsonProcessingException
     * @throws mosesweb.accounthelper.exceptions.CustomerNotFoundException
     */
    public String editCustomer(Integer id, String name, String email,
                               Integer houseNumber, String roadName,
                               String postcode) throws JsonProcessingException,
                                                       CustomerNotFoundException
    {
        // check that the customer with id exists
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        if (name != null) {
            customer.setName(name);
        }

        if (email != null) {
            customer.setEmail(email);
        }

        if (houseNumber != null) {
            customer.setHouseNumber(houseNumber);
        }

        if (roadName != null) {
            customer.setRoadName(roadName);
        }

        if (postcode != null) {
            customer.setPostcode(postcode);
        }

        return mapper.writeValueAsString(saveCustomer(customer));
    }

    /**
     *
     * @param id
     * @return
     * @throws CustomerNotFoundException
     */
    public String deleteCustomer(Integer id) throws CustomerNotFoundException
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        String result = "customer " + customer.getName() + " with ID " + customer.getId() + " deleted.";
        if (customer.getReceivables().isEmpty()) {
            customerRepository.deleteById(id);
            return result;
        }
        return "This customer has receivables and must be kept for system records. Please consider removing personal details instead of deleting.";
    }

    private Customer saveCustomer(Customer customer) throws
            ConstraintViolationException
    {
        MyModelValidator validator = new MyModelValidator();
        validator.validate(customer);
        return customer;
        //return customerRepository.save(customer);
    }
}
