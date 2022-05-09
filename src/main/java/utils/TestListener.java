package utils;

import com.relevantcodes.extentreports.LogStatus;
import extent.ExtentManager;
import extent.ExtentTestManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


/**
 * Standard TestNG Listener Class
 */
@Slf4j
public class TestListener implements ITestListener {

    private static synchronized String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    public void onStart(ITestContext iTestContext) {
       log.info((FileUtils.getPropertyValue(Constants.MESSAGES_PROPERTIES_PATH,
                Constants.ON_START_MESSAGE) + iTestContext.getName()));
    }

    public void onFinish(ITestContext iTestContext) {
       log.info((FileUtils.getPropertyValue(Constants.MESSAGES_PROPERTIES_PATH,
                Constants.ON_FINISH_MESSAGE) + iTestContext.getName()));
        //Do tier down operations for extentreports reporting!
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }

    public void onTestStart(ITestResult iTestResult) {
        log.info((FileUtils.getPropertyValue(Constants.MESSAGES_PROPERTIES_PATH,
                Constants.ON_TEST_START_MESSAGE) + getTestMethodName(iTestResult) + " start"));
    }

    public void onTestSuccess(ITestResult iTestResult) {
        //ExtentReports log operation for passed tests.
        log.info((FileUtils.getPropertyValue(Constants.MESSAGES_PROPERTIES_PATH,
                Constants.ON_TEST_SUCCESS_MESSAGE) + getTestMethodName(iTestResult) + " succeed"));

        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
    }

    public void onTestFailure(ITestResult iTestResult) {
       log.info((FileUtils.getPropertyValue(Constants.MESSAGES_PROPERTIES_PATH,
                Constants.ON_TEST_FAILURE_MESSAGE) + getTestMethodName(iTestResult) + " failed"));
        ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed");
    }

    public void onTestSkipped(ITestResult iTestResult) {
       log.info((FileUtils.getPropertyValue(Constants.MESSAGES_PROPERTIES_PATH,
                Constants.ON_TEST_SKIPPED_MESSAGE) + getTestMethodName(iTestResult) + " skipped"));
        //ExtentReports log operation for skipped tests.
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
       log.info((FileUtils.getPropertyValue(Constants.MESSAGES_PROPERTIES_PATH,
                Constants.ON_SUCCESS_RATIO_MESSAGE) + getTestMethodName(iTestResult)));
    }
}	