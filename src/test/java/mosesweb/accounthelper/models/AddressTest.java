package mosesweb.accounthelper.models;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Ben Moses
 */
public class AddressTest
{

    public AddressTest()
    {
    }

    /**
     * Test of setHouseNumber method, of class Address.
     */
    @Test
    public void testSetHouseNumber()
    {
        Integer houseNumber = 2;
        Address instance = new Address();
        instance.setHouseNumber(houseNumber);
        assert (instance.getHouseNumber().equals(houseNumber));
    }

    /**
     * Test of setRoadName method, of class Address.
     */
    @Test
    public void testSetRoadName()
    {
        String roadName = "london road";
        Address instance = new Address();
        instance.setRoadName(roadName);
        assert (instance.getRoadName().equals(roadName));
    }

    /**
     * Test of setPostcode method, of class Address.
     */
    @Test
    public void testSetPostcode()
    {
        String postcode = "ln19bh";
        Address instance = new Address();
        instance.setPostcode(postcode);
        assert (instance.getPostcode().equals(postcode));
    }
}
