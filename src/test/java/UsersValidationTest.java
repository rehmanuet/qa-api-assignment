import Constants.Constants;

import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UsersValidationTest extends ValidationBaseClass {


    @Test
    public void tc001_checkStatusCodeForUsers() {
        int response_code = getStatusCode(Constants.USERS_ENDPOINT);
        System.out.printf("Endpoint: %s, Status Code: %s\n",Constants.USERS_ENDPOINT,response_code);
        Assert.assertEquals(response_code, 200);
    }

    @Test
    public void tc002_checkIfUserIdExist() {
        int userId = getUser(Constants.VALID_USERNAME);
        System.out.printf("Username: %s, Found ID: %s\n",Constants.VALID_USERNAME,userId);
        Assert.assertNotNull(userId);
    }

    @Test
    public void tc003_searchValidUser() {
        boolean isUserPresent = getUsername(Constants.VALID_USERNAME);
        System.out.printf("Username: %s, Status: %s\n", Constants.VALID_USERNAME, isUserPresent);
        Assert.assertTrue(isUserPresent);
    }

    @Test
    public void tc004_searchInvalidUser() {
        boolean isUserPresent = getUsername(Constants.INVALID_USERNAME);
        System.out.printf("Username: %s, Status: %s\n", Constants.INVALID_USERNAME, isUserPresent);
        Assert.assertFalse(isUserPresent);
    }

    @Test
    public void tc005_schemaValidationUsers() {
        RestAssured.get(Constants.BASE_URL + Constants.USERS_ENDPOINT).then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.USERS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}

