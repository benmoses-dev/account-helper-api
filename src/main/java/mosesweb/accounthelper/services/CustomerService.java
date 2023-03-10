package mosesweb.accounthelper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
import mosesweb.accounthelper.models.Address;
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
     * @return @throws JsonProcessingException
     */
    public String getAllCustomers() throws JsonProcessingException
    {
        return mapper.writeValueAsString(customerRepository.findAll());
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    public String getCustomer(Integer id) throws JsonProcessingException
    {
        return mapper.writeValueAsString(customerRepository.findById(id).orElseThrow(
                    () -> new CustomerNotFoundException(id)));
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    public String getCustomerLedger(Integer id) throws JsonProcessingException
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return mapper.writeValueAsString(customer.getReceivables());
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    public String getCustomerAddress(Integer id) throws JsonProcessingException
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return mapper.writeValueAsString(customer.getAddress());
    }

    /**
     *
     * @param customer
     * @return
     * @throws JsonProcessingException
     */
    public String addNewCustomer(String name, String email, Integer houseNumber,
                                 String roadName, String postcode) throws JsonProcessingException
    {
        Customer customer = new Customer();

        if (name != null) {
            customer.setName(name);
        }

        if (email != null) {
            customer.setEmail(email);
        }

        Address address = customer.getAddress();

        if (houseNumber != null) {
            address.setHouseNumber(houseNumber);
        }

        if (roadName != null) {
            address.setRoadName(roadName);
        }

        if (postcode != null) {
            address.setPostcode(postcode);
        }

        return mapper.writeValueAsString(customerRepository.save(customer));
    }

    /**
     *
     * @param id
     * @param customer
     * @return
     * @throws JsonProcessingException
     */
    public String editCustomer(Integer id, String name, String email,
                               Integer houseNumber, String roadName,
                               String postcode) throws JsonProcessingException
    {
        // check that the customer with id exists
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        if (name != null) {
            customer.setName(name);
        }

        if (email != null) {
            customer.setEmail(email);
        }

        Address address = customer.getAddress();

        if (houseNumber != null) {
            address.setHouseNumber(houseNumber);
        }

        if (roadName != null) {
            address.setRoadName(roadName);
        }

        if (postcode != null) {
            address.setPostcode(postcode);
        }

        return mapper.writeValueAsString(customerRepository.save(customer));
    }

    public String deleteCustomer(Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        String result = "customer " + customer.getName() + " with ID " + customer.getId() + " deleted.";
        if (customer.getReceivables().isEmpty()) {
            customerRepository.deleteById(id);
            return result;
        }
        return "This customer has receivables and must be kept for system records. Please consider removing personal details instead of deleting.";
    }
}
