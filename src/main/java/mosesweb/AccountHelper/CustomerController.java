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
    * @param name the customer's surname or full name
    * @param email the customer's email address
    * @param postcode the customer's postcode
    * @return a String confirming that the customer was saved
    */
    @PostMapping("/customers") // Map ONLY POST Requests
    public String addNewCustomer (@RequestBody String name,
                    @RequestBody String email,
                    @RequestBody String postcode)
    {
    
        Customer customer = new Customer(name, email, postcode);
        customerRepository.save(customer);
        return "Saved";
    }

    /**
    *
    * @return a collection of all customers
    */
    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }
  
    /**
     *
     * @param id the unique customer id
     * @return the Customer with the given id if found, otherwise 404 error
     */
    @GetMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("id") Integer id)
    {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
    
    /**
     *
     * @param customer the Customer object with the new details
     * @return String representing the new customer details
     */
    @PutMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer editCustomer(@PathVariable("id") Integer id,
                                 @RequestBody Customer customer)
    {
        // check that the customer with id exists
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        // set the saved customer details to the details of the provided customer
        foundCustomer.setName(customer.getName());
        foundCustomer.setEmail(customer.getEmail());
        foundCustomer.setPostcode(customer.getPostcode());
        // save the modified customer details
        customerRepository.save(foundCustomer);
        // retrieve the customer to ensure that it has saved correctly
        Customer confirmedCustomer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return confirmedCustomer;
    }
    
    /**
     *
     * @return a String representing whether the customer was successfully deleted
     */
    @PostMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.deleteById(id);
        if (customerRepository.existsById(id)) {
            Customer notDeleted = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
            return "customer" + notDeleted.getName() + "not deleted!";
        } else {
            return "customer" + customer.getName() + "deleted!";
        }
    }
}