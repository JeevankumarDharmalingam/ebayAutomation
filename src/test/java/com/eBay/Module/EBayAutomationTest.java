package com.eBay.Module;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Application.Pages.eBay.LoginPage;
import com.Application.Pages.eBay.PaymentPage;
import com.Application.Pages.eBay.ProductPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.basic.utility.Constants;
import com.basic.utility.KeywordFunctions;
import com.basic.utility.ParentTest;


import dataUtility.TestDataClass;

public class EBayAutomationTest extends ParentTest{
	
	private static final Logger LOGGER = Logger.getLogger(EBayAutomationTest.class);
	TestDataClass testData;
	
	@Test(priority=0)
	public void eBay_Login() throws Exception {
		try{
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		startReportingTest(methodName);
		LoginPage eB=new LoginPage(driver);
		testStepStatus=eB.LogIn(driver,testData.getUserName(),testData.getPassWord());
		if (testStepStatus) {
			logger.log(Status.PASS, "User logged in successfully with the following Username :"+testData.getUserName()+" and password :"+testData.getPassWord());
			assertTrue(testStepStatus);
		}else{
			logger.log(Status.FAIL, "User is not logged in successfully with the following Username :"+testData.getUserName()+" and password :"+testData.getPassWord());
			assertEquals(testStepStatus, true);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(priority=1,dependsOnMethods={"eBay_Login"})
	public void searchAndPlaceOrder() throws Exception {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		startReportingTest(methodName);
		ProductPage eBPP=new ProductPage(driver);
		try {
			testStepStatus=eBPP.searchProduct(driver,testData.getSearchText());
			
			if (testStepStatus) {
				logger.log(Status.PASS, "Search operation was successful with the following search data :"+testData.getSearchText());
				assertTrue(testStepStatus);
			}else{
				logger.log(Status.FAIL, "Search operation was unsuccessful with the following search data :"+testData.getSearchText());
				assertEquals(testStepStatus, true);
			}
			
			testStepStatus=eBPP.calibratePriceFilter(testData.getMinPrice(), testData.getMaxPrice());
			if (testStepStatus) {
				logger.log(Status.PASS, "Filter is set between range "+testData.getMinPrice()+" and "+testData.getMaxPrice());
				assertTrue(testStepStatus);
			}else{
				logger.log(Status.FAIL, "Setting price range filter is failed");
				assertEquals(testStepStatus, true);
			}
			
			testStepStatus=eBPP.selectingSearchResults(driver);
			if (testStepStatus) {
				logger.log(Status.PASS, "Selection of random product from the search results was successful");
				assertTrue(testStepStatus);
			}else{
				logger.log(Status.FAIL, "Selection of random product from the search results was unsuccessful");
				assertEquals(testStepStatus, true);
			}
			
			testStepStatus=eBPP.verifyProductPage(driver);
			if (testStepStatus) {
				logger.log(Status.PASS, "Order was reviewed and successfully proceeded to checkout page");
				assertTrue(testStepStatus);
			}else{
				logger.log(Status.FAIL, "Order was not proceeded till payment page");
				assertEquals(testStepStatus, true);
			}
			testStepStatus=eBPP.verifyCheckoutPage(driver);
			if (testStepStatus) {
				logger.log(Status.PASS, "Order was reviewed and information is verified and then successfully proceeded till payment page");
				assertTrue(testStepStatus);
			}else{
				logger.log(Status.FAIL, "Order was not proceeded till payment page");
				assertEquals(testStepStatus, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(priority=2,dependsOnMethods={"searchAndPlaceOrder"})
	public void paymentValidation() {
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		startReportingTest(methodName);
		PaymentPage payment=new PaymentPage(driver);
		testStepStatus=payment.navigateToCardPaymnetType(testData.getPaymentType());
		//testStepStatus=payment.processCardPayment(driver,testData.getCreditCardNumber(),testData.getCreditCardName(),testData.getCreditCardExpiryMonth(),testData.getCreditCardExpiryYear(),testData.getCreditCardCvv());
		if (testStepStatus) {
			logger.log(Status.PASS, "Payment navigation was successful");
			//assertTrue(stepResult);
		}else{
			logger.log(Status.FAIL, "Payment navigation was unsuccessful as payment details were not given");
			assertEquals(testStepStatus, true);
		}
		testStepStatus=payment.UPIPayment(testData.getUPI());
		//testStepStatus=payment.processCardPayment(driver,testData.getCreditCardNumber(),testData.getCreditCardName(),testData.getCreditCardExpiryMonth(),testData.getCreditCardExpiryYear(),testData.getCreditCardCvv());
		if (testStepStatus) {
			logger.log(Status.PASS, "Payment operation through UPI was successful");
			//assertTrue(stepResult);
		}else{
			logger.log(Status.FAIL, "Payment operation through UPI was unsuccessful as payment details were incorrect");
			assertEquals(testStepStatus, true);
		}
	}
	@BeforeSuite
	public void suite(ITestContext ctx) throws Exception{
		try{
		LOGGER.info("Execution of Suite "+ctx.getSuite().getName()+" Started");
		driver = getDriver();
		LOGGER.info(application+" is Successfully launched");
		
		}catch (Exception e) {
			e.printStackTrace();
		}			
	}
	@BeforeTest(alwaysRun=true)
	 public void startReport(ITestContext testContext) throws Exception{
		try {
			System.out.println("Test Name "+testContext.getName());
			testData=new TestDataClass(testContext.getName());
			startReportingInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	 }
	@AfterMethod
	public void getResult(ITestResult result){
		try {
			if(result.getStatus() == ITestResult.FAILURE){
				String temp=KeywordFunctions.getScreenshot(driver);
				logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@AfterTest
	public void endReport(){
		LOGGER.info("Report is getting pushed");
	               extent.flush();
	   }
	@AfterSuite
	public void quitDriver(){
		LOGGER.info("Driver is getting closed");
	               driver.quit();
	   }
}




