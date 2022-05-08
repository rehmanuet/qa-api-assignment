package validators;

import extent.ExtentTestManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import utils.Constants;
import pojos.CommentsPOJO;
import pojos.PostsPOJO;
import pojos.UsersPOJO;
import utils.TestListener;
import io.restassured.RestAssured;
import org.testng.annotations.Listeners;
import com.google.gson.Gson;
import org.json.simple.JSONObject;


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

    public static void validateResponseStatusCode(Response response, int expectedCode) {
        int actualCode = response.getStatusCode();
        Assert.assertEquals(actualCode, expectedCode, "Response: status code NOT correct");
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


    public static <T> List<T> getListFromResponse(Response response, String path, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        try {
            result = response.jsonPath().getList(path, tClass);
        } catch (Exception ex) {
//            log(ex.getMessage());
//            Logger.logError("FAILED to get list '" + path + "' from '" + response.body().asString() + "'");
        }
        return result;
    }
    public static <T> T getObjectFromResponsePath(Response response, String path, Class<T> tClass) {
        // Step 1 -> get response body as hashMap
        HashMap resultAsHashMap = new HashMap();
        try {
            resultAsHashMap = getResponsePath(response, path);
        } catch (Exception ex) {

        }
        T result;
        try {
            // Step 2 -> Convert HashMap to JsonString
            String jsonString = new JSONObject(resultAsHashMap).toJSONString();
            // Step 3 -> Convert JsonString to Object of specified class
            result = new Gson().fromJson(jsonString, tClass);
        } catch (Exception e) {
            result = null;
        }

        return result;
    }


    public static <T> T getResponsePath(Response response, String path) {
        T result = null;
        try {
            result = response.path(path);
        } catch (Exception ex) {
        }
        return result;
    }

    public static <T> T getObjectFromResponse(Response response, Class<T> tClass) {
        // Step 1 -> get response body as hashMap
        HashMap resultAsHashMap = new HashMap();
        try {
            resultAsHashMap = response.as(HashMap.class);
        } catch (Exception ex) {
//            Logger.logError("FAILED to convert response: " + response.body().asString() + "' into HashMap'");
//            log(ex.getMessage());
        }
        // Step 2 -> Convert HashMap to JsonString
        String jsonString = new JSONObject(resultAsHashMap).toJSONString();
        // Step 3 -> Convert JsonString to Object of specified class
        T result = new Gson().fromJson(jsonString, tClass);

        return result;
    }
    /**
     * Executes at the end of the test suite to populate Extent Report and Send it as email
     */

    @AfterSuite(alwaysRun = true)
    public void endTestSuite() {
        ExtentTestManager.flush();
    }
}
