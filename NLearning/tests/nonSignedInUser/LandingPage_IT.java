package com.snc.surf.marketing.NLearning.tests.nonSignedInUser;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.surf.marketing.NLearning.pageclass.LandingPage;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import java.util.List;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class LandingPage_IT extends NLETestBase {

	List<WebElement> listElements;
	CardDetails cardData;

	@Step(value = 0, info = "Non Signed In User - verify Showing All count")
	public void verifyShowingAllCount() {
		openLiveClassesPage();
		nleEntitlementUtils.getGuestUserInfo("all", "count");

		Assert.assertEquals(uiUtil.getSectionCount("Showing All"), nleEntitlementUtils.contentCount);

	}

	@Step(value = 2, info = "Non Signed In User - verify Live Classes count")
	public void verifyLiveClassesCount() {
		nleEntitlementUtils.getGuestUserInfo("live classes", "count");

		Assert.assertEquals(uiUtil.getSectionCount("Live Classes"), nleEntitlementUtils.contentCount);
	}

	@Step(value = 4, info = "Non Signed In User -verify Simulator count")
	public void verifySimulatorCount() {
		nleEntitlementUtils.getGuestUserInfo("simulator", "count");

		Assert.assertEquals(uiUtil.getSectionCount("Simulator"), nleEntitlementUtils.contentCount);
	}

	@Step(value = 6, info = "Non Signed In User - verify Featured count")
	public void verifyFeaturedCount() {
		nleEntitlementUtils.getGuestUserInfo("featured", "count");

		Assert.assertEquals(uiUtil.getSectionCount("Featured"), nleEntitlementUtils.contentCount);
	}

	@Step(value = 8, info = "Non Signed In User - verify Certification count")
	public void verifyCertificationCount() {
		nleEntitlementUtils.getGuestUserInfo("certification", "count");

		Assert.assertEquals(uiUtil.getSectionCount("Certification"), nleEntitlementUtils.contentCount);
	}

}
