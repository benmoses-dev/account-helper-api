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
  
  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPostcode() {
      return postcode;
  }
  public void setPostcode(String postcode) {
      this.postcode = postcode;
  }
}