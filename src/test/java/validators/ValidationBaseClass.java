package validators;

import extent.ExtentTestManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import utils.Constants;
import pojos.CommentsPOJO;
import pojos.PostsPOJO;
import pojos.UsersPOJO;
import listeners.TestListener;
import io.restassured.RestAssured;
import org.testng.annotations.Listeners;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Implementation of the Base Class for the generic methods used for test cases
 *
 * @author Abdur.Rehman
 */

@Listeners(TestListener.class)
public class ValidationBaseClass {

    /**
     * To retrieve user's id from the Response
     *
     * @param name, Name of user
     */
    Integer getUser(String name) {
        Integer userId = null;
        UsersPOJO[] users = RestAssured.get(Constants.BASE_URL + Constants.USERS_ENDPOINT).as(UsersPOJO[].class);
        for (UsersPOJO user : users) {
            if (user.getUsername().equals(name)) {
                userId = user.getId();
            }
        }
        return userId;
    }

    /**
     * To retrieve all the posts made by specific user
     *
     * @param userId, I'd of user
     */
    ArrayList<Integer> getPosts(Integer userId) {
        PostsPOJO[] userPosts = RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT).as(PostsPOJO[].class);
        ArrayList<Integer> allPost = new ArrayList<>();
        for (PostsPOJO post : userPosts) {
            if (post.getUserId().equals(userId)) {
                allPost.add(post.getId());
            }
        }
        return allPost;
    }

    /**
     * To retrieve status code of endpoint
     *
     * @param path, path of endpoint
     */
    int getStatusCode(String path) {
        return RestAssured.get(Constants.BASE_URL + path).getStatusCode();
    }

    /**
     * To retrieve the username of user
     *
     * @param name, name of user
     */
    boolean getUsername(String name) {
        boolean isPresent = false;
        UsersPOJO[] users = RestAssured.get(Constants.BASE_URL + Constants.USERS_ENDPOINT + "?username=" + name).as(UsersPOJO[].class);
        for (UsersPOJO user : users) {
            if (user.getUsername().equals(name)) {
                isPresent = true;
                break;
            }
        }
        return isPresent;
    }

    /**
     * To retrieve the response body of given path
     *
     * @param path, path of endpoint
     */
    List<HashMap<String, Object>> getResponse(String path) {
        return RestAssured.get(Constants.BASE_URL + path).jsonPath().getList(".");
    }

    /**
     * To retrieve all the comments of specific post
     *
     * @param postId, path of endpoint
     */
    CommentsPOJO[] getComments(int postId) {
        return RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT + "/" + postId + Constants.COMMENTS_ENDPOINT).as(CommentsPOJO[].class);
    }

    /**
     * To retrieve all emails against the post made by specific users
     *
     * @param username, username of the user
     */
    ArrayList<String> getEmail(String username) {
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<Integer> posts = getPosts(getUser(username));
        for (int post : posts) {
            CommentsPOJO[] comments = getComments(post);
            for (CommentsPOJO comment : comments) {
                emails.add(comment.getEmail());
            }
        }
        return emails;
    }

    /**
     * Executes at the end of the test suite to populate Extent Report and Send it as email
     */

    @AfterSuite(alwaysRun = true)
    public void endTestSuite() {
        ExtentTestManager.flush();
    }
}
