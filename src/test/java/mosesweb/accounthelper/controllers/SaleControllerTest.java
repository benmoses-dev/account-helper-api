package mosesweb.accounthelper.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import mosesweb.accounthelper.models.Sale;
import mosesweb.accounthelper.services.CustomerService;
import mosesweb.accounthelper.services.SaleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Ben Moses
 */
@WebMvcTest
public class SaleControllerTest
{
    
    @MockBean
    private SaleService mockService;
    
    @MockBean
    private CustomerService mockCustomerService;
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    private MockMvc mockMvc;
    
    @BeforeAll
    public static void configMapper()
    {

        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }
    
    @Test
    public void testGetSale() throws Exception
    {
        Sale sale = new Sale(BigDecimal.valueOf(55.65), LocalDate.of(2020, Month.MARCH, 2), true);
        Mockito.when(mockService.getSale(ArgumentMatchers.anyInt())).thenReturn(sale);
        mockMvc.perform(
                get("/sales/1/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(mapper.writeValueAsString(sale)));
    }
}
