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
public class Asmnt_QuizWithResultAsFail_IT extends NLETestBase {

	AssessmentUtils assessmentUtils = new AssessmentUtils();
	String quizName;
	List<String> assessmentHeaderInfo = new LinkedList<>();
	private String contentSysID;
	private String contentType;
	private String impersonateEndUser;
	private int numberOfQuestionActual;

	@BeforeClass
	public void loginToNL() {
		open(LXP_HOME_URL);
		userSignIn(userName,password);
		myprofilepage.waitForMyProfilePage();
		impersonateEndUser = this.getActiveNlUser();
		open("/navpage.do");
		Log.info("impersonateUser" + impersonateEndUser);
		leutils.impersonateWithUserName(impersonateEndUser);
	}
	//	Scenario: Quiz with Result as Fail

	@Step(value =1, info ="Verify that the end user is notified for their incorrect answers of Quiz")
	public void takeQuizAsFail() {
		//	method to launch course
		contentSysID = assessmentUtils.getCourseWithAssessment("quiz");
		contentType = "course";
		quizName = nleEntitlementUtils.getName("asmt_metric_type", leutils.getGlideRecordEncodedQuery(	"x_snc_lxp_course", "exam", 
				"active=true^sys_id=" + contentSysID));
		open("/lxp?id=overview&sys_id="+contentSysID+"&type="+contentType);
		wait.waitAndObtainWebElement(AssessmentUtils.homeIcon, 80);
		pathpageoverview.clickEnrollBtn();
		assessmentUtils.clickOnAssessmentBasedOnContent(quizName);
		numberOfQuestionActual = assessmentUtils.getNumberOfQuestions(assessmentUtils.getAssesssmentSysID(quizName));
		assessmentUtils.answerExamOrQuiz(quizName, "Fail");
		Assert.assertFalse("Red tick mark NOT displayed after answering Quiz Incorreclt",assessmentUtils.getQuizMarker(numberOfQuestionActual));
	}
	@Step(value=2, info="Verify the User passes quiz by giving all answers correclty")
	public void verifyTheAssessmentInstance() {
		String endUserSysID = assessmentUtils.getUserSysID(impersonateEndUser);
		String transcriptNo = assessmentUtils.getTranscriptNumber(endUserSysID, contentType, contentSysID);
		String result_actual = assessmentUtils.getAssessmentResult(transcriptNo,quizName);
		Assert.assertTrue("Assessment is Not Failed",result_actual.startsWith("fail"));
	}
	@Step(value=4, info="Verify the User passes quiz by giving all answers correclty")
	public void verifyAndCLickRetakeButton() {
		Assert.assertTrue("Retake button is not displayed",assessmentUtils.isRetakeBtnDisplayedInQuiz());
		assessmentUtils.clickRetake_Quiz();
	}
	
	@Step(value=6, info="Retake the Quiz by ansering all questions correclty")
	public void retakeQuizWithResultAsPass() {
		assessmentUtils.answerExamOrQuiz(quizName, "Pass");
		Assert.assertTrue("Green tick mark NOT displayed after Quiz is passed",
				assessmentUtils.getQuizMarker(numberOfQuestionActual));
	}
	@Step(value=8, info="Verify the User passes quiz by giving all answers correclty")
	public void verifyRetakeButton_afterReatke() {
		Assert.assertTrue("Retake button is not displayed",assessmentUtils.isRetakeBtnDisplayedInQuiz());
	}
	@Step(value=10, info="Verify the User passes quiz by giving all answers correclty")
	public void verifyTheAssessmentInstance_afterRetake() {
		String endUserSysID = assessmentUtils.getUserSysID(impersonateEndUser);
		String transcriptNo = assessmentUtils.getTranscriptNumber(endUserSysID, contentType, contentSysID);
		String result_actual = assessmentUtils.getAssessmentResult(transcriptNo,quizName);
		Assert.assertTrue("Assessment is Not Passed successfully",result_actual.startsWith("pass"));
	}
}
