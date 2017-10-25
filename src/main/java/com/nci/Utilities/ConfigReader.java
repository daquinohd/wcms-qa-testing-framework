package com.nci.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


/*To read the Property file*/
public class ConfigReader {
	Properties properties;
	
	public ConfigReader(){
		try{
			File file= new File ("./configuration/ConfigPROD.property");
			FileInputStream fis= new FileInputStream(file);
			properties= new Properties();
    		properties.load(fis);				
		} catch (Exception e) {
			System.out.println("Exception:" +e.getMessage());
		}
		
	}

	public String getPageURL(String pageURL){
		return properties.getProperty(pageURL);
	}
	
	public String getDriverPath(String driverPath){
		return properties.getProperty(driverPath);
	}
}
