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
public class HamburgerIcon_NonSignedInIT extends NLETestBase {


	 @Step(value =0, info ="Verify the hamburger icons, Dicvoer Link")
	    public void hamburgerIcon_Discover() {
	    	open(LXP_HOME_URL);
	    	closeModalDialog();
	    	landingpage.clickHamburgerIcon();
	    	landingpage.clickOnLinkInHamburgerNonSignedIn("Discover");
	    	Assert.assertTrue("'Discover your Path' section is Not displayed", landingpage.isSectionDisplayed("discover"));
	    }
    @Step(value =3, info ="Verify the hamburger icons, Live Clases link")
    public void hamburgerIcon_LiveClases() {
        landingpage.clickHamburgerIcon();
        landingpage.clickOnLinkInHamburgerNonSignedIn("Live Classes");
       Assert.assertTrue("'Live Clases' section is Not displayed",landingpage.isSectionDisplayed("Live Classes"));

    }
    @Step(value =5, info ="Verify the hamburger icons, Certifications link")
    public void hamburgerIcon_Certifications() {
        landingpage.clickHamburgerIcon();
        landingpage.clickOnLinkInHamburgerNonSignedIn("Certifications");
       Assert.assertTrue("'Certificstion' section is Not displayed",landingpage.isSectionDisplayed("Certification"));

    }
    @Step(value =7, info ="Verify the hamburger icons, Simulators link")
    public void hamburgerIcon_Simulators() {
        landingpage.clickHamburgerIcon();
        landingpage.clickOnLinkInHamburgerNonSignedIn("Simulators");
       Assert.assertTrue("'Simulators' section is Not displayed",landingpage.isSectionDisplayed("simulator"));

    }
    @Step(value =9, info ="Verify the hamburger icons, Featured link")
    public void hamburgerIcon_Featured() {
        landingpage.clickHamburgerIcon();
        landingpage.clickOnLinkInHamburgerNonSignedIn("Featured");
       Assert.assertTrue("'Featured' section is Not displayed",landingpage.isSectionDisplayed("featured"));

    }
   
 
}
