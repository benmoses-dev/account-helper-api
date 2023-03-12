package mosesweb.accounthelper.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import mosesweb.accounthelper.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * A REST Controller for managing client requests for Customer objects. Can get
 * all Customer objects in the system, as well as the details for any individual
 * Customer. Can add a Customer to the system. Can update a Customer that
 * belongs to the system. Can delete a Customer that belongs to the system.
 *
 * @author Ben Moses
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController
{

    @Autowired
    private CustomerService customerService;

    // ***** PRESENTER *****
    /**
     *
     * Returns a collection of all Customer objects in the system.
     *
     * @return a collection of all customers
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @GetMapping(value = "/customers/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllCustomers() throws JsonProcessingException
    {
        return customerService.getAllCustomers();
    }

    /**
     *
     * Returns the Customer with the provided ID.
     *
     * @param id the unique customer id
     * @return the Customer with the given id if found, otherwise 404 error
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @GetMapping(value = "/customers/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomer(@PathVariable("id") Integer id) throws JsonProcessingException
    {
        return customerService.getCustomer(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/customers/{id}/ledger/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomerLedger(@PathVariable("id") Integer id) throws JsonProcessingException
    {
        return customerService.getCustomerLedger(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/customers/{id}/address/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomerAddress(@PathVariable("id") Integer id) throws JsonProcessingException
    {
        return customerService.getCustomerAddress(id);
    }

    // ***** CONTROLLER *****
    /**
     *
     * Add a new Customer to the system.Throws a RuntimeException if the
     * Customer name is null.
     *
     * @param customer the Customer object to create
     * @return the Customer object that was created
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @PostMapping(value = "/customers/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addNewCustomer(
            @RequestBody(required = false) ObjectNode customer) throws JsonProcessingException
    {
        JsonNode nameNode = null;
        JsonNode emailNode = null;
        JsonNode houseNumberNode = null;
        JsonNode roadNameNode = null;
        JsonNode postcodeNode = null;

        if (customer != null) {
            // Extract request json
            nameNode = customer.get("name");
            emailNode = customer.get("email");
            houseNumberNode = customer.get("houseNumber");
            roadNameNode = customer.get("roadName");
            postcodeNode = customer.get("postcode");
        }

        // Initialise needed parameters for sale
        String name = null;
        String email = null;
        Integer houseNumber = null;
        String roadName = null;
        String postcode = null;

        // Validate http request json and parse json to java values if they are present
        if (nameNode != null) {
            name = nameNode.asText();
        }
        if (emailNode != null) {
            email = emailNode.asText();
        }
        if (houseNumberNode != null) {
            houseNumber = houseNumberNode.asInt();
        }
        if (roadNameNode != null) {
            roadName = roadNameNode.asText();
        }
        if (postcodeNode != null) {
            postcode = postcodeNode.asText();
        }

        // Pass the values to the sale service to validate and create the sale
        return customerService.addNewCustomer(name, email, houseNumber, roadName, postcode);
    }

    /**
     *
     * Edit an existing Customer.Throws a RuntimeException if the Customer does
     * not exist in the system. The Customer provided must have the correct ID.
     *
     * @param id the ID of the Customer to edit
     * @param customer the Customer object with the new details
     * @return String representing the new customer details
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @PutMapping(value = "/customers/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String editCustomer(@PathVariable("id") Integer id,
                               @RequestBody ObjectNode customer) throws JsonProcessingException
    {
        // Extract request json
        JsonNode nameNode = customer.get("name");
        JsonNode emailNode = customer.get("email");
        JsonNode houseNumberNode = customer.get("houseNumber");
        JsonNode roadNameNode = customer.get("roadName");
        JsonNode postcodeNode = customer.get("postcode");

        // Initialise needed parameters for sale
        String name = null;
        String email = null;
        Integer houseNumber = null;
        String roadName = null;
        String postcode = null;

        // Validate http request json and parse json to java values if they are present
        if (nameNode != null) {
            name = nameNode.asText();
        }
        if (emailNode != null) {
            email = emailNode.asText();
        }
        if (houseNumberNode != null) {
            houseNumber = houseNumberNode.asInt();
        }
        if (roadNameNode != null) {
            roadName = roadNameNode.asText();
        }
        if (postcodeNode != null) {
            postcode = postcodeNode.asText();
        }
        return customerService.editCustomer(id, name, email, houseNumber, roadName, postcode);
    }

    /**
     *
     * Delete a Customer from the system with the provided ID. Throws a
     * RuntimeException if the Customer does not exist.
     *
     * @param id the ID of the Customer to delete
     * @return a String representing whether the customer was successfully
     * deleted
     */
    @DeleteMapping("/customers/{id}/")
    public String deleteCustomer(@PathVariable("id") Integer id)
    {
        return customerService.deleteCustomer(id);
    }
}
