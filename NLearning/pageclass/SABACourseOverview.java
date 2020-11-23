package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.utils.UiUtility;
import com.snc.surf.marketing.NLearning.utils.Entitlements.NLEntitlement;
import com.snc.util.SurfUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.Collectors;

public class SABACourseOverview extends SABACourseclasscreation {
	
//	NLEntitlement nlentitlement = new NLEntitlement();
//	NLETestBase nltestbase = new NLETestBase();
//	SurfUtils utils = new SurfUtils();

	protected By selectClassBtn = By.xpath("//div/a[contains(text(),'Select a class')]");
	protected By proceedBtn = By.cssSelector("a[ng-click*='vm.warningPopup']");
	protected By exitNLBtn = By.cssSelector("a[ng-click*='vm.continueRedirection()']");
	protected By readtext = By.cssSelector("label#label-1103");
	protected By addToPlanBtn = By.xpath("//span[contains(text(),'Add to Plan')]");
	// protected By enrolbtn =
	// By.xpath("//span[text()='Enroll']/following-sibling::span");
//	protected By enrolbtns = By.xpath("//*[@id=\"button-1102-btnIconEl\"]");
	protected By enrolbtn = By.cssSelector("a[data-qtip*='Enroll']");
	protected By frame = By.xpath("//*[@class='sc-iframe sc-iframe--fixed ng-star-inserted']//iframe");
	protected By addtoPlan = By.xpath("//a[@title='Add to Plan']//following-sibling::span");

	protected By coursetxtfld = By.cssSelector("input[name*='_var_name$kString$kLike']");
	protected By searchbtn = By.xpath("//a[@title='Search']//span");
	protected By coursetitle = By.cssSelector("a.sbLinkTableDisplay");
	protected By Rosterbtn = By.xpath("//a[@title='Roster']//span");
	protected By resultsBtn = By.xpath("//a[@title='Results']//span");
	protected By usercheckbox = By.cssSelector("div.x-grid-row-checker");
	protected By statusdrdp = By.cssSelector("div[data-sabaid='comboboxinputel-trigger']");
	protected By statusvalue = By.cssSelector("ul[class='x-list-plain']>li:nth-child(2)");
	protected By scorefld = By.cssSelector("input[data-sabaid='numberfieldinputel']");
	protected By saveBtn = By.xpath("//span//span[contains(text(),'Save')]");
	By Frame = By.cssSelector("iframe[class='ng-star-inserted']");
	By profileicon = By.cssSelector("img#trq-framework-header-action-user-image");
	By signoutbtn = By.cssSelector("div[title='Sign out']");
	String classpageURL = "https://servicenowsb3.sabacloud.com/Saba/Web_spf/NA1TNB0118/app/admin/learning/classes/manage";

	//SABACourseclasscreation sb = new SABACourseclasscreation();

	public void clickProceedBtn() {
		uiUtil.clickUsingJs(selectClassBtn);
		waitForKmSpinnerToDisappear();
		uiUtil.clickUsingJs(proceedBtn);
		waitForKmSpinnerToDisappear();
	}

	public void clickExitBtnAndEnrolInSABA() {
	//	String parentWindow = getDriver().getWindowHandle();
	//	JavascriptExecutor js = (JavascriptExecutor) getDriver();
		uiUtil.clickUsingJs(exitNLBtn);
		// Switching to SABA Page from NL Page
		timers.waitUntilDOMReady(20);
		WebDriver driver = getDriver();
		
		String parentWindowHandler = getDriver().getWindowHandle();
	//	uiUtil.switchBrowserWindowTab();
		uiUtil.switchToWindow();
		pauseMe(3);
		uiUtil.switchToIFrame(frame);
		timers.waitUntilDOMReady(5);
		// clicking enroll button in SABA System
		uiUtil.clickUsingJs(enrolbtn);
		
		timers.waitUntilDOMReady(5);
	
		driver.switchTo().defaultContent();
		// sign-out from the SABA System
		userSignOutFromSABA();
		timers.waitUntilDOMReady(3);
     //   wait.waitForEleVisibility(nlentitlement.cancel, 15);
		getDriver().close();
        getDriver().switchTo().window(parentWindowHandler);
        getDriver().navigate().refresh();
		
	//	nlentitlement.
		 

				
				
	}

	public void completecourseinSaba(String Coursename, int i) {
		getDriver().navigate().to(classpageURL);
		timers.waitUntilDOMReady(5);
		getDriver().switchTo().frame(findElement(Frame));
		System.out.println("current URL:" + getDriver().getCurrentUrl());
		uiUtil.sendKeys(coursetxtfld, Coursename);
		uiUtil.clickEle(searchbtn);
		uiUtil.clickEle(coursetitle);
		if(i==0 || i==1)
		{
		uiUtil.waitForElementClickable(Rosterbtn, 5);
		uiUtil.clickUsingJs(Rosterbtn);
		}
		if(i==2)
		{
			uiUtil.waitForElementClickable(resultsBtn, 5);
			uiUtil.clickUsingJs(resultsBtn);	
		}
		uiUtil.clickEle(usercheckbox);
		uiUtil.clickEle(statusdrdp);
		uiUtil.clickUsingJs(statusvalue);
		findElement(scorefld).clear();
		uiUtil.sendKeys(scorefld, "89");
		findElement(scorefld).sendKeys(Keys.ENTER);
	//	uiUtil.pressEnterKey(scorefld);
		uiUtil.waitForElementClickable(saveBtn, 2);
		uiUtil.clickUsingJs(saveBtn);
		pauseMe(2);

	}

}
