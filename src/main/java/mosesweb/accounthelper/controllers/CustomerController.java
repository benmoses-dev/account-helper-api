package mosesweb.accounthelper.controllers;

import mosesweb.accounthelper.models.Receivable;
import mosesweb.accounthelper.models.Customer;
import mosesweb.accounthelper.models.Address;
import java.util.Collection;
import mosesweb.accounthelper.repositories.CustomerRepository;
import mosesweb.accounthelper.exceptions.CustomerAlreadyExistsException;
import mosesweb.accounthelper.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * A REST Controller for managing client requests for Customer objects. Can get
 * all Customer objects in the system, as well as the details for any individual
 * Customer. Can add a Customer to the system. Can update a Customer that
 * belongs to the system. Can delete a Customer that belongs to the system.
 *
 * @author Ben Moses
 */
@RestController
public class CustomerController
{
    
    @Autowired
    private CustomerRepository customerRepository;

    // ***** PRESENTER *****
    
    /**
     *
     * Returns a collection of all Customer objects in the system.
     *
     * @return a collection of all customers
     */
    @GetMapping(value = "/customers/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    /**
     *
     * Returns the Customer with the provided ID.
     *
     * @param id the unique customer id
     * @return the Customer with the given id if found, otherwise 404 error
     */
    @GetMapping(value = "/customers/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("id") Integer id)
    {
        return customerRepository.findById(id).orElseThrow(
                    () -> new CustomerNotFoundException(id));
    }
    
    @GetMapping(value = "/customers/{id}/ledger/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Receivable> getCustomerLedger(@PathVariable("id") Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return customer.getReceivables();
    }
    
    @GetMapping(value = "/customers/{id}/address/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Address getCustomerAddress(@PathVariable("id") Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return customer.getAddress();
    }
    
    // ***** CONTROLLER *****
        /**
     *
     * Add a new Customer to the system. Throws a RuntimeException if the
     * Customer name is null.
     *
     * @param customer the Customer object to create
     * @return the Customer object that was created
     */
    @PostMapping(value = "/customers/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer addNewCustomer(@RequestBody Customer customer)
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

    /**
     *
     * Edit an existing Customer. Throws a RuntimeException if the Customer does
     * not exist in the system. The Customer provided must have the correct ID.
     *
     * @param id the ID of the Customer to edit
     * @param customer the Customer object with the new details
     * @return String representing the new customer details
     */
    @PutMapping(value = "/customers/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer editCustomer(@PathVariable("id") Integer id,
                                 @RequestBody Customer customer)
    {
        // check that the customer with id exists and is the same as the provided customer
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
    
    @PutMapping(value = "/customers/{id}/address/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer editAddress(@PathVariable("id") Integer id,
                                @RequestBody Address address)
    {
        Customer found = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        Address oldAddress = found.getAddress();
        if (address.getHouseNumber() != 0) {
            oldAddress.setHouseNumber(address.getHouseNumber());
        }
        if (address.getPostcode() != null) {
            oldAddress.setPostcode(address.getPostcode());
        }
        if (address.getRoadName() != null) {
            oldAddress.setRoadName(address.getRoadName());
        }
        return customerRepository.save(found);
    }
    
    /**
     *
     * Delete a Customer from the system with the provided ID. Throws a
     * RuntimeException if the Customer does not exist.
     *
     * @param id the ID of the Customer to delete
     * @return a String representing whether the customer was successfully
     * deleted
     */
    @PostMapping("/customers/{id}/")
    public String deleteCustomer(@PathVariable("id") Integer id)
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