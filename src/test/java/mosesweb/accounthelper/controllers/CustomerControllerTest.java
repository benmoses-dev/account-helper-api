package mosesweb.accounthelper.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Customer alice = new Customer("alice", "alice@example.com", 2, "aliceroad", "al1 3ce");

    @BeforeAll
    public static void configMapper()
    {
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @Test
    public void testAddNewCustomer() throws Exception
    {
        Mockito.when(mockCustomerService.addNewCustomer("alice", "alice@example.com", 2, "aliceroad", "al1 3ce"))
                .thenReturn(mapper.writeValueAsString(alice));
        Map<String, Object> request = new HashMap<>();
        request.put("name", "alice");
        request.put("email", "alice@example.com");
        request.put("houseNumber", "2");
        request.put("roadName", "aliceroad");
        request.put("postcode", "al1 3ce");
        mockMvc.perform(post("/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(mapper.writeValueAsString(alice), true));
    }

    @Test
    public void testAddNewCustomer2() throws Exception
    {
        Customer blank = new Customer();
        Mockito.when(mockCustomerService.addNewCustomer(null, null, null, null, null)).thenReturn(mapper.writeValueAsString(blank));
        mockMvc.perform(post("/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(mapper.writeValueAsString(blank), true));
    }
}
