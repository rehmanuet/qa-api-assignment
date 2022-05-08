package validators;

import com.relevantcodes.extentreports.LogStatus;
import extent.ExtentTestManager;
import utils.Constants;
import pojos.CommentsPOJO;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.regex.Pattern;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CommentsValidationTest extends ValidationBaseClass {

    @Test
    public void tc010_checkStatusCodeForComments() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT+" StatusCode","Status Code Verification");
        int response_code = getStatusCode(Constants.COMMENTS_ENDPOINT);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Endpoint: "+Constants.COMMENTS_ENDPOINT+" Status Code: "+response_code);
        Assert.assertEquals(response_code, 200);
    }

    @Test
    public void tc011_checkCommentsForEachPost() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT+" CommentsForEachPost","Validate the comment for each post");
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
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT+" EmailForEachComment","Validate the email for each comment");
        ArrayList<String> emails = getEmail(Constants.VALID_USERNAME);
        Pattern ptr = Pattern.compile(Constants.EMAIL_REGEX);
        if (emails.size() == 0) Assert.fail("No Email found");
        for (String email : emails) {
            Assert.assertTrue(ptr.matcher(email).matches());
        }
    }

    @Test
    public void tc013_validateBodyIsPresentForEachComment() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT+" BodyForEachComment","Validate the body for each comment");
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
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT+" SchemaValidation","Schema Validation");
        RestAssured.get(Constants.BASE_URL + Constants.COMMENTS_ENDPOINT).then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.COMMENTS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}

