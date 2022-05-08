package validators;

import base.JsonPlaceholder;

import extent.ExtentTestManager;
import io.restassured.response.Response;
import pojos.PostsPOJO;
import pojos.UsersPOJO;
import utils.Constants;
import pojos.CommentsPOJO;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CommentsValidationTest extends ValidationBaseClass {

    @Test
    public void tc010_checkStatusCodeForComments() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " StatusCode", "Status Code Verification");
        Response response = JsonPlaceholder.getInstance().comment.getComment.getComments();
//        ExtentTestManager.getTest().log(LogStatus.INFO, "Endpoint: " + Constants.COMMENTS_ENDPOINT + " Status Code: " + response_code);
        validateResponseStatusCode(response, 200);

    }

    @Test
    public void tc011_checkCommentsForEachPost() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " CommentsForEachPost", "Validate the comment for each post");

        // Get Users
        Integer id = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.VALID_USERNAME);
        // Get all post
        Response response = JsonPlaceholder.getInstance().post.getPost.getPosts();
        List<PostsPOJO> posts = getListFromResponse(response, PostsPOJO.class);

        //TODO add assertion for 0 noth found
        //Get ids of post made by "Delphine"
        List<Integer> list = posts.stream().filter(a -> a.getUserId().equals(id)).map(PostsPOJO::getId).collect(Collectors.toList());
        for (Integer post : list) {
            // Get all comments for each post
            response = JsonPlaceholder.getInstance().comment.getComment.getCommentByPostId(post);
            List<CommentsPOJO> comments = getListFromResponse(response, CommentsPOJO.class);
            for (CommentsPOJO comment : comments) {
                Assert.assertEquals(post, comment.getPostId());
            }
        }
    }

    @Test
    public void tc012_validateEmailForEachComment() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " EmailForEachComment", "Validate the email for each comment");

        // Get Users
        Integer userId = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.VALID_USERNAME);
        // Get postIds
        Response response = JsonPlaceholder.getInstance().post.getPost.getPosts();
        List<PostsPOJO> posts = getListFromResponse(response, PostsPOJO.class);
        List<Integer> postIds = posts.stream().filter(a -> a.getUserId().equals(userId)).map(PostsPOJO::getId).collect(Collectors.toList());

        ArrayList<String> emails = new ArrayList<>();
        for (Integer id : postIds) {
            response = JsonPlaceholder.getInstance().comment.getComment.getCommentByPostId(id);
            List<CommentsPOJO> comments = getListFromResponse(response, CommentsPOJO.class);
            for (CommentsPOJO comment : comments) {
                emails.add(comment.getEmail());
            }
        }
        Pattern ptr = Pattern.compile(Constants.EMAIL_REGEX);
        if (emails.size() == 0) Assert.fail("No Email found");
        for (String email : emails) {
            Assert.assertTrue(ptr.matcher(email).matches());
        }
    }

    @Test
    public void tc013_validateBodyIsPresentForEachComment() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " BodyForEachComment", "Validate the body for each comment");
        // Get userId
        Integer userId = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(Constants.VALID_USERNAME);
        // Get posts
        Response response = JsonPlaceholder.getInstance().post.getPost.getPosts();
        List<PostsPOJO> posts = getListFromResponse(response, PostsPOJO.class);
        //Get ids of post made by "Delphine"
        List<Integer> list = posts.stream().filter(a -> a.getUserId().equals(userId)).map(PostsPOJO::getId).collect(Collectors.toList());

        for (int post : list) {
            response = JsonPlaceholder.getInstance().comment.getComment.getCommentByPostId(post);
            List<CommentsPOJO> comments = getListFromResponse(response, CommentsPOJO.class);
            for (CommentsPOJO comment : comments) {
                Assert.assertNotNull(comment.getBody());
            }
        }
    }

    @Test
    public void tc014_schemaValidationComments() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " SchemaValidation", "Schema Validation");
        Response response = JsonPlaceholder.getInstance().comment.getComment.getComments();
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.COMMENTS_ENDPOINT.replace("/", "") + "_schema.json"));
    }
}