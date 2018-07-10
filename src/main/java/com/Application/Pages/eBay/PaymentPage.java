package com.Application.Pages.eBay;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.basic.utility.Constants;
import com.basic.utility.KeywordFunctions;

public class PaymentPage extends KeywordFunctions{
	private static final Logger LOGGER = Logger.getLogger(PaymentPage.class);
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	private WebElement pageLoad;
	
	@FindBy(xpath="//*[contains(@text,'Credit Card')]")
	private WebElement creditCard;
	
	@FindBy(xpath="//android.widget.RadioButton[@text='American Express']")
	private WebElement cardType;
	
	@FindBy(xpath="//*[@resource-id='btnPay']")
	private WebElement payBtn;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[1]")
	private WebElement cardNumber;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[2]")
	private WebElement cardName;
	
	@FindBy(xpath="//android.widget.Spinner[@text='MM']")
	private WebElement expiryMonth;
	
	@FindBy(xpath="//android.widget.Spinner[@text='YY']")
	private WebElement expiryYear;
	
	@FindBy(xpath="(//*[@id='CreditCardDetailsForm']/*/*[@class='android.widget.EditText'])[3]")
	private WebElement cvv;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	private WebElement closePaymentPage;
	
	@FindBy(xpath="//*[@text='select address']")
	private WebElement selectAddress;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/home']")
	private WebElement home;
	
	
	@FindBy(xpath="//android.widget.RadioButton")
	private WebElement UPIRadioBtn;
	
	@FindBy(xpath="//*[@resource-id='vpAddress']")
	private WebElement VPA;
	
	@FindBy(xpath="//*[@resource-id='vpAddress']//parent::android.view.View//following::android.view.View[1]/android.widget.Button")
	private WebElement makePaymentBtn;
	
	@FindBy(xpath="//*[@resource-id='vpAddress']//parent::android.view.View//preceding::android.view.View[1]")
	private WebElement errorMsg;
	

	
	public PaymentPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	
	}
	
/**********************************************************************************************************************************	
	Name of Function		: navigateToCardPaymnetType
	Description	: Navigate to Payment type
		
**********************************************************************************************************************************/	


	public Boolean navigateToCardPaymnetType(String paymentType) {
		testStepStatus=false;
		waitUntilInvisible(pageLoad);
		try {
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
		} catch (Exception e) {
			return testStepStatus=false;
		}
		return testStepStatus;
	}
/**********************************************************************************************************************************	
	Name of Function		: UPIPayment
	Description	: Make UPI Paymenr
		
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
				wait(1);
				clickOn(driver, makePaymentBtn);
				if (checkForVisiblity(makePaymentBtn, driver)) {
					clickOn(driver, makePaymentBtn);
				}
			}
			wait(4);
			if (checkForVisiblity(errorMsg,driver)) {
				LOGGER.error("The error occured is "+errorMsg.getAttribute("text"));
				LOGGER.info("Error message popped Up");
				testStepStatus=false;
			}else {
				LOGGER.info("Error message not popped Up");
				testStepStatus=true;
			}
		} catch (Exception e) {
			return testStepStatus=false;
		}
		return testStepStatus;
	}


}


