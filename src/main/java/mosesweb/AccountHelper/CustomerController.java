package mosesweb.AccountHelper;

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
 * @author user
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
            throw new CustomerNameNeededException();
        }
        return customerRepository.save(customer);
    }

    /**
     *
     * Edit an existing Customer. Throws a RuntimeException if the Customer does
     * not exist in the system.
     *
     * @param id the ID of the Customer to edit
     * @param customer the Customer object with the new details
     * @return String representing the new customer details
     */
    @PutMapping(value = "/customers/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer editCustomer(@PathVariable("id") Integer id,
                                 @RequestBody Customer customer)
    {
        // check that the customer with id exists
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(
                    () -> new CustomerNotFoundException(id));
        // set the saved customer details to the details of the provided customer
        foundCustomer.updateDetails(customer);
        // save the modified customer details
        return customerRepository.save(foundCustomer);
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
        Customer customer = customerRepository.findById(id).orElseThrow(
                    () -> new CustomerNotFoundException(id));
        customerRepository.deleteById(id);

        if (customerRepository.existsById(id)) {
            Customer notDeleted = customerRepository.findById(id).orElseThrow(
                        () -> new CustomerNotFoundException(id));
            if (notDeleted.equals(customer)) {
                return "customer " + notDeleted.getName() + " not deleted!";
            }
        }
        return "customer " + customer.getName() + " deleted!";
    }
}