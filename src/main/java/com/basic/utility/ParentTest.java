package com.basic.utility;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Map;
import java.util.Properties;


import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;

public class ParentTest {
	protected static final Logger LOGGER = Logger.getLogger(ParentTest.class);
	public static WebDriver driver=null;
	public String application=System.getProperty("application");
	private DeviceDataClass deviceData;
	public boolean testStepStatus;
	protected ExtentHtmlReporter htmlReporter;
	protected ExtentReports extent;
	protected ExtentTest logger;
	

	public WebDriver getBaseDriver() {
		return driver;
	}
	
	 public ParentTest() {
			 deviceData=new DeviceDataClass(application);
	}
	 public void startReportingInstance() {
		 	htmlReporter = new ExtentHtmlReporter(Constants.reportPath);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
	}
	
	 public void startReportingTest(String methodInstance) {
		 logger = extent.createTest(methodInstance);	
	}
	
public  WebDriver getDriver() throws InterruptedException, Exception {
	
	
		try {
					DesiredCapabilities capability =new DesiredCapabilities();
					capability .setCapability("autoAcceptAlerts",true);
					capability.setCapability("platformName",deviceData.getOsName());
					capability.setCapability("deviceName", deviceData.getUDID());
					capability.setCapability("platformVersion",deviceData.getOsVersion());
					capability.setCapability("newCommandTimeout",300);
					capability.setCapability("noReset",false);
					capability.setCapability("fullReset",true);
					capability.setCapability("setWebContentsDebuggingEnabled",true);
					capability.setCapability("app", new File(Constants.applicationPath));
					capability.setCapability("appPackage",deviceData.getAppPackage());
					capability.setCapability("appActivity",deviceData.getAppActivity());
					LOGGER.info("Driver instantiated with port no "+deviceData.getPortNo());
					driver = new AndroidDriver(new URL("http://0.0.0.0:"+deviceData.getPortNo()+"/wd/hub"),capability);
		}catch(Exception e) {
			e.printStackTrace();
			
			
		}
		return driver;
	}



}
