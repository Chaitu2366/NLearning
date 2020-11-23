package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class CreateVoucherCodePathTest_IT extends NLETestBase {


    String voucherCodeURL = "/x_snc_lxp_voucher_code.do?sys_id=-1";
    String encQuery = "role=ba45e0fe0a00070400a4cb85f3c806a7";
    String certificationAdmin = null;
    String code,certName,pathSysId,pathSysIds;
    List<String> pathDetails;
    String pathName = "Test Auto Path_16";
    String expDate,sysId,voucherState;
    String encodedQuery = "name=%s";
    Map<String, String> map = new HashMap<>();
    String pathEncQuery = "active=True^category.name=Accreditation";

    public String impersonateUser = null;

    @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainActiveNLUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);

        List<String> pathSysIds = leutils.getSysIdSForGivenEncQuery(PATH_TABLE,pathEncQuery);
        pathSysId = leutils.fetchPathSysIdBasedOnCoursesAvailable("x_snc_lxp_m2m_course_path",pathSysIds);
    }

    @Step(value =1, info ="Signed In User - As certification admin, create voucher code")
    public void createNewVoucherCode() {
        userSignIn(userName, password);
        open("/nav_to.do");
        String userSysID = leutils.getGlideRecordEncodedQuery("sys_user_has_role","user",encQuery);
        certificationAdmin = leutils.getRecordValueBySysID("sys_user",userSysID,"user_name");
        certName = leutils.getGlideRecordEncodedQuery("x_snc_lxp_certification","name","active=true");
        code = voucherPage.randomAlphaNumeric(18);
        leutils.updateGlideRecordValue(PATH_TABLE,SYS_ID,pathSysId,"voucher","true");
        leutils.impersonateWithUserName(certificationAdmin);

        open(voucherCodeURL);
        expDate = leutils.getDateAfterBeforeCurrent("yyyy-MM-dd",220);
        voucherPage.createVoucherCode(code,certName,pathSysId,expDate);

    }

    @Step(value =2, info ="Signed In User - As certification admin, validate created voucher code")
    public void validateVoucherCreated() {
        sysId = leutils.getGlideRecordEncodedQuery(VOUCHER_TABLE,"sys_id","code="+code);
        Assert.assertNotNull("Voucher code is not created for specific course",sysId);

    }

    @Step(value =3, info ="Signed In User - As certification admin, validate created voucher code State -- Available")
    public void validateVoucherStateAvailable() {
        voucherState = leutils.getGlideRecordEncodedQuery(VOUCHER_TABLE,"state","code="+code);
        Assert.assertEquals("Voucher code is not in Available state","available",voucherState);
    }


    @Step(value =4, info ="Signed In User - complete Path to obtain voucher code")
    public void completePathToObtainVoucherCode() {
        userSignIn(userName, password);
        open("/nav_to.do");
        leutils.impersonateWithUserName(impersonateUser);
        open("/lxp?id=overview&type=path&sys_id="+pathSysId);
        //open("/lxp?id=overview&type=path&sys_id=132b34be1ba40450528f639fbd4bcbe7");
        Assert.assertTrue("Enroll Btn is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        String pathName = pathpageoverview.getPathNameDisplayed();
        // Assert.assertTrue("Path name is not displayed as expected",pathName.contains(Constants.CARD_TITLE));
        pathDetails = pathpageoverview.fetchPathDetails();
        Assert.assertNotNull("Path details are not displayed under header for specific card search",pathDetails);
        pathpageoverview.clickEnrollBtn();
        pathpageoverview.clickFirstModuleLessonAndCompletePath(assessmentUtils);
        pathpageoverview.closeBadgeDialog();
        String btntext = pathpageoverview.getTextEnrolBtn();
        Assert.assertEquals("NL Path is not completed yet","Completed",btntext);
    }

    @Step(value =5, info ="Signed In User - As certification admin, validate created voucher code State -- Assigned")
    public void validateVoucherStateAssigned() {
        voucherState = leutils.getGlideRecordEncodedQuery(VOUCHER_TABLE,"state","code="+code);
        Assert.assertEquals("Voucher code is not in Available state","assigned",voucherState);

        open(LXP_VOUCHERS_TAB);
        pathpageoverview.closeBadgeDialog();
        Assert.assertTrue("Voucher code assigned to user is not displayed",vouchersPage.validateVoucherCodeDisplay(code));
        Assert.assertTrue("Certificate assigned to user is not displayed",vouchersPage.validateVoucherCertificNameDisplay(certName));
    }


    @Step(value =6, info ="Signed In User - Validate voucher view link")
    public void validateViewLinkContent() {
        vouchersPage.clickViewForVoucherCode(code);
        Assert.assertEquals("Voucher code displayed is not as expected",code,vouchersPage.getVoucherNoFromDialog());
        open("/nav_to.do");

    }
}
