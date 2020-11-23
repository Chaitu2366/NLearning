package com.snc.surf.marketing.NLearning.pageclass;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SendKeys;

import com.google.inject.Key;
import com.snc.selenium.core.TestEnvironment;
import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.utils.NLEUtils;
import com.snc.util.SurfUtils;

import antlr.collections.List;

public class SABACourseclasscreation extends NLEPageBase {

	public String coursename = null;
	public String classname = null;

	// Course Locators
	By orderContact = By.id("sabacombobox-1044-inputEl");
	// By newCatelogItem = By.cssSelector("a[text='New Catalog Item']");
	By newCatelogItem = By.xpath("//a[contains(text(),'New Catalog Item')]");
	By Frame = By.cssSelector("iframe[class='ng-star-inserted']");
	By newCourselink = By.cssSelector("a[title='New Course']");
	By coursetitle = By.cssSelector("input[name='comp_title']");
	By domaintxt = By.cssSelector("input[name='comp_domainbookkeep']");
	By slctdomainvalue = By.cssSelector("div.sbPickerCell0");
	By SKUtextfield = By.cssSelector("input[name='comp_customFields_custom0']");
	By savebtn = By.cssSelector("a[title='Save']");

	// class Locators
	By newclasslink = By.cssSelector("a[title='New Class']");
//	By coursesearchfield = By.cssSelector("input.sbPickerInput");
	By coursesearchfield = By.cssSelector("input[name='OtPickerbookkeep']");
	By slctcoursevalue = By.cssSelector("div.sbPickerCell0");
	By classdelvrydrdp = By.cssSelector("select[name='unifiedDelTypeSelect']");
	By classDeliveryTypeILT = By.cssSelector("select[name='unifiedDelTypeSelect']>option:nth-child(2)");
	By classDeliveryTypeVILT = By.cssSelector("select[name='unifiedDelTypeSelect']>option:nth-child(3)");
	By classDeliveryTypeWB = By.cssSelector("select[name='unifiedDelTypeSelect']>option:nth-child(4)");
	By nextbtn = By.cssSelector("a[title='Next']");
	By classIdfld = By.cssSelector("input[name='comp_class_no']");
	By classIdfldWBT = By.cssSelector("input[name='comp_wbt_no']");
	By classstartdate = By.cssSelector("img[alt='Pick Start Date']");
	String datepart1 = "a[title='";
	String datepart2 = "']";
	// By selectdate = By.cssSelector("a[title='7]");
	By sessiontemplate = By.cssSelector("img[alt='Session Template']");
	// By selctsessionradiobtn = By.cssSelector("input#id1035_wdk0");
	By selctsessionradiobtn = By
			.cssSelector(".//span[contains(text(),'11:00 AM -8:30 PM')]/../../td[2]/span/following-sibling::input");
	By selectclosebtn = By.cssSelector("a[title='Select And Close']");
	By languagefield = By.cssSelector("input[name='comp_language_idbookkeep']");
	By selectlanguage = By.xpath("//div[contains(text(),'English')]");
	By Finishbtn = By.cssSelector("a[title='Finish']");
	By profileframe = By.cssSelector("trq-framework-header[class='trq-framework-header ng-star-inserted']");
	By profileicon = By.cssSelector("img#trq-framework-header-action-user-image");
	By signoutbtn = By.cssSelector("div[title='Sign out']");
	By descriptiontextfld = By.cssSelector("textarea[name='comp_description']");
	By durationWBTclass = By.cssSelector("input[name='comp_duration']");
	By classCreationMessage = By.cssSelector("td.sbMainPageHeadingText");
	By CourseId = By.xpath(".//span[contains(text(),'Course ID')]/../../td[2]//span");
	
String sabaClassesURL = "https://servicenowsb3.sabacloud.com/Saba/Web_spf/NA1TNB0118/app/admin/learning/classes/manage";

	public String createCourse(String SKU) {
		// TODO Auto-generated method stub
		// System.out.println("****************");
	//	coursename = "SABA" + surfUtils.getCurrentDateTime(getCurrentDateTime());
		coursename = "SABAcourse" + leUtils.getCurrentDateWithMins_NoSpace();
		timers.waitUntilDOMReady(5);
		getDriver().switchTo().frame(findElement(Frame));
		findElement(newCatelogItem).click();
		timers.waitUntilDOMReady(5); // Refacotr to Expeted wait UIutils
		System.out.println("clicked on new catelog item");
		findElement(newCourselink).click();
		timers.waitUntilDOMReady(5);
		findElement(coursetitle).sendKeys(coursename);
		findElement(domaintxt).clear();
		findElement(domaintxt).sendKeys("Common");
		timers.waitUntilDOMReady(5);
		findElement(domaintxt).sendKeys(Keys.ENTER);
//		findElement(slctdomainvalue).click();
//		timers.waitUntilDOMReady(5);
		findElement(SKUtextfield).sendKeys(SKU);
		findElement(savebtn).click();
		System.out.println("clicked on Save button");
		return coursename;
	}

	public String createClass(String course, int i) {
		// TODO Auto-generated method stub
		classname = "class"+leUtils.getCurrentDateWithMins_NoSpace();
		timers.waitUntilDOMReady(15);
		getDriver().navigate().to(sabaClassesURL);
		timers.waitUntilDOMReady(3);
		getDriver().switchTo().frame(findElement(Frame));
		findElement(newclasslink).click();
		findElement(coursesearchfield).sendKeys(course);
		findElement(coursesearchfield).sendKeys(Keys.ENTER);
		 timers.waitUntilDOMReady(5);
	//	timers.waitForElementVisible(slctcoursevalue, 5);
	//	findElement(slctcoursevalue).click();
	//	timers.waitUntilDOMReady(10);

		findElement(classdelvrydrdp).click();
		timers.waitUntilDOMReady(5);
		if (i == 0) {
			findElement(classDeliveryTypeILT).click();
		} 
		else if (i == 1){
			findElement(classDeliveryTypeVILT).click();
		}
		else
		{
			findElement(classDeliveryTypeWB).click();
		}	
		findElement(nextbtn).click();
		timers.waitUntilDOMReady(10);
	
		if(i==0 || i==1) {
		findElement(classIdfld).sendKeys(classname);
		Calendar today = Calendar.getInstance();
		// If you want the day/month in String format
		String date = today.get(Calendar.DATE) + "";
		// If you want the day/month in Integer format
		int dateInt = (int) Integer.parseInt(date);
		System.out.println("Today's date:-" + dateInt);

		findElement(classstartdate).click();
		timers.waitUntilDOMReady(5);
		// entering date field
		String parentWindow = getDriver().getWindowHandle();
		Set<String> windows = getDriver().getWindowHandles();
		for (String string : windows) {
			if (!string.equals(parentWindow)) {
				getDriver().switchTo().window(string);
			}
		}

		findElement(By.cssSelector(datepart1 + dateInt + datepart2)).click();
		getDriver().switchTo().window(parentWindow);
		getDriver().switchTo().frame(findElement(Frame));
		pauseMe(1);

		// entering session template
		findElement(sessiontemplate).click();
		Set<String> windows1 = getDriver().getWindowHandles();
		for (String string : windows1) {
			if (!string.equals(parentWindow)) {
				getDriver().switchTo().window(string);
				// findElement(selctsessionradiobtn).click();
				
			//	 uiUtil.clickEle(By.xpath(".//span[contains(text(),'11:00 AM -8:30 PM')]/../../td[2]//input"));
				 uiUtil.clickEle(By.xpath(".//span[contains(text(),'8:00 AM -5:00 PM;')]/../../td[2]//input"));

			//	uiUtil.clickEle(By.xpath(".//span[contains(text(),'930am - 630pm')]/../../td[2]//input"));
			//	uiUtil.clickEle(selctsessionradiobtn);
				pauseMe(3);
				findElement(selectclosebtn).click();

			}

		}

		
		getDriver().switchTo().window(parentWindow);
		getDriver().switchTo().frame(findElement(Frame));
		}
		findElement(languagefield).sendKeys("English");
		findElement(languagefield).sendKeys(Keys.ENTER);
	//	findElement(selectlanguage).click();
		if(i==2)
		{
			uiUtil.sendKeys(classIdfldWBT, classname);
			findElement(durationWBTclass).clear();
		uiUtil.sendKeys(durationWBTclass, "04:00");
		}
		findElement(Finishbtn).click();
		timers.waitUntilDOMReady(5);
		/*String courseId = uiUtil.getText(CourseId);
		String actualMessage = uiUtil.getText(classCreationMessage);
		String expectedMessage = "Classroom Instructor Led Class Details: "+course+",# "+courseId+",#"+classname;*/
	//	assert.assertTrue("class is not created", expectedMessage.contains(actualMessage));
		uiUtil.sendKeys(descriptiontextfld, "testing purpose");
		uiUtil.clickEle(savebtn);
		timers.waitUntilDOMReady(5);
		
		getDriver().switchTo().defaultContent();
		// findElement(profileicon).click();
		// findElement(signoutbtn).click();
        
		return classname;
	}

	public void userSignOutFromSABA() {
		uiUtil.clickEle(profileicon);
		uiUtil.clickEle(signoutbtn);
		
	}

}