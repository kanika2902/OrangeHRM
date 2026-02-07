package com.orhrm.utilities;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
public class extentmanager {
static ExtentReports extent;
static String projectpath = System.getProperty("user.dir");
public static synchronized ExtentReports getreport() {
    if (extent == null) {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = projectpath + "/test-output/ExtentReports/ExtentReport_" + time + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("OrangeHRM Automation");
        spark.config().setReportName("Leave Module");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Optional system info
        extent.setSystemInfo("Tester", "Kanika Choudhary");
        extent.setSystemInfo("Module", "Leave");
        extent.setSystemInfo("Browser", "Chrome");
    }
    return extent;
}
}

