package mosesweb.AccountHelper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;
    private String email;
    private String postcode;

    public Customer(){}
  
    public Customer(String name, String email, String postcode)
    {
        this.name = name;
        this.email = email;
        this.postcode = postcode;
    }
    
    /**
     *
     * @param name the new name of the customer
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     *
     * @param email the new email of the customer
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    /**
     *
     * @param postcode the new postcode of the customer
     */
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }
    
    /**
    *
    * @return the id of the Customer. null if not saved in the repository
    */
    public Integer getId()
    {
        return id;
    }

    /**
    *
    * @return the name of the Customer
    */
    public String getName()
    {
        return name;
    }
    
    /**
     *
     * @return the email of the Customer
     */
    public String getEmail()
    {
        return email;
    }
    
    /**
     *
     * @return the postcode of the Customer
     */
    public String getPostcode()
    {
      return postcode;
    }
  
    /**
     *
     * @param customer a Customer object with the desired name, email, and postcode. 
     * This Customer will update its name, email, and postcode to the same values as the provided customer
     */
    public void updateDetails(Customer customer)
    {
        setName(customer.getName());
        setEmail(customer.getEmail());
        setPostcode(customer.getPostcode());
    }
}