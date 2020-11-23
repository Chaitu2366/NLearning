package com.snc.surf.marketing.NLearning.tests.nonSignedInUser;

import com.glide.util.Log;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.objectData.CardDetails;
import com.snc.surf.marketing.NLearning.pageclass.Constants;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class CourseDetailsTest_IT extends NLETestBase {

    List<String> pathDetails;
    Map<String, List<String>> listModuleDetails;
    String courseSysID;
    String courseEncQuery = "active=true^source=lxp^categoryISNOTEMPTY";
    //String courseEncQuery="active=true^source=lxp^category!=719ee27bdbb8374069a56a5b8a961997^ORcategory=NULL^category!=d4aeea7bdbb8374069a56a5b8a9619c1^ORcategory=NULL";

    @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainNlUserAndCourseDetails() {
        List<String> courseSysIds = leutils.getSysIdSForGivenEncQuery(COURSES_TABLE,courseEncQuery);// encQueryQuiz);//
        courseSysID = leutils.fetchCourseIdBasedOnModulesandassessmentsAvailable("x_snc_lxp_m2m_modules_courses",courseSysIds);        
    }  

    @Step(value =2, info ="Non Signed In User - Validate Course details Page")
    public void openCourseDetailsPage() {
        open("/lxp?id=overview&type=course&sys_id="+courseSysID);
        Assert.assertTrue("Enroll Btn is not displayed",pathpageoverview.isEnrollBtnDisplayed());
        String pathName = pathpageoverview.getPathNameDisplayed();
        pathDetails = pathpageoverview.fetchPathDetails();
        Assert.assertNotNull("Path details are not displayed under header for specific card search",pathDetails);

    }

    @Step(value =3, info ="Non Signed In User - Validate warning message when clicked on Enroll button")
    public void validateWarningMessageForEnrollBtn() {

        pathpageoverview.clickEnrollBtn("");
        Assert.assertTrue("Warning message is not displayed when clicked on enroll button",pathpageoverview.isWarningAlertDisplayed());
        pathpageoverview.closeWarningAlert();
        pathpageoverview.expandCourse();
        pathpageoverview.clickModule(1);
        pathpageoverview.clickLesson(1);
        Assert.assertTrue("Warning message is not displayed when clicked on Module Lesson button",pathpageoverview.isWarningAlertDisplayed());
        pathpageoverview.closeWarningAlert();
        pathpageoverview.demoQuizBtn();
        Assert.assertTrue("Warning message is not displayed when clicked on Demo Quiz button",pathpageoverview.isWarningAlertDisplayed());
        pathpageoverview.closeWarningAlert();
        pathpageoverview.clickLessonRadioBtn(1,1);
        Assert.assertTrue("Warning message is not displayed when clicked on Demo Quiz button",pathpageoverview.isWarningAlertDisplayed());
        pathpageoverview.closeWarningAlert();
    }


    @Step(value =4, info ="Non Signed In User - validate course details published on the overview page")
    public void validateCourseModuleDetails() {
        listModuleDetails = pathpageoverview.getModulesAndCorrespondingLessons();
        Assert.assertNotNull("Module and corresponding lessons details are not listed for specific course",listModuleDetails);

        // Just to print values for debug
        listModuleDetails.entrySet().forEach(entry -> {
            System.out.println(entry.getKey()+" "+entry.getValue());
        });
    }
}