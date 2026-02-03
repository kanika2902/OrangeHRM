package com.orhrm.tests;

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
      nameInput.sendKeys("dgjhs");

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

    }