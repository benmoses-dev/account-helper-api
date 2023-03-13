package mosesweb.accounthelper.models;

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
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * A customer in the system. Has a name. Can be associated with an email and an
 * address.
 *
 * @author Ben Moses
 */
@Entity
@Table(name = "customer")
public class Customer
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "cannot be null")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "name must only contain letters or whitespace")
    private String name;

    @Column(name = "email")
    @NotNull(message = "cannot be null")
    @Email
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @NotNull(message = "cannot be null")
    private Address address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private final Collection<Receivable> receivables = new ArrayList<>();

    public Customer()
    {
        this("", "", 0, "", "");
    }

    public Customer(String name, String email, Integer houseNumber,
                    String roadName, String postcode)
    {
        this.name = name.strip().replaceAll("\\s\\s", " ");
        this.email = email;
        this.address = new Address(houseNumber, roadName, postcode);
    }

    public void setName(String name)
    {
        this.name = name.strip().replaceAll("\\s\\s", " ");
    }

    /**
     *
     * @param email the new email of the customer
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setHouseNumber(Integer houseNumber)
    {
        this.address.setHouseNumber(houseNumber);
    }

    public void setRoadName(String roadName)
    {
        this.address.setRoadName(roadName);
    }

    public void setPostcode(String postcode)
    {
        this.address.setPostcode(postcode);
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

    /**
     *
     * Get the sales ledger of this customer.
     *
     * @return a collection of all the receivables associated with this
     * customer.
     */
    public Collection<Receivable> getReceivables()
    {
        return receivables;
    }
}
