package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPOJO {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    UsersAddressPOJO address;
    UsersCompanyPOJO company;

    @Getter
    @Setter
    public static class UsersCompanyPOJO {
        private String name;
        private String catchPhrase;
        private String bs;
    }

    @Getter
    @Setter
    public static class UsersAddressPOJO {

        private String street;
        private String suite;
        private String city;
        private String zipcode;
        UsersAddressGeoPOJO geo;

        @Getter
        @Setter
        public static class UsersAddressGeoPOJO {
            private String lat;
            private String lng;
        }
    }
}


