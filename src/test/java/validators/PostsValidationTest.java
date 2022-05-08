package validators;

import com.relevantcodes.extentreports.LogStatus;
import extent.ExtentTestManager;
import utils.Constants;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostsValidationTest extends ValidationBaseClass {

    @Test
    public void tc006_checkStatusCodeForPosts() {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT+" StatusCode","Status Code Verification");
        int response_code = getStatusCode(Constants.POSTS_ENDPOINT);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Endpoint: "+Constants.POSTS_ENDPOINT+" Status Code: "+response_code);
        Assert.assertEquals(response_code, 200);
    }

    @Test
    public void tc007_checkIfPostsExistForValidUser() {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT+" PostsForValidUser","Check # of Post for valid user");
        ArrayList<Integer> posts = getPosts(getUser(Constants.VALID_USERNAME));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Username: "+Constants.VALID_USERNAME+" Number of Post : "+posts.size());
        Assert.assertNotNull(posts);
    }

    @Test
    public void tc008_checkIfPostsExistForInvalidUser() {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT+" PostsForInvalidUser","Check # of Post for invalid user");
        ArrayList<Integer> posts = getPosts(getUser(Constants.INVALID_USERNAME));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Username: "+Constants.INVALID_USERNAME+" Number of Post : "+posts.size());
        Assert.assertEquals(posts.size(), 0);
    }

    @Test
    public void tc009_schemaValidationPosts() {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT+" SchemaValidation","Schema Validation");
        RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT).then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.POSTS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}

