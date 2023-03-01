package mosesweb.accounthelper;

import mosesweb.accounthelper.models.Sale;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepository extends CrudRepository<Sale, Integer> {

}