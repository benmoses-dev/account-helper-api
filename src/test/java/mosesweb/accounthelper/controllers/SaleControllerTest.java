package mosesweb.accounthelper.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mosesweb.accounthelper.models.Customer;
import mosesweb.accounthelper.models.Sale;
import mosesweb.accounthelper.services.CustomerService;
import mosesweb.accounthelper.services.SaleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.*;
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
public class SaleControllerTest
{

    @MockBean
    private SaleService mockSaleService;

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
        Mockito.when(mockSaleService.getSale(anyInt())).thenReturn(sale);
        mockMvc.perform(
                    get("/sales/1/"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().json(mapper.writeValueAsString(sale), true));
    }

    @Test
    public void testGetAllSales() throws Exception
    {
        Sale firstSale = new Sale(BigDecimal.valueOf(55.65), LocalDate.of(2020, Month.MARCH, 2), true);
        Customer customer = new Customer();
        Sale secondSale = new Sale(BigDecimal.valueOf(33.33), LocalDate.of(2022, Month.DECEMBER, 17), false, 1, customer);
        Collection<Sale> sales = new ArrayList<>();
        sales.add(firstSale);
        sales.add(secondSale);
        Iterable<Sale> returnedSales = sales;
        Mockito.when(mockSaleService.getAllSales()).thenReturn(returnedSales);
        mockMvc.perform(get("/sales/"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().json(mapper.writeValueAsString(returnedSales), true));
    }

    @Test
    public void testAddNewSale() throws Exception
    {
        Sale sale = new Sale(BigDecimal.valueOf(55.65), LocalDate.of(2020, Month.MARCH, 2), true);
        Mockito.when(mockSaleService.addNewSale(BigDecimal.valueOf(55.65), LocalDate.of(2020, Month.MARCH, 2), true, null, null)).thenReturn(sale);
        Map<String, Object> request = new HashMap<>();
        request.put("amount", 55.65);
        request.put("date", "2020-03-02");
        request.put("cash", true);
        mockMvc.perform(post("/sales/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().json(mapper.writeValueAsString(sale), true));
    }
}
