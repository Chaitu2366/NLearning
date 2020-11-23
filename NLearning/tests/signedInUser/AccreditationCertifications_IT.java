package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import org.junit.runner.RunWith;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;

import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class AccreditationCertifications_IT extends NLETestBase {

	protected String pathName;
	protected String CrtName;
	protected List<String> courseNames = new ArrayList<String>();
	protected List<String> badges = new ArrayList<String>();
	protected String AccrCertname;
	protected String pathbadge;
	protected List<String> coursebadge = new ArrayList<String>();;
	protected String certbadge;
	protected String email;
	protected String sysId;
	protected List<String> emailnotification = new ArrayList<String>();
	protected String pathStatus;
	protected String JobUsrc2Srfsync = "ACCREDITATION_USER_CERT_SYNC_TO_SURF";
	protected String pName = "Automation_Accreditation_Path_Test02";
	protected String surfStatus;
	protected Object flag;

	@Step(value = 0, info = "Signed In User - Obtain existing NL User")
	public void obtainAccreditationCertifcationForNlUser() {
		email = this.getActiveNlUser();
		Log.info("impersonateUser" + email);
	}

	@Step(value = 1, info = "Signed In User - Enroll Accreditation path and complete it")
	public void enrollAccreditationPath() throws InterruptedException {

		userSignIn(userName, password);
		AccrCertname = leutils.getGlideRecordEncodedQueryDisplyvalue(PATH_TABLE, "certificate", "name=" + pName);
		sysId = nleEntitlementUtils.getSysyID(PATH_TABLE, pName);
		MessageLogger.annotate("Accreditation  path sysId is: " + sysId);
		MessageLogger.annotate("Accreditation certification for the path is: " + AccrCertname);
		leutils.impersonateWithUserName(email);
		open("/lxp?id=overview&sys_id="+sysId+"&type=path");
		timers.waitUntilDOMReady(4);
		Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
		pathName = pathpageoverview.getPathNameDisplayed();
		pathpageoverview.clickEnrollBtn();
		courseNames = pathpageoverview.getCourseNames();
		if(courseNames==null)
		{
			flag= null;
		}
		
		Assert.assertNull("Courses are not available for the accreditation path", flag);
	}
	
	
	
	 @Step(value =2, info ="Signed In User - Validate Share Functionality")
	    public void validateShareFunctionality() {			 
		 Assert.assertTrue("Share Email not working", pathpageoverview.shareViaEmail(userName));
		 
		 String obtainedExterbalUrl = pathpageoverview.clickShareExtrlLnk("LinkedIn");
		 Assert.assertTrue(obtainedExterbalUrl.contains("linkedin"));
		 obtainedExterbalUrl = pathpageoverview.clickShareExtrlLnk("Facebook");
		 Assert.assertTrue(obtainedExterbalUrl.contains("facebook")&obtainedExterbalUrl.contains("sys_id"));
		 obtainedExterbalUrl = pathpageoverview.clickShareExtrlLnk("Twitter"); 
		 Assert.assertTrue(obtainedExterbalUrl.contains("twitter")&obtainedExterbalUrl.contains("sys_id"));
	    }   
	
	@Step(value = 3, info = "Signed In User - Complete accreditation path ")
	public void completeAccrPathwithAssmt() {
		pathpageoverview.clickFirstModuleLessonAndCompleteCourseaccrdt();
		timers.waitUntilDOMReady(4);
		getBadgeDialogTextAndCloseDlg(); // badge for path
		getBadgeDialogTextAndCloseDlg(); // badge for course
		open("/lxp?id=dashboard");
		learningPlanPage.clickOnLearningTab();
		pathStatus = learningPlanPage.getPathStatus(pathName);
		MessageLogger.annotate("Accreditation certification path status is: " + pathStatus);
		Assert.assertEquals("Accreditation Delta path completed", "Completed", pathStatus);
		courseNames.stream().forEach(courseName -> Assert.assertEquals("Accreditation course badge achieved",
				"Completed", learningPlanPage.getPathStatus(courseName)));
	}

	@Step(value = 4, info = "Signed In User - My Certifications tab - Get accreditation Certification achieved ")
	public void getAccrCertification() {
		getCertificationDialogTextAndCloseDlg();
		getBadgeDialogTextAndCloseDlg();
		timers.waitUntilDOMReady(5);
		myCertificationPage.click_Certificationtab();
		timers.waitUntilDOMReady(4);
		CrtName = myCertificationPage.getCertificateStatus(AccrCertname);
		MessageLogger.annotate("Accreditation certification status is: " + CrtName);
		Assert.assertEquals("Accreditation certification achieved", "Current", CrtName);
	}

	@Step(value = 5, info = "Signed In User - My Badges tab - Get accreditation Badges achieved ")
	public void getAccrbadges() {
		mybadgespage.clickMyBadgesTab();
		timers.waitUntilDOMReady(4);
		pathbadge = mybadgespage.getBadgeStatus(pathName);
		MessageLogger.annotate("Accreditation path - badge status is: " + pathbadge);
		certbadge = mybadgespage.getBadgeStatus(AccrCertname);
		MessageLogger.annotate("Accreditation Certiifcation - badge status is: " + certbadge);
		Assert.assertEquals("Accreditation certification badge achieved", "Completed", certbadge);
		courseNames.stream().forEach(courseName -> Assert.assertEquals("Accreditation course badge achieved",
				"Completed", mybadgespage.getBadgeStatus(courseName)));
		Assert.assertEquals("Accreditation path badge achieved", "Completed", pathbadge);
	}

	@Step(value = 6, info = "Signed In User - Transcripts tab - Verify Completed accreditation certifications, paths and courses")
	public void verifyaccrcertTranscripttab() {
		mytranscriptspage.clickmytranscriptstab();
		timers.waitUntilDOMReady(6);
		Assert.assertEquals("Accreditation Certification completed status", "Current",
				mytranscriptspage.getCertificateStatus(AccrCertname));
		Assert.assertTrue("Accreditation Path is not completed", mytranscriptspage.validatePathNameDisplayed(pathName));
		courseNames.stream().forEach(courseName -> Assert.assertTrue("Accreditation course is not completed",
				mytranscriptspage.validatePathNameDisplayed(courseName)));
	}

	@Step(value = 7, info = "Signed In User - Email Notification -Verify Completed accreditation certifications")
	public void verfyaccrcertCertEmailNotification() {
		emailnotification = leutils.getMultipleFields_EncodedQuery(Email_table, "subject",
				"recipients=" + email + "^subject=Great job on finishing  " + pathName + "!");
		Assert.assertEquals("Accreditation Email notification triggered", "Great job on finishing  " + pathName + "!",
				emailnotification.get(0));
	}

	@Step(value = 8, info = "Signed In User - Sync NL to Surf -Verify Completed accreditation certifications")
	public void verfyaccrcertNLsurfSync() {
		nleEntitlementUtils.runJob(JobUsrc2Srfsync);
		timers.waitUntilDOMReady(6);
		surfStatus = leutils.getSingleField_AccEncodedQuerySurf(SKILL_TABLE, "u_certification_status",
				"u_email=" + email + "^u_skill.u_typeINAccreditation^" + "u_skill.u_skill=" + AccrCertname,
				leutils.getInstanceEndpointURLSurf());
		Assert.assertEquals("Accreditation certification Status in Surf at cmn_skills table", CrtName, surfStatus);
	}

}
