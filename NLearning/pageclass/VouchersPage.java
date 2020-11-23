package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class VouchersPage extends NLEPageBase {

    protected By voucherTableRows = By.cssSelector("div[sn-atf-area='Lxp User Vouchers'] table tr.learning-plan-row");

    protected By name = By.cssSelector("span[uib-popover-template*='textTemplate']");

    protected By voucherNumber = By.xpath(".//td[2]");
    protected By viewLink = By.xpath(".//td[2]/a");
    protected By voucherNo = By.xpath(".//div[contains(text(),'Voucher No')]/following-sibling::div");


    public boolean validateVoucherCodeDisplay(String voucherCode) {
        List<WebElement> listEle = findElements(voucherTableRows);
        boolean bValue = listEle.stream()
                .filter(ele -> ele.findElement(voucherNumber).getText().trim().contains(voucherCode)).findAny().isPresent();

       return bValue;
    }

    public boolean validateVoucherCertificNameDisplay(String certificName) {
        List<WebElement> listEle = findElements(voucherTableRows);
        boolean bValue = listEle.stream()
                .filter(ele -> ele.findElement(name).getText().trim().contains(certificName)).findAny().isPresent();

        return bValue;
    }

    public void clickViewForVoucherCode(String voucherCode) {
        List<WebElement> listEle = findElements(voucherTableRows);
        listEle.stream()
                .filter(ele -> ele.findElement(voucherNumber).getText().trim().contains(voucherCode)).collect(Collectors.toList()).get(0).findElement(viewLink).click();
    }

    public String getVoucherNoFromDialog() {
        uiUtil.waitForEleVisibility(voucherNo,12);
        return uiUtil.getText(voucherNo);
    }





}
