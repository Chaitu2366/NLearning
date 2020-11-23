package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.util.ScreenShotUtil;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class CreateCase_IT extends NLETestBase {

    String[] array = {"Feedback","Certification - Assessment Issue","Course Content Issue","Technical Error/Bug","Login/SSO Issue","Account Merging","Path Issue"};
    ScreenShotUtil screenShotUtil = new ScreenShotUtil();
    String ticketNumber,encQuery;
    String serviceCaseSysID,surfDefectId, recipientGroup,assignmentGroup;
    String surfInstance = null;

    public String impersonateUser = null;

   // @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainMainlineCertiifcationForNlUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);
        surfInstance = leutils.getInstanceEndpointURL();
    }

    @Step(value =1, info ="Signed In User - Click Help --> Create Case incident")
    public void openHelp_CreateCase() {
        userSignIn(userName,password);
        landingpage.clickMenuOptions();
        landingpage.clickHelp();
    }

    @Step(value =2, info ="Signed In User - submit a new Case incident -> validate details")
    public void submitNewCaseAndValidateDetails() {
        List<String> listCategories = Arrays.asList(array);
        String category = uiUtil.getRandomElement(listCategories);
        String currentdate = leutils.getCurrentDateWithMins_NoSpace();
        screenShotUtil.takeScreenshot(currentdate);
        casePage.chooseCategoryOption(category);
        casePage.enterSubject("Subject "+currentdate);
        casePage.enterDescription("description "+currentdate);
        MessageLogger.annotate("File Path: "+System.getProperty("user.dir")+"/target/screenshots/"+currentdate+".png");
        casePage.addAttachment(System.getProperty("user.dir")+"/target/screenshots/"+currentdate+".png");
        casePage.clickSubmit();
        Assert.assertTrue("Ticket number is not generated after case creation",casePage.validateTicketSubmitted());
        ticketNumber = casePage.getTicketNumber();
        Assert.assertEquals("Category name is incorrect",category,casePage.getCaseDetails("Category"));
        Assert.assertEquals("Subject name is incorrect",category,casePage.getCaseDetails("Subject"));
        Assert.assertEquals("Description detail is incorrect",category,casePage.getCaseDetails("Description"));
    }

    @Step(value =3, info ="Signed In User - submit a new Case incident -> validate details in backend")
    public void validateCaseDetailsInBackendTable() {
        if(ticketNumber!=null) {
            encQuery = "number=%s";
            encQuery = String.format(encQuery,ticketNumber);
            int recordCount = leutils.getGlideRecordCount(CASE_TABLE,encQuery);
            Assert.assertNotNull("No records observed for specified criterion",recordCount);
            String sysID = leutils.getGlideRecordEncodedQuery(CASE_TABLE,"sys_id",encQuery);
            Assert.assertNotNull("Sys id not obtained for specific record",sysID);
        }
    }

    @Step(value =4, info ="Signed In User - submit a new Case incident -> create Surf Defect id for case filed")
    public void createSurfDefectForNLCaseFiled() {
        String encQuery = "number=";
        serviceCaseSysID = leutils.getSysIDOfRecord("sn_customerservice_case",encQuery+ticketNumber,"sys_id");
        open("/sn_customerservice_case.do?sys_id="+serviceCaseSysID);

        button.assertPresent("create_surf_defect");
        button.click("create_surf_defect");
        timers.waitUntilDOMReady(40);
        Assert.assertTrue("Defect link notification is not visible",casePage.validateNewDefectNotification());
        surfDefectId = casePage.getSurfDefectId();
        Assert.assertNotNull("Surf Defect ID is not obtained",surfDefectId);
        assignmentGroup = leutils.getGlideRecordEncodedQueryDisplyvalue("sn_customerservice_case","assignment_group",encQuery+ticketNumber);
    }

    @Step(value =5, info ="Signed In User - submit a new Case incident -> validate Surf Defect ID in related Surf Records tab")
    public void validateSurfRecordID() {
        tab.view("Related Surf Records");
        String defectID = uiUtil.getAttributeForElement(By.cssSelector("input[id*='sn_customerservice_case.u_surf_defect'][type='text']"),"value");
        Assert.assertEquals("Surf Defect id is not displayed as expected",defectID,surfDefectId);
    }

    @Step(value =6, info ="Signed In User - submit a new Case incident -> validate Email received in NL Instance")
    public void validateEmailReceivedInNLInstance() {
        Assert.assertEquals("Email is not triggered for the case opened",leutils.validateEmailReceivedForCaseCreated(ticketNumber),1);
        recipientGroup = leutils.getRecipientGroupName(ticketNumber);
        Assert.assertNotNull("Recipient Group is not assigned for the corresponding case",recipientGroup);
        Assert.assertTrue("Assignment group and recipient group are not same.",recipientGroup.toLowerCase().contains(assignmentGroup.toLowerCase()));
    }

    @Step(value =7, info ="Signed In User - submit a new Case incident -> validate Defect created in SurfTable")
    public void validateDefectCreatedInSurfDefectsTable() {
        surfInstance = leutils.getInstanceEndpointURL();
        String encQuery = "number=";
        String defectID = leutils.getRecordValueBasedonEncQuery("rm_defect",encQuery+surfDefectId,"number",surfInstance);
        Assert.assertEquals("Surf Defect id is not displayed as expected",defectID,surfDefectId);

        String description = leutils.getRecordValueBasedonEncQuery("rm_defect",encQuery+surfDefectId,"description",surfInstance);
        Assert.assertTrue("Defect description doesn't contain NL Case Ticket Number",description.contains(ticketNumber));
    }
}
