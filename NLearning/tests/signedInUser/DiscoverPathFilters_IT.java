package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.surf.marketing.NLearning.utils.Entitlements.CardObject;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import java.util.List;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class DiscoverPathFilters_IT extends NLETestBase {

    CardObject cObj;

    @Step(value =1, info ="Signed In User - navigate to Discover Page, Path Validations")
    public void navigateToDiscoverPage() {
        userSignIn(userName,password);

        openDiscoverPage();
        cObj = nleEntitlementUtils.setCardObject();

        /* discoverPage.selectFilter(FilterNames.ROLE.toString(), "Tester");  // Actual
        discoverPage.selectFilter("Level", "Advanced");
        discoverPage.selectFilter("Product", "HR Service Delivery");
        //discoverPage.selectFilter("Other Interests","Dynamic Scheduling","User Profile");  */

        discoverPage.selectFilter(FilterNames.ROLE.toString(), cObj.role);
        discoverPage.selectFilter("Level", cObj.level);
        discoverPage.selectFilter("Product", cObj.product);
        discoverPage.selectFilter("Other Interests",cObj.tag);
    }

    @Step(value =2, info ="Signed In User - Discover Perform Search and Validate Results")
    public void performSearchForSpecificFilter() {
        discoverPage.clickShowResults();
        //Assert.assertTrue("Results are not displayed", discoverPage.getResultsCount()>0);
        List<WebElement> listElements= discoverPage.getFilteredResults();
        CardDetails cardData = discoverPage.fetchCardDetails(listElements.get(0));
        String s1 = cardData.getCategoryTypeStr(); // cObj.category;
        Assert.assertEquals("Card Category is mismatched",cardData.getCategoryTypeStr().toLowerCase(),cObj.category.toLowerCase());    // category
        Assert.assertEquals("Duration value doesnt match",cardData.getDurationStr().toLowerCase(),cObj.duration.toLowerCase());   // duration
        Assert.assertEquals("Card Name doesnt match",cardData.getCardNameStr().toLowerCase(),cObj.name.toLowerCase());   // name
        Assert.assertTrue("Card Short Description doesnt match",cObj.shortDescription.toLowerCase().contains(cardData.getCardDescrStr().toLowerCase()));  // description

        if(cObj.courseCount!=0) {
            Assert.assertEquals("Courses count on the card doesnt match", cardData.getCourses(), cObj.courseCount); // courseCount
        }

        Assert.assertNotNull("Card Value - Free/ $ Value not displayed",cardData.getCardValueStr());   // notnull
        Assert.assertNotNull("Card type is not displayed",cardData.getCardTypeStr()); // not null
    }

    @Step(value =3, info ="Non Signed In User - Get Course Names from filtered results and validate")
    public void validateCourseNames() {

        List<String> listStr = discoverPage.getContentObjectNames("course");
        listStr.forEach(System.out::println);

        List<String> listStr1 = discoverPage.getContentObjectNames("path");
        listStr1.forEach(System.out::println);

        //Assert.assertTrue("Results are not displayed", discoverPage.getResultsCount()>0);
    }
}
