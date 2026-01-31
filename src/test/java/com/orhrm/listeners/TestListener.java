package com.orhrm.listeners;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.*;
import org.openqa.selenium.WebDriver;
import com.orhrm.utilities.extentmanager;
import com.orhrm.utilities.screenshotutil;
import com.orhrm.Base.BaseTest;
public class TestListener implements ITestListener {
private static final ExtentReports extent = extentmanager.getreport();
private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
@Override
public void onTestStart(ITestResult result) {
    ExtentTest t = extent.createTest(result.getMethod().getMethodName());
    test.set(t);
}

@Override
public void onTestSuccess(ITestResult result) {
    if (test.get() != null) {
        test.get().pass(MarkupHelper.createLabel("PASSED", ExtentColor.GREEN));
    }
}

@Override
public void onTestFailure(ITestResult result) {
    if (test.get() != null) {
        test.get().fail(result.getThrowable());
    }

    WebDriver driver = extractDriver(result.getInstance());
    if (driver != null) {
        try {
            String scrPath = screenshotutil.capturescreenshot(driver);
            if (test.get() != null) {
                test.get().addScreenCaptureFromPath(scrPath, "Screenshot on Failure");
            }
        } catch (Exception e) {
            if (test.get() != null) {
                test.get().warning("Could not attach screenshot: " + e.getMessage());
            }
        }
    } else {
        if (test.get() != null) {
            test.get().warning("WebDriver not found for screenshot.");
        }
    }
}

@Override
public void onTestSkipped(ITestResult result) {
    if (test.get() == null) return;
    if (result.getThrowable() != null) {
        test.get().skip("Test skipped: " + result.getMethod().getMethodName());
        test.get().skip(result.getThrowable());
    } else {
        test.get().skip("Test skipped: " + result.getMethod().getMethodName());
    }
}

@Override
public void onFinish(ITestContext context) {
    extent.flush();
    System.out.println("[Extent] Flushed report.");
}

private WebDriver extractDriver(Object testInstance) {
    try {
        if (testInstance instanceof BaseTest) {
            return ((BaseTest) testInstance).getDriver();
        }
    } catch (Exception ignored) {}
    return null;
}
}

