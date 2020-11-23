package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

import javax.swing.text.html.Option;
import java.util.Optional;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class BrightCoveCourse_IT extends NLETestBase {
    String notesEntered = "test notes 1";
    String feedbackEntered = "Feedback Provided";
    String ratingsComment = "Ratings Provided";
    String encQuery = "user.user_nameLIKE%s^sys_updated_on>=javascript:gs.beginningOfLast15Minutes()";
    String courseEncQuery = "source=Brightcove^active=true";
    String courseSysID;

    public String impersonateUser = null;

    @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainActiveNLUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);
        Optional<String> opt = Optional.ofNullable(leutils.getSysIDOfRecord(COURSES_TABLE,courseEncQuery,"sys_id"));
        courseSysID = opt.get() == null ? leutils.getSysIDOfRecord(COURSES_TABLE,courseEncQuery,"sys_id") : opt.get();
    }

    @Step(value =1, info ="Signed In User - Enroll Brightcove course and complete it")
    public void openBrightCoveCourse() {
        userSignIn(userName,password);
        open("/nav_to.do");
        leutils.impersonateWithUserName(impersonateUser);

        open("/lxp?id=overview&type=course&sys_id="+courseSysID);
        Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
        pathpageoverview.clickEnrollBtn();
        pathpageoverview.clickFirstModuleLessonAndCompleteBrightCoveCourse();
        String btntext = pathpageoverview.getTextEnrolBtn();
        Assert.assertEquals("NL In-House Course is not completed yet","Completed",btntext);
    }

    @Step(value =2, info ="Signed In User - Submit Notes")
    public void enterNotesAndSave() {
        open("/lxp?id=overview&type=course&sys_id="+courseSysID);
        Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
        pathpageoverview.clickNotes();
        pathpageoverview.enterNotes(notesEntered);
        pathpageoverview.saveNotes();
    }


    @Step(value =3, info ="Signed In User - Submit Notes, Validate in Notes table")
    public void ValidateNotesInTable() {
        String notesEncQuery = "user.user_name="+impersonateUser;
        String notesContent = leutils.getGlideRecordEncodedQuery(NOTES_TABLE,"notes",notesEncQuery);
        Assert.assertTrue("Notes is not saved",notesContent.contains(notesEntered));
    }


    @Step(value = 4, info ="Signed In User - Provide Feedback")
    public void provideFeedback() {
        open("/lxp?id=overview&type=course&sys_id="+courseSysID);
        Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
        pathpageoverview.clickFeedbackIcon();
        pathpageoverview.enterFeedback("Course","Experience",feedbackEntered);
    }

    @Step(value = 5, info ="Signed In User - Validate Feedback")
    public void ValidateFeedback() {
        String feedbackEncQuery = "user.user_name="+impersonateUser;
        String notesContent = leutils.getGlideRecordEncodedQuery(FEEDBACK_TABLE,"text",feedbackEncQuery);
        Assert.assertTrue("Feedback entered is not saved",notesContent.contains(feedbackEntered));

    }

    @Step(value = 6, info ="Signed In User - Assign Ratings")
    public void assignRatings() {
        open("/lxp?id=overview&type=course&sys_id="+courseSysID);
        Assert.assertTrue("Enroll Btn is not displayed", pathpageoverview.isEnrollBtnDisplayed());
        pathpageoverview.assignRating(4,ratingsComment);

        int ratingsAssigned = pathpageoverview.getRatingsAssigned();
        MessageLogger.annotate("Ratings Assigned: "+ratingsAssigned);
        Assert.assertEquals("Assigned Ratings is not displayed",4,ratingsAssigned);
    }


    @Step(value =7, info ="Signed In User - Validate Ratings Assigned")
    public void ValidateRatingsAssigned() {
        String userSysID = leutils.getSysIDOfRecord("sys_user","user_name="+impersonateUser,"sys_id");
        String notesContent = leutils.getGlideRecordEncodedQuery(RATINGS_TABLE,"comment","user="+userSysID);
        String ratingVal = leutils.getGlideRecordEncodedQuery(RATINGS_TABLE,"rating","user="+userSysID);
        MessageLogger.annotate("Ratings Value: "+ratingVal);
        Assert.assertTrue("Ratings entered is not saved",notesContent.contains(ratingsComment));
    }


}
