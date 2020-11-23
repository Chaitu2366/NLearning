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
public class RustICTest_IT extends NLETestBase {

    private String encQuery = "course.name=%s";
    private String courseName = "Rustici_orlando";
    String encodedQuery = "user.user_nameLIKE%s^course=%s";
    String courseSysID;

    public String impersonateUser = null;

    @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainActiveNLUser() {
        impersonateUser = this.getActiveNlUser();

        Log.info("impersonateUser" + impersonateUser);
        courseSysID = leutils.getSysIDOfRecord(COURSES_TABLE,"name="+courseName,"sys_id");
    }

    @Step(value = 1, info ="Signed In User - Enroll RustIC test course and complete it")
    public void openRustICCourseAndComplete() {
        userSignIn(userName,password);
        open("/nav_to.do");
        leutils.impersonateWithUserName(impersonateUser);
        open("/lxp?id=overview&type=course&sys_id="+courseSysID);

        Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
        pathpageoverview.clickEnrollBtn();
        pathpageoverview.clickStartBtn();
        Assert.assertTrue("Rust IC Course iframe is not opened",rustICCoursePage.validateCourseFrameDisplayed());
        rustICCoursePage.switchToIFrame();
        rustICCoursePage.clickStartCourseBtn();
        rustICCoursePage.completeAutomationRustICCourse();
        rustICCoursePage.exitCourse();
        rustICCoursePage.exitVideoCourse();

        String btntext = pathpageoverview.getTextEnrolBtn();
        Assert.assertEquals("NL In-House Course is not completed yet","Completed",btntext);
    }

    @Step(value = 2, info ="Signed In User - Enroll RustIC test course and complete it, Validate Badge earned")
    public void validateBadgeDisplayedPostCompletion() {
        Assert.assertTrue("Unlocked New badge is still not displayed",pathpageoverview.validateBadgeDisplayed());
        pathpageoverview.closeBadgeDialog();
        String btntext = pathpageoverview.getTextEnrolBtn();
        Assert.assertEquals("NL RustIC Course is not completed yet","Completed",btntext);
    }

    @Step(value = 3, info ="Signed In User - Enroll RustIC test course and complete it, verify CoursePercent Complete In Transcripts Table")
    public void verifyCoursePercentCompleteInTranscriptsTable() {
        encQuery = String.format(encQuery,courseName);
        String percentComplete = leutils.getGlideRecordEncodedQuery(TRANSCRIPTS_TABLE,"percent_complete",encQuery);
        MessageLogger.annotate("Percent Complete: "+percentComplete);
    }

    @Step(value = 4, info ="Signed In User - Enroll RustIC test course and complete it, Validate Badge earned in UserBadges Table")
    public void validateUserBadgeObtainedInUserBadgesTable() {
        encodedQuery = String.format(encodedQuery,impersonateUser,courseSysID);
        int recordCount = leutils.getGlideRecordCount(USER_BADGES_TABLE,encQuery);
        Assert.assertTrue("No Badge is displayed in user badges table",recordCount>0);
    }

    @Step(value = 5, info ="Signed In User - Enroll RustIC test course and complete it, Validate Completed Status under Learning Plan tab")
    public void validateCourseStatusUnderLearningPlanTab() {
        open(LXP_LEARNING_PLAN);
        pathpageoverview.closeBadgeDialog();
        String courseStatus = learningPlanPage.getCourseStatus(courseName);
        Assert.assertEquals("Course is not completed","Completed",courseStatus);

    }

    @Step(value = 6, info ="Signed In User - Enroll RustIC test course and complete it, Validate Badges obtained under LXP -> My Badges Section")
    public void validateBadgesSectionForGivenCourse() {
        open(LXP_LEARNING_MYBADGES);
        String badgeStatus = mybadgespage.getBadgeStatus(courseName);
        Assert.assertEquals("Course is not completed","Completed",badgeStatus);
    }
}
