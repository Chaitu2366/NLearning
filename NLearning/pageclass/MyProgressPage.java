package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MyProgressPage extends NLEPageBase {
	
	
	protected By lrnplan_hdr = By.xpath("//strong[contains(text(),'Add to My Learning Plan')]");	
	protected By mylrngplan = By.xpath("//a[contains(text(),'My Learning Plan')]");
	protected By banner= By.xpath("//button[contains(@class,'lets-fix-btn')]");
	protected By atriskWindw = By.xpath("//div[contains(text(),'At Risk Certifications')]");
	protected By banaccCert = By.xpath("//div[contains(@class,'inline-block text-lg')]");
	protected By banaccPath = By.xpath("//div[@ng-repeat='path in expiredCerts.paths']");
	protected By deltapatharrw = By.xpath("//div[contains(@class,'inline-block text-lg')]/a");
	protected List<WebElement>certelements=null;
	protected List<WebElement>pathelements=null;
	protected List<WebElement>arrwlements=null;
	
	public void clickOnMyLearningPlan()
	{
		
		timers.waitForElementVisible(lrnplan_hdr, 30);
		findElement(mylrngplan).click();
		
	}
	
	public void clickOnBanner()
	{
		if(uiUtil.isElementPresence(banner, 5))
		{
			uiUtil.clickUsingJs(banner);
		}
	}
	
	public void clickOnAtRiskAccDeltaPath(String cert,String pathname)
	{
		String str=null;
		if(uiUtil.isElementPresence(atriskWindw, 6)) 
		{
			certelements=findElements(banaccCert);
			pathelements=findElements(banaccPath);
			arrwlements=findElements(deltapatharrw);
			for(int i=0;i<certelements.size()&&i<pathelements.size()&&i<arrwlements.size();i++)
			{		
				if(certelements.get(i).getText().equalsIgnoreCase(cert) || pathelements.get(i).getText().equalsIgnoreCase(pathname))
				{
					//uiUtil.clickUsingJs(deltapatharrw);
					WebElement we = certelements.get(i).findElement(deltapatharrw);
					uiUtil.clickUsingJs(we);
					pauseMe(4);
				}	
			}	
		}
	}
}
