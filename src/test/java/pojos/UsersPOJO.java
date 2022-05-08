package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersPOJO {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    UsersAddressPOJO address;
    UsersCompanyPOJO company;
}


