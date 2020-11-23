package com.snc.surf.marketing.NLearning.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.glide.util.Log;
import com.snc.selenium.core.SNCTest;
import com.snc.selenium.core.TestEnvironment;
import com.snc.selenium.framework.MessageLogger;
import com.snc.surf.marketing.NLearning.pageclass.*;
import com.snc.surf.marketing.NLearning.tests.nonSignedInUser.UserRegistrationIT;
import com.snc.surf.marketing.NLearning.utils.Assessments.AssessmentUtils;
import com.snc.surf.marketing.NLearning.utils.NLEUtils;
import com.snc.surf.marketing.NLearning.utils.Sabautils;
import com.snc.surf.marketing.NLearning.utils.Entitlements.NLEntitlement;
import com.snc.surf.marketing.NLearning.utils.UiUtility;
import com.snc.surf.marketing.NLearning.utils.WaitUtils;
import com.snc.util.JenkinsRunTracker;
import com.snc.util.SurfSNCTest;
import com.snc.util.SurfUtils;
import com.snc.util.UIOperations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NLETestBase extends SurfSNCTest implements Constants
{

	protected NLEUtils leutils;
	protected SurfUtils utils;
	protected Properties prop;
	protected RegistrationPage registrationpage;
	protected LandingPage landingpage;
	protected MyExperiencePage myexperiencePage;
	protected MyBadgesPage mybadgespage;
	protected CertificationPage certificationpage;
	protected MyProfilePage myprofilepage;
	protected MyProgressPage myprogresspage;
	protected CasePage casePage = null;
	protected MyTranscriptsPage mytranscriptspage;
	protected PathPageOverview pathpageoverview;
	protected CoursePageOverview coursepageoverview;
	protected CatalogPage catalogpage;
	protected UIOperations uiOperations;
	protected LiveClassesPage liveClassesPage;
	protected NLEntitlement nleEntitlementUtils;
	protected DiscoverPage discoverPage;
	protected SurfUtils surfUtils;
	protected UiUtility uiUtil;
	protected WaitUtils wait;
	protected GlobalSearchPage glbSearchPage;
	protected RustICCoursePage rustICCoursePage; 
	protected LearningPlanPage learningPlanPage;
	protected VoucherCodePage voucherPage;
	protected MyCertificationPage myCertificationPage;
	protected ExamPage examPage;
	protected MyCertificationPage  mycertificationpage;
	protected KryterionPage kryterionpage;
	protected AssessmentUtils assessmentUtils;
	protected RuleDefinitionPage ruleDefinitionPage;
	private static boolean isFirstTime = true;
	protected ArrayList<String> list;
	protected Sabautils sabautil;
	protected SABACourseclasscreation sabacourseclasscreation;
	protected SABACourseOverview sabacourseOverview;
	protected VouchersPage vouchersPage;
	//protected String userName = TestEnvironment.get().getProperty("adminUser_admin");
	//protected String pssword = TestEnvironment.get().getProperty("adminPassword_admin");
	protected By loadSpinner = By.cssSelector("div.km-loader");
	protected By username = By.id("username");
	protected String userName,password; // these variables hold creds for autmation admin userName and Password
	protected String newAutomationUser=null, newUserPassword; // these variables hold info of newly registered user/ if it fails, existing NL UserName from table is assigned to user variable

	public NLETestBase()

	{
		if(casePage == null ) {
			casePage = new CasePage();
			leutils = new NLEUtils();
			prop = new Properties();
			utils = new SurfUtils();
			registrationpage = new RegistrationPage();
			landingpage = new LandingPage();
			myexperiencePage = new MyExperiencePage();
			mybadgespage = new MyBadgesPage();
			certificationpage = new CertificationPage();
			myprofilepage = new MyProfilePage();
			myprogresspage = new MyProgressPage();
			mytranscriptspage = new MyTranscriptsPage();
			pathpageoverview = new PathPageOverview();
			coursepageoverview = new CoursePageOverview();
			catalogpage = new CatalogPage();
			uiOperations = new UIOperations();
			surfUtils = new SurfUtils();
			discoverPage = new DiscoverPage();
			liveClassesPage = new LiveClassesPage();
			learningPlanPage = new LearningPlanPage();
			uiUtil = new UiUtility();
			wait = new WaitUtils();
			glbSearchPage = new GlobalSearchPage();
			nleEntitlementUtils = new NLEntitlement();
			rustICCoursePage = new RustICCoursePage();
			voucherPage = new VoucherCodePage();
			myCertificationPage = new MyCertificationPage();
			examPage = new ExamPage();
			mycertificationpage = new MyCertificationPage();
			assessmentUtils = new AssessmentUtils();
			kryterionpage = new KryterionPage();
			ruleDefinitionPage = new RuleDefinitionPage();
			initializePropertiesFile();
			setUserCreds();
			sabautil = new Sabautils();
			sabacourseclasscreation = new SABACourseclasscreation();
			sabacourseOverview = new SABACourseOverview();
			vouchersPage = new VouchersPage();
		}
	}

	/* @BeforeClass
	// Aim: Used for fetching new registered user, if fails: should obtain active user with data under transcripts, progress, user badges tabled cleaned.
	public void setUp() {
		newAutomationUser = (String) prop.get("automation_userEmail");

		if(newAutomationUser==null || newAutomationUser.isEmpty()) {
			newAutomationUser = this.performNewUserRegistration();
			newUserPassword = (String) prop.get("automation_userPwd");
		}

		if(newAutomationUser==null) {
			newAutomationUser = getActiveNlUser();
			prop.setProperty("automation_userEmail", newAutomationUser);
			prop.setProperty("automation_userPwd", Password_value);
			newUserPassword = (String) prop.get("automation_userPwd");
		}
	} */


	// Sets automation admin username to local variables
	public void setUserCreds() {
		userName = (String) prop.get("automation_username");
		password = (String) prop.get("automation_password");
	}

	// This will be invoked for first time as a part of @BeforeClass execution ( defined in NLETestBase)
	public String performNewUserRegistration() {
		String success_info = "Thanks for signing up with Now Learning! To get started, please check your inbox to confirm your email address.";
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


	// If new UserRegistration code fails, this is called to fetch existing Active NL User, also clears data for the specified user in respective tables
	// Call this method as first step in each of the signed tests
	public String getActiveNlUser() {
		String userid = null;
		int iCounter = 0;
		String encQuery = "user_nameLIKEautomation_tester_";
		while(userid ==null && iCounter<10) {
			userid = leutils.getRandomGlideRecordEncodedQuery("sys_user", encQuery, "user_name");
			pauseMe(4);
		}
		newAutomationUser = userid;
		MessageLogger.annotate("Obtained username from NL: "+userid);
		clearDataInRelatedTablesForUser(userid);
		return userid;
	}

	// for the specified user, records are deleted under Transcripts, Progress, User Badges, User Certifications table
	public void clearDataInRelatedTablesForUser(String user) {

		String encQuery = "student.user_nameLIKE%s";
		encQuery = String.format(encQuery,user);
		//leutils.deleteGlideRecords(TRANSCRIPTS_TABLE,encQuery);
		leutils.deleteGlideRecords_Script(TestEnvironment.get().getDefaultUrl(),userName,password,TRANSCRIPTS_TABLE,encQuery);

		encQuery = new String("user.user_name=%s");
		encQuery = String.format(encQuery,user);
		//leutils.deleteGlideRecords("x_snc_lxp_progress",encQuery);
		leutils.deleteGlideRecords_Script(TestEnvironment.get().getDefaultUrl(),userName,password,"x_snc_lxp_progress",encQuery);

		encQuery = new String("user.user_name=%s");
		encQuery = String.format(encQuery,user);
		//leutils.deleteGlideRecords(USER_BADGES_TABLE,encQuery);
		leutils.deleteGlideRecords_Script(TestEnvironment.get().getDefaultUrl(),userName,password,USER_BADGES_TABLE,encQuery);

		encQuery = new String("user.user_name=%s");
		encQuery = String.format(encQuery,user);
		//leutils.deleteGlideRecords("x_snc_lxp_user_certification",encQuery);
		leutils.deleteGlideRecords_Script(TestEnvironment.get().getDefaultUrl(),userName,password,"x_snc_lxp_user_certification",encQuery);
	}

	public void initializePropertiesFile(){
		try {
			InputStream input = null;
			input = new FileInputStream("glide.it.nowLearning.properties");
			prop.load(input);
		}
		catch(Exception err)
		{
			MessageLogger.annotate("Exception Observed: "+err.getMessage());
			err.printStackTrace();
		}
	}

	/* @Override
    public void open(String url) {
        url = prop.getProperty("nowLearning.instance.url") + url;

        if (isFirstTime) {
            isFirstTime = false;
            this.getDriver().manage().timeouts().pageLoadTimeout(300L, TimeUnit.SECONDS);
            //JenkinsRunTracker.getInstance().setEnvironment(TestEnvironment.get().getDefaultUrl());
            this.addChromeDesiredCapabilities();
        }

        try {
            this.getDriver().switchTo().defaultContent();
            this.form.ignoreDirtyForm();
            getDriver().get(url);
            this.getDriver().manage().timeouts().pageLoadTimeout(300L, TimeUnit.SECONDS);
            this.getDriver().switchTo().defaultContent();
            this.timers.waitUntilDOMReady(20);
            if (!"ui_page_process.do".equals(this.processURL(this.getDriver().getCurrentUrl()))) {
                this.mainFrame();
            }

            //TestEnvironment.get().setCurrentURL("");
            this.timers.waitUntilPageLoaded(15);
        }
        catch(Exception err)
        {
            this.getDriver().manage().timeouts().implicitlyWait(300L, TimeUnit.SECONDS);
            this.logDebugMsg("Retrying Open after Timeout");
            if (!err.getMessage().contains("org.openqa.selenium.TimeoutException")) {
                throw err;
            }

            this.pauseMe(3);
            String fullUrl = prop.getProperty("nowLearning.instance.url") + url;
            this.pauseMe(3);
            this.getDriver().get(fullUrl);
            this.getDriver().manage().timeouts().pageLoadTimeout(300L, TimeUnit.SECONDS);
            this.timers.waitUntilDOMReady(30);
        }

    } */


	public void openLandingPage() {
		open(LXP_HOME_URL);
		closeModalDialog();
		discoverPage.waitForDiscoverPageToLoad();
	}


	public void openDiscoverPage() {
		open(DISCOVER_URL);
		closeModalDialog();
	}

	public void openLiveClassesPage() {
		open(LIVECLASSES_URL);
		closeModalDialog();
	}
	public void openCertificationsPage() {
		open(CERTIFIC_URL);
		closeModalDialog();
	}

	public void openSimulatorsPage() {
		open(SIMUL_URL);
		closeModalDialog();
	}

	public void openFeaturedItemsPage() {
		open(FEATUREDITEMS_URL);
		closeModalDialog();
	}

	public void userSignIn(String userName, String pssword)
	{
		if(!uiUtil.isElementVisible(username,5)) {
			closeModalDialog();
			landingpage.clickSignIn();
		}
		landingpage.performSignIn(userName,pssword);
	}

	public void closeModalDialog()
	{
		By modal = By.id("propModal");
		By closeBtn = By.cssSelector("button[ng-click*='closeModalPopUp']>span");
		if(isElementPresentBy(modal,12))
		{
			uiUtil.clickEle(closeBtn);
			pauseMe(1);
		}
	}

	public static enum FilterNames {
		ROLE("Role"),
		LEVEL("Level"),
		PRODUCT("Product"),
		OTHER_INTERESTS("Other Interests")
		;

		private final String text;

		/**
		 * @param text
		 */
		FilterNames(final String text) {
			this.text = text;
		}

		// @Override
		public String toString() {
			return text;
		}

	}

	public boolean activateUser(String user_email) {
		String token = registrationpage.getToken(user_email);
		String Activate_Url_Token = Activate_Url.replace("u_Email", user_email).replace("u_token", token);
		open(Activate_Url_Token);
		boolean test =  registrationpage.waitForSignInAfterRegisteration();
		System.out.println(test);
		if (test)
			return registrationpage.verifyConfirmedEmail(user_email);

		return false;
	}

	public void waitForLoadingSpinner()
	{
		uiUtil.waitForElementInvisibility(loadSpinner,20);
	}
	public void performExactNameSearch(String contentName) {
		openLandingPage();
		glbSearchPage.clickSearchIcon();
		glbSearchPage.performSearch(contentName);
	}

	public List<String> getExpectedSearchedResults(String keyword, String filter , int numberOfElelementToBeChecked) {
		List<String> courseNameList_expected = new ArrayList<String>();
		nleEntitlementUtils.buildGlobalResults(keyword, filter);
		courseNameList_expected = nleEntitlementUtils.getGuestContent();
		courseNameList_expected = courseNameList_expected.stream().limit(numberOfElelementToBeChecked).collect(Collectors.toList());
		return courseNameList_expected;
	}

	public List<String> getActualSearchedResults(String keyword, String filter ) {
		performExactNameSearch(keyword);
		glbSearchPage.clickOnSortInGlobalSearchResult(filter);
		return glbSearchPage.getContentName();
	}
	public void userSignOut()
	{
		landingpage.performSignOut();
	}

	public void closeCertificationModalDialog()
	{
		try {
		By closeBtn = By.xpath(".//div[@class='modal-dialog']//button");
		//findElement(closeBtn).click();
		if(isElementPresentBy(closeBtn,8))
		{
			uiUtil.clickEle(closeBtn);
			pauseMe(1);
		}
	} catch (Exception e) {
		Log.info("Dialog window error :::" + e.getMessage());
	}
	}

	public void closeBadgeModalDialog()
	{
		By closeBtn = By.xpath(".//div[@class = 'modal-dialog']//following::button[@class='close']");
		//findElement(closeBtn).click();
		if(isElementPresentBy(closeBtn,8))
		{
			uiUtil.clickEle(closeBtn);
			pauseMe(1);
		}
	}

	// This method would return the badge dialog text and also closes the dlg.
	protected String getBadgeDialogTextAndCloseDlg() {
		By closeBtn = By.cssSelector("div.modal-dialog button[aria-label='Close']");
		By dialogText = By.cssSelector("div.modal-body p");
		String badgeDlgText = null;
		if(isElementPresentBy(dialogText,8))
		{
			badgeDlgText = uiUtil.getText(dialogText);
			uiUtil.clickEle(closeBtn);
			pauseMe(1);
		}
		return badgeDlgText;
	}

	protected String getCertificationDialogTextAndCloseDlg() {
		By certificationModaldialog = By.xpath(".//div[@class='modal-dialog']");
		By closeBtn = By.xpath(".//div[@class='modal-dialog']//button");
		String text = null;
		if (isElementPresentBy(certificationModaldialog, 8)) {
			text = uiUtil.getText(certificationModaldialog);
			uiUtil.clickEle(closeBtn);
		}
		return text;
	}

	protected void refreshPage() {
		getDriver().navigate().refresh();
		timers.waitUntilDOMReady(12);
	}
	
	protected boolean validateEmailNotificationLog(String user, String subject) {
		 String encQueryEmail = "sys_created_onONToday@javascript:gs.daysAgoStart(0)@javascript:gs.daysAgoEnd(0)^recipientsSTARTSWITH%s";
		 encQueryEmail = String.format(encQueryEmail,user);
		 leutils.updateGlideRecordValue(USER_TABLE, "user", user, "active", "True");
		 List<String> emailSubject = leutils.getMultipleFieldsDisplayValues_EncodedQuery(EMAIL_LOG_TABLE,
					"subject", encQueryEmail);
		 for(int i=0; i<emailSubject.size();i++) {
			 if(subject.contains(emailSubject.get(i))) {
				 return true;
			 }
		 }return false;
		 
	
	}
	
	
		 
	

}
