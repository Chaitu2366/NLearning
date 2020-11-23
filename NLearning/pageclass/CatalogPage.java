package com.snc.surf.marketing.NLearning.pageclass;

import org.openqa.selenium.By;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;

public class CatalogPage extends NLEPageBase {
	 protected By roleSelectionSection = By.cssSelector("dropdown-multiselect[options*='filterMetadata.persona'] div");
	 
	 
	public void waitForCatalogPageLoad(){
    	wait.waitAndObtainWebElement(roleSelectionSection, 30);
    	
    }

}
