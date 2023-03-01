package mosesweb.accounthelper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * An address, associated with a customer.
 * Has a house number, road name, and postcode.
 * Do not use in a hash based data structure as Address is mutable.
 * 
 * @author Ben Moses
 */
@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    private int houseNumber;
    private String roadName;
    private String postcode;

    public Address()
    {
    }
    
    public Address(int houseNumber, String roadName, String postcode)
    {
        this.houseNumber = houseNumber;
        this.roadName = roadName;
        this.postcode = postcode;
    }
    
    /**
     *
     * @param houseNumber the house number of the address
     */
    public void setHouseNumber(int houseNumber)
    {
        this.houseNumber = houseNumber;
    }
    
    /**
     *
     * @param roadName the name of the road of the address
     */
    public void setRoadName(String roadName)
    {
        this.roadName = roadName;
    }
    
    /**
     *
     * @param postcode the new postcode of the address
     */
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }
    
    /**
    *
    * @return the id of the Address. null if not saved in the database
    */
    public Integer getId()
    {
        return id;
    }

    /**
    *
    * @return the house number of the Address
    */
    public int getHouseNumber()
    {
        return houseNumber;
    }
    
    /**
     *
     * @return the name of the road of the Address
     */
    public String getRoadName()
    {
        return roadName;
    }
    
    /**
     *
     * @return the postcode of the Address
     */
    public String getPostcode()
    {
      return postcode;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        if ((this.id != null && address.id != null) && this.id.equals(address.id)) {
            return true;
        }
        boolean result = (this.houseNumber != 0 && this.houseNumber == address.houseNumber);
        result = result && (this.postcode != null && address.postcode != null);
        result = result && (this.roadName != null && address.roadName != null);
        result = result && this.postcode.equals(address.postcode) && this.roadName.equals(address.roadName);
        return result && (this.id == null && address.id == null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + this.houseNumber;
        hash = 11 * hash + Objects.hashCode(this.roadName);
        hash = 11 * hash + Objects.hashCode(this.postcode);
        return hash;
    }
}