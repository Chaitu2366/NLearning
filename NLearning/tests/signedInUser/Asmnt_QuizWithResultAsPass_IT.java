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
public class Asmnt_QuizWithResultAsPass_IT extends NLETestBase {
	
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
//	Scenario: Quiz with Result as Pass
	
	@Step(value =1, info ="Verify the Assessment type is displaying correctly to the end user")
	public void quizDisplayForUser() {
		//	method to launch course
		contentSysID = assessmentUtils.getCourseWithAssessment("quiz");
		contentType = "course";
		quizName = nleEntitlementUtils.getName("asmt_metric_type", leutils.getGlideRecordEncodedQuery(	"x_snc_lxp_course", "exam", 
				"active=true^sys_id=" + contentSysID));

		open("/lxp?id=overview&sys_id="+contentSysID+"&type="+contentType);
		wait.waitAndObtainWebElement(AssessmentUtils.homeIcon, 80);
		pathpageoverview.clickEnrollBtn();
		assessmentUtils.clickOnAssessmentBasedOnContent(quizName);
		assessmentHeaderInfo = assessmentUtils.getAssessmentHeaderDetails_UI();
		Assert.assertTrue("Assessmenttype is not correct",assessmentHeaderInfo.get(0).contains("Quiz"));
	}

	@Step(value=2, info="Verify the Number of questions is displaying correclty in UI")
	public void getNumberOfQuestionInExam() {
		numberOfQuestionActual = assessmentUtils.getNumberOfQuestions(assessmentUtils.getAssesssmentSysID(quizName));
		Assert.assertTrue("Number of Question displayed in Exam are not correct",
				assessmentHeaderInfo.get(1).contains(String.valueOf(numberOfQuestionActual)));
	}
	
	@Step(value=4, info="Verify the Duration of questions is displaying correctly in UI")
	public void getDurationInExam() {
		String assmntDetails = assessmentUtils.getAssessmentValues(quizName).get("Assessment Duration").trim();
		Assert.assertTrue("Duration displayed in Exam are not correct",
				assessmentHeaderInfo.get(2).contains(assmntDetails));
	}

	@Step(value=6, info="User answers the quiz by giving all answers correclty")
	public void takeExam() {
		assessmentUtils.answerExamOrQuiz(quizName, "Pass");
//		assessmentUtils.closeBadgedialoge();
		Assert.assertTrue("Green tick mark NOT displayed after Quiz is passed",
				assessmentUtils.getQuizMarker(numberOfQuestionActual));
	}
	@Step(value=8, info="Verify the User passes quiz by giving all answers correclty")
	public void verifyRetakeButton() {
		Assert.assertTrue("Retake button is not displayed",assessmentUtils.isRetakeBtnDisplayedInQuiz());
	}
	@Step(value=10, info="Verify the User passes quiz by giving all answers correclty")
	public void verifyTheAssessmentInstance() {
		String endUserSysID = assessmentUtils.getUserSysID(impersonateEndUser);
		String transcriptNo = assessmentUtils.getTranscriptNumber(endUserSysID, contentType, contentSysID);
		String result_actual = assessmentUtils.getAssessmentResult(transcriptNo,quizName);
		Assert.assertTrue("Assessment is Not Passed successfully",result_actual.startsWith("pass"));
	}
}
