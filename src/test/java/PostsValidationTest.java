import Constants.Constants;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostsValidationTest extends ValidationBaseClass {

    @Test
    public void tc006_checkStatusCodeForPosts() {
        int response_code = getStatusCode(Constants.POSTS_ENDPOINT);
        System.out.printf("Endpoint: %s, Status Code: %s\n", Constants.POSTS_ENDPOINT, response_code);
        Assert.assertEquals(response_code, 200);
    }

    @Test
    public void tc007_checkIfPostsExistForValidUser() {
        ArrayList<Integer> posts = getPosts(getUser(Constants.VALID_USERNAME));
        System.out.printf("Valid Username: %s, Number of Posts: %s\n", Constants.VALID_USERNAME, posts.size());
        Assert.assertNotNull(posts);
    }

    @Test
    public void tc008_checkIfPostsExistForInvalidUser() {
        ArrayList<Integer> posts = getPosts(getUser(Constants.INVALID_USERNAME));
        System.out.printf("Invalid Username: %s, Number of Posts: %s\n", Constants.INVALID_USERNAME, posts.size());
        Assert.assertEquals(posts.size(), 0);
    }

    @Test
    public void tc009_schemaValidationPosts() {
        RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT).then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.POSTS_ENDPOINT.replace("/", "") + "_schema.json"));
    }

}

