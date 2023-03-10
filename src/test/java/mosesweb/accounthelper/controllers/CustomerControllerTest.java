package mosesweb.accounthelper.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import mosesweb.accounthelper.models.Address;
import mosesweb.accounthelper.models.Customer;
import mosesweb.accounthelper.services.CustomerService;
import mosesweb.accounthelper.services.SaleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Ben Moses
 */
@WebMvcTest
public class CustomerControllerTest
{

    @MockBean
    private SaleService mockSaleService;

    @MockBean
    private CustomerService mockCustomerService;

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void configMapper()
    {

        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @Test
    public void testGetAllCustomers() throws Exception
    {
        Customer customer1 = new Customer();
        customer1.setName("bob");
        customer1.setEmail("bob@example.com");
        customer1.setAddress(new Address(1, "bobsroad", "bo12 3bb"));
        Customer customer2 = new Customer();
        customer1.setName("alice");
        customer1.setEmail("alice@example.com");
        customer1.setAddress(new Address(2, "aliceroad", "al1 3ce"));
        Collection<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        Iterable<Customer> returnedCustomers = customers;
        Mockito.when(mockCustomerService.getAllCustomers()).thenReturn(returnedCustomers);
        mockMvc.perform(get("/customers/"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().json(mapper.writeValueAsString(returnedCustomers), true));
    }
}
