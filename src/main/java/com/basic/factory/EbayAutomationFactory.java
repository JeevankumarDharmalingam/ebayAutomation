package com.basic.factory;

import org.openqa.selenium.WebDriver;

import com.Application.Pages.eBay.LoginImpl;
import com.Application.Pages.eBay.LoginPage;
import com.Application.Pages.eBay.PaymentImpl;
import com.Application.Pages.eBay.PaymentPage;
import com.Application.Pages.eBay.ProductImpl;
import com.Application.Pages.eBay.ProductPage;

public class EbayAutomationFactory {
	
	public static LoginImpl createLoginPageInstance(WebDriver driver) {
		return new LoginPage(driver);
	}
	
	public static ProductImpl createProductPageInstance(WebDriver driver) {
		return new ProductPage(driver);
	}
	public static PaymentImpl createPaymentPageInstance(WebDriver driver) {
		return new PaymentPage(driver);
	}
}
