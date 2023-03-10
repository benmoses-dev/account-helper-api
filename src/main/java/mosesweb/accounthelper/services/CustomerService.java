package mosesweb.accounthelper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
import mosesweb.accounthelper.exceptions.CustomerAlreadyExistsException;
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
    public String addNewCustomer(Customer customer) throws JsonProcessingException
    {
        if (customer.getName() == null) {
            customer.setName("");
        }
        if (customer.getEmail() == null) {
            customer.setEmail("");
        }
        if (customer.getAddress() == null) {
            customer.setAddress(new Address(0, "", ""));
        }
        if (customer.getId() != null) {
            throw new CustomerAlreadyExistsException(customer.getId());
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
    public String editCustomer(Integer id, Customer customer) throws JsonProcessingException
    {
        // check that the customer with id exists
        Customer found = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        if (customer.getEmail() != null) {
            found.setEmail(customer.getEmail());
        }
        if (customer.getName() != null) {
            found.setName(customer.getName());
        }
        if (customer.getAddress() != null) {
            found.setAddress(customer.getAddress());
        }
        return mapper.writeValueAsString(customerRepository.save(found));
    }

    /**
     *
     * @param customerId
     * @param newAddress
     * @return the edited customer as a json string
     * @throws JsonProcessingException
     */
    public String editCustomerAddress(Integer customerId, Address newAddress) throws JsonProcessingException
    {
        Customer found = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Address oldAddress = found.getAddress();
        if (newAddress.getHouseNumber() != null) {
            oldAddress.setHouseNumber(newAddress.getHouseNumber());
        }
        if (newAddress.getPostcode() != null) {
            oldAddress.setPostcode(newAddress.getPostcode());
        }
        if (newAddress.getRoadName() != null) {
            oldAddress.setRoadName(newAddress.getRoadName());
        }
        return mapper.writeValueAsString(customerRepository.save(found));
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
