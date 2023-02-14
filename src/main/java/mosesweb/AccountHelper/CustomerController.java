package mosesweb.AccountHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController
{
  
  @Autowired
  private CustomerRepository customerRepository;

  @PostMapping(path="/customers") // Map ONLY POST Requests
  public String addNewCustomer (@RequestParam String name, 
                                @RequestParam String email, 
                                @RequestParam String address)
  {
    
    Customer c = new Customer(name, email, address);
    customerRepository.save(c);
    return "Saved";
  }

  @GetMapping(path="/customers")
  public @ResponseBody Iterable<Customer> getAllCustomers() {
    // This returns a JSON or XML with the users
    return customerRepository.findAll();
  }
}