package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import com.snc.util.SurfDates;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBadgesPage extends LandingPage {

	protected By tableRows = By
			.xpath(".//div[contains(@class,'my-badges-tab')]//table//tr[contains(@class,'tab-list-item')]");

	protected By name = By.xpath(".//td[1]/span[contains(@class,'item-name')]");
	protected By status = By.xpath(".//td[contains(@class,'list-item-status')]//div/div/div");
	protected By type = By.xpath(".//td[3]");

	// Almost Earned Badges locators
	protected By almostEarnedTableRows = By
			.xpath(".//div[contains(@id,'almost-earned')]//table//tr[contains(@class,'almost-earned-list-item')]");
	protected By badgeType_CourseName = By
			.xpath(".//td[contains(@class,'list-item-name')]//div[contains(@class,'list-item-name')]");
	protected By badgeStatus = By
			.xpath(".//td[contains(@class,'list-item-status')]//div[contains(@class,'list-item-state')]");
	protected By progressPercent = By
			.xpath(".//td[contains(@class,'list-item-status')]//div[contains(@class,'list-item-progress')]");

	// Earn More Badges
	protected By earnMoreBadgesTableRows = By
			.xpath(".//div[contains(@sn-atf-area,'You can earn more badges')]//table//tr");
	protected By courseName = By.xpath(".//p[1]");
	protected By categoryType = By.xpath(".//p[2]");
	protected By checkBox = By.xpath(".//input[contains(@id,'checkbox')]");
	protected By addToPlan = By.xpath(".//span[contains(text(),'Add to my plan')]/parent::a");
	protected By deletePlan = By.xpath(".//span[contains(text(),'Delete from my plan')]/parent::a");
	protected String tabs = ".//li[contains(text(),'%s')]";

	protected By successMsg = By.cssSelector("div#lxp-alert-container>div[id*='notification-alert']");

	// Validate Badge Status

	protected By myBadgestab = By.cssSelector("li[heading='My Badges']");
	protected By loadMoreMyBadges = By
			.xpath("(.//div[@sn-atf-area='LXP Dashboard Tab']//a[contains(text(),'Load More')])[1]");

	public String getBadgeStatus(String courseName) {
		clickLoadMore_MyBadges();
		List<WebElement> listEle = findElements(tableRows);
		WebElement statusValue = listEle.stream().filter(ele -> ele.findElement(name).getText().trim().contains(courseName)).findAny()
				.orElse(null);

		return statusValue != null ? statusValue.getText().trim() : null;
	}

	public boolean validateBadgeStatusForCourses_Path(ArrayList<String> courses) {

		for (String s1 : courses) {
			String encQuery = new String("name=%s");
			encQuery = String.format(encQuery, s1);
			String badgeValue = leUtils.getGlideRecordEncodedQuery(COURSES_TABLE, "badge", encQuery);
			if (badgeValue != null && !badgeValue.isEmpty()) {
				String status = getBadgeStatus(s1);
				if (!status.contains("Completed")) {
					return false;
				}
			}
		}
		return true;
	}

	// Almost Earned Badges related methods
	public boolean validateCourseNameDisplayed(String courseName) {
		List<WebElement> listEle = findElements(almostEarnedTableRows);
		for (WebElement ele : listEle) {
			if (ele.findElement(badgeType_CourseName).getText().trim().contains(courseName)) {
				return true;
			}
		}
		return false;
	}

	public String getAlmostEarnedBadgeStatus(String courseName) {
		String statusValue = null;
		List<WebElement> listEle = findElements(almostEarnedTableRows);
		for (WebElement ele : listEle) {
			if (ele.findElement(badgeType_CourseName).getText().trim().contains(courseName)) {
				statusValue = ele.getText().trim();
				break;
			}
		}
		return statusValue;
	}

	public String getAlmostEarnedBadgeProgress(String courseName) {
		String badgeProgress = null;
		List<WebElement> listEle = findElements(almostEarnedTableRows);
		for (WebElement ele : listEle) {
			if (ele.findElement(badgeType_CourseName).getText().trim().contains(courseName)) {
				badgeProgress = ele.getText().trim();
				break;
			}
		}
		return badgeProgress;
	}

	public boolean validateRowsDisplayedUnderEarnMoreBadges() {
		int size = findElements(earnMoreBadgesTableRows).size();
		if (size > 0) {
			return true;
		}
		return false;
	}

	public void selectFirstCourse() {
		findElements(earnMoreBadgesTableRows).stream().findFirst().map(ele -> ele.findElement(checkBox)).get().click();
	}

	public void addToMyPlan() {
		uiUtil.clickEle(addToPlan);
		waitForKmSpinnerToDisappear();
		pauseMe(2);
	}

	public void deleteFromMyPlan() {
		uiUtil.clickEle(deletePlan);
		waitForKmSpinnerToDisappear();
		pauseMe(2);
	}

	public String getCourseName() {
		return findElements(earnMoreBadgesTableRows).stream().map(ele -> ele.findElement(courseName)).findFirst().get()
				.getText().trim();
	}

	public void validateSuccessMessage() {
		uiUtil.waitForEleVisibility(successMsg, 6);
	}

	public void selectTab(String tabName) {
		tabs = new String(".//li[contains(text(),'%s')]");
		tabs = String.format(tabs, tabName);
		By tab = By.xpath(tabs);
		uiUtil.clickUsingJs(tab);
		waitForKmSpinnerToDisappear();
	}

	public boolean validateCourseAddedToLeaningPlan(String crseName) {
		return findElements(earnMoreBadgesTableRows).stream().filter(ele -> ele.findElement(courseName).getText().trim().contains(crseName))
				.findFirst().isPresent();
	}

	public void clickLoadMore_EarnMoreBadges() {
		WebElement ele = findElement(By.xpath(".//div[contains(@sn-atf-area,'You can earn more badges')]"));
		ele.findElement(loadMoreLink).click();
		waitForKmSpinnerToDisappear();
		pauseMe(2);

	}

	public void clickLoadMore_MyBadges() {
		if (uiUtil.isElementVisible(loadMoreMyBadges, 5)) {
			WebElement ele = findElement(loadMoreMyBadges);
			uiUtil.clickUsingJs(ele);
			waitForKmSpinnerToDisappear();
			pauseMe(2);
		}

	}

	public String frontEndValidations(String certificationName, String impersonateUser) {
		open("/lxp");
		leUtils.impersonateWithUserName(impersonateUser);
		String BadgeStatus = getBadgeStatus(certificationName);
		String Result = BadgeStatus.equalsIgnoreCase("Complete") ? "pass" : "Fail";
		return Result;
	}

	public void clickMyBadgesTab() {
		if (uiUtil.isElementVisible(myBadgestab, 5)) {
			uiUtil.moveToElement(myBadgestab);
			uiUtil.clickUsingJs(myBadgestab);
		}
	}

}
