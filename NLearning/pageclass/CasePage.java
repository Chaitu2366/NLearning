package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class CasePage extends NLEPageBase {

    protected By categoryDiv = By.cssSelector("#s2id_sp_formfield_category > a");
    protected By categoryInput = By.cssSelector("input#s2id_autogen1_search");
    public String categoryOptions = ".//div[contains(@id,'select2-drop')]//div[contains(text(),'%s')]";
    protected By shortDescription = By.cssSelector("input[id*='short_description']");
    protected By description = By.id("sp_formfield_description");
    protected By submitBtn = By.cssSelector("div.text-right button[name='submit']");
    protected By ticketSubmittedNumber = By.xpath(".//dt[contains(text(),'Number')]/parent::dl/dd[1]/div");
    protected By attachFile = By.cssSelector("input.sp-attachments-input");
    protected By defectNotification = By.cssSelector("div.outputmsg_info.notification");
    protected By surfDefectId = By.cssSelector("div.outputmsg_info.notification div.outputmsg_text>a");
    public String sLabelValue = ".//b[contains(text(),'%s')]/parent::label/following-sibling::div";

    public void enterSubject(String text)
    {
        uiUtil.sendKeys(shortDescription,text);
    }

    public void enterDescription(String text)
    {
        uiUtil.sendKeys(description,text);
    }

    public void clickSubmit()
    {
        uiUtil.clickUsingJs(submitBtn);
        timers.waitUntilDOMReady(30);
    }

    public void chooseCategoryOption(String sValue)
    {
        uiUtil.typeAndSelectValueFromDropdown(categoryDiv,categoryInput,sValue);
    }

    public boolean validateTicketSubmitted()
    {
        return uiUtil.isElementVisible(ticketSubmittedNumber,20);
    }

    public void addAttachment(String fileName)
    {
        uiUtil.attachFile(attachFile,fileName);
    }

    public String getCaseDetails(String sText)
    {
        String text = "";
        sLabelValue = String.format(sLabelValue,sText);
        By label = By.xpath(sLabelValue);
        switch(sText.toLowerCase())
        {
            case "category":
                text = uiUtil.getText(label);
            case "subject":
                text = uiUtil.getText(label);
            case "description":
                text = uiUtil.getText(label);
            case "contact":
                text = uiUtil.getText(label);

        }
        return text;
    }

    public String getTicketNumber()
    {
        return uiUtil.getText(ticketSubmittedNumber);
    }
    
    public void waitForCasePageLoad(){
    	wait.waitAndObtainWebElement(categoryDiv, 30);
    }

    public boolean validateNewDefectNotification() {
        return uiUtil.isElementVisible(defectNotification,12);
    }

    public String getSurfDefectId() {
        return uiUtil.getText(surfDefectId);
    }

}
