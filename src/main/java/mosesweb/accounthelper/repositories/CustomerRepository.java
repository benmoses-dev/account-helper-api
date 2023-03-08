package mosesweb.accounthelper.repositories;

import mosesweb.accounthelper.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    
}