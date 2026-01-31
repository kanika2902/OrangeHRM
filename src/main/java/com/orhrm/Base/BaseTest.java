package com.orhrm.Base;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.orhrm.utilities.configreader;
public class BaseTest {
protected WebDriver driver;
public WebDriver getDriver() {
    return driver;
}

@BeforeMethod
public void beforeMethod() throws Exception {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    String url = configreader.getproperty("url");
    driver.get(url);
    System.out.println("Navigated to: " + url);
}

@AfterMethod(alwaysRun = true)
public void afterMethod() {
    if (driver != null) {
        try { driver.quit(); } catch (Exception ignored) {}
    }
}
}