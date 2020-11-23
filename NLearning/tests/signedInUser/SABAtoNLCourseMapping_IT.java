package com.snc.surf.marketing.NLearning.tests.signedInUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.glide.communications.RemoteGlideRecord;
import com.glide.script.proxy.File;
import com.glide.util.log.*;
import org.jfree.util.Log;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriverService;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.utils.Entitlements.NLEntitlement;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class SABAtoNLCourseMapping_IT extends NLETestBase {

	public String sabaCourseName = null;
	public String transcriptId = null;
	public String mappedNLCourse = null;
	public String updatedCourseName = null;
	public String activeNLUser = null;
	public String SKU = "EDUCERTSYSADM5PK";
	HashMap<String, String> hMap = new HashMap<>();
	public List<String> classes = new ArrayList<String>();
	public String courseTypes = "scorm";
	public List<String> transcriptNumbers = new ArrayList<String>();
	public List<String> actualStatusesinUI = new ArrayList<String>();
	public List<String> expectedStatusesinUI = new ArrayList<>(Arrays.asList("Completed", "Completed"));

	@Step(value = 1, info = "SABA course creation")
	public void CreateSABACourse() throws FileNotFoundException, IOException {
		getDriver().get(prop.getProperty("sabaurl"));
		sabautil.loginToTheSABAApplication(prop.getProperty("saba_username"), prop.getProperty("saba_password"));
		timers.waitUntilDOMReady(3);
		getDriver().navigate().to(prop.getProperty("LearningURL"));
		timers.waitUntilDOMReady(5);
		sabaCourseName = sabacourseclasscreation.createCourse(SKU);
		Assert.assertNotNull("Course creation is Failed", sabaCourseName);
		System.out.println("Created Course is:" + sabaCourseName);
	}

	@Step(value = 2, info = "SABA class creation")
	public void CreateSABAClass() {

		int j = 2;
		String SABAclassName = sabacourseclasscreation.createClass(sabaCourseName, j);
		Assert.assertNotNull("Class creation is failed", SABAclassName);
		classes.add(SABAclassName);
		System.out.println("created classes are :" + classes);
		sabacourseclasscreation.userSignOutFromSABA();

	}

	@Step(value = 3, info = "run the batch job:-Get Updated Courses and Classes")
	public void badgeJobExecution1() {
		nleEntitlementUtils.runJob("Get Updated Courses and Classes");
		pauseMe(5);
	}

	@Step(value = 4, info = "SABA Courses verification in Courses table")
	public void validateSABAcoursesinCourses() throws NullPointerException {

		String encQuery1 = "nameSTARTSWITH%s";
		int expectedcount = 1;
		String encQuery2 = String.format(encQuery1, sabaCourseName);

		int actualcount = leutils.getGlideRecordCount(COURSES_TABLE, encQuery2);
		System.out.println("actual count:" + actualcount);
		leutils.updateGlideRecordValue(COURSES_TABLE, "name", sabaCourseName, "active", "true");

		Log.info("Test case failed due to: ");
		Assert.assertNotNull("Failed due to unavialability of the saba courses ", actualcount);

	}

	@Step(value = 5, info = "get LXP course to Map with SABA course")
	public void getLXP_Rustici_BrightcoveCourses() {
		String encQuery = null;

		encQuery = "source=" + courseTypes + "^active=true^badge!=NULL^nameLIKE_rustici_";

		mappedNLCourse = leutils.getRandomGlideRecordEncodedQuery(COURSES_TABLE, encQuery, "name");
		System.out.println(courseTypes + "Course:-" + mappedNLCourse);
		Assert.assertNotNull("NL course is not availble for mapping", mappedNLCourse);
	}

	@Step(value = 6, info = "Map LXP course with SABA course in mapping table")
	public void mapLXPCoursesWithSabaCourse() {
		hMap.put("new_course", mappedNLCourse);
		hMap.put("old_course", sabaCourseName);

		leutils.insertGlideRecordValue("x_snc_lxp_saba_to_nl_course_mapping", hMap);
		String encQuery = "old_course.nameSTARTSWITH" + sabaCourseName;
		int recordCount = leutils.getGlideRecordCount("x_snc_lxp_saba_to_nl_course_mapping", encQuery);
		Assert.assertNotNull("saba course and Rustici course mapping is not completed", recordCount);
	}

	@Step(value = 7, info = "Get Active NL Automation user for enroll SABA Courses")
	public void getActiveNlUsers() {
		String encodedQuery = "active=true^user_nameLIKEautomation_tester_^u_status=Email Verification Completed";

		activeNLUser = leutils.getRandomGlideRecordEncodedQuery("sys_user", encodedQuery, "user_name");
		clearDataInRelatedTablesForUser(activeNLUser);
		System.out.println("selected automation User: " + activeNLUser);
		Assert.assertNotNull("saba course and Brightcove course mapping is not completed", activeNLUser);

	}

	@Step(value = 8, info = "SABA course search and enoll")
	public void enrolSABACourse() {
		open("/lxp");
		userSignIn(activeNLUser, prop.getProperty("automationUsersPassword"));
		String courseSysId1 = surfUtils.getSingleGlideRecEncodedQuery(COURSES_TABLE, "name=" + sabaCourseName,
				"sys_id");
		open("/lxp?id=overview&sys_id=" + courseSysId1 + "&type=course");
		timers.waitUntilDOMReady(20);
		Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
		pathpageoverview.clickEnrollBtn();
		sabacourseOverview.clickProceedBtn();
		timers.waitUntilDOMReady(5);
		sabacourseOverview.clickExitBtnAndEnrolInSABA();
		userSignOut();
	}

	@Step(value = 9, info = "run the badge job:-Get Updated Enrollments")
	public void badgeJobExecution2() {
		nleEntitlementUtils.runJob("Get Updated Enrollments");
	}

	@Step(value = 10, info = "get Transcript record number for enrolled saba course")
	public void verifyTranscriptRecordForEnrolledCourse() {

		String encQuery = "course.nameSTARTSWITH" + sabaCourseName;
		transcriptId = surfUtils.glideRecordGetDisplayValue(TRANSCRIPTS_TABLE, encQuery, "number");
		System.out.println("Transcript Id is:-" + transcriptId);
		Assert.assertNotNull("Trancript Id is not created for a Enrolled SABA course", transcriptId);
	}

	@Step(value = 11, info = "SABA course search and complete in SABA system")
	public void completeSABACourse() {
		pauseMe(5);
		getDriver().get(prop.getProperty("sabaurl"));
		sabautil.loginToTheSABAApplication(prop.getProperty("saba_username"), prop.getProperty("saba_password"));
		int j = 2;
		sabacourseOverview.completecourseinSaba(sabaCourseName, j);
		pauseMe(3);
		getDriver().switchTo().defaultContent();
		sabacourseclasscreation.userSignOutFromSABA();
	}

	@Step(value = 12, info = "run the badge job:-Get Updated Enrollments")
	public void badgeJobExecutionforTrancriptupdation() {
		pauseMe(5);
		nleEntitlementUtils.runJob("Get Updated Enrollments");
	}

	@Step(value = 13, info = "SABA course status verification in Transcript table")
	public void statusVerificationInTranscript() {

		int expectedcount = 1;
		String encQuery = "student.user_nameLIKE" + activeNLUser + "^course.nameLIKE" + mappedNLCourse;
		int actualcount = leutils.getGlideRecordCount(TRANSCRIPTS_TABLE, encQuery);
		System.out.println("count:" + actualcount);
		updatedCourseName = surfUtils.glideRecordGetDisplayValue(TRANSCRIPTS_TABLE, encQuery, "course");
		Assert.assertNotNull("Trancript Id is not created for a Enrolled SABA course", actualcount);
	}

	@Step(value = 14, info = "courses status verification in My LearningPlan page")
	public void statusInMyLearningPlanPage() {

		open("/lxp");
		userSignIn(activeNLUser, prop.getProperty("automationUsersPassword"));
		String coursestatus = learningPlanPage.getCourseStatus(mappedNLCourse);
		System.out.println("Course status:" + coursestatus);

		mybadgespage.selectTab("My Badges");
		String badgeStatus = mybadgespage.getBadgeStatus(mappedNLCourse);
		System.out.println("Badge status:" + badgeStatus);
		mytranscriptspage.clickmytranscriptstab();
		boolean transcriptStatus = mytranscriptspage.validateCourseNameDisplayed(mappedNLCourse);
		System.out.println("transcript status:- " + transcriptStatus);
		actualStatusesinUI.add(coursestatus);
		actualStatusesinUI.add(badgeStatus);

		System.out.println(actualStatusesinUI);
		Assert.assertEquals("status in myLearning or My Badges is not completed:", expectedStatusesinUI,
				actualStatusesinUI);
	}

	@Step(value = 15, info = "Re-Launching Rustici course")
	public void relaunchingRusticiCourse() {

		open("/lxp");
		String courseSysId1 = surfUtils.getSingleGlideRecEncodedQuery(COURSES_TABLE, "name=" + mappedNLCourse,
				"sys_id");
		open("/lxp?id=overview&sys_id=" + courseSysId1 + "&type=course");
		timers.waitUntilDOMReady(20);
		pauseMe(4);

		pathpageoverview.clickStartBtn();
		pauseMe(4);
		Assert.assertTrue("Rust IC Course iframe is not opened", rustICCoursePage.validateCourseFrameDisplayed());
		rustICCoursePage.switchToIFrame();
		rustICCoursePage.completeAutomationRustICCourse();

	}

}