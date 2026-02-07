package com.orhrm.utilities;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
public class screenshotutil {
public static String capturescreenshot(WebDriver driver) throws IOException {
File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
String dest = System.getProperty("user.dir") +
File.separator + "test-output" +
File.separator + "ExtentReports" +
File.separator + "screenshots" +
File.separator + "Screenshot_" + timestamp + ".png";
File destfile = new File(dest);
destfile.getParentFile().mkdirs();
FileUtils.copyFile(src, destfile);
return destfile.getAbsolutePath();
}

public static String takePng(WebDriver driver, String msg) {
	// TODO Auto-generated method stub
	return null;
}
}

