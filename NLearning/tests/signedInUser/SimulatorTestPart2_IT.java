package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.pageclass.Constants;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;

import com.snc.selenium.runner.WithUser;
import com.snc.util.SurfUiRunner;

import java.text.ParseException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class SimulatorTestPart2_IT extends NLETestBase {

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
	}

	@Step(value = 2, info = "Check if the Simulator is enrolled")
	public void VerifyEnrollment() {

		Assert.assertEquals("Enrolled", findElement(nleEntitlementUtils.enrol).getText());

	}

	@Step(value = 4, info = "Check if the Open Simulator and cancel button")
	public void VerifyOpenSimulatorCancel() {
		Assert.assertTrue(findElement(nleEntitlementUtils.openSim).isDisplayed());
		findElement((nleEntitlementUtils.openSim)).click();
		String parentWindowHandler = getDriver().getWindowHandle();
		utils.switchBrowserWindowTab();
		wait.waitForEleVisibility(nleEntitlementUtils.cancel, 15);
		pauseMe(5);
		findElement(nleEntitlementUtils.cancel).click();
		findElement(nleEntitlementUtils.ok).click();
		nleEntitlementUtils.swtichBack(parentWindowHandler);
	}

	@Step(value = 6, info = "Verify Cancel Simulator transcript result field")
	public void VerifySimulatorCancelResult() {

		Assert.assertEquals("Fail",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "result", "type=simulator^course=" + sysId
						+ "^student=" + impersonateUserSysid));

	}

	@Step(value = 8, info = "Verify Cancel Simulator transcript state field")
	public void VerifySimulatorCancelState() {

		Assert.assertEquals("300",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "state", "type=simulator^course=" + sysId
						+ "^student=" + impersonateUserSysid));

	}

	@Step(value = 10, info = "Verify Cancel Simulator active learning path instance state")
	public void VerifySimulatorCancelLPT() {
		lptSysid = leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "simulator_path", "type=simulator^course="
				+ sysId + "^student=" + nleEntitlementUtils.getSysyIDUser("sys_user", userName));
		Assert.assertTrue(nleEntitlementUtils.waitPause(lptSysid, "terminated", "instance_state"));
		Assert.assertEquals("terminated",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "instance_state", "sys_id=" + lptSysid));
	}

	@Step(value = 12, info = "Verify the Enroll button state after the Simulator is canceled by User")
	public void VerifyOpenSimulator() {

		Assert.assertTrue(findElement(nleEntitlementUtils.openSim).isDisplayed());

	}

	@Step(value = 14, info = "Verify the Re-enroll button is visible to the user")
	public void VerifyReenrollSimulator() {

		Assert.assertTrue(findElement(nleEntitlementUtils.reEnroll).isDisplayed());

	}

	@Step(value = 16, info = "Verify the Re-enroll popup")
	public void VerifyReenrollSimulatorpopup() {

		findElement(nleEntitlementUtils.reEnroll).click();

		Assert.assertTrue(findElement(nleEntitlementUtils.model).getText().contains(Constants.reenroolline_1));
		Assert.assertTrue(findElement(nleEntitlementUtils.model).getText().contains(Constants.reenroolline_2));

	}

	@Step(value = 18, info = "Verify the Re-enroll popup")
	public void VerifyReenrollSimulatorpopupbuttons() {

		Assert.assertTrue(findElement(nleEntitlementUtils.ok).isDisplayed());
		Assert.assertTrue(findElement(nleEntitlementUtils.cancl).isDisplayed());
		findElement(nleEntitlementUtils.ok).click();
		uiUtil.waitForElementInvisibility(loadSpinner, 25);

	}

	@Step(value = 20, info = "Verify the Re-enroll backend transcript result field")
	public void VerifyReenrollSimulatorBackendResult() {

		Optional<String> courseopt = Optional
				.of(leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "result", "type=simulator^course="
						+ sysId + "^student=" + impersonateUserSysid));

		Assert.assertEquals("", courseopt.orElse("None"));
	}

	@Step(value = 22, info = "Verify the Re-enroll backend transcript state field")
	public void VerifyReenrollSimulatorBackendState() {
		Assert.assertEquals("100",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "state", "type=simulator^course=" + sysId
						+ "^student=" + impersonateUserSysid));

	}

	@Step(value = 24, info = "Verify the Pause Simulator job")
	public void VerifySimulatorPause() {

		nleEntitlementUtils.pausMeJOb();
		lptSysid = leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "simulator_path", "type=simulator^course="
				+ sysId + "^student=" + impersonateUserSysid);
		Assert.assertTrue(nleEntitlementUtils.waitPause(lptSysid, "paused", "instance_state"));
	}

	@Step(value = 26, info = "Verify the Unpause button")
	public void VerifySimulatorPauseButton() {
		findElement(nleEntitlementUtils.openSim).click();
		String parentWindowHandler = getDriver().getWindowHandle();
		utils.switchBrowserWindowTab();

		wait.waitForEleVisibility(nleEntitlementUtils.cancel, 15);
		Assert.assertTrue(findElement(nleEntitlementUtils.unpause).isDisplayed());
		nleEntitlementUtils.swtichBack(parentWindowHandler);
	}

	@Step(value = 28, info = "Verify the 7 days Simulator reminder")
	public void Verify7DayReminder() throws ParseException {
		expDtae = nleEntitlementUtils.setExpirationnDate(lptSysid, 7);
		nleEntitlementUtils.runJob("SIMs Expiration 7 Day Reminder");

		Assert.assertTrue(
				nleEntitlementUtils.verifyEmailVerification(userName, Constants.remDays.replace("xyy", expDtae)));
	}

	@Step(value = 30, info = "Verify the 2 days Simulator reminder")
	public void Verify2DayReminder() throws ParseException {
		expDtae = nleEntitlementUtils.setExpirationnDate(lptSysid, 2);
		nleEntitlementUtils.runJob("SIMs Expiration 2 Day Reminder");

		Assert.assertTrue(
				nleEntitlementUtils.verifyEmailVerification(userName, Constants.remDays.replace("xyy", expDtae)));
	}

	@Step(value = 32, info = "Verify pause Simulator Notification")
	public void VerifyPauseNotification() {

		Assert.assertTrue(nleEntitlementUtils.verifyEmailVerification(userName, Constants.pauseDays));
	}

	@Step(value = 34, info = "Verify expire Simulator state")
	public void VerifyExpireSimulator() throws ParseException {

		expDtae = nleEntitlementUtils.setExpirationnDate(lptSysid, 0);
		nleEntitlementUtils.runJob("SIMs Expire learning paths");
		pauseMe(2);
		Assert.assertEquals("300",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "state", "type=simulator^course=" + sysId
						+ "^student=" + impersonateUserSysid));
	}

	@Step(value = 36, info = "Verify expire Simulator result")
	public void VerifyExpireSimulatorResult() {

		Assert.assertEquals("Fail",
				leutils.getGlideRecordEncodedQuery("x_snc_lxp_transcript", "result", "type=simulator^course=" + sysId
						+ "^student=" + impersonateUserSysid));
	}

	@Step(value = 38, info = "Verify expire Simulator LPT result")
	public void VerifyExpireSimulatorLPTResult() {

		Assert.assertEquals("failed",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "result", "sys_id=" + lptSysid));
	}

	@Step(value = 40, info = "Verify expire Simulator LPT state")
	public void VerifyExpireSimulatorLPTState() {

		Assert.assertEquals("expired",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "state", "sys_id=" + lptSysid));
	}

	@Step(value = 42, info = "Verify expire Simulator LPT insatnce state")
	public void VerifyExpireSimulatorinstanceState() {

		Assert.assertTrue(nleEntitlementUtils.waitPause(lptSysid, "terminated", "instance_state"));
		Assert.assertEquals("terminated",
				leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", "instance_state", "sys_id=" + lptSysid));
	}

}
