package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.utils.Assessments.AssessmentUtils;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class CreatePathToAttainCertficWithAssessmentIT extends NLETestBase {

    AssessmentUtils assessmentUtils = new AssessmentUtils();

    List<String> pathDetails;
    String pathName = "Test-Automation_path_11";
    String certificName = "Certified Implementation Specialist – Discovery";

    public String certificationName = null;
    public String certificationNumber = null;
    public String impersonateUser = null;
    String examName = null;
    String encQuery = "path=%s^courseISNOTEMPTY";
    String examEncQuery = "path=%s^assessment!=NULL";
    String testSuiteCertificate = "Test Automation Suite Certificate";
    ArrayList<String> listCourses;


    @Step(value =0, info ="Signed In User - Obtain Main Line Certification for existing NL User")
    public void performSignInAndImpersonateWithNlUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);
        String sysId = leutils.getSysIDOfRecord(PATH_TABLE,"name="+pathName,SYS_ID);
        encQuery = String.format(encQuery,sysId);
        listCourses = leutils.getMultipleFieldsDisplayValues_EncodedQuery("x_snc_lxp_m2m_course_path","course",encQuery);
        examEncQuery = String.format(examEncQuery,sysId);
        examName = leutils.getMultipleFieldsDisplayValues_EncodedQuery("x_snc_lxp_m2m_course_path","assessment",examEncQuery).get(0);
        this.attainMainLineCertification();
    }

    @Step(value =11, info ="Signed In User - Validate Test Automation Suite Certificsate obtained after attaining above certificate")
    public void validateSuiteCertficateAssignedToUser() {
        open(LXP_LEARNING_CERTIFICATION);

        String certificStatus = myCertificationPage.getCertificateStatus(testSuiteCertificate);
        Assert.assertEquals("Suite certification status is not set to Current","Current",certificStatus);
    }

    @Step(value =14, info ="Signed In User - Enroll to path and complete few courses execution")
    public void completePathWithCourses_Assessment() {

        open("/lxp?id=overview&sys_id=d7b81264db01330069a56a5b8a961955&type=path");
        Assert.assertTrue("Enroll Btn is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        String pathName = pathpageoverview.getPathNameDisplayed();
        pathDetails = pathpageoverview.fetchPathDetails();
        Assert.assertNotNull("Path details are not displayed under header for specific card search",pathDetails);
        pathpageoverview.clickEnrollBtn();
        pathpageoverview.clickFirstModuleLessonAndCompleteCourse();
        pathpageoverview.closeBadgeDialog();   // for course badge
        pathpageoverview.clickCourse(2);
        pathpageoverview.clickFirstModuleLessonAndCompleteCourse();
        pathpageoverview.closeBadgeDialog();   // for course badge

        pathpageoverview.clickAssessment();
        examPage.clickRadioBtn_Exam(1,2);
        examPage.clickRadioBtn_Exam(2,1);

        examPage.clickRadioBtns_Exam(3, Arrays.asList(1,2,3));

        examPage.clickSubmitBtn_Exam();

        //assessmentUtils.answerExamOrQuiz(examName, "Pass");

        pathpageoverview.closeBadgeDialog();  // for path badge

        Assert.assertTrue("Assessment completed dialog is not displayed",examPage.waitForAssessmentCompletionDialog());

        pathpageoverview.clickNextBtn_Lessons();
        pathpageoverview.closeBadgeDialog();

        String btntext = pathpageoverview.getTextEnrolBtn();
        Assert.assertEquals("NL Path is not completed yet","Completed",btntext);
    }


    private void attainMainLineCertification() {
        userSignIn(userName, password);

        String[] reqFields = { "number", "name" };
        List<String> reqFieldValues = leutils.getMultipleFields_EncodedQuery(CERTIFICATION_TABLE, reqFields, "name="+certificName);
        Assert.assertNotNull("Required Field Values are not obtained", reqFieldValues);
        certificationNumber = reqFieldValues.get(0);
        certificationName = reqFieldValues.get(1);

        Log.info("impersonateUser" + impersonateUser);
        kryterionpage.kryterionProfileCreation(impersonateUser);
        String testTakerID = leutils.getSingleGlideRecord(PROFILE_TABLE, "u_kryterion_login", impersonateUser,
                "u_test_taker_id", leutils.getInstanceEndpointURL());
        Assert.assertNotNull("Profile Not Created", testTakerID);

        //leutils.runKryterionBatchJobs("Kryterion Profile");
        nleEntitlementUtils.runJob("Kryterion Profile","rest");

        kryterionpage.addKryterionSkill(this.impersonateUser, this.certificationName, this.certificationNumber);

       // leutils.runKryterionBatchJobs("Kryterion user exam");
        nleEntitlementUtils.runJob("Kryterion user exam","rest");

        open("/navpage.do");
        leutils.impersonateWithUserName(this.impersonateUser);
        String certText = getCertificationDialogTextAndCloseDlg();
        Assert.assertNotNull("User Certification dialog not displayed",certText);

        String text = getBadgeDialogTextAndCloseDlg();
        Assert.assertNotNull("Badge dialog is not displayed",text);
    }

    @Step(value = 15, info ="Signed In User - Complete Path, Validate Badges obtained under LXP -> My Badges Section")
    public void validateBadgesSectionForGivenPath() {
        open(LXP_LEARNING_MYBADGES);
        pathpageoverview.closeBadgeDialog();  // at times super badge pops up
        String badgeStatus = mybadgespage.getBadgeStatus(pathName);  // This is for path
        Assert.assertEquals("Path is not completed, badge not earned","Completed",badgeStatus);

        Assert.assertTrue("Course Badges are not attained",mybadgespage.validateBadgeStatusForCourses_Path(listCourses));

    }

    @Step(value = 16, info ="Signed In User - Complete Path, Validate My Certifications obtained under LXP -> My Certifications Section")
    public void validateMyCertificationsSectionForGivenPath() {
        open(LXP_LEARNING_CERTIFICATION);

        String certificStatus = myCertificationPage.getCertificateStatus(certificName);
        Assert.assertEquals("MainLine certification status not set to Current","Current",certificStatus);

    }

    @Step(value = 17, info ="Signed In User - Complete Path, Validate Certific Status obtained under LXP -> My Transcript Section")
    public void validateMyTranscriptsTabForGivenPath() {
        open(LXP_LEARNING_TRANSCRIPTS);

        String certificationStatus = mytranscriptspage.getCertificateStatus(certificName);
        Assert.assertEquals("MainLine certification status not set to Current","Current",certificationStatus);

    }

    @Step(value = 18, info ="Signed In User - Complete Path, Validate Path completion under Path table")
    public void validateMyTranscriptsTabForGivenPathCompletion() {

        boolean pathCompletion = mytranscriptspage.validatePathNameDisplayed(pathName);
        Assert.assertTrue("Path is not completed, not displayed under Transcripts",pathCompletion);

        Assert.assertTrue("Courses are not displayed under MyTranscripts --> Path section",mytranscriptspage.validatecoursesInTranscripts(listCourses));

    }
}
