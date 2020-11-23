package com.snc.surf.marketing.NLearning.pageclass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class LiveClassesPage extends LandingPage {

    protected String sectionCards = ".//div[contains(text(),'%s')]/following-sibling::div//div[contains(@class,'content-object-tile-container')]";

    // Card Details Locators
    protected By categoryType = By.xpath(".//div[@class='flex-grow']/span");
    protected By duration = By.xpath(".//span[contains(@ng-if,'item.duration.display')]");
    protected By cardType = By.xpath(".// div[contains(@class,'block h-6')]/ span[1]");
    protected By certification = By.xpath(".// div[contains(@class,'block h-6')]/ span[2]");
    protected By notRatedStars = By.xpath(".//stars[@rating-value='item.rating']//li[contains(@class,'text-gray-dark')]");  // 5 - number of Gray Stars == Actual Rating
    protected By cardName = By.xpath(".//div[contains(@class,'h-40')]/ span[1]");
    protected By cardDescription = By.xpath(".//div[contains(@class,'h-40')]/span[2]");
    protected By cardValue = By.xpath(".//span[contains(@class,'item-value')]");

    protected int getCount(String section)
    {
        List<WebElement>  listEle = getSection_CardCount(section);
        return listEle.size();
    }


    public List<WebElement> getSection_CardCount(String sText)
    {
        sectionCards= String.format(sectionCards,sText);
        By byLoc = By.xpath(sectionCards);
        uiUtil.waitForEleVisibility(byLoc,12);
        List<WebElement> listEle = findElements(byLoc);
        return listEle;
    }

    public void clickCard(List<WebElement> listEle, String text)
    {
        listEle.stream().filter(element -> element.findElement(cardName).getText().trim().contains(text)).findFirst().get().click();
    }
}
