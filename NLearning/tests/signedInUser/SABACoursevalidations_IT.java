package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.snc.glide.it.runners.Step;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.core.NavMenu;
import com.snc.selenium.core.SNCTest;
import com.snc.selenium.core.TestEnvironment;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.crm.ObjectData;
import com.snc.surf.crm.UserData;
import com.snc.surf.marketing.NLearning.pageclass.*;
import com.snc.surf.marketing.NLearning.utils.Sabautils;
import com.snc.surf.marketing.NLearning.utils.Entitlements.NLEntitlement;
import com.snc.surf.marketing.NLearning.utils.NLEUtils;

import com.snc.surf.mrkt.LPTLibrary;
import com.snc.util.Login;
import com.snc.util.ScreenShotUtil;
import com.snc.util.SurfUiRunner;

import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

import org.jfree.util.Log;
import org.junit.Assert;
import org.junit.runner.RunWith;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class SABACoursevalidations_IT extends NLETestBase {
//	private static final boolean SABA16 = false;


	public String Coursename = null;
	public String SKU = null;
	public List<String> courses = new ArrayList<String>();
	public List<String> classes = new ArrayList<String>();
	public List<String> useremail = new ArrayList<String>();
	public String email = null;

	@BeforeClass
	public void init() throws TimeoutException, InterruptedException, IOException {

	/*	getDriver().get(prop.getProperty("SurfUrl"));
		SKU= sabautil.loginSurfInstance(prop.getProperty("surfuat_Username"), prop.getProperty("surfuat_pwd"));
		System.out.println("SKU value:-"+SKU);*/
		getDriver().get(prop.getProperty("sabaurl"));
		sabautil.loginToTheSABAApplication(prop.getProperty("saba_username"), prop.getProperty("saba_password"));
	}

	@Step(value = 1, info = "SABA course creation")
	public void CreateSABACourse() {

		for (int i = 0; i < 3; i++) {
			getDriver().navigate().to(prop.getProperty("LearningURL"));
			timers.waitUntilDOMReady(5);
			Coursename = sabacourseclasscreation.createCourse(SKU);
			Assert.assertNotNull("Course creation is Failed", Coursename);
			courses.add(Coursename);
		}
		System.out.println("Created Courses are:" + courses);
	}

	@Step(value = 2, info = "SABA class creation")
	public void CreateSABAClass() {
		for (int i = 0; i < 3; i++) {
			String SABAclassname = sabacourseclasscreation.createClass(this.courses.get(i), i);
			Assert.assertNotNull("Class creation is failed", SABAclassname);
			classes.add(SABAclassname);
		}
		System.out.println("created classes are :" + classes);
		sabacourseclasscreation.userSignOutFromSABA();

	}

	@Step(value = 3, info = "run the batch job:-Get Updated Courses and Classes")
	public void badgeJobExecution1() {
		nleEntitlementUtils.runJob("Get Updated Courses and Classes");
		pauseMe(10);
	}

	@Step(value = 4, info = "SABA Courses verification in Courses table")
	public void validateSABAcoursesinCourses() {

		
		HashMap<String, String> hMap = new HashMap<>();
		for (int i = 0; i < 3; i++) {
			String encQuery1 = "nameSTARTSWITH%s";
			int expectedcount = 1;
			String encQuery2 = String.format(encQuery1, this.courses.get(i));
			int actualcount = leutils.getGlideRecordCount(COURSES_TABLE, encQuery2);
			Log.info("count:" + actualcount);
			leutils.updateGlideRecordValue(COURSES_TABLE, "name", courses.get(i), "active", "true");
			if (i == 0) {
				if (expectedcount != actualcount) {
					hMap.put("ILT Saba Course vailability", "failed");
				}

			}
			if (i == 1) {
				if (expectedcount != actualcount) {
					hMap.put("VILT Saba Course avilability", "failed");
				}

			}
			if (i == 2) {
				if (expectedcount != actualcount) {
					hMap.put("WBT Saba Course vailability", "failed");
				}

			}
		}
		Log.info("Test case failed due to: ");
		hMap.forEach((k, v) -> System.out.println("Failed due to Type of Saba Course: " + k + "Status " + v));
		Assert.assertTrue("Failed due to unavialability of one of the saba courses ", hMap.isEmpty());

	}

	@Step(value = 5, info = "Get three Active NL Automation users for enroll SABA Courses")
	public void getActiveNlUsers() {
		for (int i = 0; i < 3; i++) {
			String encodedQuery = "active=true^user_nameLIKEautomation_tester_^u_status=Email Verification Completed";
			String activenluser = leutils.getRandomGlideRecordEncodedQuery("sys_user", encodedQuery, "user_name");
			useremail.add(activenluser);
		}
		System.out.println("Selected Users are:" + useremail);
		Assert.assertFalse("Failed due to unavialability of automation users", useremail.isEmpty());

	}

	@Step(value = 6, info = "SABA course search and enoll")
	public void enrolSABACourse() {
		System.out.println("user email id's:" + useremail);
		for (int i = 0; i < 3; i++) {
			open("/lxp");
			userSignIn(useremail.get(i), "Password@1234");
			String courseSysId1 = surfUtils.getSingleGlideRecEncodedQuery(COURSES_TABLE, "name=" + courses.get(i),
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

	}

	@Step(value = 7, info = "SABA course search and complete in SABA system")
	public void completeSABACourse() {
		pauseMe(5);
		getDriver().get(prop.getProperty("sabaurl"));
		sabautil.loginToTheSABAApplication(prop.getProperty("saba_username"), prop.getProperty("saba_password"));
		for (int i = 0; i < 3; i++) {
			sabacourseOverview.completecourseinSaba(courses.get(i), i);
		}
		pauseMe(5);
	}

	@Step(value = 8, info = "run the badge job:-Get Updated Enrollments")
	public void badgeJobExecution2() {
		nleEntitlementUtils.runJob("Get Updated Enrollments");
		pauseMe(10);
	}

	@Step(value = 9, info = "SABA course status verification in Transcript table")
	public void statusVerificationInTranscript() {
		HashMap<String, String> hMapT = new HashMap<>();
		System.out.println("Hash map size:-" + hMapT.size());
		for (int i = 0; i < 3; i++) {
			String encQuery2 = "course.nameSTARTSWITH" + courses.get(i) + "^completion_date!=NULL";
			int actualcount = leutils.getGlideRecordCount(TRANSCRIPTS_TABLE, encQuery2);
			System.out.println("count:" + actualcount);
			String actualState = surfUtils.glideRecordGetDisplayValue(TRANSCRIPTS_TABLE, encQuery2, "state");
			System.out.println(courses.get(i) + " Transcript state :-" + actualState);
			String expectedState = "Completed";
			if (i == 0) {
				if (!expectedState.equals(actualState)) {
					hMapT.put("ILT Saba Course status is not completed in Transcript table", "failed");
				}

			}
			if (i == 1)
				if (!expectedState.equals(actualState)) {
					hMapT.put("VILT Saba Course status is not completed in Transcript table", "failed");
				}

			if (i == 2)
				if (!expectedState.equals(actualState)) {
					hMapT.put("WBT Saba Course status is not completed in Transcript table", "failed");
				}

		}
		hMapT.forEach((k, v) -> System.out.println("Failed due to Type of Saba Course: " + k + "Status " + v));
		System.out.println("Hash map size:-" + hMapT.size());
		Assert.assertTrue("Failed due to Transcript status is not completed for one of the saba courses ",
				hMapT.isEmpty());
	}

	@Step(value = 10, info = "courses status verification in My LearningPlan page")
	public void statusInMyLearningPlanPage() {

		for (int i = 0; i < 3; i++) {
			userSignIn(useremail.get(i), "Password@1234");
			String coursestatus = learningPlanPage.getCourseStatus(courses.get(i));
			System.out.println("Course status:" + coursestatus);
			Assert.assertEquals(courses.get(i) + "course status is not completed in My Learning Plan Page:",
					"Completed", coursestatus);
			userSignOut();
		}
	}

}
