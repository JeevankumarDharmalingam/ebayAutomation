package com.Application.Pages.eBay;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;

import com.basic.utility.KeywordFunctions;


public class LoginPage extends KeywordFunctions implements LoginImpl{
	private static final Logger LOGGER = Logger.getLogger(LoginPage.class);
	
	@FindBy(xpath="//android.widget.Button[contains(@text,'SIGN IN')]")
	private static WebElement signInBtn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/edit_text_username']")
	private WebElement userName;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/edit_text_password']")
	private WebElement password;
	
	@FindBy(xpath="//*[contains(@text,'Home')]")
	private WebElement homeBtn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/sign_in_alert_text_view']")
	private WebElement loginErrorMsg;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	private WebElement gridBtn;
	
	@FindBy(xpath="//android.widget.Button[contains(@text,'MAYBE LATER')]")
	private WebElement mayBeLater;
	
	@FindBy(xpath="//android.widget.Button[contains(@text,'NOT NOW')]")
	private WebElement notNow;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/textview_sign_in_status']")
	private WebElement signInStatus;
	
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
/* -------------------------------------------------------------------------------------------------------------
 	Method Functionality - Navigates to About under settings menu
	Author - Jeevankumar
	Date -  6th Jul 18
----------------------------------------------------------------------------------------------------------------*/
	public boolean logIn_Into_App(String usernameText, String passwordText){
		try {
			testStepStatus=false;
			if (checkForVisiblity(signInBtn, driver)) {
				if (checkForVisiblity(userName, driver)) {
					LOGGER.info("Landed in SignIn Page");
				}else {
					LOGGER.info("Sign In Button is Present");
					clickOn(driver,signInBtn);
				}
				if(checkForVisiblity(userName, driver)) {
					LOGGER.info("Logging in with UserName "+usernameText);
					enterTextValue(userName, usernameText);
					enterTextValue(password, passwordText);
					LOGGER.info("Credentials entered Successfully");
					clickOn(driver, signInBtn);
					LOGGER.info("Clicked on SignIn Button");
				}
				if (checkForVisiblity(mayBeLater, driver)) {
					clickOn(driver, mayBeLater);
				}else if (checkForVisiblity(notNow, driver)) {
					clickOn(driver, notNow);
				}
				
			}else {
				LOGGER.info("Sign In Button is Not Visible");
				return false;
			}
			if (checkForVisiblity(gridBtn, driver)) {
				clickOn(driver, gridBtn);
				LOGGER.info("Clicked on Menu Button");
				if (checkForVisiblity(homeBtn, driver)) {
					testStepStatus=true;
					clickOn(driver,homeBtn);
					LOGGER.info("User is logged in Successfully");
				}else{
					LOGGER.info("User is not logged in Successfully");
					return false;
				}
			}
			}catch (Exception e) {
				return false;
			}	
		return testStepStatus;
		}
	
	public boolean invalidLogin(String invalidUsernameText, String invalidPasswordText) {
		testStepStatus=false;
		try {
			if (checkForVisiblity(signInBtn, driver)) {
				LOGGER.info("Sign In Button is Present");
				clickOn(driver,signInBtn);
				if(checkForVisiblity(userName, driver)) {
					LOGGER.info("Logging in with UserName "+invalidUsernameText);
					enterTextValue(userName, invalidUsernameText);
					enterTextValue(password, invalidPasswordText);
					LOGGER.info("Credentials entered Successfully");
					clickOn(driver, signInBtn);
					LOGGER.info("Clicked on SignIn Button");
					
				}
				if (checkForVisiblity(loginErrorMsg, driver)) {
					System.out.println("Login Message"+loginErrorMsg.getAttribute("text"));
					LOGGER.info("Login is Failed due to invalid Credentials");
					testStepStatus=true;
				}
			}
		}catch (Exception e) {
				e.printStackTrace();
				return false;
			}	
		return testStepStatus;
	}
}


