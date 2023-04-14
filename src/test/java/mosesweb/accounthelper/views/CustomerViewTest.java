package mosesweb.accounthelper.views;

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
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Ben Moses
 */
@WebMvcTest
class CustomerViewTest {

    @MockBean
    private SaleService mockSaleService;

    @MockBean
    private CustomerService mockCustomerService;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Customer bob = new Customer("bob", "bob@example.com", 1, "bobsroad", "bo12 3bb");
    private static final Customer alice = new Customer("alice", "alice@example.com", 2, "aliceroad", "al1 3ce");

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
        Collection<Customer> customers = new ArrayList<>();
        customers.add(bob);
        customers.add(alice);
        Mockito.when(mockCustomerService.getAllCustomers()).thenReturn(mapper.writeValueAsString(customers));
        mockMvc.perform(get("/customers/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}