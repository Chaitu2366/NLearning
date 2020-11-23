package com.snc.surf.marketing.NLearning.pageclass;

import java.awt.List;
import java.io.IOException;
import java.sql.Driver;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.glide.communications.RemoteGlideRecord;
import com.glide.sys.WaitUtils;
import com.snc.selenium.core.TestEnvironment;
import com.snc.selenium.framework.MessageLogger;
import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.surf.marketing.NLearning.utils.NLEUtils;
import com.snc.util.ScreenShotUtil;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class RegistrationPage extends NLEPageBase

{

	protected NLEUtils leutils = new NLEUtils();
	protected By sbmt_btn = By.cssSelector("button[type='submit']");
	protected By FName = By.name("first_name");
	protected By LName = By.name("last_name");
	protected By Email = By.name("email");
	protected By Company = By.name("company");
	protected By phone = By.name("phone");
	protected By Country = By.name("country");
	protected By State = By.name("state");
	protected By Job_Level = By.name("u_job_level");
	protected By Job_Function = By.name("u_job_function");
	protected By Job_Role = By.name("u_job_role");
	protected By paswrd = By.name("password");
	protected By confrmpaswrd = By.name("password_confirmation");
	protected By signInDrpDwn = By.cssSelector("a[ng-click*='togglePopup'][aria-label='Welcome']");
	protected By registerBtn = By.cssSelector("li[ng-click*='c.openModal']");
	protected By successinfo = By.cssSelector("div[ng-if='c.loginSuccess']");
	protected By verify_emailid = By.cssSelector("span[ng-show='c.validatingEmail']");
	protected String uname;
	protected String password;
	protected String successregistry;
	protected static String registred_Email;
	protected String fdate = leutils.getCurrentDateWithMins_NoSpace().substring(4);
	protected String edate = leutils.getCurrentDateWithMins_NoSpace();//.substring(0,
			//leutils.getCurrentDateWithMins_NoSpace()).length())- 2);

	protected By username = By.id("username");

	ScreenShotUtil takescreenshot = new ScreenShotUtil();

	public void clickRegister() {
		uiUtil.clickEle(signInDrpDwn);
		uiUtil.clickEle(registerBtn);
		timers.waitUntilDOMReady(40);
	}

	public String perform_registration_CLevel() {

		clickRegister();
		uiUtil.sendKeys(FName, first_name + fdate.substring(0, fdate.length() - 2));
		uiUtil.sendKeys(LName, last_name);
		uiUtil.sendKeys(Email, Email1 + edate + Email2);
		registred_Email = Email1 + edate + Email2;
		MessageLogger.annotate("New User Email: "+ registred_Email);
		uiUtil.sendKeys(Company, Company_value);
		uiUtil.sendKeys(phone, Phone_value);
		uiUtil.selectOptionByValue(Country, Country_value);
		uiUtil.selectOptionByVisibleText(State, State_value);
		uiUtil.selectOptionByValue(Job_Level, Job_Level_value);
		uiUtil.selectOptionByVisibleText(Job_Function, Job_Function_value_SCNR1);
		uiUtil.sendKeys(paswrd, Password_value);
		uiUtil.sendKeys(confrmpaswrd, Confirm_Password_value);
		uiUtil.clickUsingJs(sbmt_btn);
		return registred_Email;
	}

	public String perform_registration_other() {

		clickRegister();
		uiUtil.sendKeys(FName, first_name + fdate.substring(0, fdate.length() - 2));
		uiUtil.sendKeys(LName, last_name);
		uiUtil.sendKeys(Email, Email1 + edate + Email2);
		registred_Email = Email1 + edate + Email2;
		uiUtil.sendKeys(Company, Company_value);
		uiUtil.sendKeys(phone, Phone_value);
		uiUtil.selectOptionByValue(Country, Country_value);
		uiUtil.selectOptionByVisibleText(State, State_value);
		uiUtil.selectOptionByValue(Job_Level, Job_Level_value);
		uiUtil.selectOptionByValue(Job_Role, Select_Job_role_value);
		uiUtil.selectOptionByValue(Job_Function, Job_Function_value_SCNR2);
		uiUtil.sendKeys(paswrd, Password_value);
		uiUtil.sendKeys(confrmpaswrd, Confirm_Password_value);
		uiUtil.clickUsingJs(sbmt_btn);
		return registred_Email;
	}

	public ArrayList<String> get_userRegistration() {
		ArrayList<String> alist = new ArrayList<String>();
		if (Job_Level_value.equalsIgnoreCase("C-Level")) {
			alist.add(perform_registration_CLevel());
			successregistry = uiUtil.getText(successinfo);
			alist.add(successregistry);

		}

		else {

			alist.add(perform_registration_other());
			successregistry = uiUtil.getText(successinfo);
			alist.add(successregistry);
		}
		return alist;
	}

	

	

	public boolean verifyConfirmedEmail(String email) {

		int i = 0;
		while (i < 6) {
			RemoteGlideRecord rgr = getGlideRecordForTable("sys_user");
			rgr.addEncodedQuery("user_name=" + email + "^u_status=Email Verification Completed");

			rgr.query();
			rgr.next();

			if ((rgr.getRowCount() < 1)) {
				pauseMe(4);
				i++;
				System.out.println(i);
			} else
				return true;

		}
		return false;

	}

	public String getToken(String email) {
		String token = "";

		RemoteGlideRecord rgr = getGlideRecordForTable("sys_email");
		rgr.addEncodedQuery("recipients=" + email + "^subject=Welcome! Please confirm your email address");
		rgr.query();
		rgr.next();
		token = rgr.getValue("body").substring(rgr.getValue("body").indexOf("token="),
				rgr.getValue("body").indexOf("&email="));
		token = token.split("=")[1];
		return token;

	}

	public boolean waitForSignInAfterRegisteration() {
		boolean pageDisplay = false;
		try {
			wait.waitAndObtainWebElement(username, 30);
			pageDisplay = true;
		}
		catch (Exception e){
			pageDisplay = false;
		}
		return pageDisplay;
	}
	public  String getRegistred_Email() {
		return registred_Email;
	}

	public static void setRegistred_Email(String registred_Email) {
		RegistrationPage.registred_Email = registred_Email;
	}

}