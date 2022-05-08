package extent;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Standard class for Extent Reports Manager
 */
public class ExtentTestManager {

    private static final Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static final ExtentReports extent = ExtentManager.getReporter();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        extent.endTest(extentTestMap.get((int) (Thread.currentThread().getId())));
    }

    @SuppressWarnings("UnusedReturnValue")
    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized void flush() {
        extent.flush();
    }
}
