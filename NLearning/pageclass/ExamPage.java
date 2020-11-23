package com.snc.surf.marketing.NLearning.pageclass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ExamPage extends PathPageOverview {

    protected By examQuestions = By.cssSelector("div[id*='category-questions'] div[ng-repeat*='id in page.questions']");
    protected By radioButtonsSpan = By.cssSelector("label.check-container span.checkmark");
    protected By submitBtn_Exam = By.cssSelector("div.lxp-survey-form button[type='submit']");

    protected By modalDialogNextBtn = By.cssSelector("div.modal-dialog div.modal-footer  div[ng-if*='vm.passed']");
    protected By closeModalDialog = By.cssSelector("div.modal-dialog  button[ng-click*='vm.dismiss']");


    public void clickRadioBtn_Exam(int question, int radioBtn)
    {
        uiUtil.waitForEleVisibility(examQuestions,15);
        List<WebElement> listEle = findElements(examQuestions);
        List<WebElement> listLessonLinks = listEle.get(question-1).findElements(radioButtonsSpan);
        listLessonLinks.get(radioBtn-1).click();
    }

    public void clickRadioBtns_Exam(int question, List<Integer> radioBtnValues)
    {
        uiUtil.waitForEleVisibility(examQuestions,15);
        List<WebElement> listEle = findElements(examQuestions);
        List<WebElement> listLessonLinks = listEle.get(question-1).findElements(radioButtonsSpan);
        for(Integer iVal : radioBtnValues) {
            listLessonLinks.get(iVal-1).click();
            pauseMe(2);
        }
    }

    public void clickSubmitBtn_Exam()
    {
        if(uiUtil.isElementVisible(submitBtn_Exam,5)) {
            uiUtil.moveToElement(getDriver().findElement(submitBtn_Exam));
            uiUtil.clickUsingJs(submitBtn_Exam);
        }
        waitForKmSpinnerToDisappear();
    }

    public boolean waitForAssessmentCompletionDialog() {
        return uiUtil.isElementVisible(modalDialogNextBtn,20);
    }

    public void clickNextModalDialog() {
        uiUtil.clickUsingJs(modalDialogNextBtn);
        pauseMe(3);
    }
}
