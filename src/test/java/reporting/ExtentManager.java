//package reporting;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//public class ExtentManager {
//    private static ExtentReports extent;
//
//    public synchronized static ExtentReports getReporter() {
//        if (extent == null) {
//            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String reportPath = "test-output/ExtentReports/ExtentReport_" + time + ".html";
//
//            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
//            spark.config().setDocumentTitle("OrangeHRM Automation");
//            spark.config().setReportName("Leave Module");
//
//            extent = new ExtentReports();
//            extent.attachReporter(spark);
//
//            // Add system info (optional)
//            extent.setSystemInfo("Tester", "Kanika Choudhary");
//            extent.setSystemInfo("Module", "Leave");
//            extent.setSystemInfo("Browser", "Chrome");
//        }
//        return extent;
//    }
//}