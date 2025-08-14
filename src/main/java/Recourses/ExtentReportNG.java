package Recourses;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {
    private static ExtentReports report;

    public static ExtentReports getReportObject() {
        if (report == null) {
            String path = System.getProperty("user.dir") + "/Report/index.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setDocumentTitle("Web Automation Result");
            reporter.config().setReportName("Test Report");

            report = new ExtentReports();
            report.attachReporter(reporter);
            report.setSystemInfo("Tester", "Snehal");
        }
        return report;
    }
}
