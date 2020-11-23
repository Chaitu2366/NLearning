package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class AlmostEarnedBadgesTest_IT extends NLETestBase {

    String courseName = null;

    private String encQuery = "badge!=NULL^sourceINlxp";
    String courseUrl = "/lxp?id=overview&sys_id=%s&type=course";
    String sysId;
    public String impersonateUser = null;

    @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainMainlineCertiifcationForNlUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);
    }

    @Step(value =0, info ="Signed In User - Open existing course which has a badge assigned and partial execute it")
    public void openExistingCourse_ForPartialExecution() {
        userSignIn(userName,password);// replace with auto username
        leutils.impersonateWithUserName(impersonateUser);

      //  leutils.impersonateWithUserName("@servicenow.com");   Impersonate with newly registered user created through scripts
        sysId = leutils.getRandomSysIDOfRecord(COURSES_TABLE,encQuery);
        MessageLogger.annotate("SysID: "+sysId);

        sysId = "43ce3e8adb7d3b4069a56a5b8a961997";
        courseUrl = String.format(courseUrl,sysId);
        open(courseUrl);

        Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
        pathpageoverview.clickEnrollBtn();
        pathpageoverview.partialExecutionOfCourse();
    }


    @Step(value =1, info ="Signed In User - validate Almost Earned Badges")
    public void validateAlmostEarnedBadges() {
        courseName = leutils.getRecordValueBySysID(COURSES_TABLE,sysId,"name");

        open(LXP_LEARNING_MYBADGES);
        boolean bVal = mybadgespage.validateCourseNameDisplayed(courseName);
        Assert.assertTrue("Course name is not displayed",bVal);

        String progStatus = mybadgespage.getAlmostEarnedBadgeStatus(courseName);
        Assert.assertEquals("Course Status is not In Progress","In Progress",progStatus);
    }


}
