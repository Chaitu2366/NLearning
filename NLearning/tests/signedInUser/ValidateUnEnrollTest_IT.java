package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.surf.marketing.NLearning.base.NLETestBase;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class ValidateUnEnrollTest_IT extends NLETestBase {

    String encodedQuery = "source=lxp^ORsource=Brightcove^active=true";
    String sysId = null;
    String courseName;
    String courseUrl = "/lxp?id=overview&sys_id=%s&type=course";
    String pathURL = "/lxp?id=overview&sys_id=%s&type=path";

    public String impersonateUser = null;

    @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainActiveNLUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);
    }

   @Step(value =1, info ="Signed In User - enroll a course and also unenroll the same course")
    public void openUnEnrolledCourse() {  // mainly for LXP, Brightcove, RustIC courses which are enrolled to user
        userSignIn(userName,password); // replace with auto username
        open("/nav_to.do");
        leutils.impersonateWithUserName(impersonateUser);
        sysId = leutils.getRandomSysIDOfRecord(COURSES_TABLE,encodedQuery);
        courseUrl = String.format(courseUrl,sysId);
        open(courseUrl);
        Assert.assertTrue("Enroll Button is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        pathpageoverview.clickEnrollBtn();
        Assert.assertTrue("UnEnroll Button is not displayed after enrolling the course",pathpageoverview.isUnEnrollBtnDisplayed());
        pathpageoverview.clickUnEnrollBtn();
        Assert.assertTrue("Enroll Button is not displayed after Unenrolling the course",pathpageoverview.isEnrollBtnDisplayed());
    }

    @Step(value =2, info ="Signed In User - Enroll Brightcove course and complete it")
    public void validateUnenrollOptionForPath_Course() {
        String encQuery = "course!=NULL^path!=NULL";
        sysId = leutils.getRandomGlideRecordEncodedQuery(MAPPING_TABLE,encQuery,"path");
        MessageLogger.annotate("SysID: "+sysId);
        pathURL = String.format(pathURL,sysId);
        open(pathURL);
        Assert.assertTrue("Enroll Button is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        pathpageoverview.clickEnrollBtn();
        Assert.assertFalse("UnEnroll Button is not displayed after enrolling the course",pathpageoverview.isUnEnrollBtnDisplayed());
    }
}
