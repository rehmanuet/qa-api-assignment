package validators;

import base.JsonPlaceholder;
import extent.ExtentTestManager;
import io.restassured.response.Response;
import pojos.PostPOJO;
import pojos.UserPOJO;
import utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostsTest extends BaseValidation {

    @Test
    public void tc006_checkStatusCodeForPosts() {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT + " StatusCode", "Status Code Verification");
        Response response = JsonPlaceholder.getInstance().post.getPost.getAllPosts();
        validateResponseStatusCode(response, Constants.POSTS_ENDPOINT, 200);
    }

    @Test
    public void tc007_checkIfPostsExistForInvalidUser() {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT + " PostsForInvalidUser", "Check # of Post for invalid user");

        Response response = JsonPlaceholder.getInstance().user.getUser.getUsers();
        List<UserPOJO> users = getListFromResponse(response, UserPOJO.class);
        Assert.assertNotEquals(users.size(), 0, "User not found"); // Hard assertion
        // Get userId of invalidUser
        int userId = users.stream().filter(a -> a.getUsername().equals(Constants.INVALID_USERNAME)).map(UserPOJO::getId).findFirst().orElse(0);
        softAssert.get().assertEquals(userId, 0, "User is found"); // Hard assertion
        // Get postIds
        response = JsonPlaceholder.getInstance().post.getPost.getPostsByUserid(userId);
        PostPOJO pojo = getObjectFromResponsePath(response, "$", PostPOJO.class);
        softAssert.get().assertNull(pojo.getId());
        skipRetryAndSoftAssert();
    }

    @Test
    public void tc008_schemaValidationPosts() throws ClassNotFoundException {
        ExtentTestManager.startTest(Constants.POSTS_ENDPOINT + " SchemaValidation", "Schema Validation");
        Response response = JsonPlaceholder.getInstance().post.getPost.getAllPosts();
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.POSTS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}

