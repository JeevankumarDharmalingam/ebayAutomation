package com.basic.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;

//extending mobile base test class to use update twb results method to update reports in internal methods
public class KeywordFunctions extends ParentTest{
	private static final int TIMEOUT_IN_SECONDS = 40;
	private static final Logger LOGGER = Logger.getLogger(KeywordFunctions.class);
	WebDriverWait wait;
	public static long max=60;
	public static long med=30;
	public static long min=10;
	public String SelectedPage=null;
	public boolean testStepStatus;
	
	
	public KeywordFunctions() {
	}

	
/**********************************************************************************************************************************	
	Purpose of Method: To wait until the page loads
		
**********************************************************************************************************************************/	

	public static boolean waitForPageLoad(WebDriver driver){
		LOGGER.info(new Object(){}.getClass().getEnclosingMethod().getName());
		boolean waitValue=false;
		try {
			driver.manage().timeouts().pageLoadTimeout(max, TimeUnit.SECONDS);
			LOGGER.info("Page Loaded completely");
			waitValue=true;
		}catch(TimeoutException e){
			LOGGER.info("Page taking longer than "+ max +" seconds time to load");
			waitValue=true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return waitValue;
	}
	
/**********************************************************************************************************************************	
	Purpose of Method: To wait for any desired period of time
		
**********************************************************************************************************************************/	

	protected static void wait(int timeOutInSeconds) {
		LOGGER.info("Wait for "+timeOutInSeconds+" seconds");
		try {
			Thread.sleep(timeOutInSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
/**********************************************************************************************************************************	
	Purpose of Method: To click on any desired object
		
**********************************************************************************************************************************/	

	protected void clickOnElement(WebDriver driver,By element) {
		LOGGER.info(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+element.toString());
		try{
			WebElement element1=driver.findElement(element);
			element1.click();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
/**********************************************************************************************************************************	
	Purpose of Method: To click on any desired object
		
**********************************************************************************************************************************/	

	protected void clickOn(WebDriver driver,WebElement element) {
		try{
			WebElement el=element;
			el.click();
		}catch(Exception e){
			e.printStackTrace();
			WebElement e2=element;
			e2.click();
		}
	}
	


	protected void clickOnText(String textToFind) {
		try{
			WebElement el=driver.findElement(By.xpath("//*[@text='"+textToFind+"']"));
			el.click();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
/**********************************************************************************************************************************	
	Purpose of Method: To type any desired text into an object
		
**********************************************************************************************************************************/	

	protected void enterTextValue(WebElement element, String text) {
		LOGGER.info("Starting to Enter Text Value");
		element.sendKeys(new CharSequence[] { text });
		LOGGER.info("Entered " + text + " Successfully");
	}
	


/**********************************************************************************************************************************	
	Purpose of Method: To check if an object is present in the focused app page
		
**********************************************************************************************************************************/	

	protected static boolean checkForVisiblity(WebElement locator, WebDriver driver)
    {

        boolean waitValue = false;
        try {
            waitValue = new WebDriverWait(driver, 8)
                    .until(ExpectedConditions.visibilityOf(locator)).isDisplayed();
            
        } catch (Exception e) {
        }
        return waitValue;

    }
	
	protected static boolean isElementVisible(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
	        wait.until(ExpectedConditions.visibilityOf(element));
		}catch (Exception e) {
			e.getMessage();
		}
		return false;
	}
	protected static boolean checkForVisiblityOfText(String textToFind)
    {

        boolean waitValue = false;
        try {
     	  waitValue = new WebDriverWait(driver, 8)
                      .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@text,'"+textToFind+"')]")))).isDisplayed();
            
        } catch (Exception e) {
        }
        return waitValue;

    }
/**********************************************************************************************************************************	
Purpose of Method: To Swipe in any desired direction and speed
	
**********************************************************************************************************************************/	

	public void swipe(WebDriver driver,String direction, String speed) throws InterruptedException
	{
		try{

			direction=direction.toLowerCase();
			Dimension size;
			int starty,endy,startx,scrWidth,scrHeight,rateToSwipe;			
			size=driver.manage().window().getSize();
			scrWidth=size.getWidth();
			scrHeight=size.getHeight();
			startx=(int)(size.width*0.5);
			starty=(int)(size.height*0.5);
			String sDir;
			sDir=direction.toLowerCase();
			speed=speed.toLowerCase();
			if (speed=="slow") {
				rateToSwipe=250;
			}else if(speed=="medium"){
				rateToSwipe=150;
			}else{
				rateToSwipe=50;
			}
			switch (sDir) {
			case("up"):
				LOGGER.info("Swiping in "+direction+"  direction");
				((AndroidDriver) driver).swipe(startx,starty+(scrHeight-rateToSwipe-(starty)),startx,starty-(scrHeight-rateToSwipe-(starty)),3000);
				break;
			case("down"):
				LOGGER.info("Swiping in "+direction+"  direction");
				((AndroidDriver) driver).swipe(startx,starty-(scrHeight-rateToSwipe-(starty)),startx,starty-(scrHeight-rateToSwipe-(starty)),3000);			
				break;
			case("right"):
				LOGGER.info("Swiping in "+direction+"  direction");
				((AndroidDriver) driver).swipe(startx+(scrWidth-50-(startx)),starty,startx-(scrWidth-rateToSwipe-(startx)),starty,2000);		
				break;
			case("left"):
				LOGGER.info("Swiping in "+direction+"  direction");
				((AndroidDriver) driver).swipe(startx-(scrWidth-50-(startx)),starty,startx+(scrWidth-rateToSwipe-(startx)),starty,3000);
			break;
			
			default:
				break;
			}
		}
			catch(Exception e){
				LOGGER.info("Exception block inside Swipe Page");
				e.printStackTrace();
			}
	}

/**********************************************************************************************************************************	
	Purpose of Method: To wait until an object disappears
		
**********************************************************************************************************************************/	
    
    public void waitUntilInvisible(WebElement webElement) {
       try{
    	boolean exit=false;
    	do {
			if (checkForVisiblity(webElement, driver)) {
				wait(2);
			}else{
				exit=true;
			}
		} while (exit==false);
       }catch (Exception e) {
			e.printStackTrace();
		}
    }
 /**********************************************************************************************************************************	
	Purpose of Method: To swipe in a direction until the element is visible
		
**********************************************************************************************************************************/	
 
    public void swipeUntilVisible(WebElement webElement) throws InterruptedException {
       try{
    	boolean exit=false;
    	do {
			if (checkForVisiblity(webElement, driver)) {
				exit=true;
			}else{
				swipe(driver, "up", "fast");
			}
		} while (exit==false);
    }catch (Exception e) {
		e.printStackTrace();
	}
    }
    public static Map<String, String> getDataFromFiles(String detailInstanceName,String sheetName) {
		XSSFWorkbook myWorkBook = null;
		Map<String, String> testdata = null;
		File myFile =null;
		try {
				 myFile = new File(Constants.dataSheetPath);
			FileInputStream fis = new FileInputStream(myFile);	
			myWorkBook = new XSSFWorkbook(fis);
			XSSFSheet mySheet;
			mySheet = myWorkBook.getSheet(sheetName);
			Iterator<Row> rowIterator = mySheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cell.setCellType(1);
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cell.setCellType(1);
						break;
					default:
					}
				}
			}
			int testdataRowindex = 0;
			String TestColumn = "NotAvailable";
			Iterator<Row> rowIterator2 = mySheet.iterator();
			while (rowIterator2.hasNext()) {
				Row row = rowIterator2.next();
				if (row.getCell(0).getStringCellValue().contentEquals(detailInstanceName)) {
					testdataRowindex = row.getCell(0).getRowIndex();
					TestColumn = "Available";
					break;
				}
			}
			if (TestColumn.equalsIgnoreCase("NotAvailable")) {
				throw new RuntimeException("Data not supplied for " + detailInstanceName);
			}
			String[] firstRow = new String[1000];

			for (int colIndex = 0; colIndex <= mySheet.getRow(0).getLastCellNum() - 1; colIndex++) {
				firstRow[colIndex] = mySheet.getRow(0).getCell(colIndex).getStringCellValue();
			}

			String[] testdataRow2 = new String[1000];

			for (int colIndex = 0; colIndex <= mySheet.getRow(0).getLastCellNum() - 1; colIndex++) {
				try {
					testdataRow2[colIndex] = mySheet.getRow(testdataRowindex).getCell(colIndex).getStringCellValue();
				} catch (NullPointerException e) {
					testdataRow2[colIndex] = null;
				}
			}
			int i = 0;
			testdata = new HashMap<String, String>();
			for (String cellvalue : firstRow) {
				if (cellvalue == null || cellvalue.equalsIgnoreCase(""))
					break;
				try {
					testdata.put(cellvalue, testdataRow2[i++]);
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in fetching test data from Excel");
			e.printStackTrace();
		} finally {
			try {
				myWorkBook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return testdata;
	}
    public static void MobileappRefres(WebDriver driver) {
    	
				LOGGER.info("Local device App refreshing");
				((AndroidDriver) driver).pressKeyCode(187);
				wait(2);
				((AndroidDriver) driver).pressKeyCode(187);
    	
    }
    
    public static String getScreenshot(WebDriver driver) {
		TakesScreenshot ts=(TakesScreenshot) driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String path=Constants.screenShotPath;
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination, true);
		} catch (Exception e) {
			
		}
		return path;
	}

}
