package com.snc.surf.marketing.NLearning.tests.nonSignedInUser;

import org.junit.Assert;
import org.junit.runner.RunWith;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class HamburgerIcon_SignedInIT extends NLETestBase {
	
	public String impersonateUser = null;


	@Step(value =1, info ="Verify the hamburger icons for Signed In user, Catalog -> All")
	public void hamburgerIconSI_Catalog_All() {
		impersonateUser = this.getActiveNlUser();
		userSignIn(userName,password);
        open("/nav_to.do");
        leutils.impersonateWithUserName(impersonateUser);
		
		landingpage.clickOnLinkInHamburgerSignedIn("All");
		catalogpage.waitForCatalogPageLoad();
		Assert.assertTrue("'Catalog' page is Not displayed", landingpage.isSectionDisplayed("All"));
	}
	@Step(value =3, info ="Verify the hamburger icons for Signed In user, Catalog -> Path")
	public void hamburgerIconSI_Catalog_Path() {
		landingpage.clickOnLinkInHamburgerSignedIn("Path");
		catalogpage.waitForCatalogPageLoad();
		Assert.assertTrue("Catalog page -> Path section is Not displayed", landingpage.isSectionDisplayed("Path"));
	}
	@Step(value =5, info ="Verify the hamburger icons for Signed In user, Catalog -> Courses")
	public void hamburgerIconSI_Catalog_Courses() {
		landingpage.clickOnLinkInHamburgerSignedIn("Courses");
		catalogpage.waitForCatalogPageLoad();
		Assert.assertTrue("Catalog page -> Courses section is Not displayed", landingpage.isSectionDisplayed("course"));
	}
	@Step(value =7, info ="Verify the hamburger icons for Signed In user, Catalog -> Certification")
	public void hamburgerIconSI_Catalog_Certification() {
		landingpage.clickOnLinkInHamburgerSignedIn("Certification");
		catalogpage.waitForCatalogPageLoad();
		Assert.assertTrue("Catalog page -> Certification section is Not displayed", landingpage.isSectionDisplayed("certification"));
	}
	@Step(value =9, info ="Verify the hamburger icons for Signed In user, Home")
	public void hamburgerIconSI_Home() {
		landingpage.clickOnLinkInHamburgerSignedIn("Home");
		Assert.assertTrue("DashBoard/Home page is Not displayed", landingpage.isSectionDisplayed("Home"));
	}
	@Step(value =11, info ="Verify the hamburger icons for Signed In user, Help")
	public void hamburgerIconSI_Help() {	
		landingpage.clickOnLinkInHamburgerSignedIn("Create a Support Case");
		casePage.waitForCasePageLoad();
		Assert.assertTrue("Support/Create Case page is Not displayed", landingpage.isSectionDisplayed("Help"));
	}

}
