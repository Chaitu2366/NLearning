package com.snc.surf.marketing.NLearning.pageclass;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;

public class MyCertificationPage extends NLEPageBase {
	protected By tableRows = By
			.xpath(".//div[contains(@class,'my-certifications-tab')]//table//tr[contains(@class,'tab-list-cert')]");
	protected By name = By.xpath(".//td/span[contains(@class,'list-cert-name')]");
	protected By status = By.xpath("//div[@class='list-cert-status inline-block align-middle ng-binding']");

	// Almost Earned Certifications
	protected By almostEarnedTableRows = By
			.xpath(".//div[contains(@id,'almost-earned')]//table//tr[contains(@class,'almost-earned-list-item')]");
	protected By certificationType_CertificationName = By
			.xpath(".//td[contains(@class,'list-item-name')]//div[contains(@class,'list-item-name')]");
	protected By certificationStatus = By
			.xpath(".//td[contains(@class,'list-item-status')]//div[contains(@class,'list-item-state')]");
	protected By progressPercent = By
			.xpath(".//td[contains(@class,'list-item-status')]//div[contains(@class,'list-item-progress')]");

	// Available Certifications
	protected By availableCertifications = By.xpath(
			".//div[contains(@sn-atf-area,'Available Certifications')]//div[@class='text-2xl font-bold ng-binding ng-scope']");
	protected By certification_link = By.cssSelector("li[heading='My Certifications']>a");

	public String getCertificateStatus(String certificationName) {
		List<WebElement> listEle = findElements(tableRows);

		WebElement statusValue = listEle.stream()
				.filter(ele -> ele.getText().trim().contains(certificationName)).findAny()
				.orElse(null);

		return statusValue != null ? statusValue.findElement(status).getText().trim() : null;
	}

	public boolean validateCertificationNameDisplayed(String certificationName) {
		List<WebElement> listEle = findElements(almostEarnedTableRows);
		for (WebElement ele : listEle) {
			if (ele.getText().trim().contains(certificationName)) {
				return true;
			}
		}
		return false;
	} 

	public String getAlmostEarnedCertificationStatus(String certificationName) {
		String statusValue = null;
		List<WebElement> listEle = findElements(almostEarnedTableRows);
		for (WebElement ele : listEle) {
			if (ele.getText().trim().contains(certificationName)) {
				statusValue = ele.getText().trim();
				break;
			}
		}
		return statusValue;
	}

	public String getAlmostEarnedcertificationProgress(String certificationName) {
		String certificationProgress = null;
		List<WebElement> listEle = findElements(almostEarnedTableRows);
		for (WebElement ele : listEle) {
			if (ele.getText().trim().contains(certificationName)) {
				certificationProgress = ele.getText().trim();
				break;
			}
		}
		return certificationProgress;
	}

	public void click_Certificationtab() {
		uiUtil.clickUsingJs(certification_link);
	}

}
