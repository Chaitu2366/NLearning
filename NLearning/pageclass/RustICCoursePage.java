package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.selenium.framework.MessageLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RustICCoursePage extends PathPageOverview {

    protected By iFrame = By.cssSelector("iframe.scorm-iframe");
    protected By nextLesson = By.cssSelector("a.next-lesson__link");
    protected By startCourseBtn = By.cssSelector("a.overview__button");
    protected By lastSection = By.xpath("(.//div[@class='page__content']/section/div)[last()]");
    protected By lessonContent = By.xpath(".//div[@class='lesson__content']//div[@class='page__content']/section/div");
    protected By exitCourse = By.xpath("(.//div[@id='innerApp']//button[contains(@class,'courseExit')])[1]");
    protected By startQuiz = By.cssSelector("button.quiz-header__start-quiz");
    protected By lessonTitle = By.cssSelector("div.lesson-header__title");
    protected By resumeVideoBtn = By.cssSelector("div#overlay-mobile div[aria-label='Resume']");
    protected By continueBtn = By.cssSelector("div.block-continue");
    protected By menuLinks = By.cssSelector("div.panel-content li >a");
    protected By exitVideoCourse = By.cssSelector("div#propModal button.close");

    public boolean validateCourseFrameDisplayed() {
        return uiUtil.isElementVisible(iFrame,25);
    }

    public void switchToIFrame() {
        uiUtil.switchToIFrame(iFrame);
    }


    // Next Lesson link is only visible until we scroll down to the last page of a lesson
    public void completeCourse()
    {
        uiUtil.waitForEleVisibility(exitCourse,15);
        uiUtil.scrollToDownByPixel();
        pauseMe(2);
        uiUtil.scrollToBottomPage();
        while (lessonTitleDisplayed()) {
            MessageLogger.annotate("Entered while loop");
            //uiUtil.moveToElement(getDriver().findElement(nextLesson));
            uiUtil.waitForElementClickable(nextLesson,7);
            pauseMe(2);
            uiUtil.clickUsingJs(nextLesson);
            //uiUtil.clickElement_Actions(getDriver().findElement(nextLesson));
            pauseMe(4);
            uiUtil.moveToElement(getDriver().findElement(lastSection));
        }
    }

    public void scrollToEachPageInLesson()
    {
        //findElements(lessonContent).stream().forEach(element -> uiUtil.moveToElement(element));
        uiUtil.isElementVisible(lessonContent,4);
        uiUtil.scrollToDownByPixel();
        uiUtil.scrollToBottomPage();
        List<WebElement> listEle = findElements(lessonContent);
        for(WebElement ele : listEle) {
            uiUtil.moveToElement(ele);
            pauseMe(3);
        }
    }

    public boolean isNextLessonBtnDisplayed()
    {
        return uiUtil.isElementVisible(nextLesson,7);
    }

    public boolean lessonTitleDisplayed()
    {
        return uiUtil.isElementVisible(lessonTitle,7);
    }

    public boolean continueBtnDisplayed()
    {
        return uiUtil.isElementVisible(continueBtn,5);
    }

    public void clickStartCourseBtn()
    {
        if(uiUtil.isElementVisible(startCourseBtn,4)) {
            uiUtil.moveToElement(getDriver().findElement(startCourseBtn));
            uiUtil.clickUsingJs(startCourseBtn);
        }
    }

    public void clickResumeVideoBtn()
    {
        if(uiUtil.isElementVisible(resumeVideoBtn,4)) {
            uiUtil.moveToElement(getDriver().findElement(resumeVideoBtn));
            uiUtil.clickUsingJs(resumeVideoBtn);
        }
       // uiUtil.clickUsingJs(resumeVideoBtn);
        uiUtil.clickElement_Actions(getDriver().findElement(resumeVideoBtn));
    }

    public void clickStartVideoBtn()
    {
        if(uiUtil.isElementVisible(resumeVideoBtn,4)) {
            uiUtil.moveToElement(getDriver().findElement(resumeVideoBtn));
            uiUtil.clickUsingJs(resumeVideoBtn);
        }
    }

    public void completeAutomationRustICCourse()
    {
        uiUtil.waitForEleVisibility(exitCourse,15);
        uiUtil.scrollToDownByPixel();
        pauseMe(2);
        uiUtil.scrollToBottomPage();
        while (lessonTitleDisplayed() && continueBtnDisplayed()) {
            MessageLogger.annotate("Entered while loop");
            //uiUtil.moveToElement(getDriver().findElement(nextLesson));
            uiUtil.waitForElementClickable(continueBtn,7);
            pauseMe(2);
            uiUtil.moveToElement(getDriver().findElement(continueBtn));
            uiUtil.clickUsingJs(continueBtn);
            //uiUtil.clickElement_Actions(getDriver().findElement(nextLesson));
            pauseMe(4);
        }
    }

    public void exitCourse() {
        uiUtil.moveToElement(getDriver().findElement(exitCourse));
        uiUtil.clickUsingJs(exitCourse);
        pauseMe(3);
    }

    // clicks on each menu link for rustic video course and waits for a moment for video to start
    public void executeRusticVideoCourse() {
        uiUtil.waitForEleVisibility(menuLinks,15);
        List<WebElement> listLinks = findElements(menuLinks);
        for(WebElement ele : listLinks) {
            uiUtil.clickUsingJs(ele);
            pauseMe(5);
        }
    }

    public void exitVideoCourse() {
        uiUtil.switchToParent();
        uiUtil.waitForElementClickable(exitVideoCourse,7);
        //uiUtil.moveToElement(getDriver().findElement(exitVideoCourse));
        uiUtil.clickUsingJs(exitVideoCourse);
        pauseMe(4);
    }


}
