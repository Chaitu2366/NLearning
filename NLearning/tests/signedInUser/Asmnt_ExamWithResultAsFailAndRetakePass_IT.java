package com.snc.surf.marketing.NLearning.tests.signedInUser;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.utils.Assessments.AssessmentUtils;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class Asmnt_ExamWithResultAsFailAndRetakePass_IT extends NLETestBase {
	
	AssessmentUtils assessmentUtils = new AssessmentUtils();
	String examName;
	List<String> assessmentHeaderInfo = new LinkedList<>();
	private String contentSysID;
	private String contentType;
	private int retakeLeft_initial;
	private String transcriptNo;
	private String endUserSysID;
	private String impersonateEndUser;

	@BeforeClass
	public void loginToNL() {open(LXP_HOME_URL);
	userSignIn(userName,password);
	myprofilepage.waitForMyProfilePage();
	impersonateEndUser = this.getActiveNlUser();
	open("/navpage.do");
    Log.info("impersonateUser" + impersonateEndUser);
    leutils.impersonateWithUserName(impersonateEndUser);}
//	Retake Failed Exam, without set wait time and Pass
	@Step(value =1, info ="Verify the Assessment type is displaying correctly to the end user")
	public void assessmentDisplayForUser() {
		//	method to launch Path
		contentSysID = nleEntitlementUtils.getExamPath("micro", "x_snc_lxp_m2m_course_path","Exam");
		contentType = "path";
		examName = nleEntitlementUtils.getName("asmt_metric_type", leutils.getGlideRecordEncodedQuery(	"x_snc_lxp_m2m_course_path", "assessment", 
				"path.sys_id=" + contentSysID + "^assessment!=NULL^course=NULL"));
		assessmentUtils.disableAsmntWaitTime(examName);
		open("/lxp?id=overview&sys_id="+contentSysID+"&type="+contentType);
		wait.waitAndObtainWebElement(AssessmentUtils.homeIcon, 80);
		pathpageoverview.clickEnrollBtn();
		assessmentUtils.clickOnAssessmentBasedOnContent(examName);
		assessmentHeaderInfo = assessmentUtils.getAssessmentHeaderDetails_UI();
		Assert.assertTrue("Assessmenttype is not correct",assessmentHeaderInfo.get(0).contains("Exam"));
	}

	@Step(value=2, info="Verify the Number of questions is displaying correclty in UI")
	public void getNumberOfQuestionInExam() {
		//get the retakes left for user before taking assessment
		endUserSysID = assessmentUtils.getUserSysID(impersonateEndUser);
		transcriptNo = assessmentUtils.getTranscriptNumber(endUserSysID, contentType, contentSysID);
		retakeLeft_initial = assessmentUtils.retakesLeft(examName, transcriptNo);
		int numberOfQuestionActual = assessmentUtils.getNumberOfQuestions(assessmentUtils.getAssesssmentSysID(examName));
		Assert.assertTrue("Number of Question displayed in Exam are not correct",
				assessmentHeaderInfo.get(1).contains(String.valueOf(numberOfQuestionActual)));
	}
	
//	Need to recheck with Dev team as when the days are added to duration the minutes are not displaying in UI
//	@Step(value=4, info="Verify the Duration of questions is displaying correctly in UI")
//	public void getDurationInExam() {
//		
//		Assert.assertTrue("Duration displayed in Exam are not correct",
//				assessmentHeaderInfo.get(2).contains(""));
//	}

	@Step(value=6, info="User answers the exam by giving all answers correclty")
	public void takeExamAndFail() {
		assessmentUtils.answerExamOrQuiz(examName, "Fail");
		Assert.assertTrue("Assessment Result popup not displayed to the User", 
				assessmentUtils.getResultPopupHeader().contains("Your Assessment Result"));
	}
	
	@Step(value=8, info="Verifiy the result popup displays the failed message")
	public void verifyAssessmentResultMessage() {
		String passScore = assessmentUtils.getAssessmentPassScore(examName);
		String popupMessage_actual = assessmentUtils.getResultMessage().trim();
		Assert.assertTrue("Assessment Result popup not displayed to the User",
				popupMessage_actual.contains("You have not passed the assessment"));
		Assert.assertTrue("Assessment Result popup not displayed to the User",
				popupMessage_actual.contains(passScore+"% or better is required to pass this assessment"));
		Assert.assertTrue("Assessment Result popup not displayed to the User",
				popupMessage_actual.contains("You have not earned "+passScore+"% mark"));
	}
	@Step(value=10, info="Verifiy the result popup displays the go to course button")
	public void verifyGoToBtnInAssessmentPopup() {
		boolean goToBtn_actual = assessmentUtils.isGoToBtnDisplayed();
		Assert.assertTrue("Assessment Result popup not displayed to the User",goToBtn_actual);
		assessmentUtils.closeBtnOnAsmntPopup();
	}
	
	@Step(value=12, info="Verify the User failed exam by giving all answers correclty")
	public void verifyTheAssessmentInstanceResult() {
		String result_actual = assessmentUtils.getAssessmentResult(transcriptNo,examName);
		Assert.assertTrue("Assessment is Not Passed successfully",result_actual.startsWith("fail"));
	}
	@Step(value=14, info="Verify the retakes left decreased after user has attempted exam once")
	public void verifyRetakeLeftForUser() {
		int retakeLeft_actual = assessmentUtils.retakesLeft(examName, transcriptNo);
		int retakeLeft_expected = retakeLeft_initial-1;
		
		Assert.assertTrue("Retake limit is not decreased after user attempted assessment", 
				retakeLeft_actual==retakeLeft_expected);
	}
	@Step(value=16, info= "Retake the exam with Pass score")
	public void retakeAndPass() {
		open("/lxp?id=overview&sys_id="+contentSysID+"&type="+contentType);
		wait.waitAndObtainWebElement(AssessmentUtils.homeIcon, 80);
		assessmentUtils.answerExamOrQuiz(examName, "Pass");
		Assert.assertTrue("Assessment Result popup not displayed to the User", 
				assessmentUtils.getResultPopupHeader().contains("Your Assessment Result"));
	}
	@Step(value=18, info="Verifiy the result popup displays the Congratulations message")
	public void verifyAssessmentResultAsPass() {
		String popupMessage_actual = assessmentUtils.getResultMessage().trim();
		Assert.assertTrue("Assessment Result popup not displayed to the User",
				popupMessage_actual.contains("Congratulations, you have passed the assessment"));
	}
	@Step(value=20, info="Verifiy the result popup displays the Congratulations message")
	public void verifyNextButtonInAssessmentPopup() {
		boolean nextBtn_actual = assessmentUtils.isNextBtntDisplayed();
		Assert.assertTrue("Assessment Result popup not displayed to the User",nextBtn_actual);
		assessmentUtils.closeBadgedialoge(transcriptNo);
		assessmentUtils.closeBtnOnAsmntPopup();
	}
	@Step(value=22, info="Verify the User passes exam by giving all answers correclty")
	public void verifyTheAssessmentInstanceAfterPass() {
		String result_actual = assessmentUtils.getAssessmentResult(transcriptNo,examName);
		Assert.assertTrue("Assessment is Not Passed successfully",result_actual.contains("pass"));
	}
	
	@Step(value = 24, info="Verify the Congration message is displayed on Exam for end user")
	public void verifyAssessmentCompletedMessageAfterPass() {
		List<String> msg = new LinkedList<>();
		msg = assessmentUtils.getCompletedMessage();
		
		Assert.assertTrue("Completed message not displayed correctly", msg.get(0).contains("Congratulations, you have completed the assessment"));
		Assert.assertTrue("Review message not displayed correctly", msg.get(1).contains("Want to review the quiz again? Click here for help"));
		Assert.assertTrue("Help link not correct", msg.get(2).contains("csm_get_help&sys_id="));
	}
}
