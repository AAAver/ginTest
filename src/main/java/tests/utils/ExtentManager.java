package tests.utils;


import com.relevantcodes.extentreports.ExtentReports;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            String time = new SimpleDateFormat("yyyy_MM_dd_HH_mm").format(new Date());
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                extent = new ExtentReports(workingDir + "\\ExtentReports\\Report_"+time+"_.html", false);
            }
            else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                extent = new ExtentReports(workingDir + "/ExtentReports/ExtentReportResults.html", true);
            }
        }
        return extent;
    }
}
