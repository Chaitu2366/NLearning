package com.snc.surf.marketing.NLearning.tests.signedInUser;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class DeltaAccreditation_IT extends NLETestBase {
	protected String impersonateUser;
	protected String sysId;
	protected String pName="Automation_Accreditation_Path_Test02";
	protected String dpathName = "Automation_Accreditation_DeltaPath_Test02";
	protected String pathName, delpathName;
	protected String accCertification;
	protected String NLCrtStatus;
	protected String accCert;
	protected String pathbadge;
	protected String pathStatus;
	protected String courseStatus;
	protected String certbadge;
	protected List<String> courseNames = new ArrayList<String>();
	protected String NLcertStatus;
	protected String SurfcertStatus;
	protected ArrayList<String> emailnotification = new ArrayList<String>();;
	protected String email;
	protected String JobUsrc2Srfsync = "ACCREDITATION_USER_CERT_SYNC_TO_SURF";
	protected String JobDltaPathActv = "Acceridation Certification Status Change";

	@Step(value = 0, info = "Signed In User - Obtain existing NL User")
	public void obtainMainlineCertiifcationForNlUser() {
		email = this.getActiveNlUser();
		Log.info("impersonateUser" + email);
	}

	@Step(value = 1, info = "Signed In User - Enroll Accreditation path and complete it")
	public void enrollAccreditationPath() throws InterruptedException {
		userSignIn(userName, password);
		accCert = leutils.getGlideRecordEncodedQueryDisplyvalue(PATH_TABLE, "certificate", "name=" + dpathName);
		sysId = nleEntitlementUtils.getSysyID(PATH_TABLE, pName);
		leutils.impersonateWithUserName(email);
		open("/lxp?id=overview&sys_id="+sysId+"&type=path");
		timers.waitUntilDOMReady(4);
		Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
		pathName = pathpageoverview.getPathNameDisplayed();
		pathpageoverview.clickEnrollBtn();
		Assert.assertTrue("Path is not Enrolled", pathpageoverview.verifypathcourseBtnStatus().contains("Enrolled"));
	}

	@Step(value = 2, info = "Signed In User - Complete accreditation path ")
	public void completeAccrPath() {
		pathpageoverview.clickFirstModuleLessonAndCompleteCourseaccrdt();
		timers.waitUntilDOMReady(4);
		getBadgeDialogTextAndCloseDlg(); // badge for path
		getBadgeDialogTextAndCloseDlg(); // badge for course
		open("/lxp?id=dashboard");
		timers.waitUntilDOMReady(4);
		getCertificationDialogTextAndCloseDlg();
		learningPlanPage.clickOnLearningTab();
		pathStatus = learningPlanPage.getPathStatus(pathName);
		Assert.assertEquals("Accreditation Delta path completed", "Completed", pathStatus);
		courseNames.stream().forEach(courseName -> Assert.assertEquals("Accreditation path course badge achieved",
				"Completed", learningPlanPage.getPathStatus(courseName)));
		myCertificationPage.click_Certificationtab();
		timers.waitUntilDOMReady(4);
		NLCrtStatus = myCertificationPage.getCertificateStatus(accCert);
		Assert.assertEquals("Accreditation certification achieved", "Current", NLCrtStatus);
		MessageLogger.annotate("Accreditation certification achieved NL: " + NLCrtStatus);
		getBadgeDialogTextAndCloseDlg();
		userSignOut();
	}

	@Step(value = 3, info = "Signed In User - Delta Path activate and certification status sync between NL and SURF")
	public void activateDeltapath() throws InterruptedException {
		landingpage.clickSignIn();
		landingpage.performSignIn(userName, password);
		open("/nav_to.do?uri=%2Fhome.do%3F");
		sysId = nleEntitlementUtils.getSysyID(PATH_TABLE, dpathName);
		accCertification = pathpageoverview.accrDeltaPathactivate(sysId);
		leutils.runBatchJob(SYS_TRIGGER, JobDltaPathActv);
		nleEntitlementUtils.runJob(JobUsrc2Srfsync);
		timers.waitUntilDOMReady(6);
		NLcertStatus = leutils.getGlideRecordEncodedQuery(USER_CERTIFICATION_TABLE, "status",
				"user.user_name=" + email + "^certification.name=" + accCertification);
		Assert.assertEquals("Accreditation certification Status in NL at User certification table", "At Risk",
				NLcertStatus);
		open("/nav_to.do?uri=%2Fhome.do%3F");
		SurfcertStatus = leutils.getSingleField_AccEncodedQuerySurf(SKILL_TABLE, "u_certification_status",
				"u_email=" + email + "^u_skill.u_typeINAccreditation^" + "u_skill.u_skill=" + accCertification,
				leutils.getInstanceEndpointURLSurf());
		MessageLogger.annotate("Accreditation certification achieved Surf: " + SurfcertStatus);
		Assert.assertEquals("Accreditation certification Status in Surf at cmn_skills table", "At Risk",
				SurfcertStatus);
	}

	@Step(value = 4, info = "Signed In User - Delta Path Completion")
	public void completeDeltapath() throws InterruptedException {
		leutils.impersonateWithUserName(email);
		String parentWindowHandler = getDriver().getWindowHandle();
		getCertificationDialogTextAndCloseDlg();
		myprogresspage.clickOnBanner();
		myprogresspage.clickOnAtRiskAccDeltaPath(accCertification, dpathName);
		utils.switchBrowserWindowTab();
		Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
		delpathName = pathpageoverview.getPathNameDisplayed();
		courseNames = pathpageoverview.getCourseNames();
		pathpageoverview.clickEnrollBtn();
		pathpageoverview.clickFirstModuleLessonAndCompleteCourseaccrdt();
		getBadgeDialogTextAndCloseDlg();
		getBadgeDialogTextAndCloseDlg();
		nleEntitlementUtils.swtichBack(parentWindowHandler);
		getCertificationDialogTextAndCloseDlg();
		getBadgeDialogTextAndCloseDlg();
		getBadgeDialogTextAndCloseDlg();
		learningPlanPage.clickOnLearningTab();
		pathStatus = learningPlanPage.getPathStatus(delpathName);
		Assert.assertEquals("Accreditation Delta path completed", "Completed", pathStatus);
		courseNames.stream().forEach(courseName -> Assert.assertEquals("Accreditation Delta path course badge achieved",
				"Completed", learningPlanPage.getPathStatus(courseName)));
	}

	@Step(value = 5, info = "Signed In User - Delta Path verification with certification and badge status at dashboard")
	public void verifyDeltapathcompletionstatus() throws InterruptedException {
		myCertificationPage.click_Certificationtab();
		timers.waitUntilDOMReady(4);
		NLCrtStatus = myCertificationPage.getCertificateStatus(accCertification);
		Assert.assertEquals("Accreditation certification achieved", "Current", NLCrtStatus);
		MessageLogger.annotate("Accreditation certification achieved at Delta NL: " + NLCrtStatus);
		mybadgespage.clickMyBadgesTab();
		timers.waitUntilDOMReady(4);
		pathbadge = mybadgespage.getBadgeStatus(delpathName);
		certbadge = mybadgespage.getBadgeStatus(accCertification);
		Assert.assertEquals("Accreditation certification badge achieved", "Completed", certbadge);
		courseNames.stream().forEach(courseName -> Assert.assertEquals("Accreditation delta path course badge achieved",
				"Completed", mybadgespage.getBadgeStatus(courseName)));
		Assert.assertEquals("Accreditation path badge achieved", "Completed", pathbadge);
		mytranscriptspage.clickmytranscriptstab();
		timers.waitUntilDOMReady(4);
		Assert.assertEquals("Accreditation Certification completed status", "Current",
				mytranscriptspage.getCertificateStatus(accCertification));
		Assert.assertTrue("Accreditation Path is not completed",
				mytranscriptspage.validatePathNameDisplayed(delpathName));
		courseNames.stream().forEach(courseName -> Assert.assertTrue("Accreditation delta path course is not completed",
				mytranscriptspage.validatePathNameDisplayed(courseName)));
	}

	@Step(value = 6, info = "Signed In User - Verification of Accreditation ceritifcation status at backend in NL and Surf")
	public void backendValidationDeltapath() throws InterruptedException {
		nleEntitlementUtils.runJob(JobUsrc2Srfsync);
		timers.waitUntilDOMReady(4);
		emailnotification = leutils.getMultipleFields_EncodedQuery(Email_table, "subject",
				"recipients=" + email + "^subject=Great job on finishing  " + delpathName + "!");
		Assert.assertEquals("Delta Path Accreditation Email notification triggered",
				"Great job on finishing  " + delpathName + "!", emailnotification.get(0));
		NLcertStatus = leutils.getGlideRecordEncodedQuery(USER_CERTIFICATION_TABLE, "status",
				"user.user_name=" + email + "^certification.name=" + accCertification);
		MessageLogger.annotate("Accreditation certification achieved at Delta NL: " + NLcertStatus);
		Assert.assertEquals("Accreditation certification status at user certification table is current", NLCrtStatus,
				NLcertStatus);
		pauseMe(4);
		SurfcertStatus = leutils.getSingleField_AccEncodedQuerySurf(SKILL_TABLE, "u_certification_status",
				"u_email=" + email + "^u_skill.u_typeINAccreditation^" + "u_skill.u_skill=" + accCertification,
				leutils.getInstanceEndpointURLSurf());
		MessageLogger.annotate("Accreditation certification achieved at Delta surf: " + SurfcertStatus);
		Assert.assertEquals("Accreditation certification Status in Surf at cmn_skills table", "Current",
				SurfcertStatus);
	}
}
