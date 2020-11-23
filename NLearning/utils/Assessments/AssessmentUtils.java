package com.snc.surf.marketing.NLearning.utils.Assessments;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.utils.UiUtility;
import com.snc.surf.marketing.NLearning.utils.WaitUtils;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class AssessmentUtils extends AssessmentUlits_glide {
	WaitUtils wait = new WaitUtils();
	UiUtility uiUtil = new UiUtility();

	protected By loadSpinner = By.cssSelector("div.km-loader");
	protected By submitBtn = By.cssSelector(".bottom-button>button[type='submit']");
	protected By contentHeading = By.cssSelector("[class*='heading text-']");
	public static final By homeIcon = By.cssSelector("#start_page > div.pull-left.home-img > img");
	protected By badgeDialogeCloseBtn = By.cssSelector("#badgegifModal > div > div > div > button");
	protected By resultHeader = By.cssSelector("[class*='modal-header ng-scope']>h3");
	protected By resultPopUpCloseBtn = By.cssSelector("div[class*='modal-header ng-scope']>button");
	protected By retakeMessageHeader = By.cssSelector("[class*='retake-msg']>span");
	protected By alertMessage = By.cssSelector("[class*='alert alert-danger']");

	protected By assessmentHeader = By.cssSelector("[class*='enroll-head assessment']");
	protected By resultPopupMessage = By.cssSelector("div.modal-content>div:nth-child(2)>div:nth-child(2)");
	protected By nextButtonOnPassedPopup = By.xpath("//a[contains(text(), 'Next')]");
	protected By goToButtonOnFailedPopup = By.xpath("//a[contains(text(), 'Go to ')]");
	protected By examCompletedMsg = By.cssSelector("div.exam-complete-msg>span");
	protected By retakeBtn_quiz = By.xpath("//button[contains(text(),'Retake')]");

	//	knowledge check locators
	protected By assessmentHeaderKC = By.cssSelector("[class*='quiz-header']");
	protected By questionKC = By.cssSelector("div[class*='question-name ']");
	protected By retryBtnInKC = By.xpath("//button[contains(text(),'Retry')]");
	protected By nextBtnInKC = By.xpath("//button[contains(text(),'Next')]");
	protected By submitBtnInKC = By.xpath("//button[contains(text(),'Submit')]");
	protected By previousBtnInKC = By.xpath("//button[contains(text(),'Previous')]");
	protected By ansFeedbackScope = By.cssSelector(" div.answer-feedback.ng-scope");
	protected By successMsg_KC = By.cssSelector("div.success-msg");

	protected By chckrslt = By.xpath("//i[@class='fa fa-check ng-scope']");

	/**
	 * This method answers all the questions available in the Exam or Quiz
	 * @param examOrQuizName is the name of the Exam or the Quiz to be taken
	 * @param assessmentResult to take with result as 'Pass' or 'Fail'
	 */
	public void answerExamOrQuiz(String examOrQuizName, String assessmentResult) {
		wait.waitAndObtainWebElement(homeIcon, 80);
		clickOnAssessmentBasedOnContent(examOrQuizName);
		waitForLoadingSpinner();

		if(assessmentResult.equalsIgnoreCase("Pass")) {
			int noOfQuestions = getNoOfQuestions_UI();
			Map<String, String> questiondetails = new HashMap<>();

			for(int i=1;i<=noOfQuestions;i++) {
				questiondetails = getQuestionInfo_glide(getAssesssmentSysID(examOrQuizName), getQuestion_UI(i));
				String correctAnswer = questiondetails.get("correctAnswer");
				String questionSysID = questiondetails.get("questionSysID");

				if(questiondetails.get("questionType").equalsIgnoreCase("choice")) {
					singleChoiceAnswer(i,questionSysID,correctAnswer);
					continue;
				}
				if(questiondetails.get("questionType").equalsIgnoreCase("multiplecheckbox")) {
					multiChoiceAnswer(i,questionSysID,correctAnswer);
					continue;
				}
				if(questiondetails.get("questionType").equalsIgnoreCase("boolean")) {
					selectBooleanAnswer(i,correctAnswer);
					continue;
				}
			}
		}
		if (assessmentResult.equalsIgnoreCase("Fail")) {
			int noOfQuestions = getNoOfQuestions_UI();
			Map<String, String> questiondetails = new HashMap<>();

			for(int i=1;i<=noOfQuestions;i++) {
				questiondetails = getQuestionInfo_glide(getAssesssmentSysID(examOrQuizName), getQuestion_UI(i));
				String correctAnswer = questiondetails.get("correctAnswer");
				String questionSysID = questiondetails.get("questionSysID");

				if(questiondetails.get("questionType").equalsIgnoreCase("choice")) {
					singleChoiceWrongAnswer(i,questionSysID,correctAnswer);
					continue;
				}
				if(questiondetails.get("questionType").equalsIgnoreCase("multiplecheckbox")) {
					multiChoiceWrongAnswer(i,questionSysID,correctAnswer);
					continue;
				}
				if(questiondetails.get("questionType").equalsIgnoreCase("boolean")) {
					selectWrongBooleanAnswer(i,correctAnswer);
					continue;
				}
			}


		}
		clickSubmit();
		waitForLoadingSpinner();
	}
	/**This function select the answer provided in the parameter, for Single Select question
	 */
	public void singleChoiceAnswer(int questionNumber, String questionSysID, String answer) {
		String answerXpath ="[id*='category-questions-']>div:nth-child("+questionNumber+")>div>lxp-survey-field>div>div>div";
		WebElement choices = getDriver().findElement(By.cssSelector(answerXpath));
		for(int i = 1; i<=getNumberOfChoice(questionSysID);i++) {
			String choice= choices.findElement(By.cssSelector("label:nth-child("+i+")")).getText();
			String [] choiceText = choice.split("\\r?\\n"); //to split a String by new line uses regex "\\r?\\n"

			if (answer.contains(choiceText[0].toString())) {
				choices.findElement(By.cssSelector("label:nth-child("+i+")>span")).click();
				break;
			}
		}
	}

	/**This function select the answer provided in the parameter, for Boolean question
	 */
	public void selectBooleanAnswer(int questionNumber, String answer) {
		String answerXpath ="[id*='category-questions-']>div:nth-child("+questionNumber+")>div>lxp-survey-field>div>div";
		WebElement choices = getDriver().findElement(By.cssSelector(answerXpath));
		if (choices.findElement(By.cssSelector("label:nth-child(1)")).getText().contains(answer))
			choices.findElement(By.cssSelector("label:nth-child(1)>span")).click();
		else
			choices.findElement(By.cssSelector("label:nth-child(2)>span")).click();
	}
	/**This function select the answer provided in the parameter, for Multi-select Select question
	 */
	public void multiChoiceAnswer(int questionNumber, String questionSysID, String answer) {
		String answerXpath ="[id*='category-questions-'] > div:nth-child("+questionNumber+")>div>lxp-survey-field>div>lxp-survey-multiple-checkbox>div";
		//get answers from the answer string
		List<String> resultsList  = getMultiChoiceAnswers(answer);

		WebElement choices = getDriver().findElement(By.cssSelector(answerXpath));
		for(int i = 1; i<=getNumberOfChoice(questionSysID);i++) {
			String choice = choices.findElement(By.cssSelector("label:nth-child("+i+")")).getText();
			String [] choiceText = choice.split("\\r?\\n"); //to split a String by new line uses regex "\\r?\\n"
			while (resultsList.contains(choiceText[0].toString())) {
				choices.findElement(By.cssSelector("label:nth-child("+i+")>span")).click();
				break;
			}
		}
	}

	public List<String> getMultiChoiceAnswers(String answer) {
		List<String> resultsList  = new LinkedList<>();
		String[] answerChoices = answer.split(",");
		for(int i=0; i<answerChoices.length; i++)
			resultsList.add(answerChoices[i].toString().trim());
		return resultsList;
	}
	/**This function select the wrong answer, for Single Select question
	 */
	public void singleChoiceWrongAnswer(int questionNumber, String questionSysID, String answer) {
		String answerXpath ="[id*='category-questions-']>div:nth-child("+questionNumber+")>div>lxp-survey-field>div>div>div";
		WebElement choices = getDriver().findElement(By.cssSelector(answerXpath));
		for(int i = 1; i<=getNumberOfChoice(questionSysID);i++) {
			String choice= choices.findElement(By.cssSelector("label:nth-child("+i+")")).getText();
			String [] choiceText = choice.split("\\r?\\n"); //to split a String by new line uses regex "\\r?\\n"

			if (!choiceText[0].toString().contains(answer)) {
				choices.findElement(By.cssSelector("label:nth-child("+i+")>span")).click();
				break;
			}
		}
	}
	/**This function select the answer provided in the parameter, for Boolean question
	 */
	public void selectWrongBooleanAnswer(int questionNumber, String answer) {
		String answerXpath ="[id*='category-questions-']>div:nth-child("+questionNumber+")>div>lxp-survey-field>div>div";
		WebElement choices = getDriver().findElement(By.cssSelector(answerXpath));
		if (choices.findElement(By.cssSelector("label:nth-child(1)")).getText().contains(answer))
			choices.findElement(By.cssSelector("label:nth-child(2)>span")).click();
		else
			choices.findElement(By.cssSelector("label:nth-child(1)>span")).click();
	}
	/**This function select the wrong answer provided in the parameter, for Multi-select Select question
	 */
	public void multiChoiceWrongAnswer(int questionNumber, String questionSysID, String answer) {
		String answerXpath ="[id*='category-questions-'] > div:nth-child("+questionNumber+")>div>lxp-survey-field>div>lxp-survey-multiple-checkbox>div";
		//get answers from the answer string
		List<String> resultsList  = getMultiChoiceAnswers(answer);

		WebElement choices = getDriver().findElement(By.cssSelector(answerXpath));
		for(int i = 1; i<=getNumberOfChoice(questionSysID);i++) {
			String choice = choices.findElement(By.cssSelector("label:nth-child("+i+")")).getText();
			String [] choiceText = choice.split("\\r?\\n"); //to split a String by new line uses regex "\\r?\\n"
			while (!resultsList.contains(choiceText[0].toString())) {
				choices.findElement(By.cssSelector("label:nth-child("+i+")>span")).click();
				break;
			}
		}
	}
	/**
	 * @return the number of questions in an exam
	 */
	public int getNoOfQuestions_UI() {
		int noOfQues = 0;
		noOfQues = findElements(By.cssSelector("[id*='category-questions-'] > div")).size();
		return noOfQues;
	}

	/**
	 * @param questionNumber
	 * @return the text of the question number passed in the parameter
	 */
	public String getQuestion_UI(int questionNumber) {
		String question = null;
		String questionXpath = "div[id*='category-questions-']> div:nth-child("+questionNumber+")>div>h4";
		if(questionNumber<10)
			question = findElement(By.cssSelector(questionXpath)).getText().trim().substring(3);
		else
			question = findElement(By.cssSelector(questionXpath)).getText().trim().substring(4);
		return question;
	}

	/**Clicks Submit button on Quiz or Exam
	 */
	public void clickSubmit() {
		uiUtil.clickEle(submitBtn);
	}
	/**Clicks retake button on Quiz or Exam
	 */
	public void clickRetake_Quiz() {
		uiUtil.waitForElementClickable(retakeBtn_quiz, 50);
		findElement(retakeBtn_quiz).click();
	}


	/** Click on the assessment link, in left nav, on the Path/Course overview Page
	 * @param examOrQuizName
	 */
	public void clickOnAssessmentBasedOnContent(String examOrQuizName) {
		String cotentType = findElement(contentHeading).getText().trim();
		if(cotentType.equalsIgnoreCase("Course")) {
			By leftNavAssessmentLink = By.xpath("//a[contains(text(),'"+examOrQuizName+"')]");
			wait.waitAndObtainWebElement(leftNavAssessmentLink, 50);
			uiUtil.clickEle(leftNavAssessmentLink);
			waitForLoadingSpinner();
		}
		else if(cotentType.equalsIgnoreCase("Path")) {
			By leftNavAssessmentLink = By.xpath("//span[contains(text(),'"+examOrQuizName+"')]");
			wait.waitAndObtainWebElement(leftNavAssessmentLink, 50);
			uiUtil.clickEle(leftNavAssessmentLink);

		}

	}

	/**Closes badge popup
	 */
	public void closeBadgedialoge() {
		if(uiUtil.isElementVisible(badgeDialogeCloseBtn, 50))
			wait.waitForElementToBeClickable(badgeDialogeCloseBtn);
		uiUtil.clickEle(badgeDialogeCloseBtn);
	}
	/**Closes badge popup, by verifying if the transcript record is completed
	 */
	public void closeBadgedialoge(String transcriptNo) {
		String state = surfUtils.getSingleGlideFieldDisplay("x_snc_lxp_transcript", "number",transcriptNo, "state");
		if(state.equalsIgnoreCase("complete")) {
			if(uiUtil.isElementVisible(badgeDialogeCloseBtn, 50))
				wait.waitForElementToBeClickable(badgeDialogeCloseBtn);
			uiUtil.clickEle(badgeDialogeCloseBtn);
		}
	}

	/**Click on close button on Assessment pop-up
	 */
	public void closeBtnOnAsmntPopup() {
		if(uiUtil.isElementVisible(resultPopUpCloseBtn, 50))
			wait.waitForElementToBeClickable(resultPopUpCloseBtn);
		uiUtil.clickEle(resultPopUpCloseBtn);
	}
	/**Return the header details of assessment
	 * @return a list of Type, Number of questions & Duration of questions
	 */
	public List<String> getAssessmentHeaderDetails_UI() {
		WebElement header = findElement(assessmentHeader);
		String asmntType = header.findElement(By.cssSelector("ul>li")).getText().trim();
		String[] numberAndDuration = header.findElement(By.cssSelector("span")).getText().split("-");
		List<String> assessmentInfo = new LinkedList<>();
		assessmentInfo.add(asmntType);
		assessmentInfo.add(numberAndDuration[0].toString().trim()); //number of question info
		assessmentInfo.add(numberAndDuration[1].toString().trim()); // Duration Info

		return assessmentInfo;
	}

	/**@return the header on the pop-up displayed after submitting assessment
	 */
	public String getResultPopupHeader() {
		String popUpHeader = findElement(resultHeader).getText().trim();
		return popUpHeader;
	}

	/** @return the message displayed on the pop-up displayed after submitting assessment
	 */
	public String getResultMessage() {
		String resutMessage = findElement(resultPopupMessage).getText();
		return resutMessage;
	}
	public String getRetakeLeftMessage() {
		String retakeMsg = findElement(retakeMessageHeader).getText();
		return retakeMsg;
	}
	public boolean isNextBtntDisplayed() {
		boolean isDisplayed = uiUtil.isElementVisible(nextButtonOnPassedPopup, 50);
		return isDisplayed;
	}
	public boolean isGoToBtnDisplayed() {
		boolean isDisplayed = uiUtil.isElementVisible(goToButtonOnFailedPopup, 50);
		return isDisplayed;
	}
	public void waitForLoadingSpinner(){
		uiUtil.waitForElementInvisibility(loadSpinner,100);
	}

	public List<String> getCompletedMessage() {
		List<String> msg = new LinkedList<>();
		String congratsMsg = findElement(examCompletedMsg).getText();
		String reviewMsg =findElement(By.cssSelector("div.exam-complete-msg>div")).getText();
		//		String helpLink = findElement(By.cssSelector("div.exam-complete-msg>div>a")).getText();
		String helpLink_href = findElement(By.cssSelector("div.exam-complete-msg>div>a")).getAttribute("href");

		msg.add(0, congratsMsg);
		msg.add(1, reviewMsg);
		msg.add(2,helpLink_href);
		return msg;
	}

	public String verifyQuizKcResult()
	{
		String flag=null;
		List<WebElement> rslt = findElements(chckrslt);  // change loc  move this code to assesmnet utils
		for(int i=0;i<rslt.size();i++)
		{
			if(rslt.get(i).getAttribute("ng-if").contains("passed"))
			{
				flag="TRUE";	
			}
			else
			{
				flag="FALSE";
			}
		}  
		return flag;
	}
	/**
	 * @param n : Number of questions in Quiz
	 * @return 'true' if all questions Pass, and 'fail' and of the question is failed
	 */
	public boolean getQuizMarker(int n) {
		boolean flag = false;
		String resultMarker = null;
		for (int i=1;i<=n;i++) {
			By questionMarker_quiz = By.cssSelector("div:nth-child("+i+") > div > h4 > span.question-marker.ng-scope > i");
			resultMarker = findElement(questionMarker_quiz).getAttribute("ng-if");
			resultMarker = resultMarker.split("'")[1];
			if(resultMarker.startsWith("pass"))
				flag = true;
			else if (resultMarker.contains("fail")) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public boolean isRetakeBtnDisplayedInQuiz() {
		boolean isDisplayed = uiUtil.isElementVisible(retakeBtn_quiz, 50);
		return isDisplayed;
	}

	//	knowledge check functions
	public List<String> getAssessmentHeaderDetailsKC_UI() {
		WebElement header = findElement(assessmentHeaderKC);
		String kcName = header.findElement(By.cssSelector("span:nth-child(1)")).getText().trim();
		String numberOfQues = header.findElement(By.cssSelector("span:nth-child(2)")).getText();
		List<String> assessmentInfo = new LinkedList<>();
		assessmentInfo.add(kcName);
		assessmentInfo.add(numberOfQues); //number of questions
		return assessmentInfo;
	}

	/**
	 * @param questionNumber
	 * @return the text of the question number passed in the parameter
	 */
	public String getQuestionKC_UI(int questionNumber) {
		String questionText = null;
		if(questionNumber<10) {
			questionText = findElement(questionKC).getText().trim().substring(3);
		}
		else if (questionNumber<100)
			questionText = findElement(questionKC).getText().trim().substring(4);
		return questionText;
	}
	/**
	 * This method answers all the questions available in the Knowledge Check
	 * @param knowledgeChkName is the name of the Knowledge Check to be taken
	 * @param assessmentResult to take with result as 'Pass' or 'Fail'
	 */
	public void answerKnowledgeCheck(String knowledgeChkName, String assessmentResult) {
		Map<String, String> questiondetails = new HashMap<>();
		String questionText= null;
		int n =getNumberOfQuestions(getAssesssmentSysID(knowledgeChkName));


		if(assessmentResult.equalsIgnoreCase("Pass")) {
			for(int i=1;i<=n;i++) {
				questionText = getQuestionKC_UI(i);
				questiondetails = getQuestionInfo_glide(getAssesssmentSysID(knowledgeChkName), questionText);
				{
					String correctAnswer = questiondetails.get("correctAnswer");
					String questionSysID = questiondetails.get("questionSysID");

					if(questiondetails.get("questionType").equalsIgnoreCase("choice")) {
						singleChoiceAnswer_KC(questionSysID,correctAnswer);
						clickSubmit_KC();
						uiUtil.waitForEleVisibility(ansFeedbackScope, 50);
						if (!(i==n))
							clickNext_KC();
						continue;
					}
					if(questiondetails.get("questionType").equalsIgnoreCase("multiplecheckbox")) {
						multiChoiceAnswer_KC(questionSysID,correctAnswer);
						clickSubmit_KC();
						uiUtil.waitForEleVisibility(ansFeedbackScope, 50);
						if (!(i==n))
							clickNext_KC();
						continue;
					}
					if(questiondetails.get("questionType").equalsIgnoreCase("boolean")) {
						booleanAnswer_KC(correctAnswer);
						clickSubmit_KC();
						uiUtil.waitForEleVisibility(ansFeedbackScope, 50);
						if (!(i==n))
							clickNext_KC();
						continue;
					}
				}
			}
		}
	}
	public void singleChoiceAnswer_KC(String questionSysID, String answer) {
		int numberOfAnsChoices = getNumberOfChoice(questionSysID);
		try {for (int i=1;i<=numberOfAnsChoices;i++) {
			String answerLocator = "div.answers>ul>li:nth-child("+i+")>label";
			String ansChoices = findElement(By.cssSelector(answerLocator)).getText();
			if(ansChoices.equalsIgnoreCase(answer))
				findElement(By.cssSelector("div.answers>ul>li:nth-child("+i+")>label>span")).click();
		}
		} catch(Exception e) {
			System.out.println("couldnt select answer for single choice question");}
	}
	public void multiChoiceAnswer_KC(String questionSysID, String answer) {
		int numberOfAnsChoices = getNumberOfChoice(questionSysID);
		List<String> resultsList  = getMultiChoiceAnswers(answer);
		try {
			for (int i=1;i<=numberOfAnsChoices;i++) {
				String answerLocator = "div.answers>ul>li:nth-child("+i+")>label";
				String ansChoices = findElement(By.cssSelector(answerLocator)).getText().trim();
				while(resultsList.contains(ansChoices)) {
					findElement(By.cssSelector("div.answers>ul>li:nth-child("+i+")>label>span")).click();
					break;
				}
			}
		} catch(Exception e) {
			System.out.println("couldnt select answer for multi choice question");}
	}
	public void booleanAnswer_KC(String answer) {
		try {
			if (findElement(By.cssSelector("div.answers>ul>li:nth-child(1)>label")).getText().contains(answer))
				findElement(By.cssSelector("div.answers>ul>li:nth-child(1)>label>span")).click();
			else
				findElement(By.cssSelector("div.answers>ul>li:nth-child(1)>label>span")).click();
		}	catch (Exception e) {
			System.out.println("couldnt select answer for boolean question");	}
	}
	public void clickSubmit_KC() {
		uiUtil.clickEle(submitBtnInKC);
	}
	public void clickRety_KC() {
		uiUtil.clickEle(retryBtnInKC);
	}
	public void clickNext_KC() {
		uiUtil.clickEle(nextBtnInKC);
	}
	public void clickPrevious_KC() {
		uiUtil.clickEle(previousBtnInKC);
	}

	public String successMessage_KC() {
		return findElement(successMsg_KC).getText();
	}
}
