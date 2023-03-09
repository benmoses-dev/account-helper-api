package mosesweb.accounthelper.services;

import java.util.Collection;
import mosesweb.accounthelper.exceptions.CustomerAlreadyExistsException;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
import mosesweb.accounthelper.models.Address;
import mosesweb.accounthelper.models.Customer;
import mosesweb.accounthelper.models.Receivable;
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
    
    
    public Iterable<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }
    
    public Customer getCustomer(Integer id)
    {
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException(id));
    }
    
    public Collection<Receivable> getCustomerLedger(Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return customer.getReceivables();
    }
    
    public Address getCustomerAddress(Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return customer.getAddress();
    }
    
    public Customer addNewCustomer(Customer customer)
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
        return customerRepository.save(customer);
    }
    
    public Customer editCustomer(Integer id, Customer customer)
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
        return customerRepository.save(found);
    }
    
    public Customer editCustomerAddress(Integer customerId, Address newAddress)
    {
        Customer found = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        Address oldAddress = found.getAddress();
        if (newAddress.getHouseNumber() != 0) {
            oldAddress.setHouseNumber(newAddress.getHouseNumber());
        }
        if (newAddress.getPostcode() != null) {
            oldAddress.setPostcode(newAddress.getPostcode());
        }
        if (newAddress.getRoadName() != null) {
            oldAddress.setRoadName(newAddress.getRoadName());
        }
        return customerRepository.save(found);
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
