package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;

import org.openqa.selenium.By;



public class MyProfilePage extends NLEPageBase {
	protected By homeTextEle = By.xpath("//a[@href ='?id=dashboard'][text()='Home']");
	
	public boolean waitForMyProfilePage() {
		boolean pageDisplay = false;
		try {
			wait.waitAndObtainWebElement(homeTextEle, 30);
			pageDisplay = true;
		}
		catch (Exception e){
			pageDisplay = false;
			System.out.println("Element is not displayed : " + e);
		}
		return pageDisplay;
	}
	
	public boolean isMyProfilePageDisplayed() { 
		return waitForMyProfilePage();
	}

}
