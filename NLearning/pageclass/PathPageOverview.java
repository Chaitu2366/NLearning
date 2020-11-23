package com.snc.surf.marketing.NLearning.pageclass;

import com.glide.util.Log;
import com.snc.selenium.framework.MessageLogger;
import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import com.snc.surf.marketing.NLearning.utils.NLEUtils;
import com.snc.surf.marketing.NLearning.utils.Assessments.AssessmentUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PathPageOverview extends NLEPageBase {


	protected By enrollBtn = By.cssSelector("button[ng-click*='c.enroll()']");
	protected By unEnrollBtn = By.cssSelector("button[ng-click*='c.unenroll']");
	protected By selectClass = By.cssSelector("div[ng-click*='c.view(course)']");
	protected By pathName = By.cssSelector("div#x664b16871b4437003a2310ad2d4bcb22 div.banner h1");

	protected By pathDetails = By
			.cssSelector("div#x664b16871b4437003a2310ad2d4bcb22 div.align-right>ul.desktop-info>li span");

	protected By warningAlert = By.xpath(".//div[@id='lxp-alert-container']//span[contains(text(),'Warning')]");

	// Course Locators

	protected By courseNamesLeft = By.cssSelector("div#vertical_navigator a[role='button']");

	protected By expandCourse = By.cssSelector("div#headingTwo a[data-target*='course']");
	protected By expandModule = By.cssSelector("a[data-target*='module']");
	protected By lessonLinks = By.cssSelector("div[id*='module'] a[lesson='lesson'] div.lesson-txt");
	protected By demoQuiz = By.cssSelector("a[data-id*='course_']");
	protected By courseNames = By.cssSelector("div#headingTwo a[data-target*='course'] strong");
	protected By aboutCourseText = By.cssSelector("div#collapseAboutcourse p[ng-bind-html='data.description']");
	protected By moduleNames = By
			.xpath(".//div[contains(@id,'collapse')]/div[@ng-repeat='module in course.childData']");

	protected By lessonsUl = By.cssSelector("div#course-card div[id*='collapse'] ul.lessons");
	protected By lessonsRadioBtn = By.cssSelector("circle.inner-circle");
	protected By lessonTextDiv = By.cssSelector("a[lesson='lesson']");
	protected By lessonLinksRightScreen = By
			.cssSelector("div#course-card div[id*='collapse'] ul.lessons a[lesson='lesson']");
	protected By lessonContent = By.cssSelector("div#right_content div[selected-lesson='c.selectedLesson']");
	protected By nextBtn = By.cssSelector("div[ng-click*=\"c.getNextAndPrevious('next')\"]>a");

	// Knowledge check selection of circles/ checkbox/radio btn's
	protected By knwCheckAnswers = By.cssSelector("div[lesson*='c.selectedLesson'] span.check-mark");
	protected By questSubmitBtn = By.cssSelector("button[ng-click*=\"c.getAnswerFeedback('submit')\"]");
	protected By questNextBtn = By.cssSelector("button[ng-click*=\"c.getNextQuestion\"]");
	protected By successMessage = By.cssSelector("div.success-msg");

	// Bright cove knowledge checks for page questions
	protected By questionsInPage = By.cssSelector("div[ng-repeat*='page.questions']");
	protected By knwoldgeCheckAnswers = By.cssSelector("span.checkmark");
	protected By lastWordInTranscript = By.xpath("(.//div[@class='p3sdk-interactive-transcript']//p//span)[last()]");

	protected By badge = By.cssSelector("p.custom-bounce>strong");
	protected By closeBtn = By.cssSelector("div.modal-dialog button[aria-label='Close']");

	protected By shareBtn = By.cssSelector("a[ng-click*='vm.getShareWidget']");

	protected By rating = By.cssSelector("a[ng-click*='c.openRating']");

	protected By notesIcon = By.cssSelector("div.img-box img[src*='notes']");
	protected By feedbackIcon = By.cssSelector("div.img-box img[src*='feedback']");

	protected By ratingIcon = By.cssSelector("a[ng-click*='c.openRating']");
	protected By ratingComment = By.cssSelector("textarea[placeholder*='Write your comment']");
	protected By assignRating = By.cssSelector("star>ul.rating-star li[ng-repeat*='star in stars']");
	protected By submitBtn = By.cssSelector("a[ng-click*='saveRating']");

	protected By notesIFrame = By.cssSelector("iframe[id*='ui-tinymce']");
	protected By notesBody = By.cssSelector("body#tinymce");

	protected By saveNotes = By.cssSelector("a[ng-click*='vm.saveNotes']");
	protected By successMsgSaveNotes = By.xpath(".//span[contains(text(),'Saved your notes')]");
	protected By closeNotesSection = By.cssSelector("div.notes-area button[aria-label='Close']");

	protected By feedbackCourse = By.cssSelector("div.feedback-area div[ng-model*='vm.feedback.option1']");
	protected By feedbackArea = By.cssSelector("div.feedback-area div[ng-model*='vm.feedback.option2']");
	protected By feedbackDesc = By.cssSelector("div.feedback-area textarea[ng-model='vm.feedback.text']");
	protected By sendBtn = By.cssSelector("a[ng-click*='vm.sendFeedback']");
	protected By feedbackScsMsg = By.xpath(".//span[contains(text(),'Your feedback has been successfully shared')]");
	protected By ratingsText = By.cssSelector("a[ng-click*='c.openRating'] strong");

	protected By feedbackCourses = By.xpath(".//ul[@id='feedback_about_options']/li");

	protected By feedbackOn = By.xpath(".//ul[@id='feedback_on_options']/li");
	protected By assessmentLink = By.cssSelector("div#assessment");

	// Modal Dialog Locators
	public By certificationModaldialog = By.xpath(".//div[@class='modal-dialog']");
	public By badgeModaldialog = By.xpath(".//div[@class = 'modal-body']");

	protected By acccertification = By.cssSelector("input[name='sys_display.x_snc_lxp_path.certificate']");
	protected By availablefrom = By.name("x_snc_lxp_path.available_from");
	protected By available_to = By.name("x_snc_lxp_path.available_to");
	protected By activateBtnVal = By.cssSelector("input[name='x_snc_lxp_path.active']");
	protected By activateBtn = By.cssSelector("label[id='label.ni.x_snc_lxp_path.active']");
	protected By uptdateBtn = By.id("sysverb_update");
	protected By StoView = By.xpath("//div[@class='footer-links']//span[contains(text(),'Support and Services')]");
	protected By circle = By.cssSelector("circle.inner-circle");
	protected By btnStatus=By.cssSelector("button[class*='btn btn-default btn-enroll']");
	protected By assessmentName = By.cssSelector("a[class*='assessment-href']");
	protected By assessmentSurveyForm = By.cssSelector("div.lxp-survey-form");
	protected By pathCourses = By.cssSelector("div#headingTwo div[ng-class*='vm.selectedCourse.sys_id'] strong");
	protected By shareExternalLnk = By.cssSelector("a[ng-click*='vm.getShareWidget()']");
	protected By closeEmailWidjetIcon = By.cssSelector("i[ng-click*='c.closeWidget()']");	
	protected By shareEmailLnk = By.xpath("//div[@class='share-button email_share_button']");
	protected By shareLinkedLnk = By.xpath("//div[@class='share-button linkedin-share-button']");
	protected By shareFbLnk = By.xpath("//i[@class='fa fa-facebook']");
	protected By shareTWLnk = By.xpath("//i[@class='fa fa-twitter']");	
	protected By fbSignInVisible = By.xpath("//label[@class='uiButton uiButtonConfirm uiButtonLarge']");
	protected By twSignInVisible = By.xpath("//input[@class='button selected submit']");
	protected By LiInSignInVisible = By.xpath("//h1[contains(text(),'Make the most of your professional life')]");			
	protected By emailTo = By.name("emailTo");
	protected By sendEmailBtn = By.cssSelector("div[ng-click='c.shareEmail(shareEmailForm);']");
	protected By emailBodyText = By.xpath("//div[@id='share-email-content']//h3");

	
	
	protected NLEUtils leutils = new NLEUtils();
	protected String email;
	protected String delpathName;
	protected Properties prop = new Properties();
	public String Email_table = "/sys_email_list.do";
	
	public void clickShareExternal(){
		uiUtil.clickUsingJs(shareExternalLnk);
	}
	
	
	public String clickShareExtrlLnk(String ExternalLink){
		String externalUrlObtained="";
		switch(ExternalLink){
			case "LinkedIn":
				externalUrlObtained = ValidateExternalLinks(shareLinkedLnk, LiInSignInVisible);
				 break;
			case  "Facebook":
				externalUrlObtained = ValidateExternalLinks(shareFbLnk, fbSignInVisible);	
				break;
			case "Twitter":
				externalUrlObtained = ValidateExternalLinks(shareTWLnk, twSignInVisible);
				break;
		}
		return externalUrlObtained;
	
	}
	
	
	public String ValidateExternalLinks(By link, By locatorVisible){
		clickShareExternal();		
		String winHandleBefore = getDriver().getWindowHandle();
		uiUtil.clickEle(link);
		uiUtil.switchToWindow();
		if(link!=shareLinkedLnk){ //Webdriver is not recognising linkedin
		Assert.assertTrue("External Link is Not working", uiUtil.isElementVisible(locatorVisible, 5));
		}	
		String navigatedUrl =  uiUtil.getUrlText();
		getDriver().close();
		getDriver().switchTo().window(winHandleBefore);
		return navigatedUrl;
	}
	
	public boolean shareViaEmail(String user){		
		String nameQuery = "user_name="+user;
    	String firstName = leutils.getGlideRecordEncodedQuery(USER_TABLE, "first_name", nameQuery);
    	String lastName = leutils.getGlideRecordEncodedQuery(USER_TABLE, "last_name", nameQuery); 			
    	String expectedEmailSubject ="%s %s would like to share training information from ServiceNow";
		expectedEmailSubject = String.format(expectedEmailSubject, firstName, lastName);	
		String generatedstring=RandomStringUtils.randomAlphanumeric(10);
    	String email="testemail"+generatedstring+"@gmail.com";
    	
		clickShareExternal();
		uiUtil.clickEle(shareEmailLnk);
		Assert.assertEquals("email body is not matching", getPathNameDisplayed(),uiUtil.getText(emailBodyText));	
		uiUtil.sendKeys(emailTo, email);	
		uiUtil.clickEle(sendEmailBtn);
		uiUtil.clickEle(closeEmailWidjetIcon);		
    	pauseMe(12);	//this wait is required due to time taken for sending email and table update
    	String encodedQuery = "recipients="+email; 
		String emailnotification =leutils.getGlideRecordEncodedQuery(Email_table, "subject", encodedQuery);		
		return (expectedEmailSubject.equals(emailnotification))? true : false;
	}


	public void clickEnrollBtn(String ... bVal) {
		uiUtil.clickUsingJs(enrollBtn);
		if(bVal.length == 0) {
			waitForKmSpinnerToDisappear();
		}
	}

	public void demoQuizBtn() {
		uiUtil.clickUsingJs(demoQuiz);
	}

	public boolean isEnrollBtnDisplayed() {
		return isElementPresentBy(enrollBtn, 15);
	}

	public boolean isUnEnrollBtnDisplayed() {
		return isElementPresentBy(unEnrollBtn, 10);
	}

	public void clickUnEnrollBtn() {
		uiUtil.clickUsingJs(unEnrollBtn);
		waitForKmSpinnerToDisappear();
	}

	public String getPathNameDisplayed() {
		uiUtil.waitForEleVisibility(pathName, 6);
		return uiUtil.getText(pathName);
	}

	public List<String> fetchPathDetails() {
		uiUtil.waitForEleVisibility(pathDetails, 20);
		return getElementTextValues(pathDetails);
	}

	public boolean isWarningAlertDisplayed() {
		return isElementPresentBy(warningAlert, 12);
	}

	public void expandCourse() {
		if (!isElementPresentBy(expandModule, 4)) {
			uiUtil.clickEle(expandCourse);
		}
	}

	public void expandModule() {
		if (!isElementPresentBy(lessonLinks, 4)) {
			uiUtil.clickEle(expandModule);
		}
	}

	public void clickLesson(int iVal) {
		uiUtil.waitForEleVisibility(lessonLinks, 7);
		findElements(lessonLinks).get(iVal - 1).click();
	}

	public void clickModule(int iVal) {
		uiUtil.waitForEleVisibility(expandModule, 6);
		findElements(expandModule).get(iVal - 1).click();
	}

	public void partialExecutionOfCourse() {
		clickLesson(1, 1);
		clickNextBtn_Lessons();
	}

	public String getAboutCourseText() {
		return uiUtil.getText(aboutCourseText);
	}

	public int getCourseCount() {
		return findElements(expandCourse).size();
	}

	public List<String> getCourseNames() {
		uiUtil.waitForEleVisibility(courseNames, 8);
		return getElementTextValues(courseNames);
	}

	public int getModuleCount() {
		return findElements(expandModule).size();
	}

	public List<String> getModuleNamesForCourse() {
		uiUtil.waitForEleVisibility(expandModule, 8);
		return getElementTextValuesWithSplit(expandModule, ": ");
	}

	public Map<String, List<String>> getModulesAndCorrespondingLessons() {
		Map<String, List<String>> mapObj = new LinkedHashMap<>();
		List<WebElement> listEle = findElements(moduleNames);
		for (WebElement ele : listEle) {
			String moduleName = ele.findElement(By.xpath(".//h4")).getText().trim();
			List<WebElement> lessonNames = ele.findElements(
					By.xpath(".//ul[contains(@class,'lessons')]//a//div[contains(@class,'lesson-title')]"));
			List<String> listLessons = getLessonNames(lessonNames);
			if (!mapObj.containsKey(moduleName)) {
				mapObj.put(moduleName, listLessons);
			}

		}
		return mapObj;
	}

	public List<String> getLessonNames(List<WebElement> listEle) {
		List<String> listStr = new ArrayList<>();
		for (WebElement ele : listEle) {
			String text = ele.getText().trim();
			text = text.substring(5);
			listStr.add(text);
		}
		// listStr = listEle.stream().map(ele -> ele.getText().split("
		// ")[1]).collect(Collectors.toList());
		return listStr;
	}

	// Clicks a Lesson Radio Btn Circle from given module of a course
	public void clickLessonRadioBtn(int iModuleVal, int lessonVal) {
		List<WebElement> listEle = findElements(lessonsUl);
		List<WebElement> listRadioBtns = listEle.get(iModuleVal - 1).findElements(lessonsRadioBtn);
		listRadioBtns.get(lessonVal - 1).click();
	}

	// Clicks a Lesson text link from given module of a course ( overview page ,
	// right screen)
	public void clickLesson(int iModuleVal, int lessonVal) {
		List<WebElement> listEle = findElements(lessonsUl);
		List<WebElement> listLessonLinks = listEle.get(iModuleVal - 1).findElements(lessonTextDiv);
		listLessonLinks.get(lessonVal - 1).click();
	}

	public void completeCourse() {
		uiUtil.waitForEleVisibility(lessonsUl, 10);
		List<WebElement> listEle = findElements(lessonsUl);
		for (WebElement ele : listEle) {
			List<WebElement> listLessonLinks = ele.findElements(lessonTextDiv);
			clickEachLessonLink(listLessonLinks);
		}

	}

	public void clickEachLessonLink(List<WebElement> listLessonLinks) {
		for (WebElement ele : listLessonLinks) {
			uiUtil.scrollToView(ele);
			uiUtil.waitForElementClickable(ele, 10);
			uiUtil.clickUsingJs(ele);
			// ele.click();
			uiUtil.waitForEleVisibility(lessonContent, 10);
			uiUtil.scrollToBottomPage();
			uiUtil.clickUsingJs(nextBtn);
		}
	}

	// In House Course and performing Knowledge checks for each lesson associated to
	// modules.
	public void clickFirstModuleLessonAndCompleteCourse(AssessmentUtils ... assessmentUtils) {
		uiUtil.waitForEleVisibility(lessonsUl, 10);
		List<WebElement> listEle = findElements(lessonLinksRightScreen);
		int iCount = 0;
		for (WebElement ele : listEle) {
			uiUtil.clickUsingJs(ele);
			waitForKmSpinnerToDisappear();
			uiUtil.scrollToView(StoView);
			uiUtil.scrollToBottomPage();
			uiUtil.scrollToView(StoView);
			uiUtil.scrollToBottomPage();
			this.answerKnowledgeChecksForLessons();
			if (ele.findElement(circle).getAttribute("stroke-dashoffset").equalsIgnoreCase("0")) {
				clickNextBtn_Lessons();
			}
			if(isAssessmentFormVisible()) {
				iCount = iCount+1;
				String examLoc = new String("(.//h5[@ng-if='asset.assessment.name']/a)[%d]");
				examLoc = String.format(examLoc,iCount);
				String examName = uiUtil.getText(By.xpath(examLoc));
				completeExam(assessmentUtils[0],examName);
			}
		}
	}

	public boolean isAssessmentFormVisible() {
		return uiUtil.isElementVisible(assessmentSurveyForm,5);
	}

	public void completeExam(AssessmentUtils assessmentUtils,String examName) {
		assessmentUtils.answerExamOrQuiz(examName,"Pass");

	}

	public void clickNextBtn_Lessons() {
		if (uiUtil.isElementVisible(nextBtn, 5)) {
			uiUtil.moveToElement(getDriver().findElement(nextBtn));
			uiUtil.clickUsingJs(nextBtn);
			waitForKmSpinnerToDisappear();
		}
	}

	public String getTextEnrolBtn() {
		uiUtil.waitForEleVisibility(enrollBtn, 10);
		uiUtil.moveToElement(getDriver().findElement(enrollBtn));
		return uiUtil.getText(enrollBtn);
	}

	public void answerKnowledgeChecksForLessons() {
		while (isSubmitBtnDisplayed()) {
			selectKnowledgeCheckAnswers(1);
			clickNextBtn_Question();
		}
	}

	// Select answers for knowledge checks
	public void selectKnowledgeCheckAnswers(int iChoice) {
		uiUtil.waitForEleVisibility(knwCheckAnswers, 4);
		List<WebElement> listEle = findElements(knwCheckAnswers);
		WebElement ele = listEle.get(iChoice - 1);
		uiUtil.moveToElement(ele);
		uiUtil.clickUsingJs(ele);
		clickSubmitBtn_Question();
	}

	public void clickNextBtn_Question() {
		if (uiUtil.isElementVisible(questNextBtn, 4)) {
			uiUtil.moveToElement(getDriver().findElement(questNextBtn));
			uiUtil.clickUsingJs(questNextBtn);
		}
	}

	public void clickSubmitBtn_Question() {
		if (uiUtil.isElementVisible(questSubmitBtn, 4)) {
			uiUtil.moveToElement(getDriver().findElement(questSubmitBtn));
			uiUtil.clickUsingJs(questSubmitBtn);
		}
	}

	public void clickStartBtn() {
		if (uiUtil.isElementVisible(selectClass, 4)) {
			uiUtil.moveToElement(getDriver().findElement(selectClass));
			uiUtil.clickUsingJs(selectClass);
		}
	}

	public boolean isSuccessMessageDisplayed() {
		return uiUtil.isElementVisible(successMessage, 10);
	}

	public boolean isSubmitBtnDisplayed() {
		return uiUtil.isElementVisible(questSubmitBtn, 5);
	}

	// BrightCove Course by completing Knowledge checks associated for each lesson
	// under modules.
	public void clickFirstModuleLessonAndCompleteBrightCoveCourse() {
		uiUtil.waitForEleVisibility(lessonsUl, 10);
		List<WebElement> listEle = findElements(lessonLinksRightScreen);
		WebElement ele1 = listEle.get(0);
		uiUtil.scrollToView(ele1);
		uiUtil.moveToElement(ele1);

		uiUtil.waitForElementClickable(ele1, 6);
		uiUtil.clickUsingJs(ele1);
		waitForKmSpinnerToDisappear();
		for (WebElement ele : listEle) {
			uiUtil.waitForEleVisibility(lessonContent, 10);
			uiUtil.scrollToView(StoView);
			uiUtil.scrollToBottomPage();
			uiUtil.scrollToView(StoView);
			uiUtil.scrollToBottomPage();
			// this.answerKnowledgeChecksForLessons_BrightCove();
			scrollToLastElementUnderTranscript();
			clickNextBtn_Lessons();
			//waitForKmSpinnerToDisappear();
		}
	}

	public void answerKnowledgeChecksForLessons_BrightCove() {
		if (uiUtil.isElementVisible(questionsInPage, 7)) {
			List<WebElement> pageQuestions = findElements(questionsInPage);
			while (isSubmitBtnDisplayed()) {
				for (WebElement ele : pageQuestions) {
					selectKnowledgeCheckAnswers_BrightCove(ele, 1);
					// clickNextBtn_Question();
				}
				clickSubmitBtn_Question();
				waitForKmSpinnerToDisappear();
			}
		}
	}

	// Select answers for knowledge checks
	public void selectKnowledgeCheckAnswers_BrightCove(WebElement ele, int iChoice) {
		uiUtil.waitForEleVisibility(knwoldgeCheckAnswers, 4);
		List<WebElement> listEle = ele.findElements(knwoldgeCheckAnswers);
		WebElement reqEle = listEle.get(iChoice - 1);
		uiUtil.moveToElement(reqEle);
		uiUtil.clickUsingJs(reqEle);
	}

	public void scrollToLastElementUnderTranscript() {
		uiUtil.scrollToView(StoView);
		uiUtil.scrollToBottomPage();
		uiUtil.scrollToView(lastWordInTranscript);
		uiUtil.moveToElement(getDriver().findElement(lastWordInTranscript));
		uiUtil.clickUsingJs(lastWordInTranscript);
		// Even after clicking last word, it doesnt complete lesson, its required a wait
		// as the related video cant be automated it needs to completely played
		pauseMe(3);
		uiUtil.scrollToView(StoView);
		uiUtil.scrollToBottomPage();
		pauseMe(3);
		uiUtil.clickUsingJs(lastWordInTranscript);
		pauseMe(3);
		uiUtil.scrollToView(StoView);
		uiUtil.scrollToBottomPage();
		pauseMe(3);
	}

	public boolean validateBadgeDisplayed() {
		return uiUtil.isElementVisible(badge, 20);
	}

	public void closeBadgeDialog() {
		try {

		if (uiUtil.isElementVisible(closeBtn, 5)) {
			uiUtil.clickUsingJs(closeBtn);
		}
		} catch (Exception e) {
			Log.info("Dialog window error :::" + e.getMessage());
		}
	}

	public void clickShareIcon() {
		uiUtil.clickUsingJs(shareBtn);
	}

	public void assignRating(int starsCount, String comment) {
		uiUtil.clickUsingJs(ratingIcon);
		pauseMe(3);
		uiUtil.waitForEleVisibility(assignRating, 10);

		List<WebElement> listEle = findElements(assignRating);
		WebElement ele = listEle.get(starsCount - 1);
		uiUtil.clickUsingJs(ele);
		pauseMe(3);
		if (comment != null) {
			uiUtil.sendKeys(ratingComment, comment);
		}
		uiUtil.clickUsingJs(submitBtn);
		pauseMe(3);
	}

	public int getRatingsAssigned() {
		uiUtil.waitForEleVisibility(ratingsText, 8);
		char ch = uiUtil.getText(ratingsText).charAt(0);
		int ival = Character.getNumericValue(ch);
		return ival;
	}

	public void clickNotes() {
		uiUtil.clickUsingJs(getDriver().findElement(notesIcon));
	}

	public void enterNotes(String notesContent) {
		uiUtil.waitForEleVisibility(notesIFrame, 10);
		pauseMe(4);
		uiUtil.switchToIFrame(notesIFrame);
		uiUtil.clickEle(notesBody);
		pauseMe(4);
		uiUtil.sendKeys(notesBody, notesContent);
	}

	public void saveNotes() {
		uiUtil.switchToParent();
		pauseMe(4);
		uiUtil.clickEle(saveNotes);
		pauseMe(1);
		uiUtil.waitForElementInvisibility(successMsgSaveNotes, 7);
		uiUtil.clickEle(closeNotesSection);
	}

	public void clickFeedbackIcon() {
		uiUtil.clickUsingJs(getDriver().findElement(feedbackIcon));
		pauseMe(4);
	}

	public void enterFeedback(String fdbAbout, String fdbOn, String description) {
		uiUtil.waitForEleVisibility(feedbackCourse, 10);
		uiUtil.clickEle(feedbackCourse);
		List<WebElement> listCourses = findElements(feedbackCourses);
		listCourses.stream().filter(element -> element.getText().trim().contains(fdbAbout)).findFirst().get().click();
		uiUtil.clickEle(feedbackArea);
		pauseMe(3);
		List<WebElement> listAreas = findElements(feedbackOn);
		listAreas.stream().filter(element -> element.getText().trim().contains(fdbOn)).findFirst().get().click();
		pauseMe(3);
		uiUtil.sendKeys(feedbackDesc, description);
		uiUtil.clickEle(sendBtn);
		uiUtil.waitForEleVisibility(feedbackScsMsg, 7);
		pauseMe(3);
	}

	// Method to expand course from left side under Start Path
	public void expandCourse(int courseDisplayNumber) {
		List<WebElement> listEle = findElements(expandCourse);
		uiUtil.waitForElementClickable(listEle.get(courseDisplayNumber - 1), 10);
		listEle.get(courseDisplayNumber - 1).click();
	}

	public void clickCourse(int courseDisplayNumber) {
		List<WebElement> listEle = findElements(courseNamesLeft);
		uiUtil.waitForElementClickable(listEle.get(courseDisplayNumber - 1), 10);
		uiUtil.clickUsingJs(listEle.get(courseDisplayNumber - 1));
		// listEle.get(courseDisplayNumber - 1).click();
	}

	public void clickAssessment() {
		uiUtil.clickUsingJs(assessmentLink);
	}

	public void clickFirstModuleLessonAndCompleteCourseaccrdt() {
		uiUtil.waitForEleVisibility(lessonsUl, 10);
		List<WebElement> listEle = findElements(lessonLinksRightScreen);
		for (int iVal = 0; iVal < listEle.size(); iVal++) {
			uiUtil.scrollToView(listEle.get(iVal));
			uiUtil.moveToElement(listEle.get(iVal));
			uiUtil.waitForElementClickable(listEle.get(iVal), 6);
			uiUtil.clickUsingJs(listEle.get(iVal));
			waitForKmSpinnerToDisappear();
			uiUtil.scrollToView(StoView);
			uiUtil.scrollToBottomPage();
			uiUtil.scrollToView(StoView);
			uiUtil.scrollToBottomPage();
			if (listEle.get(iVal).findElement(circle).getAttribute("stroke-dashoffset").equalsIgnoreCase("0")) {
				clickNextBtn_Lessons();
			}
		}
	}

	public String accrDeltaPathactivate(String sysid) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String flag;
		Calendar cal = Calendar.getInstance();
		Calendar nextmonthdate = (Calendar) cal.clone();
		nextmonthdate.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		String prvdate = dateFormat.format(cal.getTime());
		String tdaydate = dateFormat.format(date);
		String availableto = dateFormat.format(nextmonthdate.getTime());
		open("/x_snc_lxp_path.do?sys_id=" + sysid);
		timers.waitUntilDOMReady(8);
		/*
		 * RemoteGlideRecord rgr = getGlideRecordForTable("x_snc_lxp_path.do");
		 * rgr.addEncodedQuery("active=true^sys_id=" + sysid); rgr.query(); rgr.next();
		 * rgr.setValue("active", "true"); String certificate =
		 * rgr.getDisplayValue("certificate"); System.out.println("previous day date"
		 * +tdaydate); System.out.println("today day date" +tdaydate);
		 * rgr.setValue("available_from", " "); rgr.setValue("available_from",
		 * "tdaydate"); rgr.setValue("available_to", " ");
		 * 
		 * rgr.setValue("available_to", "availableto"); rgr.update(); //pauseMe(4);
		 * //rgr.setValue("available_from", "tdaydate"); //rgr.setValue("active",
		 * "true"); //rgr.update();
		 */
		String certificate = findElement(acccertification).getAttribute("value");
		findElement(availablefrom).clear();
		findElement(availablefrom).sendKeys(prvdate);
		findElement(available_to).clear();
		findElement(available_to).sendKeys(dateFormat.format(nextmonthdate.getTime()));
		flag = findElement(activateBtnVal).getAttribute("value");
		if (flag.equalsIgnoreCase("false")) {
			findElement(activateBtn).click();
		}
		findElement(uptdateBtn).click();
		return certificate;
	}
	
	public String verifypathcourseBtnStatus()
	{
		String status=null;
		if(uiUtil.isElementPresence(btnStatus, 5))
		{
		String sts = findElement(btnStatus).getText();
		}
		return status;
	}

	public void clickFirstCourseLessonAndCompleteMultipleCourses() {
		List<WebElement> listCourses = findElements(courseNamesLeft);
		for (WebElement courseLink : listCourses) {
			uiUtil.waitForElementClickable(courseLink, 6);
			uiUtil.clickUsingJs(courseLink);

			uiUtil.waitForEleVisibility(lessonsUl, 10);
			List<WebElement> listEle = findElements(lessonLinksRightScreen);
			for (WebElement ele : listEle) {
				uiUtil.clickUsingJs(ele);
				waitForKmSpinnerToDisappear();
				uiUtil.scrollToView(StoView);
				uiUtil.scrollToBottomPage();
				uiUtil.scrollToView(StoView);
				uiUtil.scrollToBottomPage();
				this.answerKnowledgeChecksForLessons();
				if (ele.findElement(circle).getAttribute("stroke-dashoffset").equalsIgnoreCase("0")) {
					clickNextBtn_Lessons();
				}
			}
		}
	}

	public boolean nextBtnDisplayed() {
		return uiUtil.isElementVisible(nextBtn,5);
	}

	public void clickFirstModuleLessonAndCompletePath(AssessmentUtils ... assessmentUtils) {
		uiUtil.waitForEleVisibility(lessonsUl, 10);
		uiUtil.clickUsingJs(findElements(lessonLinksRightScreen).get(0));
		waitForKmSpinnerToDisappear();
		List<WebElement> listEle = findElements(pathCourses);
		while(nextBtnDisplayed()) {
			uiUtil.scrollToView(StoView);
			uiUtil.scrollToBottomPage();
			pauseMe(3);
			uiUtil.scrollToView(StoView);
			uiUtil.scrollToBottomPage();
			pauseMe(3);
			//this.answerKnowledgeChecksForLessons();
			clickNextBtn_Lessons();
			//waitForKmSpinnerToDisappear();
		}
		if(isAssessmentFormVisible()) {
			String examLoc = "(.//div[@id='assessment']//span)[2]";
			String examName = uiUtil.getText(By.xpath(examLoc));
			completeExam(assessmentUtils[0],examName);
		}
	}
}
