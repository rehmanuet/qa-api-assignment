package validators;

import base.JsonPlaceholder;

import extent.ExtentTestManager;
import io.restassured.response.Response;
import pojos.PostPOJO;
import utils.Constants;
import pojos.CommentPOJO;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CommentsTest extends BaseValidation {

    @Test
    public void tc09_checkStatusCodeForComments() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " StatusCode", "Status Code Verification");
        Response response = JsonPlaceholder.getInstance().comment.getComment.getAllComments();
        validateResponseStatusCode(response, Constants.COMMENTS_ENDPOINT, 200);
    }

    @Test
    public void tc010_checkCommentsForEachPost() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " CommentsForEachPost", "Validate the comment for each post");
        // Get postIds of post made by username "Delphine"
        List<Integer> postIds = getAllPostIdByUsername(Constants.VALID_USERNAME);
        for (Integer post : postIds) {
            // Get all comments for each post
            Response response = JsonPlaceholder.getInstance().comment.getComment.getCommentByPostId(post);
            List<CommentPOJO> comments = getListFromResponse(response, CommentPOJO.class);
            for (CommentPOJO comment : comments) {
                Assert.assertEquals(post, comment.getPostId());
            }
        }
        skipRetryAndSoftAssert();
    }

    @Test
    public void tc011_validateEmailForEachComment() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " EmailForEachComment", "Validate the email for each comment");
        ArrayList<String> emails = new ArrayList<>();
        // Get postIds of post made by username "Delphine"
        List<Integer> postIds = getAllPostIdByUsername(Constants.VALID_USERNAME);
        for (Integer id : postIds) {
            Response response = JsonPlaceholder.getInstance().comment.getComment.getCommentByPostId(id);
            List<CommentPOJO> comments = getListFromResponse(response, CommentPOJO.class);
            validateList(comments.size(), "Comments not found");
            for (CommentPOJO comment : comments) {
                emails.add(comment.getEmail());
            }
        }
        Pattern ptr = Pattern.compile(Constants.EMAIL_REGEX);
        validateList(emails.size(), "No Email found");
        for (String email : emails) {
            softAssert.get().assertTrue(ptr.matcher(email).matches(), "Email format incorrect'");
        }
        skipRetryAndSoftAssert();
    }

    @Test
    public void tc012_validateBodyIsPresentForEachComment() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " BodyForEachComment", "Validate the body for each comment");
        // Get postIds of post made by username "Delphine"

        List<Integer> postIds = getAllPostIdByUsername(Constants.VALID_USERNAME);
        for (int post : postIds) {
            Response response = JsonPlaceholder.getInstance().comment.getComment.getCommentByPostId(post);
            List<CommentPOJO> comments = getListFromResponse(response, CommentPOJO.class);
            validateList(comments.size(), "Comments not found");
            for (CommentPOJO comment : comments) {
                softAssert.get().assertNotNull(comment.getBody(), String.format("Body is null Id: %s and postId: %s", comment.getId(), post));
            }
        }
        skipRetryAndSoftAssert();
    }

    @Test
    public void tc013_schemaValidationComments() {
        ExtentTestManager.startTest(Constants.COMMENTS_ENDPOINT + " SchemaValidation", "Schema Validation");
        Response response = JsonPlaceholder.getInstance().comment.getComment.getAllComments();
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath(Constants.COMMENTS_ENDPOINT.replace("/", "") + "_schema.json"));
    }

    public List<Integer> getAllPostIdByUsername(String userName) {
        // Get userId of "Delphine"
        int userId = JsonPlaceholder.getInstance().user.getUser.getUserIdByName(userName);
        Assert.assertNotEquals(userId, 0, "User not found"); // Hard assertion
        // Get posts
        Response response = JsonPlaceholder.getInstance().post.getPost.getAllPosts();
        List<PostPOJO> posts = getListFromResponse(response, PostPOJO.class);
        validateList(posts.size(), "No post found");
        //Get ids of post made by "Delphine"
        List<Integer> postIds = posts.stream().filter(a -> a.getUserId().equals(userId)).map(PostPOJO::getId).collect(Collectors.toList());
        Assert.assertNotEquals(postIds.size(), 0, "Posts not found"); // Hard assertion
        return postIds;
    }
}