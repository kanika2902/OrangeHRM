package com.orhrm.tests;

import java.io.IOException;
import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.orhrm.Base.BaseTest;
import com.orhrm.page.Login_POM;
import com.orhrm.utilities.configreader;
import com.orhrm.utilities.extentmanager;
import com.orhrm.utilities.screenshotutil;
import com.orhrm.utilities.extentmanager;

@Listeners(com.orhrm.listeners.TestListener.class)





public class LeaveTests extends BaseTest {
//ExtentReports extent = extentmanager.getreport();
    private void login() throws Exception {
    	
        Login_POM lp = new Login_POM(driver);
        String uname= configreader.getproperty("username");
        String pass= configreader.getproperty("password");
        lp.login(uname,pass);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleIs("OrangeHRM"));
        
              
    }
    
    ExtentReports extent = extentmanager.getreport();
    ExtentTest test = extent.createTest("leave");
    
    //kanika
    @Test
    public void addLeaveType() throws Exception {
      login();
      Login_POM lp2 = new Login_POM(driver);
      lp2.clickonleave1();
      test.info("click on leave");
      WebElement configureMenu = driver.findElement(By.xpath("//span[normalize-space()='Configure']"));
      test.info("click on configure");
      configureMenu.click();
      driver.findElement(By.xpath("//a[normalize-space()='Leave Types']")).click();

      WebElement addBtn = driver.findElement(By.xpath("//button[.=' Add ' or normalize-space()='Add']"));
      addBtn.click();
      test.info("click on leave types");
      
      WebElement nameInput = driver.findElement(By.xpath("//label[normalize-space()='Name']/following::input[1]"));
      nameInput.click();
      test.info("click on add and add leave type");
      nameInput.clear();
      nameInput.sendKeys("Makar Sakrantiii");

      WebElement saveBtn = driver.findElement(By.xpath("//button[@type='submit' and (normalize-space()='Save')]"));
      saveBtn.click();
      test.info("click on save button");
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
      boolean verified = false;
      wait.until(ExpectedConditions.visibilityOfElementLocated(
	          By.xpath("//div[contains(@class,'oxd-toast') or contains(@class,'toast')]")));
	  verified = true;

      if (!verified) {
          System.out.println("Leave Type  saved");
      } else {
          System.out.println("Leave Type  added");
      }
      String ss = screenshotutil.capturescreenshot(driver);
      test.pass("Test passed").addScreenCaptureFromPath(ss);
      Thread.sleep(500);
      
     
      
  }
    //gungun
    @Test
    public void loginTestWithInvalidUsername() throws IOException {
       
        String url = configreader.getproperty("url");
        driver.get(url);

       
        ExtentTest test = extent.createTest("login negative - wrong username");

        
        Login_POM obj = new Login_POM(driver);
        obj.enterusername("WrongUser"); 
        test.info("username entered");// wrong username
        obj.enterpassword("admin123");  
        test.info("password entered");// (any password is fine)
        obj.clickonsubmit();
        test.info("wrong info not submitted");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                WebElement errorEl = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[contains(@class,'oxd-alert') or contains(@class,'alert')]//p[contains(@class,'oxd-alert-content-text')]")));

        String actualError = errorEl.getText().trim();

        boolean matches =
                actualError.equalsIgnoreCase("Wrong Id") ||
                actualError.equalsIgnoreCase("Invalid credentials") ||
                actualError.toLowerCase().contains("wrong");

        Assert.assertTrue(matches,
                "Expected error text 'Wrong Id' (or similar), but got: '" + actualError + "'");

        String ss = screenshotutil.capturescreenshot(driver);
        test.pass("Negative login assertion passed: " + actualError)
            .addScreenCaptureFromPath(ss);

    }
    //tanisha
    @Test
    public void loginTestWithValidCredential() throws IOException {
        System.out.println("Before Test executed");
        String url = configreader.getproperty("url");
        driver.get(url);
 
        ExtentTest test = extent.createTest("login test");
        Login_POM obj = new Login_POM(driver);
        obj.enterusername("Admin");
        test.info("username entered");
        obj.enterpassword("admin123");
        test.info("password entered");
        obj.clickonsubmit1();
        test.info("logged in");
        obj.Admin();
        test.info("go to admin");
        obj.uname();
        test.info("enter username");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        obj.search();
        test.info("click on search");
        String textt = obj.verify();
        System.out.println(textt);
        test.info("users verified");
        obj.click();
        obj.logout();
        test.info("logged out");
        
        String ss = screenshotutil.capturescreenshot(driver);
        
        test.pass("Test passed").addScreenCaptureFromPath(ss);
        
        
        extent.flush();
    }
    //PIM MODULE - MRADUL
    @Test
    public void pimSearch_EmployeeFilters() throws Exception {
        String url = configreader.getproperty("url");
        driver.get(url);

        ExtentTest test = extent.createTest("PIM Search - Employee Filters");

        Login_POM lp = new Login_POM(driver);
        lp.login("Admin", "admin123");
        test.info("logged in");

        lp.waitForTopbar();
        test.info("dashboard appeared");
        lp.clickPIM();
        test.info("click on PIM Module");
        lp.waitForEmployeeInfoPage();

        lp.setEmployeeName("Linda");             
        lp.pickFirstNameSuggestionIfPresent();  

        lp.setEmployeeId("0001");                
        lp.openEmploymentStatus();
        lp.selectEmploymentStatus("Full-Time Permanent"); 

        lp.clickSearchOnPIM();
        test.info("fill details");
        lp.waitForResultsOrNoRecords();
        test.info("no records found");
        String outcomeMsg;
        if (lp.hasResultRows()) {
            String firstRow = lp.getFirstRowTextIfPresent();
            System.out.println("First row:\n" + firstRow);
            outcomeMsg = "Results found. First row:\n" + firstRow;
            test.pass(outcomeMsg);
        } else {
            System.out.println("No Records Found");
            outcomeMsg = "No Records Found";
            test.pass(outcomeMsg);
        }
        test.info("finish");
        String ss = screenshotutil.capturescreenshot(driver);
        test.addScreenCaptureFromPath(ss);
    }
//HITESH
    @Test
    public void time_Timesheets_then_ProjectInfo_Customers_Add() throws Exception {
        String url = configreader.getproperty("url");
        driver.get(url);

        ExtentTest test = extent.createTest("Time > Timesheets > Project Info > Customers - Add");

        Login_POM lp = new Login_POM(driver);
        lp.enterusername("Admin");
        lp.enterpassword("admin123");
        lp.clickonsubmit();
        test.info("logged in");
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .visibilityOfElementLocated(org.openqa.selenium.By.cssSelector("header.oxd-topbar")));

        lp.tm_openTimeModule();
        test.info("open time module");
        lp.tm_openTimesheetsTab();
        test.info("open timesheet");
        lp.tm_openProjectInfo_Customers();
        test.info("go to project info");
        lp.tm_clickCustomersAdd();
        test.info("add customers");
        String customerName = "AutoCustomer_" + System.currentTimeMillis();
        String customerDesc = "Created by automated test for Project Info > Customers.";
        lp.tm_fillCustomerNameDesc(customerName, customerDesc);

        lp.tm_saveCustomer();
        test.info("save customer");
        boolean ok = lp.tm_verifyCustomerCreated(customerName);
        org.testng.Assert.assertTrue(ok, "Customer was not confirmed by toast or table.");

        String ss = com.orhrm.utilities.screenshotutil.capturescreenshot(driver);
        test.pass("Customer added successfully: " + customerName)
        
            .addScreenCaptureFromPath(ss);
        test.info("customer add successfully and finish");
    }
    
    
      }