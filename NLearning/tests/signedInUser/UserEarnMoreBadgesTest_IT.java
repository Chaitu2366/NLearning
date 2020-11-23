package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class UserEarnMoreBadgesTest_IT extends NLETestBase {


    private String encQuery = "badge!=NULL^sourceINlxp";
    String courseUrl = "/lxp?id=overview&sys_id=%s&type=course";
    String sysId,impersonateUser;



    @Step(value =0, info ="Signed In User - add existing course to Learning Plan and Validate")
    public void addExistingCourseToLearningPlan() {
        userSignIn(userName,password);// replace with auto username
        impersonateUser = this.getActiveNlUser();
        open("/nav_to.do");
        leutils.impersonateWithUserName(impersonateUser);

        open(LXP_LEARNING_MYBADGES);
        pathpageoverview.closeBadgeDialog();
        Assert.assertTrue("Rows are not displayed under table",mybadgespage.validateRowsDisplayedUnderEarnMoreBadges());

        String cName = mybadgespage.getCourseName();
        mybadgespage.selectFirstCourse();
        mybadgespage.addToMyPlan();
        mybadgespage.validateSuccessMessage();

        mybadgespage.selectTab("My Learning Plan");
        Assert.assertTrue("Course Name isn't displayed",mybadgespage.validateCourseAddedToLeaningPlan(cName));
    }

    @Step(value =1, info ="Signed In User - Open existing course which has a badge assigned and partial execute it")
    public void deleteTheCourseFromLearningPlan() {
        open(LXP_LEARNING_MYBADGES);
        pathpageoverview.closeBadgeDialog();

        mybadgespage.selectTab("My Learning Plan");
        Assert.assertTrue("Rows are not displayed under table",mybadgespage.validateRowsDisplayedUnderEarnMoreBadges());
        String cName = mybadgespage.getCourseName();
        mybadgespage.selectFirstCourse();
        mybadgespage.deleteFromMyPlan();
        mybadgespage.validateSuccessMessage();
        mybadgespage.selectTab("All");
        mybadgespage.clickLoadMore_EarnMoreBadges();
        Assert.assertTrue("Course Name isn't displayed",mybadgespage.validateCourseAddedToLeaningPlan(cName));

    }



}
