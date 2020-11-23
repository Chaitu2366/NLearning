package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import org.openqa.selenium.By;

public class LandingPage extends NLEPageBase {

    protected By loadMoreLink = By.xpath(".//a[contains(text(),'Load More')]");

    protected By signInDrpDwn = By.cssSelector("a[ng-click*='togglePopup'][aria-label='Welcome']");
    protected By signInBtn = By.cssSelector("a[ng-click*='c.setSignIn']");
    protected By username = By.id("username");
    protected By password = By.id("password");
    protected By signIn = By.id("submitButton");
    protected By closeBtn = By.cssSelector("span.closebtn");
    protected By searchLabel = By.cssSelector("a[aria-label='Search']");
    protected By liveClassesLink = By.cssSelector("a[aria-label='Live Classes']");
    protected By certificationsLink = By.cssSelector("a[aria-label='Certifications']");
    protected By simulatorsLink = By.cssSelector("a[aria-label='Simulators']");
    protected By featuredLink = By.cssSelector("a[aria-label='Featured']");
    protected By hamburgerMenuIcon = By.cssSelector("a[aria-label='Site Menu']");
	protected By hamburgerMenuWindow = By.cssSelector("div[sn-atf-area='LXP Site Menu']");
    protected By catalogLink = By.xpath("//ul[@class='lxp-site-menu-container']//div[contains(text(),'Catalog')]");
	protected By helpLink = By.xpath("//div[contains(text(),'Help')]");
	protected By homeLink = By.xpath("//ul[@class='lxp-site-menu-container']//a[contains(text(),'Home')]");
    protected By createCase = By.xpath(".//a[contains(text(),'Create a Support Case')]");
    protected By siteMenu = By.cssSelector("div#xfdb386121b7df7003a2310ad2d4bcbcc a[aria-label*='Site Menu']");
    protected By helpMenuOption = By.xpath(".//div[contains(text(),'Help')]");
    protected By closecertiDialog = By.cssSelector("div[class='modal-content']>div>button>span");
    protected By avatarProfile= By.xpath("//div[@primary='avatarProfile']");
	protected By signoutBtn= By.xpath("//a[text()='Sign Out']");
	protected By badgesloadMorelink= By.xpath(" //div[contains(@class,'my-badges-tab')]/div[contains(@class,'load-more')]/a");
	protected By warningMsgXicon= By.xpath("//span[@ng-click='::c.dismiss(a.id)']");
	protected By closeMessageDialog = By.cssSelector("div.sp-announcement-root span.close");
	

    public void clickSearchIcon()
    {
        uiUtil.waitForEleVisibility(searchLabel,10);
        uiUtil.clickEle(searchLabel);
    }


    public void clickSignIn()
    {
        uiUtil.clickEle(signInDrpDwn);
        uiUtil.clickEle(signInBtn);
        timers.waitUntilDOMReady(10);
    }

    public void performSignIn(String userName, String Password)
    {
        uiUtil.waitForEleVisibility(username,30);
        uiUtil.sendKeys(username,userName);
        uiUtil.sendKeys(password,Password);
        uiUtil.clickEle(signIn);
        timers.waitUntilDOMReady(40);
    }
    
    public void clickLoadMore()
    {
        if(uiUtil.isElementVisible(loadMoreLink,7)) {
            uiUtil.clickEle(loadMoreLink);
            pauseMe(3);
        }
    }

    public void clickLiveClassesLink()
    {
        uiUtil.waitForEleVisibility(liveClassesLink,10);
        uiUtil.moveToElement(getDriver().findElement(liveClassesLink));
        uiUtil.clickEle(liveClassesLink);
    }

    public void clickCertificationsLink()
    {
        uiUtil.waitForEleVisibility(certificationsLink,10);
        uiUtil.moveToElement(getDriver().findElement(certificationsLink));
        uiUtil.clickEle(certificationsLink);
    }

    public void clickSimulatorsLink()
    {
        uiUtil.waitForEleVisibility(simulatorsLink,10);
        uiUtil.moveToElement(getDriver().findElement(simulatorsLink));
        uiUtil.clickEle(simulatorsLink);
    }

    public void clickFeaturedLink()
    {
        uiUtil.waitForEleVisibility(featuredLink,10);
        uiUtil.moveToElement(getDriver().findElement(featuredLink));
        uiUtil.clickEle(featuredLink);
    }
    public void clickHamburgerIcon() {
		wait.waitAndObtainWebElement(hamburgerMenuIcon, 20);
		uiUtil.clickEle(hamburgerMenuIcon);
	}
	public void clickOnLinkInHamburgerNonSignedIn(String linkName) {
		wait.waitAndObtainWebElement(hamburgerMenuWindow, 20);
		uiUtil.findElement(By.xpath("//div[@sn-atf-area='LXP Site Menu']//a[contains(text(),'"+linkName+"')]")).click();
		timers.waitUntilDOMReady(10);
	}

	public boolean isSectionDisplayed(String linkText) {		
		boolean isPresent = false;
		if(linkText.equalsIgnoreCase("Live Classes"))
			isPresent = getDriver().getCurrentUrl().contains("live-classes");
		else if (linkText.equalsIgnoreCase("Home"))
			isPresent = getDriver().getCurrentUrl().contains("dashboard");
		else if (linkText.equalsIgnoreCase("Help"))
			isPresent = getDriver().getCurrentUrl().contains("id=csm_get_help");
		else if (linkText.equalsIgnoreCase("All"))
			isPresent = getDriver().getCurrentUrl().endsWith("id=lxp_catalog");
		else {
			System.out.println(getDriver().getCurrentUrl());
			isPresent = getDriver().getCurrentUrl().contains(linkText.toLowerCase());
		}
		return isPresent;
	}
	public void clickOnLinkInHamburgerSignedIn(String linkName) {
		if(uiUtil.findElements(warningMsgXicon).size() > 0){
			uiUtil.clickEle(warningMsgXicon);
		}
		clickHamburgerIcon();
		if(linkName.equalsIgnoreCase("Knwoledge Base")|| linkName.equalsIgnoreCase("Create a Support Case")){
			uiUtil.clickEle(helpLink);
			clickOnLinkInHamburgerNonSignedIn(linkName);
		}
		else if (linkName.equalsIgnoreCase("Help"))
			clickOnLinkInHamburgerNonSignedIn(linkName);
		else if (linkName.equalsIgnoreCase("Home"))
			clickOnLinkInHamburgerNonSignedIn(linkName);			
		else {
			uiUtil.clickEle(catalogLink);
			clickOnLinkInHamburgerNonSignedIn(linkName);
		}
	}
    public void clickMenuOptions()
    {
        if(uiUtil.isElementVisible(closeMessageDialog, 20)) {
            uiUtil.clickUsingJs(closeMessageDialog);
            pauseMe(3);
        }
            uiUtil.clickEle(siteMenu);
    }

    public void clickHelp()
    {
        uiUtil.clickEle(helpMenuOption);
        uiUtil.clickEle(createCase);
    }

    public void closeSuccessMessage()
    {
       uiUtil.clickUsingJs(closeBtn);
    } 
    public void closeCertificationDialog() {
    	
		if(uiUtil.isElementVisible(closecertiDialog, 5))
		{
		uiUtil.moveToElement(getDriver().findElement(closecertiDialog));
		uiUtil.clickEle(closecertiDialog);
		}
	}

    public void performSignOut()
	{
		uiUtil.clickEle(avatarProfile);
		uiUtil.clickEle(signoutBtn);
		timers.waitUntilDOMReady(40);
	}
    public void clickBadgesLoadMore()
    {
        if(uiUtil.isElementVisible(badgesloadMorelink,7)) {
            uiUtil.clickEle(badgesloadMorelink);
            pauseMe(3);
        }
    }
}
