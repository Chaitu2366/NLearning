package com.snc.surf.marketing.NLearning.tests.signedInUser;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.utils.Assessments.AssessmentUtils;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class Asmnt_KnowledgeCheckAsPass_IT extends NLETestBase {
	
	AssessmentUtils assessmentUtils = new AssessmentUtils();
	List<String> assessmentHeaderInfo = new LinkedList<>();
	private String contentSysID;
	private String contentType;
	private String kcName;
	private String impersonateEndUser;
	private String endUserSysID;
	private String transcriptNo;

	@BeforeClass
	public void loginToNL() {
		open(LXP_HOME_URL);
		userSignIn(userName,password);
		myprofilepage.waitForMyProfilePage();
		impersonateEndUser = this.getActiveNlUser();
		open("/navpage.do");
        leutils.impersonateWithUserName(impersonateEndUser);
	}

	@Step(value =1, info ="Verify the Assessment Details are displaying correctly to the end user")
	public void assessmentDisplayForUser() {
		//	method to launch course
		contentSysID = "df7693d91bc688509e40997fbd4bcb8d";
		contentType = "course";
		kcName ="SNF User Interface Overview Knowledge Check";
		
		open("/lxp?id=overview&sys_id="+contentSysID+"&type="+contentType);
		wait.waitAndObtainWebElement(AssessmentUtils.homeIcon, 80);
		findElement(By.cssSelector("a[id=crs-1-1-1][update*='clickLesson']")).click();
		findElement(By.cssSelector("#accordion21 > div > h5 > a")).click();
		
		pauseMe(5);
		assessmentHeaderInfo = assessmentUtils.getAssessmentHeaderDetailsKC_UI();
		int numberOfQuestionActual = assessmentUtils.getNumberOfQuestions(assessmentUtils.getAssesssmentSysID(kcName));
		Assert.assertTrue("Assessment name is not correct",assessmentHeaderInfo.get(0).contains(kcName));
		Assert.assertTrue("Number of question in KC is not correct",
				assessmentHeaderInfo.get(1).contains(String.valueOf(numberOfQuestionActual)));
	}
	@Step(value=2, info="Verify that User is able to take assessment of type knowledge check")
	public void takeKnowledgeCheck() {
		endUserSysID = assessmentUtils.getUserSysID(impersonateEndUser);
		transcriptNo = assessmentUtils.getTranscriptNumber(endUserSysID, contentType, contentSysID);
		assessmentUtils.answerKnowledgeCheck(kcName, "Pass");
		Assert.assertTrue("User is not able to attempt Knowledge Check", 
				assessmentUtils.asmntInstanceState(kcName, transcriptNo).equalsIgnoreCase("complete"));
	}
	@Step(value=6, info="Verify the messgae displayed once user completes the Knowledge check with correct answers")
	public void verifyMessage() {
		Assert.assertTrue("Correct message not displayed, when user completes knowledge check", 
				assessmentUtils.successMessage_KC().contains("Congratulations, you have completed the assessment"));
	}
}
