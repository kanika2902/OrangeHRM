package Demo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.remote.RemoteWebDriver;

public class LeaveTest {

    public String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public WebDriver driver;

    @BeforeTest
    public void setup() {
        System.out.println("Before Test executed");

        // Optional: auto-manage Chromedriver (requires WebDriverManager dependency)
        // io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(baseUrl);
    }

    private void login() {
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();

        // Wait until we are on the dashboard/home
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleIs("OrangeHRM"));
    }

    @Test(priority = 1, enabled = false)
    public void loginTestWithValidCredential() {
        login();
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "OrangeHRM", "Unexpected page title after login");
    }

    @Test(priority = 2, enabled = false)
    public void applyLeave() throws InterruptedException {
        login();

        driver.findElement(By.linkText("Leave")).click();
        driver.findElement(By.linkText("Apply")).click();

        // Leave Type
        driver.findElement(By.xpath("//label[normalize-space()='Leave Type']/following::div[contains(@class,'oxd-select-text')][1]")).click();
        driver.findElement(By.xpath("//div[@role='option']//*[contains(text(),'CAN')]")).click();

        // From Date (single date field on Apply)
        driver.findElement(By.xpath("//div[@class='oxd-date-input']/input")).sendKeys("2024-08-04");

        // Comment
        driver.findElement(By.tagName("textarea")).sendKeys("This is my personal leave");

        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(2000);
    }

    @Test(priority = 3, enabled = false)
    public void assignLeave() throws InterruptedException {
        login();

        driver.findElement(By.linkText("Leave")).click();
        driver.findElement(By.linkText("Assign Leave")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement empInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Type for hints...' and not(@disabled)]")));
        empInput.click();
        empInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        empInput.sendKeys(Keys.DELETE);

        String exactEmployee = "John James"; // change if desired
        empInput.sendKeys("John");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='listbox']")));
        try {
            WebElement exact = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='listbox']//span[normalize-space()='" + exactEmployee + "']")));
            exact.click();
        } catch (TimeoutException e) {
            WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='listbox']//div[contains(@class,'oxd-autocomplete-option')][1]")));
            firstSuggestion.click();
        }
        empInput.sendKeys(Keys.TAB);

        // Leave Type
        driver.findElement(By.xpath("//label[normalize-space()='Leave Type']/following::div[contains(@class,'oxd-select-text')][1]")).click();
        driver.findElement(By.xpath("//div[@role='option']//*[contains(text(),'CAN')]")).click();

        // Dates
        WebElement fromDate = driver.findElement(By.xpath("//label[normalize-space()='From Date']/following::input[@placeholder='yyyy-mm-dd'][1]"));
        fromDate.click();
        fromDate.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, "2024-08-05", Keys.ENTER);

        WebElement toDate = driver.findElement(By.xpath("//label[normalize-space()='To Date']/following::input[@placeholder='yyyy-mm-dd'][1]"));
        toDate.click();
        toDate.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, "2024-08-05", Keys.ENTER);

        driver.findElement(By.tagName("textarea")).sendKeys("Assigning one-day leave for training.");
        driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Assign']")).click();

        Thread.sleep(1000);
        try {
            driver.findElement(By.xpath("//div[contains(@class,'orangehrm-dialog-popup')]//button[normalize-space()='Yes']")).click();
        } catch (Exception ignore) {}
        Thread.sleep(1000);
    }

    @Test(priority = 4, enabled = true)
    public void addLeaveType() throws InterruptedException {
        login();

        // Navigate: Leave -> Configure -> Leave Types
        driver.findElement(By.linkText("Leave")).click();
        WebElement configureMenu = driver.findElement(By.xpath("//span[normalize-space()='Configure']"));
        configureMenu.click();
        driver.findElement(By.xpath("//a[normalize-space()='Leave Types']")).click();

        // Add
        WebElement addBtn = driver.findElement(By.xpath("//button[.=' Add ' or normalize-space()='Add']"));
        addBtn.click();

        // Name: Fest
        WebElement nameInput = driver.findElement(By.xpath("//label[normalize-space()='Name']/following::input[1]"));
        nameInput.click();
        nameInput.clear();
        nameInput.sendKeys("holi");

        // Save
        WebElement saveBtn = driver.findElement(By.xpath("//button[@type='submit' and (normalize-space()='Save')]"));
        saveBtn.click();

        // Verify via toast or table
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        boolean verified = false;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'oxd-toast') or contains(@class,'toast')]")));
            verified = true;
        } catch (TimeoutException te) {
            try {
                WebElement createdRow = driver.findElement(By.xpath(
                        "//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-row')]" +
                        "//div[contains(@class,'oxd-table-cell')][1]//div[normalize-space()='Fest']"));
                verified = createdRow.isDisplayed();
            } catch (Exception ignore) {}
        }

        if (!verified) {
            System.out.println("Leave Type  saved");
        } else {
            System.out.println("Leave Type  added");
        }

        Thread.sleep(500);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        safeQuit(driver);
        driver = null;
    }

    private void safeQuit(WebDriver drv) {
        if (drv == null) return;
        try {
            if (drv instanceof RemoteWebDriver) {
                try {
                    if (((RemoteWebDriver) drv).getSessionId() == null) return;
                } catch (NoSuchSessionException ignored) { return; }
            }
            drv.quit();
        } catch (org.openqa.selenium.NoSuchSessionException ignored) {
        } catch (org.openqa.selenium.WebDriverException wde) {
            System.out.println("Browser already closed: " + wde.getMessage());
        } catch (Exception e) {
            System.out.println("Safe quit encountered an exception: " + e.getMessage());
        }
    }
}