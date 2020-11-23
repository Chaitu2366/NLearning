package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class CreatePathToAttainCertificate_SimulatorIT extends NLETestBase {

    List<String> pathDetails;
    String pathName = "Test Automation _ Path_09";
    String simulator = "Human Resources Simulator";
    String certificName = null; // TBF
    String sysId,simSysId;
    String encQuery = "category.name=simulator^name=%s";
    String lptEncQuery = "student.user_name=%s^course.name=%s";
    String mappingEncQuery = "path=%s^courseISNOTEMPTY";
    ArrayList<String> listCourses;

    public String impersonateUser = null;
    String lptSysid = null;

    @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void performSignInAndImpersonateWithNlUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);
        String pathSysID = leutils.getSysIDOfRecord(PATH_TABLE,"name="+pathName,SYS_ID);
        mappingEncQuery = String.format(mappingEncQuery,pathSysID);
        listCourses = leutils.getMultipleFieldsDisplayValues_EncodedQuery("x_snc_lxp_m2m_course_path","course",encQuery);
    }

    @Step(value =1, info ="Signed In User - Enroll to path and complete few courses execution")
    public void completePathWithCourses_Simulator() {
        userSignIn(userName, password);
        leutils.impersonateWithUserName(impersonateUser);
        open("/lxp?id=overview&sys_id=332c9087db3c734069a56a5b8a96197d&type=path");
        Assert.assertTrue("Enroll Btn is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        String pathName = pathpageoverview.getPathNameDisplayed();
        // Assert.assertTrue("Path name is not displayed as expected",pathName.contains(Constants.CARD_TITLE));
        pathDetails = pathpageoverview.fetchPathDetails();
        Assert.assertNotNull("Path details are not displayed under header for specific card search",pathDetails);
        pathpageoverview.clickEnrollBtn();
        pathpageoverview.clickFirstModuleLessonAndCompleteCourse();
        pathpageoverview.closeBadgeDialog();
        pathpageoverview.clickCourse(2);
        pathpageoverview.clickFirstModuleLessonAndCompleteCourse();
        pathpageoverview.closeBadgeDialog();


        lptEncQuery = String.format(lptEncQuery,impersonateUser,simulator);

        lptSysid = leutils.getGlideRecordEncodedQuery(TRANSCRIPTS_TABLE, "simulator_path", lptEncQuery);
        Assert.assertNotNull("LPT Syd obtained is null",lptSysid);
        nleEntitlementUtils.updateStateLPT(lptSysid, "success", "x_snc_sims_lptask", "result");

        nleEntitlementUtils.runJob("SIMs Complete 100% passed learning paths");
        Assert.assertEquals("100",
                leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "passed", "sys_id=" + lptSysid));

        this.refreshPage();
        String btntext = pathpageoverview.getTextEnrolBtn();
        Assert.assertEquals("NL Path is not completed yet","Completed",btntext);
    }



    @Step(value = 2, info ="Signed In User - Complete Path, Validate Badges obtained under LXP -> My Badges Section")
    public void validateBadgesSectionForGivenPath() {
        open(LXP_LEARNING_MYBADGES);
        this.closeCertificationModalDialog();  // to close obtained certification dialog
        pathpageoverview.closeBadgeDialog();// to handle super badges dialog
        String badgeStatus = mybadgespage.getBadgeStatus(pathName);
        Assert.assertEquals("Path is not completed, badge not earned","Completed",badgeStatus);

        Assert.assertTrue("Course Badges are not attained",mybadgespage.validateBadgeStatusForCourses_Path(listCourses));
    }


    @Step(value = 4, info ="Signed In User - Complete Path, Validate Certific Status obtained under LXP -> My Transcript Section")
    public void validateMyTranscriptsTabForGivenPath() {
        open(LXP_LEARNING_TRANSCRIPTS);
        certificName= leutils.getMultipleFieldsDisplayValues_EncodedQuery(PATH_TABLE,"certificate","name="+pathName).get(0);
        String certificationStatus = mytranscriptspage.getCertificateStatus(certificName);
        Assert.assertEquals("Path is not completed, badge not earned","Current",certificationStatus);

    }

    @Step(value = 5, info ="Signed In User - Complete Path, Validate Path completion under Path table")
    public void validateMyTranscriptsTabForGivenPathCompletion() {
        open(LXP_LEARNING_TRANSCRIPTS);

        boolean pathCompletion = mytranscriptspage.validatePathNameDisplayed(pathName);
        Assert.assertTrue("Path is not completed, not displayed under Transcripts",pathCompletion);
        Assert.assertTrue("Courses are not displayed under MyTranscripts --> Path section",mytranscriptspage.validatecoursesInTranscripts(listCourses));
    }

    @Step(value = 5, info ="Signed In User - Enroll RustIC test course and complete it, Validate Completed Status under Learning Plan tab")
    public void validateCourseStatusUnderLearningPlanTab() {
        open(LXP_LEARNING_PLAN);
        String pathStatus = learningPlanPage.getCourseStatus(pathName);
        Assert.assertEquals("Path Status is not completed","Completed",pathStatus);
        Assert.assertTrue("Course Status is not completed",learningPlanPage.validateStatusForCourses_Path(listCourses));

    }

    @Step(value = 13, info ="Signed In User - Complete Path, Validate My Certifications obtained under LXP -> My Certifications Section")
    public void validateMyCertificationsSectionForGivenPath() {
        open(LXP_LEARNING_CERTIFICATION);

        String certificBadgeStatus = myCertificationPage.getCertificateStatus(certificName);
        Assert.assertEquals("Path is not completed, badge not earned","Current",certificBadgeStatus);

    }

}
