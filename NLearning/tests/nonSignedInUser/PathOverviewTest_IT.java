package com.snc.surf.marketing.NLearning.tests.nonSignedInUser;

import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.surf.marketing.NLearning.objectData.GlobalSearchCardDetails;
import com.snc.surf.marketing.NLearning.pageclass.Constants;
import com.snc.surf.marketing.NLearning.utils.Entitlements.CardObject;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import java.util.List;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class PathOverviewTest_IT extends NLETestBase {
    CardObject cObj;

    List<WebElement> listElements;
    CardDetails cardData;
    List<String> pathDetails;

    @Step(value =0, info ="Non Signed In User - navigate to Discover Page, Path Validations")
    public void navigateToDiscoverPage() {

        openDiscoverPage();
        cObj = nleEntitlementUtils.setCardObject(); // manier times the assert fails here when record is not present in backend.

        discoverPage.selectFilter(FilterNames.ROLE.toString(), cObj.role);
        discoverPage.selectFilter("Level", cObj.level);
        discoverPage.selectFilter("Product", cObj.product);
      //  discoverPage.selectFilter("Other Interests",cObj.tag);   // Unable to see this filter , need to check the latest functionality
    }


    @Step(value =1, info ="Non Signed In User - Discover Perform Search and Validate Results")
    public void performSearchforSpecificFilter() {
        discoverPage.clickShowResults();
        listElements= discoverPage.getFilteredResults();
        Assert.assertNotNull("Results are not displayed for specific card search",listElements);
        cardData = discoverPage.fetchCardDetails(listElements.get(0));
    }


    @Step(value =2, info ="Non Signed In User - Validate Path Overview Page and path details")
    public void openPathOverviewPage() {

        discoverPage.clickCard(listElements,cObj.name);
        Assert.assertTrue("Enroll Btn is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        String pathName = pathpageoverview.getPathNameDisplayed();
        Assert.assertTrue("Path name is not displayed as expected",pathName.contains(cObj.name));
        pathDetails = pathpageoverview.fetchPathDetails();
        Assert.assertNotNull("Path details are not displayed under header for specific card search",pathDetails);
        Assert.assertTrue("Duration doesnt match", pathDetails.get(0).contains(cObj.duration));
        Assert.assertTrue("Level doesnt match", pathDetails.get(1).contains(cObj.level));
        Assert.assertTrue("Category doesnt match", pathDetails.get(2).contains(cObj.category));
        Assert.assertTrue("Category doesnt match", pathDetails.get(2).contains(cObj.category));
    }

    @Step(value =3, info ="Non Signed In User - Validate warning message when clicked on Enroll button")
    public void validateWarningMessageForEnrollBtn() {
        pathpageoverview.clickEnrollBtn();
        Assert.assertTrue("Warning message is not displayed when clicked on enroll button",pathpageoverview.isWarningAlertDisplayed());

    }


}
