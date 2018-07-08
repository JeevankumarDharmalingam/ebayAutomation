package com.Application.Pages.eBay;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.basic.utility.KeywordFunctions;


public class LoginPage extends KeywordFunctions{
	private static final Logger LOGGER = Logger.getLogger(LoginPage.class);
	
	public static String usernameText,passwordText;
	
	@FindBy(xpath="//android.widget.Button[@text='SIGN IN']")
	public static WebElement sign_In_Btn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/edit_text_username']")
	public WebElement userName;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/edit_text_password']")
	public WebElement password;
	
	@FindBy(xpath="//*[@text='Home']")
	public WebElement homeBtn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	public WebElement homeButton;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	public WebElement pageLoad;
	
	@FindBy(xpath="//android.widget.Button[@text='MAYBE LATER']")
	public WebElement mayBeLater;
	
	@FindBy(xpath="//android.widget.Button[@text='NOT NOW']")
	public WebElement notNow;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/textview_sign_in_status']")
	public WebElement signInStatus;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	
/**********************************************************************************************************************************	
	Method Name			: SignIn
	Purpose of Method	: To sign in with the given username and password and verify if the sign in was successful
		
**********************************************************************************************************************************/	

	@SuppressWarnings({ "deprecation", "rawtypes" })
public boolean SignIn(WebDriver driver,String usernameText, String passwordText) throws Exception {
		
		
		try {
			testStepStatus = false;
			//waitForInvisibility(pageLoad);
			if (checkForVisiblity(sign_In_Btn, driver)) {
				LOGGER.info("Sign In Button is Visible");
				clickOn(driver,sign_In_Btn);
				waitUntilInvisible(pageLoad);
				
					LOGGER.info("Logging in with UserName "+usernameText);
					enterTextValue(userName, usernameText);
					enterTextValue(password, passwordText);
				
					clickOn(driver, sign_In_Btn);
					//waitForInvisibility(pageLoad);
				
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
				e.printStackTrace();
			}	
		return testStepStatus;
		}
}


