//package reporting;
//
//import com.aventstack.extentreports.*;
//import com.aventstack.extentreports.markuputils.MarkupHelper;
//import com.aventstack.extentreports.markuputils.ExtentColor;
//import org.testng.*;
//
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//
//import java.io.File;
//import java.lang.reflect.Field;
//import java.nio.file.Files;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class TestListener implements ITestListener {
//    private static final ExtentReports extent = ExtentManager.getReporter();
//    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
//
//    @Override
//    public void onTestStart(ITestResult result) {
//        ExtentTest t = extent.createTest(result.getMethod().getMethodName());
//        test.set(t);
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        if (test.get() != null) {
//            test.get().pass(MarkupHelper.createLabel("PASSED", ExtentColor.GREEN));
//        }
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        if (test.get() != null) {
//            test.get().fail(result.getThrowable());
//        }
//
//        WebDriver driver = extractDriver(result.getInstance());
//        if (driver != null) {
//            try {
//                String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//
//                String baseDir = System.getProperty("user.dir");
//                File dest = new File(baseDir + File.separator + "test-output" +
//                        File.separator + "ExtentReports" + File.separator + "screenshots" +
//                        File.separator + result.getMethod().getMethodName() + "_" + time + ".png");
//                dest.getParentFile().mkdirs();
//                Files.copy(scr.toPath(), dest.toPath());
//
//                if (test.get() != null) {
//                    test.get().addScreenCaptureFromPath(dest.getPath(), "Screenshot on Failure");
//                }
//            } catch (Exception e) {
//                if (test.get() != null) {
//                    test.get().warning("Could not attach screenshot: " + e.getMessage());
//                }
//            }
//        } else {
//            if (test.get() != null) {
//                test.get().warning("WebDriver not found for screenshot.");
//            }
//        }
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        if (test.get() == null) return;
//
//        if (result.getThrowable() != null) {
//            test.get().skip("Test skipped: " + result.getMethod().getMethodName());
//            test.get().skip(result.getThrowable());
//        } else {
//            test.get().skip("Test skipped: " + result.getMethod().getMethodName());
//        }
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//        extent.flush(); // write the report
//        System.out.println("[Extent] Flushed report.");
//    }
//
//    private WebDriver extractDriver(Object testInstance) {
//        try {
//            Field f = testInstance.getClass().getDeclaredField("driver");
//            f.setAccessible(true);
//            Object obj = f.get(testInstance);
//            if (obj instanceof WebDriver) return (WebDriver) obj;
//        } catch (Exception ignore) {}
//        return null;
//    }
//}