package base.apiCalls.user;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.UserPOJO;
import utils.Constants;

import java.util.List;

import static validators.BaseValidation.getListFromResponse;

public class GetUser {

    /**
     * To retrieve all the users
     */
    public Response getUsers() {
        return RestAssured.get(Constants.BASE_URL + Constants.USERS_ENDPOINT);
    }

    /**
     * To retrieve userId to given username
     *
     * @param userName, username of user
     */
    public int getUserIdByName(String userName) {
        Response response = getUsers();
        List<UserPOJO> users = getListFromResponse(response, UserPOJO.class);
        return users.stream().filter(a -> a.getUsername().equals(userName)).map(UserPOJO::getId).findFirst().orElse(0);
    }
}