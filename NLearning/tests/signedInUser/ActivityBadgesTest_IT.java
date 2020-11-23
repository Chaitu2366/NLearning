package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.glide.util.Log;
import com.snc.glide.it.GlideNode;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.core.TestEnvironment;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.HashMap;


@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class ActivityBadgesTest_IT extends NLETestBase {

    HashMap<String,String> hMap = new HashMap<>();
    String badgeEncQuery = "type=activity";
    String badgeName, metricUsed;
    String ruleDefEncQuery = "name=%s^badge.name=%s";

    public String impersonateUser = null;


   @Step(value =0, info ="Signed In User - Obtain existing NL User")
    public void obtainExistingNLUser() {
        impersonateUser = this.getActiveNlUser();
        Log.info("impersonateUser" + impersonateUser);
    }

    @Step(value =1, info ="Signed In User - create a new rule under Rule Definition table -> Busy Week")
    public void createBusyRuleDefinition() {
        userSignIn(userName, password);
       // leutils.impersonateWithUserName(impersonateUser);  // will be enabled
        badgeName = leutils.getRandomGlideRecordEncodedQuery("x_snc_lxp_badge",badgeEncQuery,"name");   // Create a activity badge when object is null
        metricUsed = leutils.getSysIDOfRecord("x_snc_lxp_metric_definition","name=Busy Week","sys_id");
        Assert.assertNotNull("Metric used obj is null",metricUsed);

        open("/x_snc_lxp_rule_definition.do?sys_id=-1");
        ruleDefinitionPage.createRuleDefinition("Busy week","1",badgeName,metricUsed,"Busy Week");
        ruleDefEncQuery = String.format(ruleDefEncQuery,"Busy week",badgeName);
        String sysId= leutils.getSysIDOfRecord("x_snc_lxp_rule_definition",ruleDefEncQuery,"sys_id");
        Assert.assertNotNull("Rule definition is not created",sysId);
    }


    @Step(value = 2, info ="Signed In User - for a given user, create entries for one week in Login tracker table")
    public void createLoginTrackerEntriesForGivenUser() {
        ruleDefinitionPage.createEntriesInLoginTracker(userName,"yyyy-MM-dd");
    }

    @Step(value = 3, info ="Signed In User - for a given user, create entry for Busy Week: invoke job")
    public void executeMetricsCalculatorJob_BusyWeek() {
        String sys_ID = leutils.getSysIDOfRecord("sys_user","user_name="+userName,"sys_id");
        leutils.executeActivityJobForUser("name=Last Login",sys_ID);
    }

    @Step(value = 4, info ="Signed In User -  Validate Badges obtained under LXP -> My Badges Section for given user -> Busy week")
    public void validateBadgeDisplayedForBusyWeek() {
        open(LXP_LEARNING_MYBADGES);
        pathpageoverview.closeBadgeDialog();  // at times super badge pops up
        String badgeStatus = mybadgespage.getBadgeStatus(badgeName);  // This is for path
        Assert.assertEquals("Badge is not earned after Busy Week Rule","Completed",badgeStatus);
    }

    @Step(value = 5, info ="Signed In User - create a new rule under Rule Definition table -> Last Login")
    public void createLastLoginRuleDefinition() {
        userSignIn(userName, password);
        // leutils.impersonateWithUserName(impersonateUser);  // will be enabled
        badgeName = leutils.getRandomGlideRecordEncodedQuery("x_snc_lxp_badge",badgeEncQuery,"name");
        metricUsed = leutils.getSysIDOfRecord("x_snc_lxp_metric_definition","name=Last Login","sys_id");
        Assert.assertNotNull("Metric used obj is null",metricUsed);

        open("/x_snc_lxp_rule_definition.do?sys_id=-1");
        ruleDefinitionPage.createRuleDefinition("Last Login","1",badgeName,metricUsed,"Last Login");
        ruleDefEncQuery = String.format(ruleDefEncQuery,"Last Login",badgeName);
        String sysId= leutils.getSysIDOfRecord("x_snc_lxp_rule_definition",ruleDefEncQuery,"sys_id");
        Assert.assertNotNull("Rule definition is not created",sysId);
    }

    @Step(value = 6, info ="Signed In User - for a given user, create entry for last login-> Yesterday in Login tracker table")
    public void createLoginTrackerEntryForLastLogin() {
        String reqDate = leutils.getGMTDateAfterBeforeCurrent("yyyy-MM-dd",0);
        ruleDefinitionPage.createEntryforSpecificUser(userName, reqDate);
    }

     @Step(value = 7, info ="Signed In User - for a given user, create entry for last login: invoke job")
    public void executeMetricsCalculatorJob() {
        String sys_ID = leutils.getSysIDOfRecord("sys_user","user_name="+userName,"sys_id");
        leutils.executeActivityJobForUser("name=Last Login",sys_ID);
    }

    @Step(value = 8, info ="Signed In User -  Validate Badges obtained under LXP -> My Badges Section for given user -> Last Login")
    public void validateBadgeDisplayedForLastLogin() {
        open(LXP_LEARNING_MYBADGES);
        pathpageoverview.closeBadgeDialog();  // at times super badge pops up
        String badgeStatus = mybadgespage.getBadgeStatus(badgeName);  // This is for path
        Assert.assertEquals("Badge is not earned after Last Login Rule","Completed",badgeStatus);
    }
}
