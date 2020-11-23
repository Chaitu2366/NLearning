package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfDates;

import shade.distupgrade.com.glide.util.Log;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.Assert;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KryterionPage extends NLEPageBase {

	// Validate Badge Status
	
	protected String testTakerId = null;
	protected String number = null;
	protected String skillProfileNumber = null;
	protected String sysUser = null;

	public void kryterionProfileCreation(String impersonateUser) {
		testTakerId = leUtils.getCurrentDateWithMins_NoSpace().substring(4,
				leUtils.getCurrentDateWithMins_NoSpace().length() - 2);
		number = "KRYPF0" + testTakerId;
		Map<String, String> tablevalues = new HashMap<String, String>();
		tablevalues.put("u_number", number);
		tablevalues.put("u_email", impersonateUser);
		tablevalues.put("u_kryterion_login", impersonateUser);
		tablevalues.put("u_first_name", Email1);
		tablevalues.put("u_test_taker_id", testTakerId);
		tablevalues.put("u_client_region", "ServiceNow");
		//String sysId = 
		leUtils.insertGlideRecordValue(PROFILE_TABLE, tablevalues, leUtils.getInstanceEndpointURL());
		// Assert.assertNotNull("record not created", sysId);

		String obtainedNumber = leUtils.getSingleGlideRecord(PROFILE_TABLE, "u_test_taker_id", testTakerId, "u_number",leUtils.getInstanceEndpointURL());
		// Assert.assertEquals("Kryterion profile not created", number, obtainedNumber);
		Log.info("Kryterion profile created :: "+obtainedNumber);
	}
	
	public String addKryterionSkillProfile() {
		String uniqueId = leUtils.getCurrentDate_NoSpace();
		skillProfileNumber = "SKPROF2"+uniqueId;
		Map<String, String> sysUserTableValues = new HashMap<String, String>();
		sysUserTableValues.put("title","Automation_Testing");
		sysUserTableValues.put("user_name","Automation_Tester");
		sysUserTableValues.put("company","Test");
		sysUserTableValues.put("sales_account","Automation_Testing");
		sysUserTableValues.put("country","AM-Armenia");
		sysUserTableValues.put("u_region","APAC");
		sysUserTableValues.put("active","true");
		leUtils.insertGlideRecordValue(USER_TABLE, sysUserTableValues, leUtils.getInstanceEndpointURL());
		sysUser = leUtils.getSingleGlideRecord(USER_TABLE, "title", "Automation_Testing", "user_name",leUtils.getInstanceEndpointURL());
		
		Map<String, String> companyTableValues = new HashMap<String,String>();
		companyTableValues.put("name", "Automation_Testing");
		companyTableValues.put("sales_account","Test");
		companyTableValues.put("country", "IN - India");
		companyTableValues.put("zip", "530075");
		companyTableValues.put("state", "Telangana");
		companyTableValues.put("city","Hyderabad");
		companyTableValues.put("customer", "true");
		leUtils.insertGlideRecordValue(COMPANY_TABLE, companyTableValues, leUtils.getInstanceEndpointURL());
		String company =  leUtils.getSingleGlideRecord(COMPANY_TABLE, "zip", "530075", "name",leUtils.getInstanceEndpointURL());
		
		Map<String, String> skillProfiletableValues = new HashMap<String, String>();
		skillProfiletableValues.put("u_number",skillProfileNumber);
		skillProfiletableValues.put("u_user",this.sysUser);
		skillProfiletableValues.put("u_user.active","true");
		skillProfiletableValues.put("u_user.company",company);
		skillProfiletableValues.put("u_state","Needs Review");
		leUtils.insertGlideRecordValue(SKILL_PROFILE_TABLE, skillProfiletableValues, leUtils.getInstanceEndpointURL());
		String encQuerySkillProf ="u_user.active=true^u_number=%s";
		encQuerySkillProf = String.format(encQuerySkillProf, skillProfileNumber);
		String skillProf =  leUtils.getSingleGlideRecord(SKILL_PROFILE_TABLE, "u_user", this.sysUser, "u_number",leUtils.getInstanceEndpointURL());
		if (skillProf!=null)
		{
			Log.info("Skill profile number Created is::"+this.skillProfileNumber);
			
		}return skillProfileNumber;
	}

	public void addKryterionSkill(String impersonateUser, String certificationName, String certificationNumber) {
		String currentDate = SurfDates.getCurrentDate("yyyy-MM-dd");
		String expiredDate;
		String skillProf =  leUtils.getSingleGlideRecord(SKILL_PROFILE_TABLE, "u_user", this.sysUser, "u_number",leUtils.getInstanceEndpointURL());
		
		  if (skillProf!= null) {
		  Log.info("Skill profile number Created is::"+skillProf);
		  
		  }else { skillProfileNumber = addKryterionSkillProfile(); }
		 
		try {
			expiredDate = SurfDates.getDateAfterBeforeCurrent("yyyy-MM-dd", 2);

			Map<String, String> tablevalues1 = new HashMap<String, String>();
			tablevalues1.put("u_employee_bio", "SKPROF0146231");
			tablevalues1.put("u_skill", certificationName);
			tablevalues1.put("u_type", "Certification");
			tablevalues1.put("u_certification_status", "Current");
			tablevalues1.put("u_certification_date", currentDate);
			tablevalues1.put("u_expiration_date", expiredDate);
			tablevalues1.put("u_certification_number", certificationNumber);
			tablevalues1.put("u_email", impersonateUser);
			tablevalues1.put("u_kryterion_profile", number);
			leUtils.insertGlideRecordValue(SKILL_TABLE, tablevalues1, leUtils.getInstanceEndpointURL());


			 
		} catch (ParseException e) {

			e.printStackTrace();
		}
	}
	

	public void addExpertKryterionSkill(String impersonateUser, String certificationName, String certificationNumber) {
		String currentDate = SurfDates.getCurrentDate("yyyy-MM-dd");
		String expiredDate;
		//String encQuerySP = "u_user.active=true^u_user.name=Automation_Tester^u_user.company.name=Automation_Testing";
		//int skillProfRowCount = leUtils.getGlideRecordCount(SKILL_PROFILE_TABLE, encQuerySP);
		//String skillProf =  leUtils.getSingleGlideRecord(SKILL_PROFILE_TABLE, "u_user", this.sysUser, "u_number",leUtils.getInstanceEndpointURL());
		/*
		 * if (skillProf!= null) {
		 * Log.info("Skill profile number Created is::"+skillProf);
		 * 
		 * }else { skillProfileNumber = addKryterionSkillProfile(); }
		 */
		try {
			expiredDate = SurfDates.getDateAfterBeforeCurrent("yyyy-MM-dd", 2);

			Map<String, String> tablevalues1 = new HashMap<String, String>();
			tablevalues1.put("u_employee_bio", "SKPROF0146231");
			tablevalues1.put("u_skill", certificationName);
			tablevalues1.put("u_type", "Certification");
			tablevalues1.put("u_certification_status", "Current");
			tablevalues1.put("u_certification_date", currentDate);
			tablevalues1.put("u_expiration_date", expiredDate);
			tablevalues1.put("u_certification_number", certificationNumber);
			tablevalues1.put("u_email", impersonateUser);
			//tablevalues1.put("u_kryterion_profile", number);
			leUtils.insertGlideRecordValue(SKILL_TABLE, tablevalues1, leUtils.getInstanceEndpointURL());


			 
		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

}
