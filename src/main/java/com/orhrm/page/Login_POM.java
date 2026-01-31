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

	
}
