package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class InHouseCourse_IT extends NLETestBase {

    List<WebElement> listElements;
    CardDetails cardData;
    List<String> pathDetails;
    Map<String, List<String>> listModuleDetails;
    String notesEntered = "test notes 1";
    String feedbackEntered = "Feedback Provided";
    String ratingsComment = "Ratings Provided";
    String courseEncQuery = "source=lxp^category=d4aeea7bdbb8374069a56a5b8a9619c1^active=true";
    public String impersonateUser = null,courseSysID;

    @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainNlUserAndCourseDetails() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);
        List<String> courseSysIds = leutils.getSysIdSForGivenEncQuery(COURSES_TABLE,courseEncQuery);
        courseSysID = leutils.fetchCourseIdBasedOnModulesAvailable("x_snc_lxp_m2m_modules_courses",courseSysIds);
    }

    @Step(value =1, info ="Non Signed In User - Validate Course details Page")
    public void openCourseDetailsPage() {
        userSignIn(userName,password);
        open("/nav_to.do");
        leutils.impersonateWithUserName(impersonateUser);
      //  open("/lxp?id=overview&type=course&sys_id=9ea7a4df1b41bb00948aed7b2f4bcb37");
        open("/lxp?id=overview&type=course&sys_id="+courseSysID);
        Assert.assertTrue("Enroll Btn is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        String pathName = pathpageoverview.getPathNameDisplayed();
        // Assert.assertTrue("Path name is not displayed as expected",pathName.contains(Constants.CARD_TITLE));
        pathDetails = pathpageoverview.fetchPathDetails();
        Assert.assertNotNull("Path details are not displayed under header for specific card search",pathDetails);
        pathpageoverview.clickEnrollBtn();
        pathpageoverview.clickFirstModuleLessonAndCompleteCourse(assessmentUtils);
        pathpageoverview.closeBadgeDialog();
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
    
	@Step(value =8, info ="Signed In User - Validate Share Functionality for course")
	public void validateShareFunctionalityCourse() {			 
		 Assert.assertTrue("Share Email not working", pathpageoverview.shareViaEmail(impersonateUser));
		 
		 String obtainedExterbalUrl = pathpageoverview.clickShareExtrlLnk("LinkedIn");
		 Assert.assertTrue(obtainedExterbalUrl.contains("linkedin"));
		 obtainedExterbalUrl = pathpageoverview.clickShareExtrlLnk("Facebook");
		 Assert.assertTrue(obtainedExterbalUrl.contains("facebook")&obtainedExterbalUrl.contains("sys_id"));
		 obtainedExterbalUrl = pathpageoverview.clickShareExtrlLnk("Twitter"); 
		 Assert.assertTrue(obtainedExterbalUrl.contains("twitter")&obtainedExterbalUrl.contains("sys_id"));
	    }   


}
