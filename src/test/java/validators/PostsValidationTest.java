package validators;

import base.JsonPlaceholder;
import com.relevantcodes.extentreports.LogStatus;
import extent.ExtentTestManager;
import io.restassured.response.Response;
import lombok.extern.java.Log;
import pojos.PostsPOJO;
import pojos.UsersPOJO;
import utils.Constants;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostsValidationTest extends ValidationBaseClass {

    @Test
    public void tc006_checkStatusCodeForPosts() {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT + " StatusCode", "Status Code Verification");
        Response response = JsonPlaceholder.getInstance().post.getPost.getPosts();
        // ExtentTestManager.getTest().log(LogStatus.INFO, "Endpoint: "+Constants.POSTS_ENDPOINT+" Status Code: "+response_code);
        validateResponseStatusCode(response, 200);
    }

    @Test
    public void tc008_checkIfPostsExistForInvalidUser() {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT + " PostsForInvalidUser", "Check # of Post for invalid user");
        Response response = JsonPlaceholder.getInstance().user.getUser.getUsers();
        List<UsersPOJO> users = getListFromResponse(response, UsersPOJO.class);
        Integer userIds = users.stream().filter(a -> a.getUsername().equals(Constants.INVALID_USERNAME)).map(UsersPOJO::getId).findFirst().orElse(0);
        // Get postIds
        response = JsonPlaceholder.getInstance().post.getPost.getPostsByUserid(userIds);
        PostsPOJO pojo = getObjectFromResponsePath(response, "$", PostsPOJO.class);
        // ExtentTestManager.getTest().log(LogStatus.INFO, "Username: " + Constants.INVALID_USERNAME + " Number of Post : " + posts.size());

        Assert.assertNull(pojo.getId());
    }

    @Test
    public void tc009_schemaValidationPosts() throws ClassNotFoundException {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT + " SchemaValidation", "Schema Validation");
        Response response = JsonPlaceholder.getInstance().post.getPost.getPosts();
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.POSTS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}

