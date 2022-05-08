package extent;

import com.relevantcodes.extentreports.ExtentReports;
import utils.Constants;

/**
 * Standard class for Extent Reports Manager
 */
public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports getReporter() {
        if (extent == null) {
            //Set HTML reporting file location
            extent = new ExtentReports(Constants.EXTENT_REPORT_PATH, true);
        }
        return extent;
    }
}
