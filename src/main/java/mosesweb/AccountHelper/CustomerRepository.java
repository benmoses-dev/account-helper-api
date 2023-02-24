package mosesweb.AccountHelper;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called customerRepository
// CRUD refers Create, Read, Update, Delete

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}