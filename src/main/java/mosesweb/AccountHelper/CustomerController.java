package mosesweb.AccountHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController
{
    @Autowired
    private CustomerRepository customerRepository;

    /**
    *
    * @param customer the Customer object to create
    * @return the Customer object that was created
    */
    @PostMapping(value = "/customers/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer addNewCustomer (@RequestBody Customer customer)
    {
        return customerRepository.save(customer);
    }

    /**
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
     * @param id the unique customer id
     * @return the Customer with the given id if found, otherwise 404 error
     */
    @GetMapping(value = "/customers/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("id") Integer id)
    {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
    
    /**
     *
     * @param customer the Customer object with the new details
     * @return String representing the new customer details
     */
    @PutMapping(value = "/customers/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer editCustomer(@PathVariable("id") Integer id, @RequestBody Customer customer)
    {
        // check that the customer with id exists
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        // set the saved customer details to the details of the provided customer
        foundCustomer.updateDetails(customer);
        // save the modified customer details
        return customerRepository.save(foundCustomer);
    }
    
    /**
     *
     * @return a String representing whether the customer was successfully deleted
     */
    @PostMapping("/customers/{id}/")
    public String deleteCustomer(@PathVariable("id") Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.deleteById(id);
        
        if (customerRepository.existsById(id)) {
            Customer notDeleted = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
            if (notDeleted.equals(customer)) {
                return "customer" + notDeleted.getName() + "not deleted!";
            }
        }
        return "customer" + customer.getName() + "deleted!";
    }
}