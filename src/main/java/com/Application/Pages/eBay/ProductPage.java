package com.Application.Pages.eBay;


import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.basic.utility.KeywordFunctions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;


public class ProductPage extends KeywordFunctions{
	private static final Logger LOGGER = Logger.getLogger(ProductPage.class);
	public static int searchVal,randomResult;
	public static String productName,itemDesc,itemPrice;
	
	public static String usernameText,passwordText;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/search_box']")
	public WebElement searchBox;
	
	@FindBy(xpath="//*[@text='Home']")
	public WebElement home;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/text_slot_1']")
	public WebElement text_Slot;

	@FindBy(xpath="//android.widget.RelativeLayout[3]//*[@resource-id='com.ebay.mobile:id/textview_item_price']")
	public WebElement searchSelect;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/textview_item_count']")
	public WebElement searchResults;
	
	@FindBy(xpath="//android.widget.Button[@text='BUY IT NOW']")
	public WebElement buyItNow;
	
	@FindBy(xpath="//android.widget.Button[@text='REVIEW']")
	public WebElement review;
	
	@FindBy(xpath="//android.widget.Button[@text='Proceed to Pay']")
	public WebElement proceedToPay;
	
	@FindBy(xpath="//*[contains(@text,'FILTER')]")
	public WebElement filter;
	
	@FindBy(xpath="//*[contains(@text,'Price range')]")
	public WebElement priceRange;
	
	@FindBy(xpath="//*[contains(@text,'Custom price range')]")
	public WebElement customPriceRange;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/minimum_price_view']")
	public WebElement customMinPriceRange;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/maximum_price_view']")
	public WebElement customMaxPriceRange;
	
	@FindBy(xpath="//android.widget.Button[@text='OK']")
	public WebElement filterOkBtn;
	
	@FindBy(xpath="//android.widget.Button[@text='DONE']")
	public WebElement filterDoneBtn;
	
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
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	public WebElement pageLoad;
	
	@FindAll(@FindBy(xpath = "//*[@resource-id='com.ebay.mobile:id/cell_collection_item']"))
	List<WebElement> totalResults;
	
	public ProductPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	
	}
	

/**********************************************************************************************************************************	
	Name of Function	: searchProduct
	Description	: To search product with the given data
		
**********************************************************************************************************************************/	

public boolean searchProduct(WebDriver driver, String searchText) throws Exception {
		
		
		try {
			testStepStatus = false;
			
				if (checkForVisiblity(searchBox, driver)) {
					enterTextValue(searchBox, searchText);
					((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
				}
				
				if (checkForVisiblity(pageLoad, driver)) {
					sleep(5);
				}
				
				if (checkForVisiblity(text_Slot, driver)) {
					clickOn(driver, text_Slot);
				}
					String searchResult=searchResults.getAttribute("text");
					String[] resVal=searchResult.split(" ");
					int searchVal=Integer.parseInt(resVal[0]);
						if (searchVal>0) {
							testStepStatus=true;
							LOGGER.info(searchVal+ " Results are displayed for search text entered");
						}else{
							LOGGER.info("No Results are displayed for search text");
						}
					
			}catch (Exception e) {
				e.printStackTrace();
			}
			
	return testStepStatus;
			}

/**********************************************************************************************************************************	
Name of Function	: selectingSearchResults
Description	: To randomly select any one of the search result except the first and last result
	
**********************************************************************************************************************************/	

public boolean selectingSearchResults(WebDriver driver) throws Exception {
	
	
	try {
		testStepStatus = false;
		if (checkForVisiblity(text_Slot, driver)) {
			clickOn(driver, text_Slot);
		}
		int minVal=3;
		int maxVal=totalResults.size();
		LOGGER.info(maxVal);
		int randomResult=(int) (Math.random()*(maxVal-minVal))+minVal;
		if (randomResult==1) {
			swipe(driver, "up", "medium");
		}
		LOGGER.info(randomResult);
		WebElement itemName=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/textview_item_title']"));
		productName=itemName.getAttribute("text");
		itemDesc=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/shipping_text']")).getAttribute("text");
		itemPrice=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/textview_item_price']")).getAttribute("text");	
		clickOn(driver, itemName);
		waitUntilInvisible(pageLoad);
		LOGGER.info("Selected product's Name is : "+productName);
		LOGGER.info("Selected product's price is : "+itemPrice);
		testStepStatus=true;
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return testStepStatus;
		}

/**********************************************************************************************************************************	
Name of Function	: calibratePriceFilter
Description	: To set a price filter for the search results, 
					  this method will make sure the results are displayed only for the given price range.
	
**********************************************************************************************************************************/	

public boolean calibratePriceFilter(String minPrice, String maxPrice) throws Exception {
	
	try {
		testStepStatus = false;
		if (checkForVisiblity(filter, driver)) {
			clickOn(driver, filter);
			if (checkForVisiblity(priceRange, driver)) {
				clickOn(driver, priceRange);
				clickOn(driver, customPriceRange);
				if (checkForVisiblity(customMinPriceRange, driver)) {
					customMinPriceRange.sendKeys(minPrice);
					clickOn(driver, customMaxPriceRange);
					customMaxPriceRange.sendKeys(maxPrice);
				}
				//((AndroidDriver) driver).getPageSource();
				if (checkForVisiblity(filterOkBtn, driver)) 
					clickOn(driver, filterOkBtn);
				if (checkForVisiblity(filterDoneBtn, driver)) {
					clickOn(driver, filterDoneBtn);
					testStepStatus=true;
					waitUntilInvisible(pageLoad);
				}else {
					LOGGER.info("Filter Done Button is not clicked");
				}
				
				
			}
		}	
	
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return testStepStatus;
		}

/**********************************************************************************************************************************	
Name of Function	: placeOrder
Description	: To take the order entry till payment window
	
**********************************************************************************************************************************/	

public boolean verifyProductPage(WebDriver driver) throws Exception {
	
	
	try {
		testStepStatus = false;
		waitUntilInvisible(pageLoad);
		driver.getPageSource();
		WebElement itemName=driver.findElement(By.xpath("//*[contains(@text,'"+productName+"')]"));
		WebElement productPrice =driver.findElement(By.xpath("//*[contains(@text,'"+itemPrice+"')]"));
		if (checkForVisiblity(itemName, driver) && checkForVisiblity(productPrice, driver))  {
			testStepStatus=true;
			LOGGER.info("The product name "+itemName+" matches with the details in the  product page");
		}else{
			LOGGER.info("The product name "+itemName+" do not matches with the details in the  product page");
		}
		
		if (checkForVisiblity(buyItNow, driver)) {
			LOGGER.info("buy It Now is Present");
			clickOn(driver, buyItNow);
			if (checkForVisiblity(review, driver) & testStepStatus) {
				LOGGER.info("review is Present");
				clickOn(driver, review);
			}else {
				LOGGER.info("Product information doesn't match");
			}
			waitUntilInvisible(pageLoad);
		}
		
		
		
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return testStepStatus;
		}

/**********************************************************************************************************************************	
Name of Function	: verifyCheckoutPage
Description	: To verfiy product in checkout Page and take the order entry till payment window
	
**********************************************************************************************************************************/	

public boolean verifyCheckoutPage(WebDriver driver) throws Exception {
	
	
	try {
		testStepStatus = false;
		waitUntilInvisible(pageLoad);
		driver.getPageSource();
		swipe(driver, "up", "fast");
		swipe(driver, "up", "fast");
		MobileappRefresh(driver);
		WebElement itemName=driver.findElement(By.xpath("//*[contains(@text,'"+productName.substring(2)+"')]"));
		WebElement productPrice =driver.findElement(By.xpath("//*[contains(@text,'"+itemPrice.substring(4)+"')]"));
		if (checkForVisiblity(itemName, driver) && checkForVisiblity(productPrice, driver))  {
			testStepStatus=true;
			LOGGER.info("The product name "+itemName+" matches with the details in the checkout page");
		}else{
			LOGGER.info("The product name "+itemName+" do not matches with the details in the checkout page");
		}
		if (checkForVisiblity(proceedToPay, driver)) {
			LOGGER.info("Proceed to Pay is Present");
			clickOn(driver, proceedToPay);
			testStepStatus=true;
		}
		
	}catch (Exception e) {
			e.printStackTrace();
		}
		
return testStepStatus;
		}

}


