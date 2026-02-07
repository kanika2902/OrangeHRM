package com.orhrm.utilities;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
 
public class configreader {
	
	static String projectpath=System.getProperty("user.dir");
	
	public static String getproperty(String key) throws IOException
		{
		Properties prob=new Properties();
		  File f2=new File(projectpath+"\\input.properties");
		  FileInputStream fs=new FileInputStream(f2);
		  prob.load(fs);
		  return prob.getProperty(key);
	}
 
}