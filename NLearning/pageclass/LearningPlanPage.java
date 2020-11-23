package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class LearningPlanPage extends LandingPage {

    protected By tableRows = By.xpath(".//div[@id='learning-plan-tab']//table//tr[contains(@class,'tab-list-item')]");
    protected By name = By.xpath(".//td[1]");
    protected By status = By.xpath("//div[@class='list-item-state inline-block align-middle ng-binding']");
    protected By type = By.xpath(".//td[3]");

    protected By loadMoreLink = By.cssSelector("div#learning-plan-tab a[ng-click*='loadMore']");

    protected By learningtab= By.xpath("//a[contains(text(),'My Learning Plan')]");
    protected By pathloadmorelink = By.xpath("//div[@id='learning-plan-tab']/div[contains(@class,'load-more')]/a");
    protected By pathnamelist = By.xpath("//div[@id='learning-plan-tab']//table//tr[contains(@class,'tab-list-item')]");
    protected By pathnames = By.xpath("//td[contains(@class,'list-item-name')]");
    
    
    public void clickOnLearningTab()
    {
    	  if(uiUtil.isElementPresence(learningtab, 5))
    	  {
    		  uiUtil.clickUsingJs(learningtab);
    	  }
    }
    
    public void clickOnPathLoadMore()
    {
    	 if(uiUtil.isElementPresence(pathloadmorelink, 5))
   	  {
   		  uiUtil.clickUsingJs(pathloadmorelink);
   		  pauseMe(4);
   	  }
    }
    public String getPathStatus(String pathName)
    {
    	  
    	String statusValue=null;
    	List<WebElement> listEle = findElements(pathnamelist);


	for(WebElement element : listEle )	
	{
		if(element.getText().trim().contains(pathName))
		{
			statusValue=element.findElement(status).getText().trim();
		}
	}
    	
    	return statusValue;
    	
    
    }
    public String getCourseStatus(String courseName)
    {
        clickLoadMore_LearningPlan();
        String statusValue = null;
        List<WebElement> listEle = findElements(tableRows);
        for(WebElement ele : listEle) {
            if(ele.findElement(name).getText().trim().contains(courseName)) {
                statusValue = ele.findElement(status).getText().trim();
                break;
            }
        }
        return statusValue;
    }


    public void clickLoadMore_LearningPlan() {
        if(uiUtil.isElementVisible(loadMoreLink,5)) {
            uiUtil.clickEle(loadMoreLink);
            pauseMe(3);
        }
    }

    public boolean validateStatusForCourses_Path(ArrayList<String> courses) {

        for(String s1 : courses) {
            String status = getCourseStatus(s1);
            if(!status.contains("Completed")) { return false;}
        }
        return true;
    }




}
