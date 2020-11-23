package com.snc.surf.marketing.NLearning.tests.nonSignedInUser;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.surf.marketing.NLearning.objectData.GlobalSearchCardDetails;
import com.snc.surf.marketing.NLearning.pageclass.Constants;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import java.util.List;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class GlobalSearch_CardDetailsIT extends NLETestBase {



    @Step(value =0, info ="Non Signed In User - global Search for path")
    public void performGlobalSearch_Path() {

        openLandingPage();
        landingpage.clickSearchIcon();
        glbSearchPage.performSearch(Constants.CARD_TITLE);
        Integer resultsCount = glbSearchPage.getSearchResultsCount();
        Assert.assertNotNull("Results are not displayed for specific card search",resultsCount);
        nleEntitlementUtils.getGuestUserInfo("All","","");
        Assert.assertTrue("card Name is not displayed for Guest User",nleEntitlementUtils.validateCardName(Constants.CARD_TITLE));

    }

    @Step(value =1, info ="Non Signed In User - Global Search for a path and validate the card details")
    public void performGlobalSearchAndValidateCardDetails() {

        List<WebElement> listElements= glbSearchPage.getCardSearchDetails();
        GlobalSearchCardDetails cardData = glbSearchPage.fetchCardDetails(listElements.get(0));
        System.out.println(cardData.getCardTypeStr());
        System.out.println(cardData.getCardNameStr());
    }


}
