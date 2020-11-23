package com.snc.surf.marketing.NLearning.pageclass;

public interface Constants {

	// Input for Registration
	public String first_name = "QA_";
	public String last_name = "Automation";
	public String Email1 = "automation_tester_";
	public String Email2 = "@gmail.com";
	public String Company_value = "ServiceNow";
	public String Phone_value ="0123456789";
	public String Country_value="IN";
	public String State_value = "Telangana";
	//*******************************Scenario1***************************
	public String Job_Level_value = "C-Level";  //****comment if you want to select other than "C-Level"
	public String Job_Function_value_SCNR1 = "Chief Executive Officer";
	//*******************************Scenario2***************************
	//public String Job_Level_value = "Vice President/SVP"; ****uncomment if you want to select other than "C-Level"
	public String Job_Function_value_SCNR2 = "Accounting Shared Services";
	public String Select_Job_role_value = "Finance";
	public String Password_value = "Password@1234";
	public String Confirm_Password_value = "Password@1234";

	// Navigation URL Constants
   
	
	public String DISCOVER_URL = "/lxp#discover";
	public String LIVECLASSES_URL = "#live-classes"; 
	public String CERTIFIC_URL = "#certification";
	public String SIMUL_URL = "#certification";
	public String FEATUREDITEMS_URL = "#featured-items";
	public String LXP_HOME_URL = "/lxp";
	public String LXP_LEARNING_PLAN = "/lxp#plan";
	public String LXP_LEARNING_MYBADGES = "/lxp#badge";
	public String LXP_LEARNING_CERTIFICATION = "/lxp#certification";
	public String LXP_LEARNING_TRANSCRIPTS = "/lxp#transcript";
	public String LXP_VOUCHERS_TAB = "/lxp#vouchers";


	public String TRANSCRIPTS_TABLE = "x_snc_lxp_transcript";
	public String COURSES_TABLE = "x_snc_lxp_course";
	public String Email_table = "sys_email";
	public String CARD_TITLE = "Agile Development 2.0, Test Management 2.0";
    public String Activate_Url = "/ssooktauser?id=ssopage&token=u_token&email=u_Email";
    public String MAPPING_TABLE = "x_snc_lxp_m2m_course_path";
    public String VOUCHER_TABLE = "x_snc_lxp_voucher_code";
    public String COMPANY_TABLE = "core_company";
    public String SKILL_PROFILE_TABLE = "u_employee_bio";
   

    // Table Names Defined here
	public String PATH_TABLE = "x_snc_lxp_path";
	public String CASE_TABLE = "sn_customerservice_case";
	public String NOTES_TABLE = "x_snc_lxp_note";
	public String FEEDBACK_TABLE = "x_snc_lxp_feedback";
	public String RATINGS_TABLE = "x_snc_lxp_rating";
	public String USER_BADGES_TABLE = "x_snc_lxp_user_badge";
	public String USER_CERTIFICATION_TABLE = "x_snc_lxp_user_certification";
	public String CERTIFICATION_TABLE = "x_snc_lxp_certification";
	public String SKILL_TABLE = "cmn_skill";
	public String PROFILE_TABLE = "u_kryterion_profile";
    public String USER_TABLE = "sys_user";
    public String USER_EXAM_TABLE = "x_snc_lxp_user_exam";
    public String PROGRESS_TABLE ="x_snc_lxp_progress";
    public String EMAIL_LOG_TABLE = "sys_email";
	public String remDays = "Simulator Expiration Reminder -  Expiration Date: xyy";
	public String pauseDays ="Simulator has been paused";
	public String reenroolline_1 = "Are you sure you want to Re-enroll this simulator";
	public String reenroolline_2 = "This will clear your current simulator and all tasks will need to be performed again in a brand new instance";

	public String confmEmail ="Welcome! Please confirm your email address";
	public String welcomeMail ="Welcome to NOW Learning";

	
	public String SYS_TRIGGER ="sys_trigger_list";
	public String NL_TO_SURF_SYNC_ACC_JOB="x_snc_lxp_cert_sync_scheduled_job";
	


}
