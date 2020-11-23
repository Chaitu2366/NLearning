package com.snc.surf.marketing.NLearning.utils;


import com.google.common.base.Function;
import com.snc.glide.it.TestUtils;
import com.snc.selenium.core.SNCTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WaitUtils extends SNCTest {

	public void waitForEleVisibility(By byLoc, long timeout) {
		try {
			TestUtils.waitFor("Element is not displayed", new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					try {

						WebElement btnElement = getDriver().findElement(byLoc);
						if (btnElement != null & btnElement.isDisplayed() == true)
							return true;
						else
							return false;
					} catch (Exception e) {
						return false;
					}
				}
			}, timeout*1000);
		} catch (Exception err) {

		}
	}

	// FlunetWait Implementation, selenium waits until timeout and returns element is found
	public WebElement waitAndObtainWebElement(By byLoc, int timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(2)) 
				.ignoring(NoSuchElementException.class);

		WebElement ele = wait.until(new Function<WebDriver, WebElement>() 
				{
			public WebElement apply(WebDriver driver) {
				return driver.findElement(byLoc);
			}
				});
		return ele;
	}

	public void waitForElementToBeClickable(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),30);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			System.out.println(e);
		}
	}


}
