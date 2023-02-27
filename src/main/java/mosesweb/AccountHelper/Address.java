package mosesweb.AccountHelper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    
    @OneToOne(mappedBy="address")
    @JsonIgnore
    private Customer customer;

    public Address()
    {
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
    
    public Customer getCustomer()
    {
        return customer;
    }
}