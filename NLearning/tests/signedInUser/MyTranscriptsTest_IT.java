package com.snc.surf.marketing.NLearning.tests.signedInUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.runner.RunWith;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")

public class MyTranscriptsTest_IT extends NLETestBase {

	// certification related variables

	String encQueryCert = "certification.name!=NULL &&!=empty^user.user_name!=empty";
	String encQueryUserStat = "certification.name!=NULL &&!=empty^user.user_name=%s";
	String encQueryUser = "user_name=%s";
	String encQueryExp = "user.user_name=%s^statusINExpired^certification.name!=NULL &&!=empty";
	String encQueryAtRisk = "user.user_name=%s^statusINAt Risk^certification.name!=NULL &&!=empty";
	String encQueryCurrent = "user.user_name=%s^statusINCurrent^certification.name!=NULL &&!=empty";
	String certificationName = null;
	String impersonateUser = null;
	String impersonateUser1 = null;
	String status = null;
	String certification = null;
	List<String> reqFieldValues = new ArrayList<>();
	List<String> curCertNameStatusValues = null;
	List<String> expCertNameStatusValues = null;
	List<String> atRiskCertNameStatusValues = null;
	List<String> transcriptUserList = null;

	// path related variables

	String encQuery = "progress=100^path!=NULL";
	String encQuery1 = "path=%s^courseISNOTEMPTY";
	ArrayList<String> listCourses;
	String courseName = null;
	String usrName = null;
	String pathName = null;

	// course related variables

	String encQueryTranscripts = "percent_complete=100^type=course^state=200^courseNSAMEAScurrent_course";
	String encQueryM2M = "course=%s";
	String encQueryTrans = "percent_complete=100^type=course^state=200^course.name=%s";

	String activeCourse = null;
	String courseUser = null;

	@Step(value = 1, info = "Get the User having all the statuses")

	public void getUserStatus() {
		reqFieldValues = mytranscriptspage.getUserStatuses();
		if (null != reqFieldValues && reqFieldValues.size() > 0) {
			int certNameStatus = reqFieldValues.size();
			impersonateUser = reqFieldValues.get(certNameStatus - 1);
			Log.info("impersonate user" + impersonateUser);
			Assert.assertNotNull("User, Certification name and its status are null", reqFieldValues);
		}
	}

	@Step(value = 2, info = "Acquire Current Certification name and status")
	public void acquireCurrentCertNameStatus() {
		encQueryCurrent = String.format(encQueryCurrent, impersonateUser);
		String curCertNameStatus[] = { "certification", "status" };
		curCertNameStatusValues = leutils.getMultipleFieldsDisplayValues_EncodedQuery(USER_CERTIFICATION_TABLE,
				curCertNameStatus, encQueryCurrent);
		Log.info("Certification meeting" + curCertNameStatusValues);
		Assert.assertNotNull(" Current Certification name and its status are null", curCertNameStatusValues);
	}

	@Step(value = 3, info = "Validate bell notification for certifications")
	public void validateCertBellNotification() {
		openLandingPage();
		userSignIn(userName, password);
		leutils.impersonateWithUserName(this.impersonateUser);
		try {
			pathpageoverview.closeBadgeDialog();
			this.closeCertificationModalDialog();
		} catch (Exception e) {
			Log.info("Dialog box close error :: " + e.getMessage());
		}
		Assert.assertTrue("Bell notification for courses for the user are not received",
				mytranscriptspage.validateSubjectBellNotification(this.impersonateUser));
		Assert.assertTrue("Load More Notification functionality is not working",mytranscriptspage.validateBellNotificationLoadMore(this.impersonateUser));
	}

	@Step(value = 4, info = "Validate Current status of the certificate")
	public void validateCurrentStatusUser() {

		open(LXP_LEARNING_TRANSCRIPTS);
		mytranscriptspage.clickStatusFilter("Current");
		mytranscriptspage.clickLoadMore_Certifications();
		String curCertName = this.curCertNameStatusValues.get(0);
		String curCertStatus = this.curCertNameStatusValues.get(1);
		Assert.assertEquals("Current Certification name for current status doesn't match" + curCertName,
				mytranscriptspage.getCertificateStatus(curCertName), curCertStatus);
	}

	@Step(value = 5, info = "Acquire At Risk Certification name and Status")
	public void acquireAtRiskCertNameStatus() {
		encQueryAtRisk = String.format(encQueryAtRisk, impersonateUser);

		String atRiskCertNameStatus[] = { "certification", "status" };
		atRiskCertNameStatusValues = leutils.getMultipleFieldsDisplayValues_EncodedQuery(USER_CERTIFICATION_TABLE,
				atRiskCertNameStatus, encQueryAtRisk);
		Log.info("Certification meeting" + atRiskCertNameStatusValues);
		Assert.assertNotNull("At Risk Certification name and its status are null", atRiskCertNameStatusValues);
	}

	@Step(value = 6, info = "Validate At Risk status of the Certificate")
	public void validateAtRiskStatus() {
		mytranscriptspage.clickStatusFilter("At Risk");
		mytranscriptspage.clickLoadMore_Certifications();
		String atRiskCertName = this.atRiskCertNameStatusValues.get(0);
		String atRiskCertStatus = this.atRiskCertNameStatusValues.get(1);
		Assert.assertEquals("At Risk Certification name for status doesn't match" + atRiskCertName,
				mytranscriptspage.getCertificateStatus(atRiskCertName), atRiskCertStatus);
	}

	@Step(value = 7, info = "Acquire Expired Certification name and status")
	public void acquireExpCertNameStatus() {

		encQueryExp = String.format(encQueryExp, impersonateUser);
		String expCertNameStatus[] = { "certification", "status" };
		expCertNameStatusValues = leutils.getMultipleFieldsDisplayValues_EncodedQuery(USER_CERTIFICATION_TABLE,
				expCertNameStatus, encQueryExp);
		Log.info("Certification meeting" + expCertNameStatusValues);
		Assert.assertNotNull("Expired Certification name and its status are null", expCertNameStatusValues);
	}

	@Step(value = 8, info = "validation of Expired status of the certificate")
	public void validateExpiredcertificate() {
		mytranscriptspage.clickStatusFilter("Expired");
		mytranscriptspage.clickLoadMore_Certifications();
		String expCertName = this.expCertNameStatusValues.get(0);
		String expCertStatus = this.expCertNameStatusValues.get(1);
		Assert.assertEquals("Expired Certification name and status doesn't match" + expCertName,
				mytranscriptspage.getCertificateStatus(expCertName), expCertStatus);

		userSignOut();
	}

	@Step(value = 9, info = "Acquire certification status from transcripts table")
	public void acquireCertificationStatusTranscipts() {

		transcriptUserList = leutils.getMultipleFieldsDisplayValues_EncodedQuery(TRANSCRIPTS_TABLE, "student",
				encQueryTranscripts);
		Collections.shuffle(transcriptUserList, new Random(5));
		String encQueryUserStat = String.format(this.encQueryUserStat, transcriptUserList.get(0));
		String reqFields[] = { "status", "certification" };
		List<String> reqFieldValues = leutils.getMultipleFieldsDisplayValues_EncodedQuery(USER_CERTIFICATION_TABLE,
				reqFields, encQueryUserStat);
		status = reqFieldValues.get(0);
		certification = reqFieldValues.get(1);
		Assert.assertNotNull("Transcripts Cert name is null", certification);
		Assert.assertNotNull("Transcripts status is null", status);
	}

	@Step(value = 10, info = "Validation of Certification Status")
	public void validateCertificationStatus() {

		userSignIn(userName, password);
		leutils.impersonateWithUserName(this.transcriptUserList.get(0));
		open(LXP_LEARNING_TRANSCRIPTS);

		pathpageoverview.closeBadgeDialog();
		this.closeCertificationModalDialog();

		mytranscriptspage.clickLoadMore_Certifications();
		String frontendStatus = mytranscriptspage.getCertificateStatus(this.certification);

		Assert.assertEquals("Validation of certification status against backend failed", this.status, frontendStatus);
		userSignOut();
	}

	@Step(value = 11, info = "Validation of Delta Certification")
	public void validateDeltaCert() {
		List<String> deltaUserCert = mytranscriptspage.acquireDeltaCert();
		String iUser = deltaUserCert.get(0);
		String userCert = deltaUserCert.get(1);
		String userDeltaExam = deltaUserCert.get(2);
		userSignIn(userName, password);
		leutils.impersonateWithUserName(iUser);
		open(LXP_LEARNING_TRANSCRIPTS);

		pathpageoverview.closeBadgeDialog();
		this.closeCertificationModalDialog();

		mytranscriptspage.clickLoadMore_Certifications();
		Assert.assertEquals("Certification name for current status doesn't match" + userCert,
				mytranscriptspage.getCertificateStatus(userCert), "At Risk");
		Assert.assertEquals("Certification name for current status doesn't match" + userDeltaExam,
				mytranscriptspage.getCertificateStatus(userDeltaExam), "Current");
		userSignOut();

	}

	@Step(value = 12, info = "Acquire Path Status from Backend")
	public void acquirePathBackendStatus() {
		String[] reqFields = { "course", "path", "user" };
		List<String> reqFieldValues = leutils.getMultipleFieldsDisplayValues_EncodedQuery(PROGRESS_TABLE, reqFields,
				encQuery);
		Log.info("Fetched values:" + reqFieldValues);
		courseName = reqFieldValues.get(0);
		pathName = reqFieldValues.get(1);
		usrName = reqFieldValues.get(2);
		String sysId = leutils.getSysIDOfRecord(PATH_TABLE, "name=" + pathName, SYS_ID);
		encQuery1 = String.format(encQuery1, sysId);
		listCourses = leutils.getMultipleFieldsDisplayValues_EncodedQuery("x_snc_lxp_m2m_course_path", "course",
				encQuery1);
		Log.info("List courses :: " + listCourses);

		Assert.assertNotNull("Path contains null courses", listCourses);

	}

	@Step(value = 13, info = "Validate bell notification for paths")
	public void validatePathBellNotification() {

		userSignIn(userName, password);
		leutils.impersonateWithUserName(usrName);
		try {
			pathpageoverview.closeBadgeDialog();
			this.closeCertificationModalDialog();
		} catch (Exception e) {
			Log.info("Dialog box close error :: " + e.getMessage());
		}
		Assert.assertTrue("Bell notification for courses for the user are not received",
				mytranscriptspage.validateSubjectBellNotification(usrName));
		Assert.assertTrue("Load More Notification functionality is not working",mytranscriptspage.validateBellNotificationLoadMore(usrName));
	}

	@Step(value = 14, info = "Validation of Path Status against Backend")
	public void validatePathStatusForObtainedPathDetails() {

		open(LXP_LEARNING_TRANSCRIPTS);

		mytranscriptspage.clickLoadMore_Certifications();
		for (int i = 0; i < listCourses.size(); i++) {
			Assert.assertTrue("Courses are not displayed under MyTranscripts --> Path section",
					mytranscriptspage.validatecoursesInTranscripts(listCourses));
			if (listCourses.get(i).contains(this.courseName)) {
				boolean pathCompletion = mytranscriptspage.validatePathNameDisplayed(this.pathName);
				Assert.assertTrue("Path is not completed, not displayed under Transcripts", pathCompletion);

			}
		}

	}

	@Step(value = 15, info = "Validate if courses of the path are not populated under courses")
	public void validatePathCoursesNotinCourses() {

		for (int i = 0; i < this.listCourses.size(); i++) {
			Assert.assertFalse("Courses are not displayed under MyTranscripts --> Courses section",
					mytranscriptspage.validateCourseNameDisplayed(listCourses.get(i)));
			userSignOut();
		}

	}

	@Step(value = 16, info = "Retrieve the courses that not associated with path")
	public void retrieveCourses() {
		List<String> reqValues = mytranscriptspage.retrieveCoursesAssociatedtoPaths();
		activeCourse = reqValues.get(0);
		courseUser = reqValues.get(1);
		Assert.assertNotNull("Course and user are null", reqValues);
	}

	@Step(value = 17, info = "Validate bell notification for course")
	public void validateBellNotification() {

		userSignIn(userName, password);
		leutils.impersonateWithUserName(this.courseUser);
		pathpageoverview.closeBadgeDialog();
		this.closeCertificationModalDialog();
		Assert.assertTrue("Bell notification for courses for the user are not received",
				mytranscriptspage.validateSubjectBellNotification(this.courseUser));
		Assert.assertTrue("Load More Notification functionality is not working",mytranscriptspage.validateBellNotificationLoadMore(this.courseUser));
	}

	@Step(value = 18, info = "Validate the courses not associated with path are displayed under courses tab")
	public void validateCourses() {
		open(LXP_LEARNING_TRANSCRIPTS);
		mytranscriptspage.clickLoadMore_Courses();
		Assert.assertTrue("Course name & status check",
				mytranscriptspage.validateCourseNameDisplayed(this.activeCourse));

	}

	@Step(value = 19, info = "Validate the courses not associated with path are displayed under courses tab")
	public void validateCoursesNotunderPath() {
		mytranscriptspage.clickLoadMore_Paths();
		Assert.assertFalse("Course name & status check",
				mytranscriptspage.validatePathNameDisplayed(this.activeCourse));
		userSignOut();
	}

	@Step(value = 20, info = "Validate the right arrow link under certification tab")
	public void validateRightArrow() {

		userSignIn(userName, password);
		open(LXP_LEARNING_TRANSCRIPTS);
		pathpageoverview.closeBadgeDialog();
		this.closeCertificationModalDialog();
		Assert.assertTrue("Right Arrow functionality for Paths not working",
				mytranscriptspage.validateRightArrowPath());
		open(LXP_LEARNING_TRANSCRIPTS);
		Assert.assertTrue("Right Arrow functionality for Courses not working",
				mytranscriptspage.validateRightArrowCourse());
	}

	@Step(value = 21, info = "validate download link")
	public void validateDownloadLink() {
		open(LXP_LEARNING_TRANSCRIPTS);
		Assert.assertTrue("File name download validation", mytranscriptspage.validateDownlaodCert());
	}

	@Step(value = 22, info = "Validate View Link")
	public void validateViewLink() {
		open(LXP_LEARNING_TRANSCRIPTS);
		Assert.assertTrue("View Link is not working", mytranscriptspage.validateViewCert());
	}

	@Step(value = 23, info = "Validate List and grid Views")
	public void validateListViewWithGridView() {

		refreshPage();

		Assert.assertTrue("Descrepencies between List view and Grid View for Certifications",
				mytranscriptspage.validateCertViewGridList());
		Assert.assertTrue("Descrepencies between List view and Grid View for Paths",
				mytranscriptspage.validatePathViewGridList());
		Assert.assertTrue("Descrepencies between List view and Grid View for Courses",
				mytranscriptspage.validateCourseViewGridList());
	}

}
