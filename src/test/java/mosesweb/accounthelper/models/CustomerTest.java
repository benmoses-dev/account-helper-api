package mosesweb.accounthelper.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author Ben Moses
 */
public class CustomerTest
{
    
    private static String name;
    private static String email;
    private static Address address;
    
    public CustomerTest()
    {
    }
    
    @BeforeAll
    public static void setUpClass()
    {
        name = "Ben";
        email = "ben@example.com";
        Address address = new Address();
        address.setHouseNumber(13);
        address.setPostcode("bh228jn");
        address.setRoadName("london road");
        address = address;
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

    /**
     * Test of setAddress method, of class Customer.
     */
    @Test
    public void testSetAddress()
    {
        Customer instance = new Customer();
        instance.setAddress(address);
        assertEquals(address, instance.getAddress());
    }    
}
