package com.snc.surf.marketing.NLearning.tests.nonSignedInUser;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.surf.marketing.NLearning.utils.UiUtility;
import com.snc.surf.marketing.NLearning.utils.Entitlements.CardObject;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import java.util.List;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class DiscoverPathIT extends NLETestBase {
    CardObject cObj;

    @Step(value =0, info ="Non Signed In User - navigate to Discover Page, Path Validations")
    public void navigateToDiscoverPage() {

        openDiscoverPage();
        cObj = nleEntitlementUtils.setCardObject();

        /* discoverPage.selectFilter(FilterNames.ROLE.toString(), "Tester");  // Actual
        discoverPage.selectFilter("Level", "Advanced");
        discoverPage.selectFilter("Product", "HR Service Delivery");
        //discoverPage.selectFilter("Other Interests","Dynamic Scheduling","User Profile");  */

        discoverPage.selectFilter(FilterNames.ROLE.toString(), cObj.role);
        discoverPage.selectFilter("Level", cObj.level);
        discoverPage.selectFilter("Product", cObj.product);
      //  discoverPage.selectFilter("Other Interests",cObj.tag);
    }

    @Step(value =1, info ="Non Signed In User - Discover Perform Search and Validate Results")
    public void performSearchForSpecificFilter() throws InterruptedException {
        discoverPage.clickShowResults();
      // Assert.assertTrue("Results are not displayed", discoverPage.getResultsCount()>0);
        List<WebElement> listElements= discoverPage.getFilteredResults();
        CardDetails cardData = discoverPage.fetchCardDetailswithcarddescription(listElements.get(0));
        String s1 = cardData.getCategoryTypeStr(); // cObj.category;
       // Assert.assertEquals("Card Category is mismatched",cardData.getCategoryTypeStr().toLowerCase(),cObj.category.toLowerCase());    // category
    //    Assert.assertEquals("Duration value doesnt match",cardData.getDurationStr().toLowerCase(), cObj.duration.toLowerCase());   // duration
      //  Assert.assertEquals("Card Name doesnt match",cardData.getCardNameStr().toLowerCase(),cObj.name.toLowerCase());   // name
  System.out.println("the data valuse is ---------" + cObj.shortDescription.toLowerCase()+ "card Category is--"+ cObj.category.toLowerCase() );
  System.out.println("the application  valuse is ---------" + cardData.getCardDescrStr().toLowerCase()+ "card Category is--"+ cardData.getCategoryTypeStr().toLowerCase());
   //Thread.sleep(50000000);
  // Assert.assertTrue("Card Short Description doesnt match",cObj.shortDescription.toLowerCase().contains(cardData.getCardDescrStr().toLowerCase()));  // description


    }
       /* if(cObj.courseCount!=0) {
            Assert.assertEquals("Courses count on the card doesnt match", cardData.getCourses(), cObj.courseCount); // courseCount
        }

        Assert.assertNotNull("Card Value - Free/ $ Value not displayed",cardData.getCardValueStr());   // notnull
        Assert.assertNotNull("Card type is not displayed",cardData.getCardTypeStr()); // not null
    }*/

    @Step(value =2, info ="Non Signed In User - Get Course Names from filtered results and validate")
    public void validateCourseNames() throws InterruptedException {

        List<String> listStr = discoverPage.getContentObjectNames("course");
        listStr.forEach(System.out::println);

        List<String> listStr1 = discoverPage.getContentObjectNames("path");
        listStr1.forEach(System.out::println);
        //Assert.assertTrue("Results are not displayed", discoverPage.getResultsCount()>0);
        String result=discoverPage.clickBtn();
        Assert.assertEquals("View 0 items selected", result);

        
    }
    
   //======================================================================================
    
    @Step(value =3, info ="Non Signed In User - search by role")
    public void searchbyRole() {

        discoverPage.selectFilter(FilterNames.ROLE.toString(), cObj.role);
        cObj = nleEntitlementUtils.setCardObject();
        discoverPage.selectFilter(FilterNames.ROLE.toString(), cObj.role);
        discoverPage.clickShowResults();
        List<WebElement> listElements= discoverPage.getFilteredResults();
        CardDetails cardData = discoverPage.fetchCardDetailswithcarddescription(listElements.get(0));
        String s1 = cardData.getCategoryTypeStr(); // cObj.category;
       // Assert.assertEquals("Card Category is mismatched",cardData.getCategoryTypeStr().toLowerCase(),cObj.category.toLowerCase());    // category
    //    Assert.assertEquals("Duration value doesnt match",cardData.getDurationStr().toLowerCase(),cObj.duration.toLowerCase());   // duration
      //  Assert.assertEquals("Card Name doesnt match",cardData.getCardNameStr().toLowerCase(),cObj.name.toLowerCase());   // name
    //  Assert.assertTrue("Card Short Description doesnt match",cObj.shortDescription.toLowerCase().contains(cardData.getCardDescrStr().toLowerCase()));  // description
      System.out.println("the value is ------"+cardData.getCardDescrStr());
     System.out.println("the 2nd value is "+cObj.shortDescription);

    }        

    @Step(value =4, info ="Non Signed In User - search by role and Level")
    public void searchbyRoleAndLevel()  {

        discoverPage.selectFilter(FilterNames.ROLE.toString(), cObj.role);
        discoverPage.selectFilter("Level", cObj.level);
        cObj = nleEntitlementUtils.setCardObject();
        discoverPage.selectFilter(FilterNames.ROLE.toString(), cObj.role);
        discoverPage.selectFilter("Level", cObj.level);
        discoverPage.clickShowResults();
        List<WebElement> listElements= discoverPage.getFilteredResults();
        CardDetails cardData = discoverPage.fetchCardDetailswithcarddescription(listElements.get(0));
        String s1 = cardData.getCategoryTypeStr(); // cObj.category;
       // Assert.assertEquals("Card Category is mismatched",cardData.getCategoryTypeStr().toLowerCase(),cObj.category.toLowerCase());    // category
    //    Assert.assertEquals("Duration value doesnt match",cardData.getDurationStr().toLowerCase(),cObj.duration.toLowerCase());   // duration
      //  Assert.assertEquals("Card Name doesnt match",cardData.getCardNameStr().toLowerCase(),cObj.name.toLowerCase());   // name
    //  Assert.assertTrue("Card Short Description doesnt match",cObj.shortDescription.toLowerCase().contains(cardData.getCardDescrStr().toLowerCase()));  // description
      System.out.println("the value is ------"+cardData.getCardDescrStr());
     System.out.println("the 2nd value is "+cObj.shortDescription);

    }        

        

    

    
   //======================================================================================    
    

}