package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.pageclass.Constants;
import com.snc.surf.marketing.NLearning.utils.Assessments.AssessmentUtils;
import com.glide.communications.RemoteGlideRecord;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;

import com.snc.selenium.runner.WithUser;
import com.snc.util.SurfUiRunner;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class DeltaCertificateSimulator_IT extends NLETestBase {

	String sysId, examName, transcriptNumber, badgeSysid,certSysid,certName,certExam,simulatorsys_id = null;
	String lptSysid, impersonateUserSysid, expDtae, impersonateUserEmail, pathName, impersonateUser;

	@BeforeClass
	public void init() {
		try {
			
			sysId = nleEntitlementUtils.getExamPath("micro", "x_snc_lxp_m2m_course_path","simulator");
			
			examName = nleEntitlementUtils.simulatorExam;
			pathName = leutils.getGlideRecordEncodedQuery("x_snc_lxp_path", "name", "sys_id=" + sysId);
			badgeSysid = leutils.getGlideRecordEncodedQuery("x_snc_lxp_path", "badge", "sys_id=" + sysId);
			simulatorsys_id=nleEntitlementUtils.simulatorsysid;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step(value = 0, info = "Signed In User - enroll a Micro-Certificate Path")
	public void enrollMicroPath() {
		userSignIn(userName, password);
		impersonateUser = this.getActiveNlUser();
		open("/navpage.do");
		leutils.impersonateWithUserName(impersonateUser);
		impersonateUserSysid = nleEntitlementUtils.getSysyIDUser("sys_user", impersonateUser);
		impersonateUserEmail = leutils.getGlideRecordEncodedQuery("sys_user", "email",
				"sys_id=" + impersonateUserSysid);
		nleEntitlementUtils.pageUrl = String.format(nleEntitlementUtils.pageUrl, sysId);
		open(nleEntitlementUtils.pageUrl);
		Assert.assertTrue("Enroll Button is not displayed", pathpageoverview.isEnrollBtnDisplayed());
		pathpageoverview.clickEnrollBtn();
		uiUtil.waitForElementInvisibility(loadSpinner, 25);
		Assert.assertEquals("Enrolled", findElement(nleEntitlementUtils.enrol).getText());
	}

	@Step(value = 2, info = "Verify Micro-certificate with Assigned state")
	public void VerifyEnrolledState() {
		
	
		Assert.assertEquals("100", leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "state",
				"type=path^path=" + sysId + "^student=" + impersonateUserSysid));

	}

	@Step(value = 4, info = "Verify Exam is passed")
	public void VerifyExamPassed() {

		lptSysid = leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "simulator_path", "type=simulator^course="
				+ simulatorsys_id + "^student=" + impersonateUserSysid);
		nleEntitlementUtils.updateStateLPT(lptSysid, "success", "x_snc_sims_lptask", "result");

	}

	@Step(value = 5, info = "Verify the User passes exam by giving all answers correclty")
	public void verifyTheAssessmentInstance() {

		nleEntitlementUtils.runJob("SIMs Complete 100% passed learning paths");
		Assert.assertEquals("100",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "passed", "sys_id=" + lptSysid));
	}

	@Step(value = 6, info = "Verify Passed Exam on path overview page")
	public void VerifyExamPassedPathOverview() {

		Assert.assertEquals("200",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "state", "type=simulator^course=" + simulatorsys_id
						+ "^student=" + impersonateUserSysid));

	}

	@Step(value = 8, info = "Verify path progress after the exam is passed based on the path's total associations")
	public void VerifyPathProgress() {
		certSysid= leutils.getGlideRecordEncodedQuery("x_snc_lxp_exam", "certification",
				"sys_id="+nleEntitlementUtils.getSysyID("x_snc_lxp_exam", examName));
		Assert.assertEquals("100", leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "percent_complete",
				"type=path^path=" + sysId + "^student=" + impersonateUserSysid));

	}

	@Step(value = 10, info = "Verify Completed on the path overview page")
	public void VerifyCompletedPath() {
		getDriver().navigate().refresh();
		uiUtil.waitForEleVisibility(nleEntitlementUtils.enrol, 25);
		Assert.assertEquals("Completed", findElement(nleEntitlementUtils.enrol).getText());

	}

	@Step(value = 12, info = "Verify path completion notification is generated")
	public void VerifyPathNotification() {
		certName= nleEntitlementUtils.getName("x_snc_lxp_certification", certSysid);
		System.out.println(nleEntitlementUtils.contentCompletion.replace("contenttype", pathName));
		Assert.assertTrue("Path Completion email is not generated", nleEntitlementUtils.verifyEmailVerification(
				impersonateUserEmail,  pathName));

	}

	@Step(value = 13, info = "Verify badge completion notification is generated")
	public void VerifyBadgeNotification() {
		System.out.println(nleEntitlementUtils.badgeCompletion
				.replace("badgetype", nleEntitlementUtils.getName("x_snc_lxp_badge", badgeSysid)));
		Assert.assertTrue("Badge Completion email is not generated",
				nleEntitlementUtils.verifyEmailVerification(impersonateUserEmail,  nleEntitlementUtils.getName("x_snc_lxp_badge", badgeSysid)));

	}

	@Step(value = 14, info = "Verify badge is generated for the user")
	public void VerifyUserBadge() {
		nleEntitlementUtils.getCount("x_snc_lxp_user_badge",
				"user=" + impersonateUserSysid + "^path=" + sysId + "^badge=" + badgeSysid);
		Assert.assertEquals(1, nleEntitlementUtils.contentCount);
		
	}

	@Step(value = 16, info = "Verify Miro-Certificate Popup")
	public void VerifyMicroCertificatePopup() {
		open(Constants.LXP_LEARNING_PLAN);
		
		uiUtil.waitForElementInvisibility(loadSpinner, 25);
		String certText = getCertificationDialogTextAndCloseDlg();
		Assert.assertNotNull("User Certification dialog not displayed",certText);
	}

	@Step(value = 18, info = "Verify Miro-Certificate is generated for the user ")
	public void VerifyMicroCertificate() {
		nleEntitlementUtils.getCount("x_snc_lxp_user_certification",
				"user=" + impersonateUserSysid + "^certification="+certSysid);
		Assert.assertEquals(1, nleEntitlementUtils.contentCount);
		
		
	}

	@Step(value = 20, info = "Verify Miro-Certificate badge notification generated for the user")
	public void VerifyMicroCertificateNotifcation() {
		String microBadgeSysid = leutils.getGlideRecordEncodedQuery("x_snc_lxp_certification", "badge","sys_id="+certSysid);
		System.out.println(nleEntitlementUtils.badgeCompletion
				.replace("badgetype", nleEntitlementUtils.getName("x_snc_lxp_badge", microBadgeSysid)));
		Assert.assertTrue("Badge Completion email is not generated",
				nleEntitlementUtils.verifyEmailVerification(impersonateUserEmail, nleEntitlementUtils.getName("x_snc_lxp_badge", microBadgeSysid)));
	}

	@Step(value = 22, info = "Verify Miro-Certificate on certification tab")
	public void VerifyMicroCertificateUI() {
		open(Constants.LXP_LEARNING_CERTIFICATION);
		String certificateStatus = mycertificationpage.getCertificateStatus(certName);
		Assert.assertEquals("Micro Certificate is generated", "Current", certificateStatus);

	}

	@Step(value = 24, info = "Verify Miro-Certificate on transcript tab")
	public void VerifyMicroCertificateUITranscripts() {
		open(Constants.LXP_LEARNING_TRANSCRIPTS);
		String certificateStatus = mytranscriptspage.getCertificateStatus(certName);
		Assert.assertEquals("Micro Certificate is generated", "Current", certificateStatus);
	}

}
