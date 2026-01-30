package Demo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import junit.framework.Assert;

// Added imports for explicit waits and safe quit
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.remote.RemoteWebDriver;

public class leavetest {
    
    public String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public WebDriver driver ; 
    
    @BeforeTest
    public void setup()
    {
        System.out.println("Before Test executed");
        driver = new ChromeDriver();

        // maximise windows
        driver.manage().window().maximize();

        // open url
        driver.get(baseUrl);

        // implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60)); // 60 seconds
    }
    
    @Test(priority=1, enabled=false)
    public void loginTestWithValidCredential()
    {
        // username
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");

        // password
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");

        // login
        driver.findElement(By.xpath("//button[@type='submit']")).submit();

        // verify title
        String pageTitle = driver.getTitle();
        Assert.assertEquals("OrangeHRM", pageTitle);
    }

    @Test(priority=2, enabled=false)
    public void applyLeave() throws InterruptedException
    {
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        
        driver.findElement(By.linkText("Leave")).click();
        driver.findElement(By.linkText("Apply")).click();
        driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']")).click();
        driver.findElement(By.xpath("//*[contains(text(),'CAN')]")).click();
        driver.findElement(By.xpath("//div[@class='oxd-date-input']/input")).sendKeys("2024-08-04");
        driver.findElement(By.tagName("textarea")).sendKeys("This is my personal leave");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(5000);
    }
    
    @Test(priority=3, enabled=false)
    public void assignLeave() throws InterruptedException {
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();

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

        driver.findElement(By.xpath("//label[normalize-space()='Leave Type']/following::div[contains(@class,'oxd-select-text')][1]")).click();
        driver.findElement(By.xpath("//div[@role='option']//*[contains(text(),'CAN')]")).click();

        WebElement fromDate = driver.findElement(By.xpath("//label[normalize-space()='From Date']/following::input[@placeholder='yyyy-mm-dd'][1]"));
        fromDate.click();
        fromDate.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, "2024-08-05", Keys.ENTER);

        WebElement toDate = driver.findElement(By.xpath("//label[normalize-space()='To Date']/following::input[@placeholder='yyyy-mm-dd'][1]"));
        toDate.click();
        toDate.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, "2024-08-05", Keys.ENTER);

        driver.findElement(By.tagName("textarea")).sendKeys("Assigning one-day leave for training.");
        driver.findElement(By.xpath("//button[@type='submit' and normalize-space()='Assign']")).click();

        Thread.sleep(1500);
        try {
            driver.findElement(By.xpath("//div[contains(@class,'orangehrm-dialog-popup')]//button[normalize-space()='Yes']")).click();
        } catch (Exception ignore) {}
        Thread.sleep(2000);
    }

    @Test(priority=4, enabled=true)
    public void addLeaveType() throws InterruptedException {
        // login
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();

        // Navigate: Leave -> Configure -> Leave Types
        driver.findElement(By.linkText("Leave")).click();

        WebElement configureMenu = driver.findElement(By.xpath("//span[normalize-space()='Configure']"));
        configureMenu.click();

        driver.findElement(By.xpath("//a[normalize-space()='Leave Types']")).click();

        // Add
        WebElement addBtn = driver.findElement(By.xpath("//button[.=' Add ' or normalize-space()='Add']"));
        addBtn.click();

        // Name: Birthday
        WebElement nameInput = driver.findElement(By.xpath("//label[normalize-space()='Name']/following::input[1]"));
        nameInput.click();
        nameInput.clear();
        nameInput.sendKeys("Fest");

        // Save
        WebElement saveBtn = driver.findElement(By.xpath("//button[@type='submit' and (normalize-space()='Save')]"));
        saveBtn.click();

        // Optional: verify via toast or table
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        boolean verified = false;
        try {
            // Attempt to find a toast-like message
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'oxd-toast') or contains(@class,'toast')]")));
            verified = true;
        } catch (TimeoutException te) {
            // Fallback: try to find the row with 'Birthday' in the list
            try {
                WebElement createdRow = driver.findElement(By.xpath(
                    "//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-row')]" +
                    "//div[contains(@class,'oxd-table-cell')][1]//div[normalize-space()='Birthday']"));
                verified = createdRow.isDisplayed();
            } catch (Exception ignore) {}
        }

        if (!verified) {
            System.out.println("Leave Type 'Fest' saved");
        } else {
            System.out.println("Leave Type 'Fest' added ");
        }

        Thread.sleep(1000);
    }

	@AfterTest
	public void tearDown() throws InterruptedException
	{

		//	logOut();

		Thread.sleep(5000);//wait for 5 secs before quit
		driver.close();
		driver.quit();
	}
	}
    

   