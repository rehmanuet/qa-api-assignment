package validators;

import com.relevantcodes.extentreports.LogStatus;
import extent.ExtentTestManager;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.TestListener;
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
@Slf4j
public class BaseValidation {
    protected ThreadLocal<SoftAssert> softAssert = new ThreadLocal<>();
    public static String BASE_URL;

    public static void validateResponseStatusCode(Response response, String endpoint, int expectedCode) {
        int actualCode = response.getStatusCode();
        ExtentTestManager.getTest().log(LogStatus.INFO, "Endpoint: " + endpoint + " Status Code: " + actualCode);
        Assert.assertEquals(actualCode, expectedCode, "Response: status code NOT correct");
    }

    public static <T> List<T> getListFromResponse(Response response, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        try {
            result = response.jsonPath().getList("$", tClass);
        } catch (Exception ex) {
            log.error("FAILED to get list '" + "$" + "' from '" + response.body().asString() + "'");
        }
        return result;
    }

    public static <T> T getObjectFromResponsePath(Response response, String path, Class<T> tClass) {
        HashMap resultAsHashMap = new HashMap();
        try {
            resultAsHashMap = getResponsePath(response, path);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        T result;
        try {
            String jsonString = new JSONObject(resultAsHashMap).toJSONString();
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
            log.error(ex.getMessage());
        }
        return result;
    }

    @AfterSuite(alwaysRun = true)
    public void endTestSuite() {
        ExtentTestManager.flush();
    }

    @Parameters({"baseURI"})
    @BeforeSuite(alwaysRun = true)
    public void startTestSuite(String baseURI) {
        BASE_URL = baseURI;
    }

    protected void skipRetryAndSoftAssert() {
        softAssert.get().assertAll();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() {
        this.softAssert.set(new SoftAssert());
    }

    public void validateList(Integer size, String logMessage) {
        if (size == 0) softAssert.get().fail(logMessage);
    }
}
