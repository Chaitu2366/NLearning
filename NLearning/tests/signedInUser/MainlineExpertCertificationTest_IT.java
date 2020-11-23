package com.snc.surf.marketing.NLearning.tests.signedInUser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")

public class MainlineExpertCertificationTest_IT extends NLETestBase {

	public String expertEncQuery = "type=expert^active=true";
	public String mainlineEncQuery = "type=mainline^active=true";
	public String mainlineCertificationName = null;
	public String mainlineCertificationNumber = null;
	public String expertCertificationName = null;
	public String experCertificationNumber = null;
	public String impersonateUser = null;

	@Step(value = 0, info = "Signed In User - Obtain existing NL User")
	public void obtainMainlineCertificationForNlUser() {
		kryterionpage.addKryterionSkillProfile();
		impersonateUser = this.getActiveNlUser();
		Log.info("impersonateUser" + impersonateUser);
	}

	@Step(value = 2, info = "Signed in User - Get Mainline Certificate name")
	public void getMainlineCertificate() {
		String[] reqFields = { "number", "name" };
		List<String> reqFieldValues = leutils.getMultipleFields_EncodedQuery(CERTIFICATION_TABLE, reqFields,
				mainlineEncQuery);
		Assert.assertNotNull("Required Field Values are not obtained", reqFieldValues);
		mainlineCertificationNumber = reqFieldValues.get(0);
		mainlineCertificationName = reqFieldValues.get(1);
	}

	@Step(value = 4, info = "Signed in User - Get Expert Certificate name")
	public void getExpertCertificate() {
		String[] reqFields = { "number", "name" };
		List<String> reqFieldValues = leutils.getMultipleFields_EncodedQuery(CERTIFICATION_TABLE, reqFields,
				expertEncQuery);
		Assert.assertNotNull("Required Field Values are not obtained", reqFieldValues);
		experCertificationNumber = reqFieldValues.get(0);
		expertCertificationName = reqFieldValues.get(1);
	}

	@Step(value = 6, info = "Kryterion Profile creation")
	public void kryterionProfile() {
		openLandingPage();
		Log.info("impersonateUser" + impersonateUser);
		kryterionpage.kryterionProfileCreation(impersonateUser);
		String testTakerID = leutils.getSingleGlideRecord(PROFILE_TABLE, "u_kryterion_login", impersonateUser,
				"u_test_taker_id", leutils.getInstanceEndpointURL());
		Assert.assertNotNull("Profile Not Created", testTakerID);
	}

	@Step(value = 8, info = "Kryterion profile job run")
	public void kryterionProfileJob() {
		openLandingPage();
		userSignIn(userName, password);
		leutils.runKryterionBatchJobs("Kryterion Profile");
		// nleEntitlementUtils.runJob("Kryterion Profile","Rest");

	}

	@Step(value = 12, info = "Add Kryterion mainline Skill")
	public void AddkryterionSkillMainline() throws ParseException {
		kryterionpage.addKryterionSkill(this.impersonateUser, this.mainlineCertificationName,
				this.mainlineCertificationNumber);

	}

	@Step(value = 14, info = "Add Kryterion expert Skill")
	public void AddkryterionSkillExpert() throws ParseException {
		kryterionpage.addKryterionSkill(this.impersonateUser, this.expertCertificationName,
				this.experCertificationNumber);
	}

	@Step(value = 16, info = "Kryterion user exam job run")
	public void kryterionUserExamJobRun() {
		// openLandingPage();

		leutils.runKryterionBatchJobs("Kryterion user exam");
		// nleEntitlementUtils.runJob("Kryterion user exam","rest");

	}

	@Step(value = 18, info = "Validation of Mainline Certification Modal dialog")
	public void certificationModalDialog() {
		// userSignIn(userName, password);
		open("/navpage.do");
		leutils.impersonateWithUserName(this.impersonateUser);
		String certText = getCertificationDialogTextAndCloseDlg(); // we could also add assert equals based on cert
		// name..
		Assert.assertNotNull("User Certification dialog not displayed", certText);

	}

	@Step(value = 20, info = "Validation of Expert Certification Modal dialog")
	public void expertCertificationModalDialog() {
		getDriver().navigate().refresh();
		// leutils.impersonateWithUserName(this.impersonateUser);
		if (isElementPresentBy(pathpageoverview.certificationModaldialog, 12)) {  // there is a metod in tetsbase to get certifc text
			String certificateNameDisplayed = findElement(pathpageoverview.certificationModaldialog).getText();
			if (certificateNameDisplayed.contains(this.expertCertificationName)) {
				Assert.assertTrue(true);   // Add message when it fails
				closeCertificationModalDialog();
			} else {
				Assert.assertTrue(false);   // why do u want to explicitly fail in else
			}

		}
	}

	@Step(value = 22, info = "Validation of Mainline Badge Modal dialog")
	public void badgeModalDialog() {
		String text = getBadgeDialogTextAndCloseDlg();
		Assert.assertNotNull("Badge dialog is not displayed", text);
	}

	@Step(value = 24, info = "Validation of Expert Badge Modal dialog")
	public void expertBadgeModalDialog() {

		if (isElementPresentBy(pathpageoverview.badgeModaldialog, 12)) {
			String badgeNameDisplayed = findElement(pathpageoverview.badgeModaldialog).getText();

			if (badgeNameDisplayed.contains(this.expertCertificationName)) {
				Assert.assertTrue(true);   /// 
				closeBadgeModalDialog();
			}
		}
	}

	@Step(value = 26, info = "Validation of User Dashboard for Mainline")
	public void userDashboardValidationMainline() {

		open(LXP_LEARNING_MYBADGES);
		String badgeStatus = mybadgespage.getBadgeStatus(this.mainlineCertificationName);
		Assert.assertEquals("Badge Status is not Updated", "Completed", badgeStatus);
		open(LXP_LEARNING_CERTIFICATION);
		String certificateStatus = mycertificationpage.getCertificateStatus(this.mainlineCertificationName);
		Assert.assertEquals("Badge Status is not Updated", "Current", certificateStatus);
		open(LXP_LEARNING_TRANSCRIPTS);
		String transcript = mytranscriptspage.getCertificateStatus(this.mainlineCertificationName);
		Assert.assertEquals("Badge Status is not Updated", "Current", transcript);
	}

	@Step(value = 30, info = "Validation of User Dashboard for Expert")
	public void userDashboardValidationExpert() {

		open(LXP_LEARNING_MYBADGES);
		String badgeStatus = mybadgespage.getBadgeStatus(this.expertCertificationName);
		Assert.assertEquals("Badge Status is not Updated", "Completed", badgeStatus);
		open(LXP_LEARNING_CERTIFICATION);
		String certificateStatus = mycertificationpage.getCertificateStatus(this.expertCertificationName);
		Assert.assertEquals("Badge Status is not Updated", "Current", certificateStatus);
		open(LXP_LEARNING_TRANSCRIPTS);
		String transcript = mytranscriptspage.getCertificateStatus(this.expertCertificationName);
		Assert.assertEquals("Badge Status is not Updated", "Current", transcript);
	}

	/*
	 * @Step(value = 10, info =
	 * "Signed In User - Pick a path assigned to Certificate and Badge") public void
	 * pickPath() {
	 * 
	 * String pathPicked = leutils.getGlideRecordEncodedQuery(PATH_TABLE, "name",
	 * encQuery); Assert.assertNotNull("path is not obtained for specific record",
	 * pathPicked);
	 * 
	 * }
	 */

	/*
	 * @Step(value = 24, info = "Kryterion user expert job run") public void
	 * kryterionUserExpertJobRun() {
	 * 
	 * nleEntitlementUtils.runJob("Expert User Certification Pull", "Rest");
	 * 
	 * }
	 */

}