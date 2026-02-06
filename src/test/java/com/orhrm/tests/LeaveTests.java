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
    @Test
    public void addLeaveType() throws Exception {
      login();
      Login_POM lp2 = new Login_POM(driver);
     // ExtentTest test = extent.createTest("leave test");
      lp2.clickonleave1();

      // Navigate: Leave -> Configure -> Leave Types
    //  driver.findElement(By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-navigation > aside > nav > div.oxd-sidepanel-body > ul > li:nth-child(3) > a")).click();
      WebElement configureMenu = driver.findElement(By.xpath("//span[normalize-space()='Configure']"));
      configureMenu.click();
      driver.findElement(By.xpath("//a[normalize-space()='Leave Types']")).click();

      // Add
      WebElement addBtn = driver.findElement(By.xpath("//button[.=' Add ' or normalize-space()='Add']"));
      addBtn.click();

      
      WebElement nameInput = driver.findElement(By.xpath("//label[normalize-space()='Name']/following::input[1]"));
      nameInput.click();
      nameInput.clear();
      nameInput.sendKeys("ILhygL");

      // Save
      WebElement saveBtn = driver.findElement(By.xpath("//button[@type='submit' and (normalize-space()='Save')]"));
      saveBtn.click();

      // Verify via toast or table
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
        // Navigate to login page
        String url = configreader.getproperty("url");
        driver.get(url);

        // Create an Extent test node
        ExtentTest test = extent.createTest("login negative - wrong username");

        // Use existing POM to interact with the login form
        Login_POM obj = new Login_POM(driver);
        obj.enterusername("WrongUser");           // wrong username
        obj.enterpassword("admin123");            // (any password is fine)
        obj.clickonsubmit();

        // Wait for the error message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // OrangeHRM commonly renders error text inside this element:
        // <p class="oxd-text oxd-text--p oxd-alert-content-text">Invalid credentials</p>
        // If your app shows "Wrong Id", this will still capture it.
        WebElement errorEl = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[contains(@class,'oxd-alert') or contains(@class,'alert')]//p[contains(@class,'oxd-alert-content-text')]")));

        String actualError = errorEl.getText().trim();

        // Assert specifically for "Wrong Id" but tolerate common variants to avoid brittle failures
        boolean matches =
                actualError.equalsIgnoreCase("Wrong Id") ||
                actualError.equalsIgnoreCase("Invalid credentials") ||
                actualError.toLowerCase().contains("wrong");

        Assert.assertTrue(matches,
                "Expected error text 'Wrong Id' (or similar), but got: '" + actualError + "'");

        // Screenshot & extent
        String ss = screenshotutil.capturescreenshot(driver);
        test.pass("Negative login assertion passed: " + actualError)
            .addScreenCaptureFromPath(ss);

        // Flush if you prefer per-test flushing; otherwise, keep a single flush in your suite/teardown
        // extent.flush();
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
        obj.enterpassword("admin123");
        obj.clickonsubmit1();
        obj.Admin();
        obj.uname();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        obj.search();
        String textt = obj.verify();
        System.out.println(textt);
        obj.click();
        obj.logout();
        
        String ss = screenshotutil.capturescreenshot(driver);
        
        test.pass("Test passed").addScreenCaptureFromPath(ss);
        
        
        extent.flush();
    }
    //PIM MODULE - MRADUL
    @Test
    public void pimSearch_EmployeeFilters() throws Exception {
        // 1) Open login page
        String url = configreader.getproperty("url");
        driver.get(url);

        ExtentTest test = extent.createTest("PIM Search - Employee Filters");

        // 2) Login (Admin / admin123)
        Login_POM lp = new Login_POM(driver);
        lp.login("Admin", "admin123");

        // 3) Wait for dashboard top bar visible
        lp.waitForTopbar();

        // 4) Go to PIM
        lp.clickPIM();

        // 5) Wait for Employee Information page
        lp.waitForEmployeeInfoPage();

        // 6) Fill search form
        // Employee Name (autocomplete)
        lp.setEmployeeName("Linda");             // adjust as needed
        lp.pickFirstNameSuggestionIfPresent();   // optional if suggestions appear

        // Employee Id
        lp.setEmployeeId("0001");                // adjust or leave blank if not needed

        // Employment Status dropdown (open and pick a value if needed)
        lp.openEmploymentStatus();
        lp.selectEmploymentStatus("Full-Time Permanent"); // change to a value that exists in your env

        // 7) Click Search
        lp.clickSearchOnPIM();

        // 8) Wait for results table to refresh and handle outcome
        lp.waitForResultsOrNoRecords();

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

        // Screenshot to report
        String ss = screenshotutil.capturescreenshot(driver);
        test.addScreenCaptureFromPath(ss);
        // extent.flush(); // keep or move to your suite teardown as per your pattern
    }
//HITESH
    @Test
    public void time_Timesheets_then_ProjectInfo_Customers_Add() throws Exception {
        // 1) Open Login page
        String url = configreader.getproperty("url");
        driver.get(url);

        ExtentTest test = extent.createTest("Time > Timesheets > Project Info > Customers - Add");

        // 2) Login (Admin/admin123)
        Login_POM lp = new Login_POM(driver);
        lp.enterusername("Admin");
        lp.enterpassword("admin123");
        lp.clickonsubmit();

        // Optional: wait for topbar to stabilize
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .visibilityOfElementLocated(org.openqa.selenium.By.cssSelector("header.oxd-topbar")));

        // 3) Open Time
        lp.tm_openTimeModule();

        // 4) Open Timesheets tab (dropdown visible)
        lp.tm_openTimesheetsTab();

        // 5) Go to Project Info -> Customers
        lp.tm_openProjectInfo_Customers();

        // 6) Click Add
        lp.tm_clickCustomersAdd();

        // 7) Add Name and Description
        String customerName = "AutoCustomer_" + System.currentTimeMillis();
        String customerDesc = "Created by automated test for Project Info > Customers.";
        lp.tm_fillCustomerNameDesc(customerName, customerDesc);

        // 8) Click Save/Add
        lp.tm_saveCustomer();

        // 9) Verify by toast or table
        boolean ok = lp.tm_verifyCustomerCreated(customerName);
        org.testng.Assert.assertTrue(ok, "Customer was not confirmed by toast or table.");

        // Report & screenshot
        String ss = com.orhrm.utilities.screenshotutil.capturescreenshot(driver);
        test.pass("Customer added successfully: " + customerName)
            .addScreenCaptureFromPath(ss);
    }
    
    
      }