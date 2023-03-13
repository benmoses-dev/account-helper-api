package mosesweb.accounthelper.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ben Moses
 */
public class SaleTest
{

    private static LocalDate date;
    private static BigDecimal amount;
    private static Customer c;
    private static Integer invoiceNumber;

    public SaleTest()
    {
    }

    @BeforeAll
    public static void setUpClass()
    {
        date = LocalDate.parse("2018-01-25");
        amount = BigDecimal.valueOf(22.66);
        c = new Customer();
        c.setEmail("ben@example.com");
        c.setName("ben");
        c.setHouseNumber(2);
        c.setPostcode("ln10bh");
        c.setRoadName("london road");
        invoiceNumber = 3884;
    }

    /**
     * Test of invoiceNumberMatches method, of class Sale.
     */
    @Test
    public void testInvoiceNumberMatchesTrue()
    {
        Sale sale = new Sale(amount, date, false, invoiceNumber, c);
        boolean result = sale.invoiceNumberMatches(invoiceNumber);
        assertEquals(true, result);
    }

    /**
     * Test of invoiceNumberMatches method, of class Sale.
     */
    @Test
    public void testInvoiceNumberMatchesFalse1()
    {
        Sale sale = new Sale(amount, date, false, invoiceNumber, c);
        boolean result = sale.invoiceNumberMatches(1234);
        assertEquals(false, result);
    }

    /**
     * Test of invoiceNumberMatches method, of class Sale.
     */
    @Test
    public void testInvoiceNumberMatchesFalse2()
    {
        Sale sale = new Sale(amount, date, true, invoiceNumber, c);
        boolean result = sale.invoiceNumberMatches(invoiceNumber);
        assertEquals(false, result);
    }
}
