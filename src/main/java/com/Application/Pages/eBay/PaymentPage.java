package com.Application.Pages.eBay;


import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.basic.utility.Constants;
import com.basic.utility.KeywordFunctions;

import io.appium.java_client.AppiumDriver;


public class PaymentPage extends KeywordFunctions{
	private static final Logger LOGGER = Logger.getLogger(PaymentPage.class);
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	public WebElement pageLoad;
	
	@FindBy(xpath="//*[contains(@text,'Credit Card')]")
	public WebElement creditCard;
	
	@FindBy(xpath="//android.widget.RadioButton[@text='American Express']")
	public WebElement cardType;
	
	@FindBy(xpath="//*[@resource-id='btnPay']")
	public WebElement payBtn;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[1]")
	public WebElement cardNumber;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[2]")
	public WebElement cardName;
	
	@FindBy(xpath="//android.widget.Spinner[@text='MM']")
	public WebElement expiryMonth;
	
	@FindBy(xpath="//android.widget.Spinner[@text='YY']")
	public WebElement expiryYear;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[3]")
	public WebElement cvv;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	public WebElement closePaymentPage;
	
	@FindBy(xpath="//*[@text='select address']")
	public WebElement selectAddress;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	public WebElement home;
	
	
	@FindBy(xpath="//android.widget.RadioButton")
	public WebElement UPIRadioBtn;
	
	@FindBy(xpath="//*[@resource-id='vpAddress']")
	public WebElement VPA;
	
	@FindBy(xpath="//*[@resource-id='vpAddress']//parent::android.view.View//following::android.view.View[1]/android.widget.Button")
	public WebElement makePaymentBtn;
	
	@FindBy(xpath="//*[@resource-id='vpAddress']//parent::android.view.View//preceding::android.view.View[1]")
	public WebElement errorMsg;
	

	
	public PaymentPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	
/**********************************************************************************************************************************	
	Name of Function		: navigateToCardPaymnetType
	Purpose of Function	: Navigate to Payment type
		
**********************************************************************************************************************************/	


	public Boolean navigateToCardPaymnetType(String paymentType) {
		testStepStatus=false;
		waitUntilInvisible(pageLoad);
		LOGGER.info("Proceeding with the payment type "+paymentType);
		switch (paymentType) {
		case "CreditCard":{
			clickOnText(Constants.creditCard);
			testStepStatus=true;
			break;
		}
		case "DebitCard":{
			clickOnText(Constants.debitCard);
			testStepStatus=true;
			break;
		}
		case "NetBanking":{
			clickOnText(Constants.netBanking);
			testStepStatus=true;
			break;
		}
		case "CreditCardEMI":{
			clickOnText(Constants.creditCard_EMI);
			testStepStatus=true;
			break;
		}
		case "UPI":{
			clickOnText(Constants.UPI);
			testStepStatus=true;
			break;
		}
		case "Wallets_CashCards":{
			clickOnText(Constants.wallets_CashCards);
			testStepStatus=true;
			break;
		}
		case "PhonePe_BHIM":{
			clickOnText(Constants.phonePe_BHIM);
			testStepStatus=true;
			break;
		}
		
		}
		return testStepStatus;
	}
	
/**********************************************************************************************************************************	
	Name of Function		: UPIPayment
	Purpose of Function	: Make UPI Paymenr
		
**********************************************************************************************************************************/	

	public boolean UPIPayment(String UPI) {
		
		testStepStatus=false;
		try {
			if(checkForVisiblity(UPIRadioBtn, driver)) {
				clickOn(driver, UPIRadioBtn);
				clickOn(driver, payBtn);
			}
			waitUntilInvisible(pageLoad);
			if(checkForVisiblity(VPA, driver)) {
				enterTextValue(VPA, UPI);
				MobileappRefresh(driver);
				sleep(2);
				clickOn(driver, makePaymentBtn);
			}
			sleep(3);
			if (checkForVisiblity(errorMsg,driver)) {
				LOGGER.error("The error occured is "+errorMsg.getAttribute("text"));
				LOGGER.info("Error message popped Up");
				testStepStatus=false;
			}else {
				LOGGER.info("Error message not popped Up");
				testStepStatus=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return testStepStatus;
	}


}


