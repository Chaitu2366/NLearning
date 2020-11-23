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
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class UiUtility extends SNCTest {

    public String dropdownValue = ".//span[contains(text(),'%s')]";

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

    // Fluent Wait Implementation, selenium waits until timeout and returns element is found
    public WebElement waitAndObtainWebElement(By byLoc, int timeout) {
        Wait wait = new FluentWait(getDriver())
                .withTimeout(timeout, SECONDS)
                .pollingEvery(2, SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement ele = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(byLoc);
            }
        });
        return ele;
    }

    public void switchToWindow() {
        Set<String> windowHandles = this.getDriver().getWindowHandles();
        for(String windName : windowHandles) {
            getDriver().switchTo().window(windName);
        }
    }

    public boolean waitForWindowToAppear(int timeout) {
        boolean bVal = false;
        int counter = 0;
        while (!bVal) {
            Set<String> windowHandles = this.getDriver().getWindowHandles();
            if (windowHandles.size() > 1) {
                bVal = true;
                return true;
            }
            pauseMe(1);
            counter++;
            if (counter > timeout) {
                return false;
            }
        }
        return bVal;
    }

    public void selectOptionByVisibleText(By byLoc, String value) {
        Select sel = new Select(getDriver().findElement(byLoc));
        sel.selectByVisibleText(value);
    }

    public void selectOptionByValue(By byLoc, String value) {
        Select sel = new Select(getDriver().findElement(byLoc));
        sel.selectByValue(value);
    }
    public boolean waitForElementInvisibility(By locator, int timeoutInSeconds) {
        return (Boolean) this.getWaiter().withTimeout((long)timeoutInSeconds, TimeUnit.SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebDriverWait getWaiter() {
        return new WebDriverWait(this.getDriver(), (long) TestEnvironment.get().getTimeout());
    }

    public String getSelectedOptionValue(By byLoc) {
        Select sel = new Select(getDriver().findElement(byLoc));
        return sel.getFirstSelectedOption().getText().trim();
    }

    public String getUrlText()
    {
        return getDriver().getCurrentUrl();
    }

    public void waitForElementClickable(By byEle, int timeout)
    {
        try
        {
            new WebDriverWait(getDriver(),timeout).until(ExpectedConditions.elementToBeClickable(byEle));
        }
        catch(Exception err) {}
    }

    public void waitForElementClickable(WebElement byEle, int timeout)
    {
        try
        {
            new WebDriverWait(getDriver(),timeout).until(ExpectedConditions.elementToBeClickable(byEle));
        }
        catch(Exception err) {}
    }


    public void scrollToDownByPixel() {
        try {
            ((JavascriptExecutor)this.getDriver()).executeScript("window.scrollBy(0,1000)");
        } catch (Exception var3) {
            MessageLogger.annotate(var3.getMessage());
        }
    }

    public void scrollToBottomPage() {
        try {
            ((JavascriptExecutor)this.getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception var3) {
            MessageLogger.annotate(var3.getMessage());
        }
    }

    public void scrollToView(By by) {
        WebElement element = this.getDriver().findElement(by);
        if (element != null) {
            try {
                ((JavascriptExecutor)this.getDriver()).executeScript("arguments[0].scrollIntoView(false);", new Object[]{element});
                (new FluentWait(this.getDriver())).withTimeout(1L, TimeUnit.SECONDS).pollingEvery(500L, TimeUnit.MILLISECONDS).until(ExpectedConditions.visibilityOf(element));
            } catch (Exception var3) {
                MessageLogger.annotate(var3.getMessage());
            }

        }
    }

    public void clickUsingJs(By by) {
        waitForEleVisibility(by,8);
        waitForElementClickable(by,6);
        moveToElement(by);
        WebElement element = this.getDriver().findElement(by);
        JavascriptExecutor executor = (JavascriptExecutor)this.getDriver();
        executor.executeScript("arguments[0].click();", new Object[]{element});
    }

    public void clickUsingJs(WebElement element) {
        scrollToView(element);
        moveToElement(element);
        waitForElementClickable(element,6);
        JavascriptExecutor executor = (JavascriptExecutor)this.getDriver();
        executor.executeScript("arguments[0].click();", new Object[]{element});
    }


    public void clickEle(By ele)
    {
        scrollToView(ele);
        //scrollToDownByPixel();
        waitForEleVisibility(ele,8);
        waitForElementClickable(ele,7);
        findElement(ele).click();
    }

    public void sendKeys(By ele, String text)
    {
        waitForEleVisibility(ele,8);
        findElement(ele).click();
        findElement(ele).sendKeys(text);
    }

    public String getText(By byLoc)
    {
        scrollToView(byLoc);
        return findElement(byLoc).getText().trim();
    }

    public void pressEnterKey(By byLoc)
    {
        findElement(byLoc).sendKeys(Keys.ENTER);
        pauseMe(3);
    }
    
	/**
	 * 
	 * @param sectionName
	 *            should be the name of the section example:Showing All, Live
	 *            classes
	 * @return it will return the total count of the cards in the particular section
	 */
	public int getSectionCount(String sectionName) {
		return Integer.parseInt(findElement(By.xpath("//div[contains(text(),'" + sectionName + "')]/span")).getText()
				.replace("(", "").replace(")", ""));
	}

	/**
	 * 
	 * @return it will return the list which contains all the names present in the
	 *         footer
	 */
	public List<String> footerNamesUi() {

		List<String> footerAllNames = new ArrayList<>();
		WebElement footer = findElement(By.xpath("//div[@class='row']"));
		List<WebElement> footerNames = footer.findElements(By.tagName("a"));
		for (int i = 0; i < footerNames.size(); i++) {
			if (footerNames.get(i).getText().length() > 0) {
				footerAllNames.add(footerNames.get(i).getText());

			}
		}
		return footerAllNames;

	}

	/**
	 * 
	 * @return it will return the list which contains all the links present in the
	 *         footer
	 */
	public List<String> footerLinksUi() {

		List<String> footerAllLinks = new ArrayList<>();
		WebElement footer = findElement(By.xpath("//div[@class='row']"));
		List<WebElement> footerLinks = footer.findElements(By.tagName("a"));
		for (int i = 0; i < footerLinks.size(); i++) {
			if (footerLinks.get(i).getText().length() > 0) {
				footerAllLinks.add(footerLinks.get(i).getAttribute("href"));

			}
		}
		return footerAllLinks;
	}

	public void clickElementWithSpecificText( List<WebElement> listEle, By reqEle, String text)
    {
        listEle.stream().filter(element -> element.findElement(reqEle).getText().trim().contains(text)).findFirst().get().click();
    }

    public boolean isElementVisible(By by, int timeout)
    {
        try {
            new WebDriverWait(getDriver(),timeout).until(ExpectedConditions.visibilityOf(getDriver().findElement(by)));
            return true;
        }
        catch(Exception err)
        {
            return false;
        }
    }

    public boolean isElementPresence(By by, int timeout)
    {
        try {
            new WebDriverWait(getDriver(),timeout).until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        }
        catch(Exception err)
        {
            return false;
        }
    }

    public void moveToElement(WebElement ele)
    {
        try {
            new Actions(getDriver()).moveToElement(ele).build().perform();
        }
        catch(Exception err) {}
    }

    public void moveToElement(By by)
    {
        try {
            WebElement ele = getDriver().findElement(by);
            new Actions(getDriver()).moveToElement(ele).build().perform();
        }
        catch(Exception err) {}
    }

    public void clickElement_Actions(WebElement ele)
    {
        try {

        new Actions(getDriver()).moveToElement(ele).click().build().perform();
      }
        catch(Exception err) {}
    }

    public void typeAndSelectValueFromDropdown(By eleDiv,By eleInput,String value)
    {

        this.waitForEleVisibility(eleDiv,8);
        scrollToView(eleDiv);
        scrollToDownByPixel();
        findElement(eleDiv).click();
        waitForEleVisibility(eleInput,8);
        findElement(eleInput).click();
        findElement(eleInput).sendKeys(value);

        dropdownValue = String.format(dropdownValue,value);
        waitForEleVisibility(By.xpath(dropdownValue),6);
        findElement(By.xpath(dropdownValue)).click();
        pauseMe(3);
    }

    public void typeAndSelectValueFromPlatformDropdown(By eleDiv,By eleInput,String value)
    {
        String drpDownValue = ".//div[@id='select2-drop']//div[contains(text(),'%s')]";
        this.waitForEleVisibility(eleDiv,8);
        scrollToView(eleDiv);
        scrollToDownByPixel();
        findElement(eleDiv).click();
        clickUsingJs(eleDiv);

        drpDownValue = String.format(drpDownValue,value);
        waitForEleVisibility(By.xpath(drpDownValue),6);
        findElement(By.xpath(drpDownValue)).click();
        pauseMe(3);
    }

    public String getRandomElement(List<String> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public void attachFile(By inputEle, String fileName)
    {
        moveToElement(getDriver().findElement(inputEle));
        WebElement inputs = this.getDriver().findElement(inputEle);
        ((JavascriptExecutor)this.getDriver()).executeScript("arguments[0].removeAttribute('readonly','readonly')", new Object[]{inputs});
        inputs.sendKeys(new CharSequence[]{fileName});
    }

    public void switchToIFrame(By byEle) {
	    WebElement ele = getDriver().findElement(byEle);
	    getDriver().switchTo().frame(ele);
    }

    public void switchToParent() {
	    getDriver().switchTo().parentFrame();
    }
    
    public String getAttributeForElement(By byLoc, String attrib) {
	    if(isElementVisible(byLoc,9)) {
	        return findElement(byLoc).getAttribute(attrib).trim();
        }
	    return null;
    }

}
