package validators;

import base.JsonPlaceholder;
import extent.ExtentTestManager;
import io.restassured.response.Response;
import utils.Constants;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UsersTest extends BaseValidation {

    @Test
    public void tc001_checkStatusCodeForUsers() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT + " StatusCode", "Status Code Verification");
        Response response = JsonPlaceholder.getInstance().user.getUser.getUsers();
        validateResponseStatusCode(response, Constants.USERS_ENDPOINT, 200);
    }

    @Test
    public void tc002_checkIfUserIdExist() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT + " CheckIfUserExist", "User ID Verification");
        int id = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.VALID_USERNAME);
        Assert.assertNotEquals(id, 0, "UserId not found");
    }

    @Test
    public void tc003_searchValidUser() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT + " SearchValidUser", "Check if valid user is present");
        Integer id = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.VALID_USERNAME);
        Assert.assertNotEquals(id, 0, "UserId not found");
    }

    @Test
    public void tc004_searchInvalidUser() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT + " SearchInvalidUser", "Check response for invalid user");
        Integer id = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.INVALID_USERNAME);
        Assert.assertEquals(id, 0, "UserId found");
    }

    @Test
    public void tc005_schemaValidationUsers() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT + " SchemaValidation", "Schema Validation");
        RestAssured.get(Constants.BASE_URL + Constants.USERS_ENDPOINT).then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.USERS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}

