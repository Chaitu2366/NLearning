package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.surf.marketing.NLearning.objectData.GlobalSearchCardDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class GlobalSearchPage extends LandingPage {

	GlobalSearchCardDetails globalSearchCardData;

	protected By cardResults = By.xpath(".//div[contains(@ng-click,'openLink')]");
	protected By searchInput = By.cssSelector("input[placeholder='Search Now Learning']");
	protected By searchingText = By.xpath(".//div[contains(text(),'Searching... Please wait')]");
	protected By cardType = By.xpath(".//div[contains(@class,'head mb-1')]/span");
	protected By cardName = By.xpath(".//div[contains(@class,'head mb-1')]/a");
	protected By categoryName = By.xpath(".//div[contains(@class,'demand-courses')]/p[contains(@ng-show,'item.category')]");
	protected By courseCount = By.xpath(".//div[contains(@class,'demand-courses')]/p[contains(@ng-repeat,' c.getCount(item, item')]");
	protected By courseCost = By.xpath(".//div[contains(@class,'demand-courses')]/p[contains(@ng-repeat,' c.getCount(item, item')]/following-sibling::p");
	protected By sortByInGS = By.xpath("//a[contains(text(), 'Sort by')]");

	public void performSearch(String text)
	{
		uiUtil.waitForEleVisibility(searchInput,10);
		uiUtil.sendKeys(searchInput,text);
		uiUtil.pressEnterKey(searchInput);
		uiUtil.waitForElementInvisibility(searchingText,15);
	}

	public Integer getSearchResultsCount()
	{
		uiUtil.waitForEleVisibility(cardResults,12);
		List<WebElement> listEle = findElements(cardResults);
		return listEle.size();
	}

	public GlobalSearchCardDetails fetchCardDetails(WebElement element)
	{

		String cardTypeStr = element.findElement(cardType).getText().trim();
		String cardNameStr = element.findElement(cardName).getText().trim();
		String categoryNameStr = element.findElement(categoryName).getText().trim();
		String courseCountStr = element.findElement(courseCount).getText().trim();
		String courseCostStr = element.findElement(courseCost).getText().trim();
		globalSearchCardData = new GlobalSearchCardDetails(cardTypeStr,cardNameStr,categoryNameStr,courseCountStr,courseCostStr);
		return globalSearchCardData;
	}

	public List<WebElement> getCardSearchDetails()
	{
		uiUtil.waitForEleVisibility(cardResults,12);
		List<WebElement> listEle = findElements(cardResults);
		return listEle;
	}

	public List<String> getContentName() {
		List<WebElement> listEleName = getCardSearchDetails();//get the web element for each content
		List<String> contentNameList = new ArrayList<String>();
		int i = listEleName.size(); //iterate through each content and get their name
		for(int j = 1; j<=i;j++){
			String xpathString= "//div[contains(@ng-click,'openLink')]["+j+"]";
			WebElement eachContentRow = findElement(By.xpath(xpathString));
			String contentName = eachContentRow.findElement(By.cssSelector("div[class*='head mb-1']>a")).getText();
			contentNameList.add(contentName);
		}
		return contentNameList;
	}
	
	public void clickOnSortInGlobalSearchResult(String sortName) {
		uiUtil.clickEle(sortByInGS); //click on sort by in Global Search
		if (sortName.equalsIgnoreCase("Most Recent")){
			uiUtil.clickEle(By.xpath("//a[contains(text(), 'Most Recent')]"));
			waitForSearchResutlsToLoad();
		}
		else if (sortName.equalsIgnoreCase("Most Viewed")) {
			uiUtil.clickEle(By.xpath("//a[contains(text(), 'Most Viewed')]"));
			waitForSearchResutlsToLoad();
		}
		
	}
	
	public void waitForSearchResutlsToLoad() {
		wait.waitAndObtainWebElement(cardResults, 10);		
	}



}
