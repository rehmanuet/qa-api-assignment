package pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersAddressPOJO {

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
