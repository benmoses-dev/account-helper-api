package mosesweb.AccountHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String addNewCustomer (@RequestParam String name, 
                    @RequestParam String email, 
                    @RequestParam String postcode)
    {
    
        Customer c = new Customer(name, email, postcode);
        customerRepository.save(c);
        return "Saved";
    }

    /**
    *
    * @return a collection of all customers
    */
    @GetMapping("/customers")
    public Iterable<Customer> getAllCustomers()
    {
        // This returns a JSON or XML with the users
        return customerRepository.findAll();
    }
  
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable("id") Integer id)
    {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
    
    /**
     *
     * @param name the customer's surname or full name
     * @param email the customer's email
     * @param postcode the customer's postcode
     * @return String representing the new customer details
     */
    @PutMapping("/customers/{id}")
    public String editCustomer(@PathVariable("id") Integer id, @RequestParam(name="name") String name, @RequestParam(name="email") String email, @RequestParam(name="postcode") String postcode)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setName(name);
        customer.setEmail(email);
        customer.setPostcode(postcode);
        customerRepository.save(customer);
        return "customer altered: name=" + customer.getName() + ", email=" + customer.getEmail() + ", postcode=" + customer.getPostcode();
    }
    
    @PostMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        String success = "deleted customer: id=" + customer.getId() + ", name=" + customer.getName() 
            + ", email=" + customer.getEmail() + ", postcode=" + customer.getPostcode();
        customerRepository.deleteById(id);
        if (customerRepository.existsById(id)) {
            return "customer not deleted!";
        } else {
            return success;
        }
    }
}