package com.snc.surf.marketing.NLearning.utils;

import com.glide.usageanalytics.framework.utils.FrameworkTestUtils;
import com.google.common.base.Function;
import com.snc.glide.it.TestUtils;
import com.snc.sa.analytics.common.util.TimeUtil;
import com.snc.selenium.core.Actions;
import com.snc.selenium.core.Common;
import com.snc.selenium.core.SNCTest;
import com.snc.selenium.core.TestEnvironment;
import com.snc.selenium.framework.MessageLogger;
import com.snc.surf.marketing.NLearning.base.NLEPageBase;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Size;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Sabautils extends SNCTest {
	
	//locaters of SB3 login page
	By userNamefld = By.xpath("//input[@id='username']");
	By usernameSubmitBtn = By.xpath("//button[@id='usernameSubmitButton']");
	By passwordfld = By.xpath("//input[@id='password']");
	By submitBtn = By.xpath("//button[@id='submitButton']");
	//By SKUvalue = By.xpath("*//td[@gwt-make-visible='true']");
	By SKUvalue = By.xpath("//*[@id=\"sys_readonly.sales_product.u_sku\"]");
	
	protected By TrangUnit = By.xpath(".//div[contains(text(),'Training Unit')]");
	protected By MangAgrmt = By.xpath(".//span[contains(text(),'Manage Agreements')]");
	protected By RegisterDesktop = By.xpath(".//div[contains(text(),'Registrar Desktop')]");
	protected By SubscriptionOrders = By.xpath(".//span[contains(text(),'Subscription Orders')]");

	public void loginToTheSABAApplication(String username, String password) {

	//	getDriver().get(TestEnvironment.get().getProperty("sabaurl"));
		timers.waitForElementVisible(userNamefld, 10);
		getDriver().findElement(userNamefld).sendKeys(username);
		findElement(usernameSubmitBtn).click();
		findElement(passwordfld).sendKeys(password);
		findElement(submitBtn).click();
		pauseMe(5);
	}

	public void traingOrder() {
		timers.waitUntilDOMReady(20);
		findElement(TrangUnit).click();
		findElement(MangAgrmt).click();
	}

	public void subscriptionOrder() {
		timers.waitUntilDOMReady(20);
		findElement(RegisterDesktop).click();
		findElement(SubscriptionOrders).click();
	}
	
	public String loginSurfInstance(String user, String password) {
		
		timers.waitForElementVisible(userNamefld, 10);
		getDriver().findElement(userNamefld).sendKeys(user);
		findElement(usernameSubmitBtn).click();
		findElement(passwordfld).sendKeys(password);
		findElement(submitBtn).click();
	//	timers.waitUntilDOMReady(20);
		pauseMe(6);
		String SKU = getDriver().findElement(SKUvalue).getText();
		for (int i = 0; i < SKU.length(); i++) {
			System.out.println("the value of sku are "+SKU);
			
		}
		System.out.println("SKU"+SKU);
		return SKU;
	}

}
