package com.snc.surf.marketing.NLearning.tests.nonSignedInUser;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;

import com.glide.communications.RemoteGlideRecord;
import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.pageclass.Constants;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class UserRegistrationIT extends NLETestBase implements Constants{
	protected String success_info = "Thanks for signing up with Now Learning! To get started, please check your inbox to confirm your email address.";
	protected ArrayList<String> list;
	protected String userEmail;

	@Step(value = 0, info = "Perform user registration and fetching registered email address with successfull message")
	public void userRegistration() {
		openLandingPage();
		list = registrationpage.get_userRegistration();
		Assert.assertTrue("Registration is not done successfully", list.get(1).equalsIgnoreCase(success_info));
		MessageLogger.annotate("New User Created: "+list.get(1));
	}

	@Step(value = 1, info = "Verify the Welcome email generated for the user")
	public void userRegistrationEmailVerification() {

		Assert.assertTrue("Registration Welcome email is not generated",
				nleEntitlementUtils.verifyEmailVerification(list.get(0),Constants.confmEmail));
	}

	@Step(value = 2, info = "Verify User Registration is confirmed")
	public void userRegistrationIsApproved() {
		Assert.assertTrue("User Registration is not confirmed", activateUser(list.get(0)));
		newAutomationUser = list.get(0);
		MessageLogger.annotate("New UserName: "+newAutomationUser);
		prop.setProperty("automation_userEmail",newAutomationUser);
		prop.setProperty("automation_userPwd",Password_value);
        MessageLogger.annotate((String) prop.get("automation_userEmail"));
	}
	
	@Step(value=5, info ="Login with newly register User") 
	public void newUserLogIn() {
		openLandingPage();
		userSignIn(newAutomationUser, Password_value);
		Assert.assertTrue(myprofilepage.isMyProfilePageDisplayed());
	}

	@Step(value=6, info ="Login notification check") 
	public void verifyWelcomeMail() {
		Assert.assertTrue("Welcome email is not generated",
				nleEntitlementUtils.verifyEmailVerification(list.get(0),Constants.welcomeMail));
	}


	// This will be invoked for first time as a part of @BeforeClass execution ( defined in NLETestBase)
	public String performNewUserRegistration() {
		try {
			//leutils.deleteEmailsLogUntilLastMonth(userName,password);    // Lets expose this when we are good , ideally for jenkins
			openLandingPage();
			list = registrationpage.get_userRegistration();
			Assert.assertTrue("Registration is not done successfully", list.get(1).equalsIgnoreCase(success_info));
			MessageLogger.annotate("New User Created: " + list.get(0));
			nleEntitlementUtils.verifyEmailVerification(list.get(0),Constants.confmEmail);

			activateUser(list.get(0));
			newAutomationUser = list.get(0);
			MessageLogger.annotate("New UserName Activated: " + newAutomationUser);
			prop.setProperty("automation_userEmail", newAutomationUser);
			prop.setProperty("automation_userPwd", Password_value);
			MessageLogger.annotate((String) prop.get("automation_userEmail"));
			return newAutomationUser;
		}
		catch(Exception err) {
			MessageLogger.annotate("New User registration failed, need to fetch existing user");
		}
		return null;
	}
	
}

