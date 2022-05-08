import Constants.Constants;
import pojo.CommentsPOJO;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import java.util.regex.Pattern;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CommentsValidationTest extends ValidationBaseClass {

    @Test
    public void tc010_checkStatusCodeForComments() {
        int response_code = getStatusCode(Constants.COMMENTS_ENDPOINT);
        System.out.printf("Endpoint: %s, Status Code: %s\n", Constants.COMMENTS_ENDPOINT, response_code);
        Assert.assertEquals(response_code, 200);
    }

    @Test
    public void tc011_checkCommentsForEachPost() {
        ArrayList<Integer> posts = getPosts(getUser(Constants.VALID_USERNAME));
        for (Integer post : posts) {
            CommentsPOJO[] comments = getComments(post);
            for (CommentsPOJO comment:comments){
                Assert.assertEquals(post, comment.getPostId());
            }
        }
    }

    @Test
    public void tc012_validateEmailForEachComment() {
        ArrayList<String> emails = getEmail(Constants.VALID_USERNAME);
        Pattern ptr = Pattern.compile(Constants.EMAIL_REGEX);
        if (emails.size() == 0) Assert.fail("No Email found");
        for (String email : emails) {
            Assert.assertTrue(ptr.matcher(email).matches());
        }
    }

    @Test
    public void tc013_validateBodyIsPresentForEachComment() {
        ArrayList<Integer> posts = getPosts(getUser(Constants.VALID_USERNAME));
        for (int post : posts) {
            CommentsPOJO[] comments = getComments(post);
            for (CommentsPOJO comment : comments) {
               Assert.assertNotNull(comment.getBody());
            }
        }
    }

    @Test
    public void tc014_schemaValidationComments() {
        RestAssured.get(Constants.BASE_URL + Constants.COMMENTS_ENDPOINT).then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.COMMENTS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}

