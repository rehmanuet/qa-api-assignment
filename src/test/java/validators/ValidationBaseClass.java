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


    public static void validateResponseStatusCode(Response response, int expectedCode) {
        int actualCode = response.getStatusCode();
        Assert.assertEquals(actualCode, expectedCode, "Response: status code NOT correct");
    }

    public static <T> List<T> getListFromResponse(Response response, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        try {
            result = response.jsonPath().getList("$", tClass);
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
