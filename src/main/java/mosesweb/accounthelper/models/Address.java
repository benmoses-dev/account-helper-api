package mosesweb.accounthelper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import java.util.Objects;

/**
 *
 * An address, associated with a customer. Has a house number, road name, and
 * postcode. Do not use in a hash based data structure as Address is mutable.
 *
 * @author Ben Moses
 */
@Entity
@Table(name = "address")
public class Address
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "house_number")
    @PositiveOrZero(message = "must not be negative")
    private Integer houseNumber;

    @Column(name = "road_name")
    @NotNull(message = "cannot be null")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "road name must only contain letters or whitespace")
    private String roadName;

    @Column(name = "postcode")
    @NotNull(message = "cannot be null")
    @Pattern(regexp = "[a-zA-Z]{2}[0-9]{2,3}[a-zA-Z]{2}", message = "postcode must only contain letters and numbers")
    private String postcode;

    public Address()
    {
        this(0, "", "");
    }

    public Address(Integer houseNumber, String roadName, String postcode)
    {
        this.houseNumber = houseNumber;
        this.roadName = roadName.strip().replaceAll("[\\s]{2,}", " ");
        this.postcode = postcode.strip().replaceAll("\\s", "");
    }

    /**
     *
     * @param houseNumber the house number of the address
     */
    public void setHouseNumber(Integer houseNumber)
    {
        this.houseNumber = houseNumber;
    }

    /**
     *
     * @param roadName the name of the road of the address
     */
    public void setRoadName(String roadName)
    {
        this.roadName = roadName.strip().replaceAll("[\\s]{2,}", " ");
    }

    /**
     *
     * @param postcode the new postcode of the address
     */
    public void setPostcode(String postcode)
    {
        this.postcode = postcode.strip().replaceAll("\\s", "");
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
    public Integer getHouseNumber()
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
        boolean result = (this.houseNumber != null && address.houseNumber != null);
        result = result && (this.postcode != null && address.postcode != null);
        result = result && (this.roadName != null && address.roadName != null);
        result = result && this.postcode.equals(address.postcode) && this.roadName.equals(address.roadName) && this.houseNumber.equals(address.houseNumber);
        return result && (this.id == null && address.id == null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.houseNumber);
        hash = 11 * hash + Objects.hashCode(this.roadName);
        hash = 11 * hash + Objects.hashCode(this.postcode);
        return hash;
    }
}
