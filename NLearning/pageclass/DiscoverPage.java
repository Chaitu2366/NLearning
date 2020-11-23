package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.surf.marketing.NLearning.utils.UiUtility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DiscoverPage extends LandingPage {

    protected By roleSelectBtn = By.cssSelector("dropdown-multiselect[options*='filterMetadata.persona'] button");
    protected By roleSelectDiv = By.cssSelector("dropdown-multiselect[options*='filterMetadata.persona']");
    protected By levelSelectBtn = By.cssSelector("dropdown-multiselect[options*='filterMetadata.level'] button");
    protected By levelSelectDiv = By.cssSelector("dropdown-multiselect[options*='filterMetadata.level']");
    protected By productSelectBtn = By.cssSelector("dropdown-multiselect[options*='filterMetadata.product'] button");
    protected By productSelectDiv = By.cssSelector("dropdown-multiselect[options*='filterMetadata.product']");
    protected By otherInterestDrpDown = By.cssSelector("div.lxp-filter-icon.float-right");
    protected By placeHolderInput = By.cssSelector("form[ng-submit*='c.modal.addTag()'] input");
    protected By dropDownMenuOptions = By.cssSelector("ul[active='activeIdx'] > li >a");
    protected By pathResults = By.xpath(".//div[contains(text(),'Filtered Results')]/following-sibling::div//div[contains(@class,'content-object-tile-container')]");
    protected By showResults = By.cssSelector("a.result-btn");
    protected By viewAll = By.xpath(".//a[contains(text(),'View All')]");
    protected By resetBtn = By.xpath(".//a[contains(text(),'Reset')]");
    protected By resetShowResult= By.xpath("//*[@id='results']/a[contains(., 'View 0 items selected')]");
    public String option = ".//span[text()='%s']";
    public String otherInterestOption = "form[ng-submit*='c.modal.addTag()'] a[title='%s']";
    public String contentType;
    protected By showAllCount = By.xpath(".//div[contains(text(),'Showing All')]/span");
    protected By goToTopIcon = By.xpath("//i[@name='gotoTop']");

    // Card Details Locators
    protected By categoryType = By.xpath(".//div[@class='flex-grow']/span");
    protected By duration = By.xpath(".//span[contains(@ng-if,'item.duration.display')]");
    protected By cardType = By.xpath(".// div[contains(@class,'block h-6')]/ span[1]");
    protected By certification = By.xpath(".// div[contains(@class,'block h-6')]/ span[2]");
    protected By notRatedStars = By.xpath(".//stars[@rating-value='item.rating']//li[contains(@class,'text-gray-dark')]");  // 5 - number of Gray Stars == Actual Rating
    protected By cardName = By.xpath(".//div[contains(@class,'h-40')]/ span[1]");
    protected By cardDescription = By.xpath(".//div[contains(@class,'h-40')]/span[2]");
    protected By cardValue = By.xpath(".//span[contains(@class,'item-value')]");
    protected By byCourses = By.xpath(".//span[contains(@ng-if,'course_count')]");

    CardDetails cardData;
    
    public String clickGoToTopIcon(){
    	uiUtil.clickEle(goToTopIcon);
    	return uiUtil.getUrlText();
    }



    protected int getShowAllCount()
    {
        int iVal = getCountValue(showAllCount);
        return iVal;
    }

    public void selectFilter(String input1, String... values)
    {
        switch(input1)
        {
            case "Role":
                uiUtil.waitAndObtainWebElement(roleSelectBtn,10);
                uiUtil.clickUsingJs(roleSelectBtn);
                selectValues(roleSelectDiv,values);
                break;
            case "Level":
                uiUtil.waitAndObtainWebElement(levelSelectBtn,10);
                uiUtil.clickUsingJs(levelSelectBtn);
                selectValues(levelSelectDiv,values);
                break;
            case "Product":
                uiUtil.waitAndObtainWebElement(productSelectBtn,10);
                uiUtil.clickUsingJs(productSelectBtn);
                selectValues(productSelectDiv,values);
                break;
            case "Other Interests":
                uiUtil.waitAndObtainWebElement(otherInterestDrpDown,10);
                uiUtil.clickUsingJs(otherInterestDrpDown);
                chooseTags(values);
                uiUtil.waitAndObtainWebElement(otherInterestDrpDown,6);
                uiUtil.clickUsingJs(otherInterestDrpDown);
        }
    }
    //-----------------------------------------------------------
    public void selectMultipFilter(String input1, String... values)
    {
        switch(input1)
        {
            case "Role":
                uiUtil.waitAndObtainWebElement(roleSelectBtn,10);
                uiUtil.clickUsingJs(roleSelectBtn);
                selectValues(roleSelectDiv,values);
                break;
            case "Level":
                uiUtil.waitAndObtainWebElement(levelSelectBtn,10);
                uiUtil.clickUsingJs(levelSelectBtn);
                selectValues(levelSelectDiv,values);
                break;
            case "Product":
                uiUtil.waitAndObtainWebElement(productSelectBtn,10);
                uiUtil.clickUsingJs(productSelectBtn);
                selectValues(productSelectDiv,values);
                
            
        }
    }
    //--------------------------------------------------------------


    private void chooseTags(String... values)
    {
        try {
            for (String val : values) {
                otherInterestOption = new String("form[ng-submit*='c.modal.addTag()'] a[title='%s']");
                otherInterestOption = String.format(otherInterestOption, val);
                By loc = By.cssSelector(otherInterestOption);
                uiUtil.scrollToView(placeHolderInput);
                findElement(placeHolderInput).sendKeys(val);
                pauseMe(3);
                uiUtil.waitForEleVisibility(dropDownMenuOptions, 7);
                uiUtil.waitForEleVisibility(loc, 5);
                pauseMe(3);
                uiUtil.clickUsingJs(getDriver().findElement(loc));
                pauseMe(4);
                uiUtil.pressEnterKey(placeHolderInput);
                pauseMe(2);
            }
        }
        catch(Exception err) {err.printStackTrace();}
    }


    private void selectValues(By by, String... values)
    {
        for(String val : values)
        {
            option = new String(".//span[text()='%s']");
            option= String.format(option,val);
            By loc = By.xpath(option);
            uiUtil.waitForEleVisibility(loc,8);
            pauseMe(2);
            uiUtil.clickUsingJs(findElement(by).findElement(loc));
            pauseMe(2);
        }
    }

    public void clickShowResults()
    {
        uiUtil.clickUsingJs(showResults);
    }
   public String clickBtn()
   {
	   uiUtil.clickEle(resetBtn);
	   return uiUtil.getText(resetShowResult);

   }
    public List<String> getContentObjectNames(String type)
    {
        List<String> listStr = new ArrayList<>();
        String s1 = ".//div[contains(text(),'Filtered Results')]/following-sibling::div//div[contains(@class,'content-object-tile-container')]//span[text()='%s']/../following-sibling::div/span[1]";
        contentType = new String(s1);
        contentType = String.format(contentType,type.toLowerCase());
        By byLoc = By.xpath(contentType);
        uiUtil.waitForEleVisibility(pathResults,20);
        listStr = getElementTextValues(byLoc);
        return listStr;
    }

    public List<WebElement> getFilteredResults()
    {
        uiUtil.waitForEleVisibility(pathResults,12);
        List<WebElement> listEle = findElements(pathResults);
        return listEle;
    }

    public CardDetails fetchCardDetails(WebElement element)
    {

        String categoryTypeStr = element.findElement(categoryType).getText().trim();
        String durationStr = element.findElement(duration).getText().trim();
        String cardTypeStr = element.findElement(cardType).getText().trim();
        String certificationStr = element.findElement(certification).getText().trim();
        int notRatedStarsStr = element.findElements(notRatedStars).size();
        String cardNameStr = element.findElement(cardName).getText().trim();
        String cardDescrStr = element.findElement(cardDescription).getText().trim();
        String cardValueStr = element.findElement(cardValue).getText().trim();
        int courses = getCourses(element.findElement(byCourses));
        cardData = new CardDetails(categoryTypeStr,durationStr,cardTypeStr,certificationStr,notRatedStarsStr,cardNameStr,cardDescrStr,cardValueStr,courses);
        return cardData;
    }

 //--------------------------------------------------------------------------------
    
    public CardDetails fetchCardDetailswithcarddescription(WebElement element)
    {

        String categoryTypeStr = element.findElement(categoryType).getText().trim();
        //String durationStr = element.findElement(duration).getText().trim();
        //String cardTypeStr = element.findElement(cardType).getText().trim();
        //String certificationStr = element.findElement(certification).getText().trim();
        int notRatedStarsStr = element.findElements(notRatedStars).size();
        String cardNameStr = element.findElement(cardName).getText().trim();
        String cardDescrStr = element.findElement(cardDescription).getText().trim();
       // String cardValueStr = element.findElement(cardValue).getText().trim();
       // int courses = getCourses(element.findElement(byCourses));
        cardData = new CardDetails(categoryTypeStr,notRatedStarsStr,cardNameStr,cardDescrStr);
        return cardData;
    }
    
 //--------------------------------------------------------------------------------
    
    public void scrollDown(By loc)
    {
        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
        jse.executeScript("arguments[0].scrollIntoView()", getDriver().findElement(loc));
        jse.executeScript("scroll(0, 800)");
    }

    public int getCourses(WebElement element) {
        String sText = element.getText().trim();
        sText = sText.replaceAll("[^\\d.]", "");
        int iVal = Integer.parseInt(sText);
        return iVal;
    }
    
    public void waitForDiscoverPageToLoad() {
    	wait.waitAndObtainWebElement(roleSelectBtn, 30);
    }

    public void clickCard(List<WebElement> listEle, String text)
    {
        listEle.stream()
        .filter(element -> element.findElement(cardName).getText().trim().contains(text)).findFirst().get().click();
        timers.waitUntilDOMReady(20);
    }


}
