package com.orhrm.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.orhrm.Base.BaseTest;
import com.orhrm.page.Login_POM;
import com.orhrm.utilities.configreader;

@Listeners(com.orhrm.listeners.TestListener.class)


public class LeaveTests extends BaseTest {

    private void login() throws Exception {
        Login_POM lp = new Login_POM(driver);
        String uname= configreader.getproperty("username");
        String pass= configreader.getproperty("password");
        lp.login(uname,pass);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleIs("OrangeHRM"));
    }


    @Test
    public void addLeaveType() throws Exception {
      login();
      Login_POM lp2 = new Login_POM(driver);
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
      nameInput.sendKeys("dgvas");

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

      Thread.sleep(500);
  }

    // Future tests:
    // @Test(priority = 3)
    // public void applyLeave() throws Exception { /* implement via POMs as needed */ }

    // @Test(priority = 4)
    // public void assignLeave() throws Exception { /* implement via POMs as needed */ }
}