package mosesweb.accounthelper.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import mosesweb.accounthelper.exceptions.SaleNotFoundException;
import mosesweb.accounthelper.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * A REST Controller for managing client requests for Sale objects. Can get all
 * Sale objects in the system, as well as the details for any individual Sale.
 * Can add a Sale to the system. Can update a Sale that belongs to the system.
 * Can delete a Sale that belongs to the system.
 *
 * @author Ben Moses
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SaleView {

    @Autowired
    private SaleService saleService;

    /**
     *
     * Returns a collection of all Sale objects in the system.
     *
     * @return all Sale objects
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @GetMapping(value = "/sales/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllSales() throws JsonProcessingException
    {
        return saleService.getAllSales();
    }

    /**
     *
     * Returns the Sale object with the provided ID.Throws a RuntimeException if
     * the sale does not exist.
     *
     * @param id the unique id of the Sale
     * @return the Sale with the given id
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws mosesweb.accounthelper.exceptions.SaleNotFoundException
     */
    @GetMapping(value = "/sales/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSale(@PathVariable("id") Integer id) throws
            JsonProcessingException, SaleNotFoundException
    {
        return saleService.getSale(id);
    }
}
