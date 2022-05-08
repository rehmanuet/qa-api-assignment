package validators;

import base.JsonPlaceholder;
import com.relevantcodes.extentreports.LogStatus;
import extent.ExtentTestManager;
import io.restassured.response.Response;
import utils.Constants;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UsersValidationTest extends ValidationBaseClass {

    @Test
    public void tc001_checkStatusCodeForUsers() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT+" StatusCode","Status Code Verification");
        Response response = JsonPlaceholder.getInstance().user.getUser.getUsers();
        validateResponseStatusCode(response,200);
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Endpoint: "+Constants.USERS_ENDPOINT+"Status Code: "+response_code);
    }

    @Test
    public void tc002_checkIfUserIdExist() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT+" CheckIfUserExist","User ID Verification");

        Integer id = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.VALID_USERNAME);
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Username: "+Constants.VALID_USERNAME+" Found ID: "+userId);
        Assert.assertNotEquals(id,0,"UserId not found");
    }

    @Test
    public void tc003_searchValidUser() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT+" SearchValidUser","Check if valid user is present");
        Integer id = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.VALID_USERNAME);
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Username: "+Constants.VALID_USERNAME+" Is Present: "+isUserPresent);
        Assert.assertNotEquals(id,0,"UserId not found");
    }

    @Test
    public void tc004_searchInvalidUser() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT+" SearchInvalidUser","Check response for invalid user");
        Integer id = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.INVALID_USERNAME);
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Username: "+Constants.INVALID_USERNAME+" Is Present: "+isUserPresent);
        Assert.assertEquals(id,0,"UserId found");
    }

    @Test
    public void tc005_schemaValidationUsers() {
        ExtentTestManager.startTest(Constants.USERS_ENDPOINT+" SchemaValidation","Schema Validation");
        RestAssured.get(Constants.BASE_URL + Constants.USERS_ENDPOINT).then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.USERS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}

