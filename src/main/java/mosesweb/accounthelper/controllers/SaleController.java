package mosesweb.accounthelper.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import mosesweb.accounthelper.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class SaleController
{

    @Autowired
    private SaleService saleService;

    // ***** PRESENTER *****
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
     */
    @GetMapping(value = "/sales/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSale(@PathVariable("id") Integer id) throws JsonProcessingException
    {
        return saleService.getSale(id);
    }

    // ***** CONTROLLER *****
    /**
     *
     * Adds a new sale to the system.If the sale is a cash sale, a BankDebit
     * will be created and attached to the sale. Otherwise, a Receivable will be
     * created and attached to the sale: a customer ID and a unique invoice
     * number will need to be provided alongside the sale; both can be omitted
     * if the sale is cash. The date must be formatted as yyyy-MM-dd.
     *
     * @param saleWrapper amount, date, isCash, invoiceNumber, and customerId to
     * add. Set cash to true to create a cash sale.
     * @return the Sale that has been added.
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @PostMapping(value = "/sales/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addNewSale(@RequestBody ObjectNode saleWrapper) throws JsonProcessingException
    {
        // Extract request json
        JsonNode amountNode = saleWrapper.get("amount");
        JsonNode dateNode = saleWrapper.get("date");
        JsonNode cashNode = saleWrapper.get("cash");
        JsonNode invoiceNumberNode = saleWrapper.get("invoiceNumber");
        JsonNode customerIdNode = saleWrapper.get("customerId");

        // Initialise needed parameters for sale
        BigDecimal amount = null;
        LocalDate date = null;
        boolean cash = false;
        Integer invoiceNumber = null;
        Integer customerId = null;

        // Validate http request json and parse json to java values if they are present
        if (amountNode != null) {
            if (amountNode.isNumber()) {
                amount = amountNode.decimalValue();
            }
            else {
                amount = BigDecimal.valueOf((amountNode.asDouble()));
            }
        }
        if (dateNode != null) {
            date = LocalDate.parse(dateNode.asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (cashNode != null) {
            cash = cashNode.asBoolean();
        }
        if (invoiceNumberNode != null) {
            invoiceNumber = invoiceNumberNode.asInt();
        }
        if (customerIdNode != null) {
            customerId = customerIdNode.asInt();
        }

        // Pass the values to the sale service to validate and create the sale
        return saleService.addNewSale(amount, date, cash, invoiceNumber, customerId);
    }

    /**
     *
     * Helper method for development. Should a sale be deleted in a production
     * system?
     *
     * @param id the id of the sale to delete.
     * @return success if the operation was successful.
     */
    @DeleteMapping(value = "/sales/{id}/")
    public String deleteSale(@PathVariable("id") Integer id)
    {
        return saleService.deleteSale(id);
    }

    /**
     *
     * Helper method for development. Deletes all sales.
     *
     * @return success if successful.
     */
    @DeleteMapping(value = "/sales/all/")
    public String deleteAllSales()
    {
        return saleService.deleteAllSales();
    }
}
