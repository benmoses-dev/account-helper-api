package mosesweb.AccountHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController
{
  
  @Autowired
  private SaleRepository saleRepository;
  @Autowired
  private CustomerRepository customerRepository;
  
  @PostMapping(path="/sales")
  public String addNewSale (@RequestParam Integer customerId, 
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, 
                            @RequestParam BigDecimal amount,
                            @RequestParam Integer invoiceNumber,
                            @RequestParam boolean isCash)
  {

    Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    Customer confirmedCustomer = (Customer) customer;
    Sale s = new Sale(confirmedCustomer.getId(), date, amount, invoiceNumber, isCash);
    saleRepository.save(s);
    return "Saved";
  }

  @GetMapping(path="/sales")
  public Iterable<Sale> getAllSales() {
    // This returns a JSON or XML with the users
    return saleRepository.findAll();
  }
}