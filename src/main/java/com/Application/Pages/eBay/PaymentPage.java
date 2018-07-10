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
	
	@FindBy(xpath=".//*[contains(@text,'Choose your payment method')]​")
	private WebElement paymentPage;
	
	@FindBy(xpath="//*[@resource-id='btnPay']")
	private WebElement payBtn;

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
	
/* -------------------------------------------------------------------------------------------------------------
 	Method Functionality - Navigate to the mentioned Payment type
	Author - Jeevankumar
	Date -  6th Jan 18
----------------------------------------------------------------------------------------------------------------*/
	public Boolean navigateToCardPaymnetType(String paymentType) {
		testStepStatus=false;
		wait(3);
		if(checkForVisiblity(paymentPage, driver)) {
			LOGGER.info("Landed in Payment Page");
		}
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
/* -------------------------------------------------------------------------------------------------------------
 	Method Functionality - This method handles UPI Payment Part
	Author - Jeevankumar
	Date -  6th Jan 18
----------------------------------------------------------------------------------------------------------------*/
	public boolean UPIPayment(String UPI) {
		testStepStatus=false;
		try {
			if(checkForVisiblity(UPIRadioBtn, driver)) {
				clickOn(driver, UPIRadioBtn);
				clickOn(driver, payBtn);
			}
			wait(4);
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


