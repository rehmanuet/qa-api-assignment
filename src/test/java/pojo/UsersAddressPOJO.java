package pojo;

public class UsersAddressPOJO {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    UsersAddressGeoPOJO geo;

    public UsersAddressPOJO(String street, String suite, String city, String zipcode, UsersAddressGeoPOJO geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

    public UsersAddressPOJO() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public UsersAddressGeoPOJO getGeo() {
        return geo;
    }

    public void setGeo(UsersAddressGeoPOJO geo) {
        this.geo = geo;
    }


    public static class UsersAddressGeoPOJO {
        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public UsersAddressGeoPOJO(String lat, String lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public UsersAddressGeoPOJO() {

        }

        private String lat;
        private String lng;

    }
}
