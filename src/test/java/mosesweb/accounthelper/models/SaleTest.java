package mosesweb.accounthelper.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ben Moses
 */
public class SaleTest
{
    
    public SaleTest()
    {
    }
    
    private static LocalDate date;
    private static BigDecimal amount;
    
    @BeforeAll
    public static void setUpClass()
    {
        date = LocalDate.parse("2018-01-25");
        amount = BigDecimal.valueOf(22.66);
    }

    /**
     * Test of createCashSale method, of class Sale.
     */
    @Test
    public void testCreateCashSale()
    {
        Sale sale = new Sale(amount, date);
        Sale cashSale = sale.createCashSale();
        assertEquals(amount, cashSale.getAmount());
        assertEquals(date, cashSale.getDate());
        assertEquals(true, cashSale.getIsCash());
        assertEquals(amount, cashSale.getBankDebit().getAmount());
        assertEquals(date, cashSale.getBankDebit().getDate());
        assertEquals(null, cashSale.getReceivable());
    }

    /**
     * Test of createCreditSale method, of class Sale.
     */
    @Test
    public void testCreateCreditSale()
    {
        Sale sale = new Sale(amount, date);
        Customer c = new Customer();
        c.setEmail("ben@example.com");
        c.setName("ben");
        Address address = new Address();
        address.setHouseNumber(2);
        address.setPostcode("ln10bh");
        address.setRoadName("london road");
        c.setAddress(address);
        Integer invoiceNumber = 1294;
        Sale creditSale = sale.createCreditSale(invoiceNumber, c);
        assertEquals(amount, creditSale.getAmount());
        assertEquals(date, creditSale.getDate());
        assertEquals(false, creditSale.getIsCash());
        assertEquals(amount, creditSale.getReceivable().getAmount());
        assertEquals(date, creditSale.getReceivable().getDate());
        assertEquals(null, creditSale.getBankDebit());
        assertEquals(c, creditSale.getReceivable().getCustomer());
        assertEquals(invoiceNumber, creditSale.getReceivable().getInvoiceNumber());
    }

    /**
     * Test of invoiceNumberMatches method, of class Sale.
     */
    @Test
    public void testInvoiceNumberMatchesTrue()
    {
        Sale sale = new Sale(amount, date);
        Customer c = new Customer();
        c.setEmail("ben@example.com");
        c.setName("ben");
        Address address = new Address();
        address.setHouseNumber(2);
        address.setPostcode("ln10bh");
        address.setRoadName("london road");
        c.setAddress(address);
        Integer invoiceNumber = 3884;
        Sale creditSale = sale.createCreditSale(invoiceNumber, c);
        boolean result = creditSale.invoiceNumberMatches(3884);
        assertEquals(true, result);
    }
    
    /**
     * Test of invoiceNumberMatches method, of class Sale.
     */
    @Test
    public void testInvoiceNumberMatchesFalse()
    {
        Sale sale = new Sale(amount, date);
        Customer c = new Customer();
        c.setEmail("ben@example.com");
        c.setName("ben");
        Address address = new Address();
        address.setHouseNumber(2);
        address.setPostcode("ln10bh");
        address.setRoadName("london road");
        c.setAddress(address);
        Integer invoiceNumber = 3884;
        Sale creditSale = sale.createCreditSale(invoiceNumber, c);
        boolean result = creditSale.invoiceNumberMatches(1234);
        assertEquals(false, result);
    }
}
