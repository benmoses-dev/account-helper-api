package mosesweb.AccountHelper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    private String name;
    private String email;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;
    
    @OneToMany(mappedBy="customer", fetch=FetchType.LAZY)
    private Collection<Receivable> receivables = new ArrayList<>();

    public Customer()
    {
    }
    
    public void setId(Integer id)
    {
        this.id = id;
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
     * @param address the new address of the customer
     */
    public void setAddress(Address address)
    {
        this.address = address;
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
     * @return the address of the Customer
     */
    public Address getAddress()
    {
      return address;
    }
    
    public Collection<Receivable> getReceivables()
    {
        return receivables;
    }
}