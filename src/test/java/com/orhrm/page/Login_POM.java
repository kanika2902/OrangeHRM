package com.orhrm.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_POM {
    WebDriver driver;

    // (spelling kept as in your file)
    By uesrname = By.name("username");
    By password = By.name("password");
    By submitbutton = By.xpath("//button[@type='submit']");
    By dashboard = By.xpath("//h6[text()='Dashboard']");
    By leave = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/aside/nav/div[2]/ul/li[3]/a");
    
    By uesrname1=By.name("username");
	By password1=By.name("password");
	By submitbutton1=By.xpath("//button[@type='submit']");
	By dashboard1=By.xpath("//h6[text()='Dashboard']");
	
	By Admin = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/aside/nav/div[2]/ul/li[1]/a");
	By uname = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input");
	//By empname = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div/input");
	
	By search = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]");
	By verify = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span");
	By name = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[3]/ul/li/span");
	By logout = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[3]/ul/li/ul/li[4]/a");
   
	//mradul

By topbarHeader = By.cssSelector("header.oxd-topbar");
By pimMenu = By.xpath("//a[.//span[normalize-space()='PIM']]");
By employeeInfoHeader = By.xpath("//h5[normalize-space()='Employee Information']");
By employeeNameInput = By.xpath("//label[normalize-space()='Employee Name']/following::input[1]");
By employeeIdInput = By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");

By employmentStatusSelect = By.xpath("//label[normalize-space()='Employment Status']/following::div[contains(@class,'oxd-select-text')][1]");
By pimSearchButton = By.xpath("//button[normalize-space()='Search']");

// Results table
By tableRows = By.cssSelector(".oxd-table-body .oxd-table-card");
By noRecordsFound = By.xpath("//*[contains(normalize-space(),'No Records Found')]");

 //HITESH


//======== TIME > PROJECT INFO > CUSTOMERS (locators) ========
By leftTimeMenu_TM = By.xpath("//a[.//span[normalize-space()='Time']]");
By topbar_TM = By.cssSelector("header.oxd-topbar");

//Tabs
By tabTimesheets_TM = By.xpath("//span[normalize-space()='Timesheets']");
By tabProjectInfo_TM = By.xpath("//span[normalize-space()='Project Info']");
By dropdownMenu_TM = By.cssSelector("ul.oxd-dropdown-menu");

//Submenu
By subCustomers_TM = By.xpath("//a[normalize-space()='Customers']");

//Customers page
By customersHeader_TM = By.xpath("//h6[contains(normalize-space(),'Customers')]");
By customersAddBtn_TM = By.xpath("//button[normalize-space()='Add' or normalize-space()=' Add ']");
By customerNameInput_TM = By.xpath("//label[normalize-space()='Name']/following::input[1]");
By customerDescriptionTextarea_TM = By.xpath("//label[normalize-space()='Description']/following::textarea[1]");
By customerSaveBtn_TM = By.xpath("//button[@type='submit' and normalize-space()='Save']");

//Verification
By anyToast_TM = By.cssSelector("div.oxd-toast");
By toastContent_TM = By.cssSelector(".oxd-toast-content");
By customersTableCells_TM = By.cssSelector(".oxd-table-body .oxd-table-card .oxd-table-cell div");
By loadingOverlay_TM = By.cssSelector(".oxd-overlay, .oxd-loading-spinner");


    public Login_POM(WebDriver driver2) {
        this.driver = driver2;
    }

    public void enterusername(String uname) {
        driver.findElement(uesrname).sendKeys(uname);
    }
    
    public void clickonleave1() {
        driver.findElement(leave).click();
    }

    public void enterpassword(String pword) {
        driver.findElement(password).sendKeys(pword);
    }

    public void clickonsubmit() {
        driver.findElement(submitbutton).click();
    }

    public boolean dashboardisplayed() {
        return driver.findElement(dashboard).isDisplayed();
    }

    // ✅ Add/ensure this helper exists
    public void login(String uname, String pword) {
        enterusername(uname);
        enterpassword(pword);
        clickonsubmit();
    }
    
    public void enterusername1(String uname)
	{
		driver.findElement(uesrname).sendKeys(uname);
	}
	
	public void enterpassword1(String pword)
	{
		driver.findElement(password).sendKeys(pword);
	}
 
	public void clickonsubmit1()
	{
		driver.findElement(submitbutton).click();
	}
	
	public boolean dashboardisplayed1()
	{
		return driver.findElement(dashboard).isDisplayed();
	}
	public void Admin() {
		 driver.findElement(Admin).click();
	}
	public void uname() {
		driver.findElement(uname).sendKeys("Admin");
	}
	
	public void search() {
	 driver.findElement(search).click();
	}
	public String verify() {
	 String text = driver.findElement(verify).getText();
	 return text;
	}
	public void  click() {
		driver.findElement(name).click();
	}
	public void logout() {
		driver.findElement(logout).click();
	}

	//mradul

public void waitForTopbar() {
    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
        .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(topbarHeader));
}

// 4) Go to PIM
public void clickPIM() {
    driver.findElement(pimMenu).click();
}

// 5) Wait for Employee Information page
public void waitForEmployeeInfoPage() {
    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
        .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(employeeInfoHeader));
}

//6a) Employee Name (autocomplete)
public void setEmployeeName(String name) {
 org.openqa.selenium.WebElement el = driver.findElement(employeeNameInput);
 el.clear();
 el.sendKeys(name);
}

//6a-optional) Pick first suggestion if it appears
public void pickFirstNameSuggestionIfPresent() {
 // First suggestion in autocomplete listbox
 org.openqa.selenium.By firstOption = org.openqa.selenium.By.xpath("//div[@role='listbox']//div[@role='option'][1]");
 try {

	 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
	            .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(firstOption));
	        java.util.List<org.openqa.selenium.WebElement> opts = driver.findElements(firstOption);
	        if (!opts.isEmpty()) {
	            opts.get(0).click();
	        }
	    } catch (Exception ignored) {
	        // no suggestion shown, continue without selecting
	    }
	}

//6b) Employee Id
public void setEmployeeId(String id) {
 org.openqa.selenium.WebElement el = driver.findElement(employeeIdInput);
 el.clear();
 el.sendKeys(id);
}

//6c) Employment Status dropdown (open)
public void openEmploymentStatus() {
 driver.findElement(employmentStatusSelect).click();
}

//6c) Employment Status dropdown (select value)
public void selectEmploymentStatus(String statusText) {
 org.openqa.selenium.By opt = org.openqa.selenium.By.xpath("//div[@role='listbox']//span[normalize-space()='" + statusText + "']");
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(opt));
 driver.findElement(opt).click();
}

//7) Click Search
public void clickSearchOnPIM() {
 driver.findElement(pimSearchButton).click();
}

//8) Wait for results to refresh (rows or "No Records Found")
public boolean waitForResultsOrNoRecords() {
 org.openqa.selenium.support.ui.WebDriverWait wait =
     new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
 return wait.until(d -> driver.findElements(tableRows).size() > 0 ||
                        driver.findElements(noRecordsFound).size() > 0);
}

//Helper: check if there are rows
public boolean hasResultRows() {
 return driver.findElements(tableRows).size() > 0;
}

//Helper: get first row text (if exists)
public String getFirstRowTextIfPresent() {
 java.util.List<org.openqa.selenium.WebElement> rows = driver.findElements(tableRows);
 if (!rows.isEmpty()) {
     return rows.get(0).getText();
 }
 return "";
}
// HITESH

//======== Common waits/util ========
private void tm_waitTopbar() {
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(topbar_TM));
}
private void tm_waitOverlayGone() {
 try {
     new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(8))
         .until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(loadingOverlay_TM));
 } catch (Exception ignored) {}
}

//======== Navigation helpers ========
public void tm_openTimeModule() {
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(12))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(leftTimeMenu_TM))
     .click();
 tm_waitTopbar();
}

public void tm_openTimesheetsTab() {
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(tabTimesheets_TM))
     .click();
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(dropdownMenu_TM));
}

public void tm_openProjectInfo_Customers() {
 // Open Project Info tab dropdown
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(tabProjectInfo_TM))
     .click();
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(dropdownMenu_TM));
 // Click Customers
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(subCustomers_TM))
     .click();
 // Page ready
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(customersHeader_TM));
 tm_waitOverlayGone();
}

//======== Customers form actions ========
public void tm_clickCustomersAdd() {
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(customersAddBtn_TM))
     .click();
}

public void tm_fillCustomerNameDesc(String name, String desc) {
 org.openqa.selenium.WebElement nameEl =
     new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
         .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(customerNameInput_TM));
 nameEl.clear();
 nameEl.sendKeys(name);

 org.openqa.selenium.WebElement descEl = driver.findElement(customerDescriptionTextarea_TM);
 descEl.clear();
 descEl.sendKeys(desc);
}

public void tm_saveCustomer() {
 new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(customerSaveBtn_TM))
     .click();
 tm_waitOverlayGone();
}

//Accept success by toast OR by table containing the name
public boolean tm_verifyCustomerCreated(String name) {
 org.openqa.selenium.support.ui.WebDriverWait wait =
     new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));

 tm_waitOverlayGone();

 return wait.until(d -> {
     // Toast present?
     if (!driver.findElements(anyToast_TM).isEmpty()) return true;

     // Table contains the new name?
     java.util.List<org.openqa.selenium.WebElement> cells = driver.findElements(customersTableCells_TM);
     for (org.openqa.selenium.WebElement c : cells) {
         if (name.equalsIgnoreCase(c.getText().trim())) return true;
     }
     return false;
 });
}

}
