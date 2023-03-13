package mosesweb.accounthelper.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ben Moses
 */
public class CustomerTest
{

    private static String name;
    private static String email;

    public CustomerTest()
    {
    }

    @BeforeAll
    public static void setUpClass()
    {
        name = "Ben";
        email = "ben@example.com";
    }

    /**
     * Test of setName method, of class Customer.
     */
    @Test
    public void testSetName()
    {
        Customer instance = new Customer();
        instance.setName(name);
        assertEquals(name, instance.getName());
    }

    /**
     * Test of setEmail method, of class Customer.
     */
    @Test
    public void testSetEmail()
    {
        Customer instance = new Customer();
        instance.setEmail(email);
        assertEquals(email, instance.getEmail());
    }
}
