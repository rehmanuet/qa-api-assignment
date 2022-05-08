package base.apiCalls.user;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.UsersPOJO;
import utils.Constants;

import java.util.List;

import static validators.ValidationBaseClass.getListFromResponse;

public class GetUser {

    public Response getUsers() {
        return RestAssured.get(Constants.BASE_URL + Constants.USERS_ENDPOINT);
    }

    public Integer getUserIdByName(String name) {
        Response response = getUsers();
        List<UsersPOJO> users = getListFromResponse(response, UsersPOJO.class);
        return users.stream().filter(a -> a.getUsername().equals(name)).map(UsersPOJO::getId).findFirst().orElse(0);
    }
}