package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersPOJO {

    private Integer id;
    private String name;
    private String username;
    private String email;
    UsersAddressPOJO address;
    private String phone;
    private String website;
    UsersCompanyPOJO company;

    public UsersPOJO(Integer id, String name, String username, String email, UsersAddressPOJO address, String phone, String website, UsersCompanyPOJO company) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    public UsersPOJO() {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsersAddressPOJO getAddress() {
        return address;
    }

    public void setAddress(UsersAddressPOJO address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public UsersCompanyPOJO getCompany() {
        return company;
    }

    public void setCompany(UsersCompanyPOJO company) {
        this.company = company;
    }


}


