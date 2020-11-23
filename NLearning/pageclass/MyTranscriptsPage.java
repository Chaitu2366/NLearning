
package com.snc.surf.marketing.NLearning.pageclass;

import com.glide.util.Log;
import com.snc.surf.marketing.NLearning.base.NLEPageBase;
//import com.snc.surf.marketing.NLearning.tests.signedInUser.landingpage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;

public class MyTranscriptsPage extends NLEPageBase {

	protected By tableRows = By.xpath(
			".//div[contains(@id,'my-transcripts-certifications-tab')]//table//tr[contains(@class,'tab-list-item')]");
	protected By name = By.xpath(".//td[contains(@class,'list-item-name')]");
	protected By status = By
			.xpath(".//td[contains(@class,'list-item-name')]/following::td[contains(@class,'list-item-status')]");
	protected By transcriptCertificationTableRows = By
			.xpath(".//div[contains(text(),'Certification')]//following::tr[contains(@class,'learning-plan-row')]");
	protected By loadMoreCertifications = By
			.xpath(".//div[contains(text() , 'Certifications')]//following::a[@ng-click='c.loadMore()']");
	protected By viewCertList = By.xpath(".//div//td[contains(@class, 'list-item-cert-view')]");
	protected By viewPopUpCertName = By.xpath(".//div[contains(@class,'leading')]");
	protected By viewPopUpCertComp = By.xpath(".//div[@class = 'py-4 ng-binding']");
	protected By downlaodCertList = By.xpath(".//div//td[contains(@class, 'list-item-cert-download')]");
	protected By gridViewCert = By.xpath(".//div[contains(@class, 'my-transcripts-certifications-select-card-view')]");
	protected By listViewCert = By.xpath(".//div[contains(@class, 'my-transcripts-certifications-select-list-view')]");
	protected By listCertName = By.xpath(".//div//td[contains(@class, 'list-item-name')]");
	protected By gridCertName = By
			.xpath(".//div[contains(text(),'Certifications')]//following::div[contains(@class, 'tab-card-content')]");
	protected By listCertComp = By.xpath(".//div//td[contains(@class, 'list-item-completed')]");
	protected By gridCertComp = By
			.xpath(".//div[contains(text(),'Certifications')]//following::div[contains(@class, 'tab-card-content')]");
	protected By listCertStatus = By.xpath(".//div//td[contains(@class, 'list-item-status')]");
	protected By gridCertStatus = By
			.xpath(".//div[contains(text(),'Certifications')]//following::div[contains(@class, 'card-item-status')]");
	protected By statusFilter = By.xpath(".//button[@ng-if = 'column.filters.length > 0' ]");
	protected By statusCurrent = By.xpath(".//li[text() = 'Current' ]");
	protected By statusAtRisk = By.xpath(".//li[text() = 'At Risk' ]");
	protected By statusExpired = By.xpath(".//li[text() = 'Expired' ]");
	protected By statusAll = By.xpath(".//li[text() = 'All' ]");
	// Transcript Path locators
	protected By transcriptPathTableRows = By
			.xpath(".//div[contains(text(),'Paths')]//following::tr[contains(@class,'learning-plan-row')]");
	protected By transcriptPathName = By.xpath("//div[@sn-atf-area='Paths']//td[contains(@class,'list-item-name')]");
	protected By pathCompletionDate = By
			.xpath(".//div[contains(text() ,'Paths')]//following::td[contains(@class,'list-item-completed')]");
	protected By loadMorePath = By.xpath(".//div[contains(text() , 'Path')]//a[@ng-click ='c.loadMore()']");
	protected By rightArrowPath = By.xpath(".//div[contains(text(),'Paths')]//following::img[@class='arrow']");
	protected By rightArrowPathEle = By.xpath(".//button[contains(text() , 'Completed')]");
	protected By gridViewPath = By.xpath("(.//div[ contains(@class, 'my-transcripts-tab-select-card')])[1]");
	protected By listViewPath = By.xpath("(.//div[ contains(@class, 'my-transcripts-tab-select-list')])[1]");
	protected By listPathName = By
			.xpath(".//div[contains(text(),'Path')]//following::td[contains(@class, 'list-item-name')]");
	protected By listPathComp = By
			.xpath(".//div[contains(text(),'Path')]//following::td[contains(@class, 'list-item-completed')]");
	protected By gridPathName = By
			.xpath(".//div[contains(text(),'Paths')]//following::div[contains(@class, 'break-words')]");
	protected By gridPathComp = By
			.xpath(".//div[contains(text(),'Paths')]//following::div[contains(@class, 'card-item-completed')]");

	// Transcript Course Locators
	protected By transcriptCourseTableRows = By
			.xpath(".//div[contains(text(),'Courses')]//following::tr[contains(@class,'learning-plan-row')]");
	protected By transcriptCourseName = By
			.xpath(".//div[contains(text() ,'Courses')]//following::td[contains(@class,'list-item-name')]");
	protected By courseCompletionDate = By
			.xpath(".//div[contains(text() ,'Courses')]//following::td[contains(@class,'list-item-completed')]");
	protected By transcriptTab = By.cssSelector("li[heading='My Transcript']");
	protected By loadMoreCourses = By.xpath(".//div[contains(text() , 'Courses')]//a[@ng-click ='c.loadMore()']");
	protected By rightArrowCourse = By.xpath(".//div[contains(text() ,'Courses')]//following::img[@class='arrow']");
	protected By rightArrowCourseEle = By.xpath(".//button[contains(text() , 'Completed')]");
	protected By gridViewCourse = By.xpath("(.//div[ contains(@class, 'my-transcripts-tab-select-card')])[2]");
	protected By listViewCourse = By.xpath("(.//div[ contains(@class, 'my-transcripts-tab-select-list')])[2]");
	protected By listCourseName = By
			.xpath(".//div[contains(text(),'Course')]//following::td[contains(@class, 'list-item-name')]");
	protected By listCourseComp = By
			.xpath(".//div[contains(text(),'Course')]//following::td[contains(@class, 'completed')]");
	protected By gridCourseName = By
			.xpath(".//div[contains(text(),'Courses')]//following::div[contains(@class, 'break-words')]");
	protected By gridCourseComp = By
			.xpath(".//div[contains(text(),'Courses')]//following::div[contains(@class, 'card-item-completed')]");

	// Notification related Locators

	protected By bellIcon = By.xpath(".//a[contains(@aria-label, 'Notification')]");
	protected By notificationRows = By.xpath(".//li[contains(@ng-repeat, 'data track')]");
	protected By LoadMoreNotifications = By.xpath(".//button[contains(@class, 'load-more')]");

	// Certification related Methods

	public String getCertificateStatus(String certificationName) {
		List<WebElement> listEle = findElements(tableRows);
		WebElement statusValue = listEle.stream().filter(ele -> ele.findElement(name).getText().trim().contains(certificationName))
				.findAny().orElse(null);
		return statusValue != null ? statusValue.findElement(status).getText().trim() : null;
	}

	public int validateRowsDisplayedUnderCertifications() {
		int size = findElements(transcriptCertificationTableRows).size();

		return size;
	}

	public void clickLoadMore_Certifications() {
		if (uiUtil.isElementVisible(loadMoreCertifications, 5)) {
			int i = 0;
			do {
				uiUtil.clickEle(loadMoreCertifications);
				i++;
			} while (isElementPresentBy(loadMoreCertifications, 12) && i < 5);
			pauseMe(3);
		}

	}
	//

	public void clickStatusFilter(String option) {
		uiUtil.clickEle(statusFilter);

		switch (option) {
		case "current":
			uiUtil.clickEle(statusCurrent);
		case "At Risk":
			uiUtil.clickEle(statusAtRisk);
		case "Expired":
			uiUtil.clickEle(statusExpired);
		case "All Status":
			uiUtil.clickEle(statusExpired);

		}

	}

	// This Method retrieves a user with all 3 status (current, expired and At-Risk)
	public List<String> getUserStatuses() {

		String encQueryUser = "user_name=%s";
		String encQueryUserStat = "certification.name!=NULL &&!=empty^user.user_name=%s";
		List<String> Users = getUniqueUser();
		List<String> reqFieldValues = null;
		String impersonateUser = (null != Users && Users.size() > 0) ? Users.get(0) : null;
		if (impersonateUser!=null) {
			encQueryUser = String.format(encQueryUser, impersonateUser);
			leUtils.updateGlideRecordValue(USER_TABLE, "user", impersonateUser, "active", "True");

			String[] reqFields = { "certification", "status" };
			encQueryUserStat = String.format(encQueryUserStat, impersonateUser);
			reqFieldValues = leUtils.getMultipleFieldsDisplayValues_EncodedQuery(USER_CERTIFICATION_TABLE, reqFields,
					encQueryUserStat);
			Log.info("Fetched values:" + reqFieldValues);
			reqFieldValues.add(impersonateUser);
		}

		return reqFieldValues;

	}

	private List<String> getUniqueUser() {
		List<String> Users;
		String encQueryExp = "certification.name!=NULL &&!=empty^user.user_name!=NULL &&!=empty^statusINExpired";
		String encQueryAtRisk = "certification.name!=NULL &&!=empty^user.user_name!=NULL &&!=empty^statusINAt Risk";
		String encQueryCurrent = "certification.name!=NULL &&!=empty^user.user_name!=NULL &&!=empty^statusINCurrent";

		List<String> expiredUsersList = leUtils.getMultipleFieldsDisplayValues_EncodedQuery(USER_CERTIFICATION_TABLE,
				"user", encQueryExp);
		List<String> atRiskUsersList = leUtils.getMultipleFieldsDisplayValues_EncodedQuery(USER_CERTIFICATION_TABLE,
				"user", encQueryAtRisk);
		List<String> currentUsersList = leUtils.getMultipleFieldsDisplayValues_EncodedQuery(USER_CERTIFICATION_TABLE,
				"user", encQueryCurrent);

		Users = expiredUsersList.stream().filter(atRiskUsersList::contains).filter(currentUsersList::contains)
				.distinct().collect(Collectors.toList());
		Log.info("Users list :: " + Users);

		expiredUsersList.retainAll(currentUsersList);
		expiredUsersList.retainAll(atRiskUsersList);
		Log.info("expiredUsersList final  list :: " + expiredUsersList);
		return Users;
	}

	public boolean validateCertViewGridList() {
		
			String certNameList = uiUtil.getText(listCertName);
			
				String certStatusList = uiUtil.getText(listCertStatus);

				List<String> listValues = new ArrayList<>();
				listValues.add(certNameList);
				listValues.add(certStatusList);
				// listValues.add(certComp);
				uiUtil.clickEle(gridViewCert);
				
					String certNameGrid = uiUtil.getText(gridCertName);
					
						String certStatusGrid = uiUtil.getText(gridCertStatus);
						List<String> gridValues = new ArrayList<>();
						gridValues.add(certNameGrid);
						gridValues.add(certStatusGrid);
						if (listValues.containsAll(gridValues)) {
							return true;
						}
				
		return false;

	}

	public boolean validateViewCert() {
		String certNameList = uiUtil.getText(listCertName);
		String certCompList = uiUtil.getText(listCertComp);
		List<String> expectedValues = new ArrayList<>();
		expectedValues.add(certNameList);
		expectedValues.add(certCompList);
		
			uiUtil.clickEle(viewCertList);
			timers.waitUntilDOMReady(20);
			String certName = uiUtil.getText(viewPopUpCertName);
			String certComp = uiUtil.getText(viewPopUpCertComp);
			List<String> viewPopUpValues = new ArrayList<>();
			viewPopUpValues.add(certName);
			viewPopUpValues.add(certComp);
			if (expectedValues.containsAll(viewPopUpValues)) {
				return true;
			}
		
		return false;
	}

	public boolean validateDownlaodCert() {
		
			uiUtil.clickEle(downlaodCertList);
			String downloads_location = System.getProperty("user.home") + "/Downloads/";
			pauseMe(7);
			String fileName;
			String expectedFileName = uiUtil.getText(listCertName);
			Path dir = Paths.get(downloads_location);
			try {
				Optional<Path> lastFilePath = Files.list(dir).filter(f -> !Files.isDirectory(f))
						.max(Comparator.comparingLong(f -> f.toFile().lastModified()));
				fileName = lastFilePath.isPresent() ? lastFilePath.get().getFileName().toString() : null;
				String comaprableFileName = fileName.replace("_", ":");
				if (comaprableFileName.contains(expectedFileName)) {
					return true;
				}
			} catch (IOException e) {
				return false;
			}

		
		return false;
	}

	public List<String> acquireDeltaCert() {
		String encQueryUserExam = "exam.nameLIKEdelta^user_certification.certification!=NULL&&!=(Empty)^statusSTARTSWITHCurrent";
		List<String> deltaUserCerts = new ArrayList<>();
		String[] reqFields = { "user", "user_certification", "exam" };
		deltaUserCerts = leUtils.getMultipleFieldsDisplayValues_EncodedQuery(USER_EXAM_TABLE, reqFields,
				encQueryUserExam);
		// now login to nl and impersonate with the user
		// Collections.shuffle(deltaUserCerts);
		return deltaUserCerts;
	}

	// Path related methods
	public boolean validatePathNameDisplayed(String courseName) {
		List<WebElement> listEle = findElements(transcriptPathName);
		for (WebElement ele : listEle) {
			if (ele.getText().trim().contains(courseName)) {
				return true;
			}
		}
		return false;
	}

	public boolean validatecoursesInTranscripts(ArrayList<String> courses) {
		for (String s1 : courses) {
			String encQuery = new String("name=%s");
			encQuery = String.format(encQuery, s1);
			String badgeValue = leUtils.getGlideRecordEncodedQuery(COURSES_TABLE, "badge", encQuery);
			if (badgeValue != null && !badgeValue.isEmpty()) {
				boolean status = validatePathNameDisplayed(s1);
				if (!status) {
					return false;
				}
			}
		}
		return true;
	}

	public String getPathCompletionDate(String courseName) {
		String completionDateValue = null;
		List<WebElement> listEle = findElements(transcriptPathTableRows);
		for (WebElement ele : listEle) {
			if (ele.findElement(transcriptPathName).getText().trim().contains(courseName)) {
				completionDateValue = ele.findElement(pathCompletionDate).getText().trim();
				break;
			}
		}
		return completionDateValue;
	}

	public boolean validateRowsDisplayedUnderPaths() {
		int size = findElements(transcriptPathTableRows).size();
		if (size > 0) {
			return true;
		}
		return false;
	}

	public void clickLoadMore_Paths() {
		
			int i = 0;
			do {
				uiUtil.clickEle(loadMorePath);
				i++;
			} while (isElementPresentBy(loadMorePath, 12) && i < 5);
			pauseMe(3);
		}

	

	public boolean validateRightArrowPath() {
		
			uiUtil.clickEle(rightArrowPath);
			pauseMe(7);
			if (uiUtil.isElementVisible(rightArrowPathEle, 5)) {
				return true;
			}
		
		return false;
	}

	public boolean validatePathViewGridList() {
		
			String pathNameList = uiUtil.getText(listPathName);
			
				String pathCompList = uiUtil.getText(listPathComp);

				List<String> listValues = new ArrayList<>();
				listValues.add(pathNameList);
				listValues.add(pathCompList);
				uiUtil.clickEle(gridViewPath);
				
					String pathNameGrid = uiUtil.getText(gridPathName);
					
						String pathCompGrid = uiUtil.getText(gridPathComp);
						pathCompGrid = pathCompGrid.replace("Completed on ", "");
						List<String> gridValues = new ArrayList<>();
						gridValues.add(pathNameGrid);
						gridValues.add(pathCompGrid);
						if (listValues.containsAll(gridValues)) {
							return true;
						}
				
		
		return false;

	}

	public boolean validateRightArrowPaths() {
		
			uiUtil.clickEle(rightArrowPath);
			timers.waitUntilDOMReady(20);
			if (uiUtil.isElementVisible(rightArrowPathEle, 5)) {
				return true;
			}
		

		return false;
	}

	// Course related methods
	public boolean validateCourseNameDisplayed(String courseName) {
		List<WebElement> listEle = findElements(transcriptCourseTableRows);
		for (WebElement ele : listEle) {
			if (ele.getText().trim().contains(courseName)) {
				return true;
			}
		}
		return false;
	}

	public String getCourseCompletionDate(String courseName) {
		String completionDateValue = null;
		List<WebElement> listEle = findElements(transcriptCourseTableRows);
		for (WebElement ele : listEle) {
			if (ele.getText().trim().contains(courseName)) {
				completionDateValue = ele.findElement(courseCompletionDate).getText().trim();
				break;
			}
		}
		return completionDateValue;
	}

	public boolean validateRowsDisplayedUnderCourses() {
		int size = findElements(transcriptCourseTableRows).size();
		if (size > 0) {
			return true;
		}
		return false;
	}

	public void clickmytranscriptstab() {
		if (uiUtil.isElementPresence(transcriptTab, 5)) {
			uiUtil.clickUsingJs(transcriptTab);
		}
	}

	public void clickLoadMore_Courses() {
		if (uiUtil.isElementVisible(loadMoreCourses, 5)) {
			int i = 0;
			do {
				uiUtil.clickUsingJs(loadMorePath);
				i++;
			} while (isElementPresentBy(loadMorePath, 12) && i < 5);
			pauseMe(3);
		}

	}

	public List<String> retrieveCoursesAssociatedtoPaths() {

		String encQueryTranscripts = "percent_complete=100^type=course^state=200^courseNSAMEAScurrent_course";
		String encQueryM2M = "course=%s";
		String encQueryTrans = "percent_complete=100^type=course^state=200^course.name=%s";
		String activeCourse = null;
		String courseUser = null;
		List<String> reqFieldValues = new ArrayList<>();

		List<String> completedCourses = leUtils.getMultipleFieldsDisplayValues_EncodedQuery(TRANSCRIPTS_TABLE, "course",
				encQueryTranscripts);

		long coursesCount = completedCourses.stream().distinct().count();
		Log.info("No. of distinct users:" + coursesCount);
		Collections.shuffle(completedCourses);
		for (int i = 0; i < completedCourses.size(); i++) {

			activeCourse = completedCourses.get(i);
			Optional<String> nullCheck = Optional.ofNullable(activeCourse);
			if (nullCheck.isPresent()) {
				encQueryM2M = String.format(encQueryM2M, activeCourse);
				int courseRowCount = leUtils.getGlideRecordCount("x_snc_lxp_m2m_course_path", encQueryM2M);
				encQueryM2M = "course=%s";

				if (courseRowCount == 0) {
					leUtils.updateGlideRecordValue(COURSES_TABLE, "name", activeCourse, "active", "True");
					Log.info("Active Course is:" + activeCourse);
					reqFieldValues.add(activeCourse);
					encQueryTrans = String.format(encQueryTrans, activeCourse);
					List<String> courseUsers = leUtils.getMultipleFieldsDisplayValues_EncodedQuery(TRANSCRIPTS_TABLE,
							"student", encQueryTrans);
					long courseUsersCount = courseUsers.stream().distinct().count();
					Log.info("No. of distinct users:" + courseUsersCount);
					Collections.shuffle(courseUsers);
					for (int j = 0; j < courseUsers.size(); j++) {
						courseUser = courseUsers.get(j);
						Optional<String> userNotNull = Optional.ofNullable(courseUser);
						if (userNotNull.isPresent()) {
							leUtils.updateGlideRecordValue(USER_TABLE, "name", courseUser, "active", "True");

							Log.info("User to be impersonated for course validation :" + courseUser);
							reqFieldValues.add(courseUser);
						} else {
							continue;
						}

						break;

					}

				} else {
					continue;
				}
			} else {
				Log.info("Course picked for course validation is null :" + activeCourse);
			}

		}

		return reqFieldValues;
	}

	public boolean validateRightArrowCourse() {
		
			uiUtil.clickEle(rightArrowCourse);

			if (uiUtil.isElementVisible(rightArrowCourseEle, 5)) {
				return true;
			}
		
		return false;
	}

	public boolean validateCourseViewGridList() {
		
			String courseNameList = uiUtil.getText(listCourseName);
			
				String courseCompList = uiUtil.getText(listCourseComp);

				List<String> listValues = new ArrayList<>();
				listValues.add(courseNameList);
				listValues.add(courseCompList);
				uiUtil.clickEle(gridViewCourse);
				
					String courseNameGrid = uiUtil.getText(gridCourseName);
				
						String courseCompGrid = uiUtil.getText(gridCourseComp);
						courseCompGrid = courseCompGrid.replace("Completed on ", "");
						List<String> gridValues = new ArrayList<>();
						gridValues.add(courseNameGrid);
						gridValues.add(courseCompGrid);
						if (listValues.containsAll(gridValues)) {
							return true;
						}
					

				
			
		
		return false;

	}

	public boolean validateRightArrowCourses() {
		
			uiUtil.clickEle(rightArrowCourse);
			timers.waitUntilDOMReady(20);
			if (uiUtil.isElementVisible(rightArrowCourseEle, 5)) {
				return true;
			}
		

		return false;
	}

	// Notification related Methods

	protected List<String> validateEmailNotifications(String user) {
		List<String> subjectList = new ArrayList<>();
		String encQueryEmail = "sys_created_onONToday@javascript:gs.daysAgoStart(0)@javascript:gs.daysAgoEnd(0)^recipientsSTARTSWITH%s";
		encQueryEmail = String.format(encQueryEmail, user);
		leUtils.updateGlideRecordValue(USER_TABLE, "user", user, "active", "True");
		List<String> emailSubject = leUtils.getMultipleFieldsDisplayValues_EncodedQuery(EMAIL_LOG_TABLE, "subject",
				encQueryEmail);
		for (int i = 0; i < 3; i++) {
			subjectList.add(emailSubject.get(i));
		}
		return subjectList;

	}

	public boolean validateSubjectBellNotification(String user) {

		
			uiUtil.clickEle(bellIcon);
			timers.waitUntilDOMReady(DOM_LOAD_TIME);
			for (int i = 0; i < 3; i++) {
				List<String> notificationList = getElementTextValues(notificationRows);
				List<String> subjectList = validateEmailNotifications(user);
				if (notificationList.containsAll(subjectList)) {
					return true;
				}
			}
		
		return false;
	}
	
	public boolean validateBellNotificationLoadMore(String user) {
		String encQueryEmail = "sys_created_onONToday@javascript:gs.daysAgoStart(0)@javascript:gs.daysAgoEnd(0)^recipientsSTARTSWITH%s";
		encQueryEmail = String.format(encQueryEmail, user);
		int emailCount = leUtils.getGlideRecordCount(EMAIL_LOG_TABLE, encQueryEmail);
		if (emailCount>4) {
			validateLoadMoreNotificationLink();
		}
		return false;
	}
	
	public boolean validateLoadMoreNotificationLink() {
		uiUtil.clickEle(bellIcon);
		timers.waitUntilDOMReady(DOM_LOAD_TIME);
		if(uiUtil.isElementVisible(LoadMoreNotifications, 5)) {
			return true;
		}
		return false;
	}
}