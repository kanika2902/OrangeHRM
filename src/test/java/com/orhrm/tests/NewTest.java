package com.orhrm.tests;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.orhrm.utilities.extentmanager;
import com.orhrm.utilities.screenshotutil;

import java.net.URL;
import java.time.Duration;
import java.util.List;
public class NewTest {
	
	ExtentReports extent = extentmanager.getreport();
	
    @Test
    public void appium() throws Exception {
    	
    	ExtentTest test = extent.createTest("Chrome App");
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName","Android");
        caps.setCapability("appium:automationName","UiAutomator2");
        caps.setCapability("appium:udid","emulator-5556");
       

        // Launch Chrome
        caps.setCapability("appium:appPackage", "com.android.chrome");
        caps.setCapability("appium:appActivity", "com.google.android.apps.chrome.Main");

        // Optional for stability
        caps.setCapability("appium:chromedriverAutodownload", true);

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        System.out.println("chrome App started");
        
        String ss = screenshotutil.capturescreenshot(driver);
        test.pass("Test Passed").addScreenCaptureFromBase64String(ss);
        test.info("chrome openedn and finish");
        extent.flush();
        System.out.println("Current Activity: " + driver.currentActivity());
        By searchXpath = By.xpath("//android.view.ViewGroup[@content-desc=\"Search YouTube\"]");
        boolean found = false;
        WebElement searchEl = null;
        long end = System.currentTimeMillis() + 40000; // 40 sec
        while (System.currentTimeMillis() < end) {
            List<WebElement> list = driver.findElements(searchXpath);
            if (list.size() > 0) {
                searchEl = list.get(0);
                found = true;
                break;
            }
            Thread.sleep(10000);
        }
        if (found) {
            System.out.println(" Search element IS PRESENT");
            System.out.println("Displayed: " + searchEl.isDisplayed());
            
        } else {
            System.out.println(" Search element NOT PRESENT after waiting");
            System.out.println("Current Activity: " + driver.currentActivity());
            
        }
        Thread.sleep(2000);
        driver.quit();
    }
}
 
 