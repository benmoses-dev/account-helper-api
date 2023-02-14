package mosesweb.AccountHelper;

import org.springframework.data.repository.CrudRepository;
import mosesweb.AccountHelper.Sale;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface SaleRepository extends CrudRepository<Sale, Integer> {

}