package mosesweb.AccountHelper;

import org.springframework.data.repository.CrudRepository;
import mosesweb.AccountHelper.Customer;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}