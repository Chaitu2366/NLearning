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
import java.util.Optional;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class CreateVoucherCodeTest_IT extends NLETestBase {

    String voucherCodeURL = "/x_snc_lxp_voucher_code.do?sys_id=-1";
    String encQuery = "role=ba45e0fe0a00070400a4cb85f3c806a7";
    String certificationAdmin = null;
    String code,certName,pathName,courseSysId;
    List<String> pathDetails;
    String expDate,sysId,voucherState;
    String courseEncQuery = "source=lxp^category=d4aeea7bdbb8374069a56a5b8a9619c1^active=true";
    String encodedQuery = "name=%s";
    Map<String, String> map = new HashMap<>();


    public String impersonateUser = null;

   @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainActiveNLUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);

       List<String> courseSysIds = leutils.getSysIdSForGivenEncQuery(COURSES_TABLE,courseEncQuery);
       courseSysId = leutils.fetchCourseIdBasedOnModulesAvailable("x_snc_lxp_m2m_modules_courses",courseSysIds);
    }


   @Step(value =1, info ="Signed In User - As certific admin, create voucher code")
    public void createNewVoucherCode() {
        userSignIn(userName, password);
        open("/nav_to.do");
        String userSysID = leutils.getGlideRecordEncodedQuery("sys_user_has_role","user",encQuery);
        certificationAdmin = leutils.getRecordValueBySysID("sys_user",userSysID,"user_name");
        certName = leutils.getGlideRecordEncodedQuery("x_snc_lxp_certification","name","active=true");
        code = voucherPage.randomAlphaNumeric(18);
        leutils.updateGlideRecordValue(COURSES_TABLE,SYS_ID,courseSysId,"voucher","true");
        leutils.impersonateWithUserName(certificationAdmin);

        open(voucherCodeURL);
        expDate = leutils.getDateAfterBeforeCurrent("yyyy-MM-dd",220);
        voucherPage.createVoucherCode(code,certName,courseSysId,expDate);

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

    @Step(value =4, info ="Signed In User - complete in house course to obtain voucher code")
    public void completeCourseToObtainVoucherCode() {
        open("/nav_to.do");
        leutils.impersonateWithUserName(impersonateUser);
        open("/lxp?id=overview&type=course&sys_id="+courseSysId);
        Assert.assertTrue("Enroll Btn is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        String pathName = pathpageoverview.getPathNameDisplayed();
        // Assert.assertTrue("Path name is not displayed as expected",pathName.contains(Constants.CARD_TITLE));
        pathDetails = pathpageoverview.fetchPathDetails();
        Assert.assertNotNull("Path details are not displayed under header for specific card search",pathDetails);
        pathpageoverview.clickEnrollBtn();
        pathpageoverview.clickFirstModuleLessonAndCompleteCourse(assessmentUtils);
        pathpageoverview.closeBadgeDialog();
        String btntext = pathpageoverview.getTextEnrolBtn();
        Assert.assertEquals("NL In-House Course is not completed yet","Completed",btntext);

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

    @Step(value =7, info ="Signed In User - As certification admin, validate updated voucher code State -- Expired for assigned voucher to specific user")
    public void validateVoucherStateExpiredForAssignedVoucher() {
        String dateToUpdate = leutils.getDateAfterBeforeCurrent("yyyy-MM-dd",-3);
        String sysid = leutils.getGlideRecordEncodedQuery(VOUCHER_TABLE,"sys_id","code="+code);
        Assert.assertNotNull("Voucher code is not created for specific course",sysid);

        leutils.updateGlideRecordValue(VOUCHER_TABLE,SYS_ID,sysid,"expiry_date",dateToUpdate);

        voucherState = leutils.getGlideRecordEncodedQuery(VOUCHER_TABLE,"state","code="+code);
        Assert.assertEquals("Voucher code is not moved expired state","expired",voucherState);
    }

    @Step(value =8, info ="Signed In User - As certification admin, validate created voucher code State -- Expired")
    public void validateVoucherStateExpired() {
        certName = leutils.getGlideRecordEncodedQuery("x_snc_lxp_certification","name","active=true");
        code = voucherPage.randomAlphaNumeric(18);
        expDate = leutils.getDateAfterBeforeCurrent("yyyy-MM-dd",4);
        map.put("code",code);
        map.put("course_path",courseSysId);
        String dateToUpdate = leutils.getDateAfterBeforeCurrent("yyyy-MM-dd",-3);
        leutils.insertGlideRecordValue(VOUCHER_TABLE,map);
        String sysid = leutils.getGlideRecordEncodedQuery(VOUCHER_TABLE,"sys_id","code="+code);
        Assert.assertNotNull("Voucher code is not created for specific course",sysid);

        leutils.updateGlideRecordValue(VOUCHER_TABLE,SYS_ID,sysid,"expiry_date",dateToUpdate);

        voucherState = leutils.getGlideRecordEncodedQuery(VOUCHER_TABLE,"state","code="+code);
        Assert.assertEquals("Voucher code is not moved expired state","expired",voucherState);
    }
}
