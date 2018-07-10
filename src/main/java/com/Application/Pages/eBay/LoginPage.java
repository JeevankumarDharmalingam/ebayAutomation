package com.Application.Pages.eBay;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.basic.utility.KeywordFunctions;


public class LoginPage extends KeywordFunctions{
	private static final Logger LOGGER = Logger.getLogger(LoginPage.class);
	
	@FindBy(xpath="//android.widget.Button[@text='SIGN IN']")
	private static WebElement sign_In_Btn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/edit_text_username']")
	private WebElement userName;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/edit_text_password']")
	private WebElement password;
	
	@FindBy(xpath="//*[@text='Home']")
	private WebElement homeBtn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	private WebElement homeButton;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	private WebElement pageLoad;
	
	@FindBy(xpath="//android.widget.Button[@text='MAYBE LATER']")
	private WebElement mayBeLater;
	
	@FindBy(xpath="//android.widget.Button[@text='NOT NOW']")
	private WebElement notNow;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/textview_sign_in_status']")
	private WebElement signInStatus;
	
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
/**********************************************************************************************************************************	
	Name of Function		: LogIn
	Purpose of Function	: To Log in with the given username and password and verify if the LogIn is successful
		
**********************************************************************************************************************************/	
public boolean LogIn(WebDriver driver,String usernameText, String passwordText) throws Exception {
		
		
		try {
			testStepStatus = false;
			if (checkForVisiblity(sign_In_Btn, driver)) {
				LOGGER.info("Sign In Button is Visible");
				clickOn(driver,sign_In_Btn);
				waitUntilInvisible(pageLoad);
				if(checkForVisiblity(userName, driver)) {
					LOGGER.info("Logging in with UserName "+usernameText);
					enterTextValue(userName, usernameText);
					enterTextValue(password, passwordText);
				
					clickOn(driver, sign_In_Btn);
					LOGGER.info("Clicked on SignIn Button");
				}
				if (checkForVisiblity(mayBeLater, driver)) {
					clickOn(driver, mayBeLater);
				}
				if (checkForVisiblity(notNow, driver)) {
					clickOn(driver, notNow);
				}
			}else {
				LOGGER.info("Sign In Button is Not Visible");
			}
			waitUntilInvisible(pageLoad);
			if (checkForVisiblity(homeButton, driver)) {
				clickOn(driver, homeButton);
				if (checkForVisiblity(signInStatus, driver)) {
					testStepStatus=true;
					clickOn(driver,homeBtn);
					LOGGER.info("User is logged in Successfully");
				}else{
					LOGGER.info("User is not logged in Successfully");
				}
			}
			}catch (Exception e) {
				return testStepStatus=false;
			}	
		return testStepStatus;
		}
}


