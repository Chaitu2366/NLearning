package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.pageclass.Constants;
import com.glide.communications.RemoteGlideRecord;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;

import com.snc.selenium.runner.WithUser;
import com.snc.util.SurfUiRunner;


import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class SimulatorTestPart1_IT extends NLETestBase {

	String sysId,lptSysid,impersonateUser,expDtae,impersonateUserSysid,impersonateUserEmail = null;
	

	@Step(value = 0, info = "Signed In User - enroll a Simulator")
	public void enrollSimulator() {
		
		
		userSignIn(userName, password);
		impersonateUser = this.getActiveNlUser();
		open("/navpage.do");
		leutils.impersonateWithUserName(impersonateUser);
		impersonateUserSysid = nleEntitlementUtils.getSysyIDUser("sys_user", impersonateUser);
		impersonateUserEmail = leutils.getGlideRecordEncodedQuery("sys_user", "email",
				"sys_id=" + impersonateUserSysid);

		sysId = leutils.getGlideRecordEncodedQuery("x_snc_lxp_course", "sys_id",
				"simulator!=NULL^course_type=Simulator^active=true^category="
						+ nleEntitlementUtils.fetchCategory("Simulator"));
	
		nleEntitlementUtils.courseUrl = String.format(nleEntitlementUtils.courseUrl, sysId);
		open(nleEntitlementUtils.courseUrl);
		Assert.assertTrue("Enroll Button is not displayed", pathpageoverview.isEnrollBtnDisplayed());
		pathpageoverview.clickEnrollBtn();
		uiUtil.waitForElementInvisibility(loadSpinner, 25);
		Assert.assertEquals("Enrolled", findElement(nleEntitlementUtils.enrol).getText());
	}

	@Step(value = 2, info = "Verify failed Simulator Scenario Lpt state")
	public void VerifyFailedLPTState() {
		lptSysid = leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "simulator_path", "type=simulator^course="
				+ sysId + "^student=" +impersonateUserSysid);
		nleEntitlementUtils.updateStateLPT(lptSysid, "completed", "x_snc_sims_lpt", "state");
		Assert.assertEquals("completed",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "state", "sys_id=" + lptSysid));

	}

	@Step(value = 4, info = "Verify failed Simulator Scenario Lpt result")
	public void VerifyFailedLPTResult() {
		Assert.assertTrue(nleEntitlementUtils.waitPause(lptSysid, "failed", "result"));

	}

	@Step(value = 6, info = "Verify failed Simulator Scenario Lpt instance")
	public void VerifyFailedLPTInstance() {

		Assert.assertTrue(nleEntitlementUtils.waitPause(lptSysid, "terminated", "instance_state"));
		Assert.assertEquals("terminated",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "instance_state", "sys_id=" + lptSysid));

	}

	@Step(value = 8, info = "Verify failed Simulator transcript state field")
	public void VerifySimulatorTranscriptlState() {

		Assert.assertEquals("300",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "state", "type=simulator^course=" + sysId
						+ "^student=" + impersonateUserSysid));

	}

	@Step(value = 10, info = "Verify failed Simulator transcript result field")
	public void VerifySimulatorTranscriptlResult() {

		Assert.assertEquals("Fail",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "result", "type=simulator^course=" + sysId
						+ "^student=" + impersonateUserSysid));

	}

	@Step(value = 12, info = "Verify failed Simulator transcript progress field")
	public void VerifySimulatorTranscriptlProgress() {

		Assert.assertEquals("0",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "percent_complete", "type=simulator^course="
						+ sysId + "^student=" + impersonateUserSysid));

	}

	@Step(value = 14, info = "Verify reEnroll button is visible ")
	public void VerifySimulatorReEnroll() {
		getDriver().navigate().refresh();
		uiUtil.waitForEleVisibility(nleEntitlementUtils.openSim, 25);
		Assert.assertTrue(findElement(nleEntitlementUtils.reEnroll).isDisplayed());
		findElement(nleEntitlementUtils.reEnroll).click();
		findElement(nleEntitlementUtils.ok).click();
		uiUtil.waitForElementInvisibility(loadSpinner, 25);
	}

	@Step(value = 16, info = "Verify complete learning tasks")
	public void VerifyLearningTasks() {
		lptSysid = leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "simulator_path", "type=simulator^course="
				+ sysId + "^student=" + impersonateUserSysid);
		nleEntitlementUtils.updateStateLPT(lptSysid, "success", "x_snc_sims_lptask", "result");

	}

	@Step(value = 18, info = "Verify state of learning tasks")
	public void VerifyLPTProgress() {

		Assert.assertEquals("100",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "completed", "sys_id=" + lptSysid));

	}

	@Step(value = 20, info = "Verify result of learning tasks")
	public void VerifyLPTPassed() {
		Assert.assertEquals("100",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "passed", "sys_id=" + lptSysid));

	}

	@Step(value = 22, info = "Verify complete Simulator job")
	public void VerifyLPTjob() {
		nleEntitlementUtils.runJob("SIMs Complete 100% passed learning paths");
		Assert.assertEquals("100",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "passed", "sys_id=" + lptSysid));

	}

	@Step(value = 24, info = "Verify complete Simulator LPT state")
	public void VerifyLPTstate() {
		
		Assert.assertEquals("completed",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "state", "sys_id=" + lptSysid));

	}

	@Step(value = 26, info = "Verify complete Simulator LPT result")
	public void VerifyLPTResult() {
		
		Assert.assertEquals("passed",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "result", "sys_id=" + lptSysid));

	}

	@Step(value = 28, info = "Verify complete Simulator LPT instance")
	public void VerifyLPTInstance() {
		Assert.assertTrue(nleEntitlementUtils.waitPause(lptSysid, "terminated", "instance_state"));
		Assert.assertEquals("terminated",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "instance_state", "sys_id=" + lptSysid));

	}

	@Step(value = 30, info = "Verify complete Simulator Transcript state")
	public void VerifyTranscriptState() {

		Assert.assertEquals("200",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "state", "type=simulator^course=" + sysId
						+ "^student=" + nleEntitlementUtils.getSysyIDUser("sys_user", userName)));

	}

	@Step(value = 32, info = "Verify complete Simulator Transcript result")
	public void VerifyTranscriptResult() {

		Assert.assertEquals("Pass",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "result", "type=simulator^course=" + sysId
						+ "^student=" + impersonateUserSysid));

	}

	@Step(value = 34, info = "Verify complete button on Simulator overview page")
	public void VerifyCompleteButton() {
		getDriver().navigate().refresh();
		uiUtil.waitForEleVisibility(nleEntitlementUtils.openSim, 25);
		Assert.assertEquals("Completed", findElement(nleEntitlementUtils.enrol).getText());

	}

}
