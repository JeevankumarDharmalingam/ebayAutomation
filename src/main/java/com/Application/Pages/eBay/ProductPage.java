package com.Application.Pages.eBay;


import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.basic.utility.KeywordFunctions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;


public class ProductPage extends KeywordFunctions{
	private static final Logger LOGGER = Logger.getLogger(ProductPage.class);
	private static String productName,itemPrice;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/search_box']")
	private WebElement searchBar;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/text_slot_1']")
	private WebElement msg_Info;

	@FindBy(xpath="//android.widget.RelativeLayout[3]//*[@resource-id='com.ebay.mobile:id/textview_item_price']")
	private WebElement searchSelect;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/textview_item_count']")
	private WebElement searchResults;
	
	@FindBy(xpath="//android.widget.Button[contains(@text,'BUY IT NOW'])")
	private WebElement buyItNowBtn;
	
	@FindBy(xpath="//android.widget.Button[contains(@text,'REVIEW'])")
	private WebElement reviewBtn;
	
	@FindBy(xpath="//android.widget.Button[contains(@text,'Proceed to Pay'])")
	private WebElement proceedToPayBtn;
	
	@FindBy(xpath="//*[contains(@text,'FILTER')]")
	private WebElement filterBtn;
	
	@FindBy(xpath="//*[contains(@text,'Price range')]")
	private WebElement priceRange;
	
	@FindBy(xpath="//*[contains(@text,'Custom price range')]")
	private WebElement customPriceRange;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/minimum_price_view']")
	private WebElement customMinPriceRange;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/maximum_price_view']")
	private WebElement customMaxPriceRange;
	
	@FindBy(xpath="//android.widget.Button[@text='OK']")
	private WebElement filterOkBtn;
	
	@FindBy(xpath="//android.widget.Button[@text='DONE']")
	private WebElement filterDoneBtn;
	
	@FindBy(xpath="//*[@resource-id='com.ebay.mobile:id/progress_bar']")
	private WebElement pageLoadIcon;
	
	@FindAll(@FindBy(xpath = "//*[@resource-id='com.ebay.mobile:id/cell_collection_item']"))
	private List<WebElement> totalResults;
	
	public ProductPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	

/**********************************************************************************************************************************	
	Name of Function	: searchProduct
	Description	: To search product with the given data
		
**********************************************************************************************************************************/	
public boolean searchProduct(WebDriver driver, String searchText) throws Exception {
		
		
		try {
			testStepStatus = false;
			
				if (checkForVisiblity(searchBar, driver)) {
					enterTextValue(searchBar, searchText);
					((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
				}			
				if (checkForVisiblity(pageLoadIcon, driver)) {
					wait(5);
				}				
				if (checkForVisiblity(msg_Info, driver)) {
					clickOn(driver, msg_Info);
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
				return testStepStatus=false;
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
		if (checkForVisiblity(msg_Info, driver)) {
			clickOn(driver, msg_Info);
		}
		int minVal=1;
		int maxVal=totalResults.size();
		LOGGER.info("Maximum results in page "+maxVal);
		int randomResult=(int) (Math.random()*(maxVal-minVal))+minVal;
		if (randomResult==1) {
			swipe(driver, "up", "medium");
		}
		LOGGER.info("Going to select "+randomResult);
		WebElement itemName=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/textview_item_title']"));
		productName=itemName.getAttribute("text");
		itemPrice=driver.findElement(By.xpath("//*[@resource-id='com.ebay.mobile:id/recycler']//android.widget.RelativeLayout["+randomResult+"]//android.widget.RelativeLayout[1]//*[@resource-id='com.ebay.mobile:id/textview_item_price']")).getAttribute("text");	
		clickOn(driver, itemName);
		waitUntilInvisible(pageLoadIcon);
		LOGGER.info("Selected product's Name is : "+productName);
		LOGGER.info("Selected product's price is : "+itemPrice);
		testStepStatus=true;
	}catch (Exception e) {
		return testStepStatus=false;
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
		waitUntilInvisible(pageLoadIcon);
		driver.getPageSource();
		WebElement itemName=elemantToText(driver, productName);
		WebElement productPrice=elemantToText(driver, itemPrice);
		if (checkForVisiblity(itemName, driver) && checkForVisiblity(productPrice, driver))  {
			testStepStatus=true;
			LOGGER.info("The product name "+itemName+" matches with the details in the  product page");
		}else{
			LOGGER.info("The product name "+itemName+" do not matches with the details in the  product page");
		}
		
		if (checkForVisiblity(buyItNowBtn, driver)) {
			LOGGER.info("buy It Now is Present");
			clickOn(driver, buyItNowBtn);
			if (checkForVisiblity(reviewBtn, driver) & testStepStatus) {
				LOGGER.info("reviewBtn is Present");
				clickOn(driver, reviewBtn);
			}else {
				LOGGER.info("Product information doesn't match");
			}
			waitUntilInvisible(pageLoadIcon);
		}
		
		
		
	}catch (Exception e) {
			return testStepStatus=false;
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
		waitUntilInvisible(pageLoadIcon);
		driver.getPageSource();
		swipe(driver, "up", "fast");
		swipe(driver, "up", "fast");
		//MobileappRefresh(driver);
		wait(2);
		WebElement itemName=elemantToText(driver, productName.substring(3, 26));
		WebElement productPrice=elemantToText(driver, itemPrice.substring(2));
		if (checkForVisiblity(itemName, driver) && checkForVisiblity(productPrice, driver))  {
			testStepStatus=true;
			LOGGER.info("The product name "+itemName+" matches with the details in the checkout page");
		}else{
			LOGGER.info("The product name "+itemName+" do not matches with the details in the checkout page");
		}
		if (checkForVisiblity(proceedToPayBtn, driver) & testStepStatus) {
			LOGGER.info("Proceed to Pay is Present");
			clickOn(driver, proceedToPayBtn);
			testStepStatus=true;
		}
		
	}catch (Exception e) {
		return testStepStatus=false;
		}
		
return testStepStatus;
		}

/**********************************************************************************************************************************	
Name of Function	: calibratePriceFilter
Description	: To set a price filterBtn for the search results, 
					  this method will make sure the results are displayed only for the given price range.
	
**********************************************************************************************************************************/	

public boolean calibratePriceFilter(String minPrice, String maxPrice) throws Exception {
	
	try {
		testStepStatus = false;
		if (checkForVisiblity(filterBtn, driver)) {
			clickOn(driver, filterBtn);
			LOGGER.info("Going to Calibrate Filter");
			if (checkForVisiblity(priceRange, driver)) {
				clickOn(driver, priceRange);
				clickOn(driver, customPriceRange);
				if (checkForVisiblity(customMinPriceRange, driver)) {
					customMinPriceRange.sendKeys(minPrice);
					clickOn(driver, customMaxPriceRange);
					customMaxPriceRange.sendKeys(maxPrice);
				}
				if (checkForVisiblity(filterOkBtn, driver)) 
					clickOn(driver, filterOkBtn);
				if (checkForVisiblity(filterDoneBtn, driver)) {
					clickOn(driver, filterDoneBtn);
					LOGGER.info("Filter Calibrated");
					testStepStatus=true;
					waitUntilInvisible(pageLoadIcon);
				}else {
					LOGGER.info("Filter Done Button is not clicked");
				}
				
				
			}
		}	
	
	}catch (Exception e) {
		return testStepStatus=false;
		}
		
return testStepStatus;
		}

}


