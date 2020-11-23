package com.snc.surf.marketing.NLearning.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import org.junit.Assert;
import org.openqa.selenium.By;

import com.amazonaws.services.s3.model.SelectRecordsInputStream;
import com.glide.communications.RemoteGlideRecord;
import com.glide.script.GlideRecord;
import com.snc.glide.it.Script;
import com.snc.selenium.core.TestEnvironment;
import com.snc.selenium.framework.MessageLogger;

public class NLEUtils extends GlideRecordBase {
	private static final String sysId1 = null;
	protected Properties prop;
	private Object aListNew;

	public NLEUtils() {
		prop = new Properties();
		initializePropertiesFile();
	}

	public void initializePropertiesFile() {
		try {
			InputStream input = null;
			input = new FileInputStream("glide.it.nowLearning.properties");
			prop.load(input);
		} catch (Exception err) {
			MessageLogger.annotate("Exception Observed: " + err.getMessage());
			err.printStackTrace();
		}
	}

	public String getSingleGlideRecord(String TableName, String FieldName, String FldValue, String FieldValueToGet) {
		String result = null;
		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addQuery("active", true);
		testRecord.addQuery(FieldName, FldValue);
		testRecord.orderByDesc("sys_created_on");
		testRecord.query();
		testRecord.next();
		result = testRecord.getValue(FieldValueToGet);
		return result;
	}

	public String glideRecordValue(String TableName, String FieldName, String FldValue, String FieldValueToGet) {
		String result = null;
		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addQuery(FieldName, FldValue);
		testRecord.orderByDesc("sys_created_on");
		testRecord.query();
		testRecord.next();
		result = testRecord.getValue(FieldValueToGet);
		return result;
	}

	public int getGlideRecordCount(String TableName, String encodedQuery) {
		int result;
		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(encodedQuery);
		testRecord.query();
		testRecord.next();
		result = testRecord.getRowCount();
		return result;

	}

	/*public RemoteGlideRecord getRemoteGlideRecord(String tableName) {
		RemoteGlideRecord rgr = new RemoteGlideRecord(GlideNode.getDefault().getUrl(), tableName);
		// rgr.setBasicAuth(TestEnvironment.get().getProperty("adminUser"),
		// TestEnvironment.get().getProperty("adminPassword"));
		String uName = (String) prop.get("automation_username");
		String pwd = (String) prop.get("automation_password");
		rgr.setBasicAuth(uName, pwd);
		return rgr;
	}*/

	public String getGlideRecordEncodedQuery(String TableName, String FieldValueToGet, String encodedQuery) {
		String result = null;
		String query = encodedQuery;

		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		testRecord.orderByDesc("sys_created_on");
		testRecord.query();

		testRecord.next();
		
		result = testRecord.getValue(FieldValueToGet);
		return result;

	}
	
	
	public String getCurrentDate_NoSpace() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMdd");
		Date currentDate = new Date();
		String strDate = sdfDate.format(currentDate);
		return strDate;
	}
	public String getEmployeeNameBySysId(String TableName, String userSysId) {
		RemoteGlideRecord rgr = this.getRemoteGlideRecord(TableName);
		rgr.addQuery("sys_id", userSysId);
		rgr.query();
		rgr.next();
		return rgr.getValue("name");
	}

	public String getRecordValueBySysID(String TableName, String userSysId, String fieldValueToGet) {
		RemoteGlideRecord rgr = getRemoteGlideRecord(TableName);
		rgr.addQuery("sys_id", userSysId);
		rgr.query();
		rgr.next();
		return rgr.getValue(fieldValueToGet);
	}

	public ArrayList<String> getMultipleFieldsGlideRecord(String TableName, String FieldName, String FldValue,
			String FieldValueToGet) {
		ArrayList<String> result = new ArrayList<String>();

		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addQuery(FieldName, FldValue);
		testRecord.orderByDesc("sys_created_on");
		testRecord.query();

		while (testRecord.next()) {
			result.add(testRecord.getValue(FieldValueToGet));
		}

		return result;

	}

	public String getSysIDOfRecord(String TableName, String query, String FieldValueToGet) {
		String result = new String();

		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		//testRecord.orderByDesc("sys_created_on");
		testRecord.query();
		testRecord.next();
		result = testRecord.getValue(FieldValueToGet);

		return result;

	}

	public String getRandomSysIDOfRecord(String TableName, String query) {
		List<String> al = new ArrayList<>();

		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		testRecord.orderByDesc("sys_created_on");
		testRecord.setLimit(30);
		testRecord.query();
		while (testRecord.next()) {
			al.add(testRecord.getValue("sys_id"));
		}
		Random rand = new Random();
		return al.get(rand.nextInt(al.size()));
	}

	public String getRandomGlideRecordEncodedQuery(String TableName, String encodedQuery, String FieldValueToGet) {
		String valueToObtain;
		int iCounter=0;
		String query = encodedQuery;
		List<String> al = new ArrayList<>();

		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		testRecord.setLimit(50);
		testRecord.query();
		while (testRecord.next()) {
			al.add(testRecord.getValue(FieldValueToGet));
		}
		while(al==null && iCounter<10) {
			RemoteGlideRecord testRecord1 = getRemoteGlideRecord(TableName);
			testRecord1.addEncodedQuery(query);
			testRecord1.setLimit(50);
			testRecord1.query();
			while (testRecord1.next()) {
				al.add(testRecord1.getValue(FieldValueToGet));
			}
		}
		Random rand = new Random();
		valueToObtain = al != null ? al.get(rand.nextInt(al.size())) : null;
		return valueToObtain;
	}

	public ArrayList<String> getMultipleFields_EncodedQuery(String TableName, String FieldValueToGet,
			String encodedQuery) {
		ArrayList<String> result = new ArrayList<String>();
		String query = encodedQuery;
		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		testRecord.query();
		while (testRecord.next()) {
			result.add(testRecord.getValue(FieldValueToGet));
		}
		return result;
	}

    public ArrayList<String> getMultipleFieldsDisplayValues_EncodedQuery(String TableName, String FieldValueToGet,
                                                            String encodedQuery) {
        ArrayList<String> result = new ArrayList<String>();
        String query = encodedQuery;
        RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
        testRecord.addEncodedQuery(query);
        testRecord.query();
        while (testRecord.next()) {
            result.add(testRecord.getDisplayValue(FieldValueToGet));
        }
        return result;
    }


	public ArrayList<String> getMultipleFields_EncodedQuery(String TableName, String[] listFields,
			String encodedQuery) {
		ArrayList<String> resultSet = new ArrayList<String>();
		int iCounter1 = 0;
		while (resultSet.size() == 0 & iCounter1 < 10) {
			try {
				String query = encodedQuery;
				RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
				testRecord.addEncodedQuery(query);
				testRecord.query();
				int length = listFields.length;
				while (testRecord.next()) {
					for (int iCounter = 0; iCounter < length; iCounter++) {
						resultSet.add(testRecord.getValue(listFields[iCounter]));
					}
				}
				pauseMe(4);
				iCounter1++;
			} catch (Exception err) {

			}
		}
		return resultSet;
	}


	public ArrayList<String> getMultipleFieldsDisplayValues_EncodedQuery(String TableName, String[] listFields,
															String encodedQuery) {
		ArrayList<String> resultSet = new ArrayList<String>();
		int iCounter1 = 0;
		while (resultSet.size() == 0 & iCounter1 < 10) {
			try {
				String query = encodedQuery;
				RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
				testRecord.addEncodedQuery(query);
				testRecord.query();
				int length = listFields.length;
				while (testRecord.next()) {
					for (int iCounter = 0; iCounter < length; iCounter++) {
						resultSet.add(testRecord.getDisplayValue(listFields[iCounter]));
					}
				}
				pauseMe(4);
				iCounter1++;
			} catch (Exception err) {

			}
		}
		return resultSet;
	}
	
	public void updateGlideRecordValue(String tableName, String fieldName, String fieldValue, String fieldToUpdate,
			String fieldValueToUpdate) {
		RemoteGlideRecord testRecord = getRemoteGlideRecord(tableName);
		testRecord.addQuery(fieldName, fieldValue);
		testRecord.query();
		testRecord.next();
		testRecord.setValue(fieldToUpdate, fieldValueToUpdate);
		testRecord.update();
	}

	public void updateGlideRecord(String URL, String userName, String passWord, String table, String sys_id,
			String fieldToUpdate, String fieldValueToUpdate) {
		String scriptToExecute = "var request = new GlideHTTPRequest('" + URL + "');" + " request.setBasicAuth('"
				+ userName + "','" + passWord + "');request.addHeader('Accept','application/json');"
				+ " request.addHeader('Content-Type','application/json');var record =new GlideRecord('" + table + "');"
				+ "record.get('" + sys_id + "');" + "record." + fieldToUpdate + "=" + "'" + fieldValueToUpdate + "';"
				+ "record.update();";
		try {
			new Script(scriptToExecute).run();
		} catch (Exception e) {

		}
	}

	public void deleteGlideRecords_Script(String URL, String userName, String passWord, String table, String encQuery) {
		String scriptToExecute = "var request = new GlideHTTPRequest('" + URL + "');" + " request.setBasicAuth('"
				+ userName + "','" + passWord + "');request.addHeader('Accept','application/json');"
				+ " request.addHeader('Content-Type','application/json');var record =new GlideRecord('" + table + "');"
				+ "record.addEncodedQuery('" + encQuery + "');record.query();"
				+ "while(record.next()){record.deleteRecord();}";
		try {
			new Script(scriptToExecute).run();
		} catch (Exception e) {

		}
	}

	public String insertGlideRecordValue(String tableName, Map<String, String> mapObj, String url) {
		RemoteGlideRecord testRecord = getRemoteGlideRecord(url, tableName);
		for (String str : mapObj.keySet()) {
			testRecord.setValue(str, mapObj.get(str));
		}
		testRecord.insert();
		return testRecord.getValue("sys_id");
	}

	public String insertGlideRecordValue(String tableName, Map<String, String> mapObj) {
		RemoteGlideRecord testRecord = getRemoteGlideRecord(tableName);
		for (String str : mapObj.keySet()) {
			testRecord.setValue(str, mapObj.get(str));
		}
		testRecord.insert();
		return testRecord.getValue("sys_id");
	}

	public void insertGlideRecord(String URL, String userName, String passWord, String table,
			Map<String, String> params) {
		Set<String> keyset = params.keySet();
		String scriptToExecute = "var request = new GlideHTTPRequest('" + URL + "');" + " request.setBasicAuth('"
				+ userName + "','" + passWord + "');request.addHeader('Accept','application/json');"
				+ " request.addHeader('Content-Type','application/json');var record =new GlideRecord('" + table + "');";
		for (String key : keyset) {
			scriptToExecute = scriptToExecute + "record.setValue('" + key + "','" + params.get(key) + "');";
		}
		scriptToExecute = scriptToExecute + "record.insert()";
		System.out.println("script to execute-- " + scriptToExecute);
		// + "record.get('"+sys_id+"');"+"record."+fieldToUpdate+
		// "="+"'"+fieldValueToUpdate +"';"+"record.update();";
		try {
			new Script(scriptToExecute).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteGlideRecord(String table, String id) {
		try {
			this.deleteRecord(table, "sys_id", id);
		} catch (Exception err) {
		}

	}

	public void deleteGlideRecord(String table, String fld, String value) {
		RemoteGlideRecord records = getRemoteGlideRecord(table);
		records.addQuery(fld, value);
		records.query();
		if (records.next()) {
			records.deleteRecord();
		}

	}

	public void deleteGlideRecords(String table, String query) {
		RemoteGlideRecord records = getRemoteGlideRecord(table);
		records.addEncodedQuery(query);
		records.query();
		while (records.next()) {
			records.deleteRecord();
		}

	}

	public String getCurrentDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		String strDate = sdfDate.format(currentDate);
		return strDate;
	}

	public String getCurrentDate(String format) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(format);
		Date currentDate = new Date();
		String strDate = sdfDate.format(currentDate);
		return strDate;
	}

	public static String getDateAfterBeforeCurrent(String format, int afterbefore) {
		String dt = null;
		try {
			DateFormat utcFormat = new SimpleDateFormat(format);
			utcFormat.setTimeZone(TimeZone.getTimeZone("PST"));
			dt = utcFormat.format(new Date());
			Calendar c = Calendar.getInstance();
			c.setTime(utcFormat.parse(dt));
			c.add(5, afterbefore);
			dt = utcFormat.format(c.getTime());
		} catch (Exception err) {
		}
		return dt;
	}

	public static String getGMTDateAfterBeforeCurrent(String format, int afterbefore) {
		String dt = null;
		try {
			DateFormat utcFormat = new SimpleDateFormat(format);
			utcFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			dt = utcFormat.format(new Date());
			Calendar c = Calendar.getInstance();
			c.setTime(utcFormat.parse(dt));
			c.add(5, afterbefore);
			dt = utcFormat.format(c.getTime());
		} catch (Exception err) {
		}
		return dt;
	}

	public String getCurrentDateWithMins() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd mm");
		Date currentDate = new Date();
		String strDate = sdfDate.format(currentDate);
		return strDate;
	}

	public String getCurrentDateWithMins_NoSpace() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentDate = new Date();
		String strDate = sdfDate.format(currentDate);
		return strDate;
	}

	// Below method has been implemented based on surf impersonation code, some
	// refactoring has been applied.
	public void impersonateWithUserName(String userName) {
		RemoteGlideRecord rgr = this.getRemoteGlideRecord("sys_user");
		rgr.addQuery("user_name", userName);
		rgr.addQuery("active", "true");
		rgr.query();
		Assert.assertEquals("Unable to find matching record for employee - " + userName, 1L, (long) rgr.getRowCount());
		rgr.next();
		String empSysId = rgr.getValue("sys_id");
		String empName = rgr.getValue("name");
		// new UIOperations().impersonatewithSysId(empSysId);
		impersonatewithSysId(empSysId);
	}

	public void impersonatewithSysId(String userSysId) {
		if (userSysId != null && !userSysId.isEmpty()) {
			try {
				this.mainFrame();
				String loggedInUserId = this.fExecutor.execSync("return g_user.userID").toString();
				if (loggedInUserId != null && userSysId.equalsIgnoreCase(loggedInUserId)) {
					MessageLogger.annotate("Current user is same as the impersonating user");
					return;
				}
			} catch (RuntimeException var5) {
				MessageLogger.annotate(var5.getMessage());
			}

		/*	new UIOperations().setDefaultHomePageWithSysId(userSysId);
			new UIOperations().setTabbedFormsOnWithSysId(userSysId);
			new UIOperations().setRelatedListLoadingWithFormWithSysId(userSysId); */

			String scriptPattern = "SNCTestJS.commons.triggerCustomEvent('%s', '%s');";
			String script = String.format(scriptPattern, "sn:impersonate", userSysId);
			this.runClientSideJS(script);
			this.getDriver().switchTo().defaultContent();
			this.timers.waitUntilDOMReady(30);
			this.parentWindow();
			//this.mainFrame();
		}
	}

	public String runClientSideJS(String script) {
		String result = "";
		try {
			Object resultObj = this.fExecutor.execSync(script);
			if (resultObj != null) {
				result = resultObj.toString();
			}
		} catch (RuntimeException var7) {
			result = this.fExecutor.execSync(script).toString();
		}

		return result;
	}

	public void deleteEmailsLogUntilLastMonth(String username, String password) {
		String encQuery = "sys_created_on<=javascript:gs.endOfLastMonth()";
		// deleteGlideRecords("sys_email",encQuery);

		deleteGlideRecords_Script(TestEnvironment.get().getDefaultUrl(), username, password, "sys_email", encQuery);
	}

	public void runaccAccBatchJobs(String jobName) {

		boolean flag;
		String tdate = this.getCurrentDate();
		String todaydate = tdate + "00:00:00";
		String job_sys_id = getGlideRecordEncodedQuery("/x_snc_lxp_cert_sync_scheduled_job", "sys_id",
				"name=" + jobName);
		// System.out.println("Job Sys id is " + job_sys_id);
		open("/x_snc_lxp_cert_sync_scheduled_job.do?sys_id=" + job_sys_id);
		pauseMe(2);
		String beforepullDate = findElement(
				By.cssSelector("input[id='x_snc_lxp_cert_sync_scheduled_job.last_execution']")).getAttribute("value");
		// System.out.println("Before pulled date " + beforepullDate);
		button.click("execute");
		open("/x_snc_lxp_cert_sync_scheduled_job.do?sys_id=" + job_sys_id);
		String afterpullDate = findElement(
				By.cssSelector("input[id='x_snc_lxp_cert_sync_scheduled_job.last_execution']")).getAttribute("value");
		// System.out.println("After pulled date " + afterpullDate);
		if (!beforepullDate.equalsIgnoreCase(afterpullDate)) {
			flag = true;
		} else {
			flag = false;
		}
		Assert.assertTrue("Accreditation batch job successfully not ran", flag);

	}

	public ArrayList<String> getMultipleFields_EncodedQuerySurf(String TableName, String FieldValueToGet,
			String encodedQuery, String... url) {
		ArrayList<String> result = new ArrayList<String>();
		String query = encodedQuery;
		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord = url.length != 0 ? getRemoteGlideRecord(url[0], TableName) : getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		testRecord.orderByDesc("sys_created_on");
		testRecord.query();
		while (testRecord.next()) {
			// result.add(testRecord.getValue(FieldValueToGet));
			result.add(testRecord.getDisplayValue(FieldValueToGet));
		}
		return result;
	}

	public String getInstanceEndpointURLSurf() {
		RemoteGlideRecord rgr = getRemoteGlideRecord("sys_properties");
		rgr.addEncodedQuery("name=x_snc_lxp.rest.endpoint_instance");
		rgr.query();
		rgr.next();
		return rgr.getValue("value");
	}

	public RemoteGlideRecord getRemoteGlideRecord(String url, String tableName) {
		RemoteGlideRecord rgr = new RemoteGlideRecord(url, tableName);
		rgr.setBasicAuth(TestEnvironment.get().getProperty("adminUser"),
				TestEnvironment.get().getProperty("adminPassword"));
		rgr.setBasicAuth("automation_admin", "automation1234@");
		return rgr;
	}

	public void runKryterionBatchJobs(String jobName) {
		String job_sys_id = getGlideRecordEncodedQuery("x_snc_lxp_sysauto_script_rest", "sys_id",
				"nameSTARTSWITH" + jobName);
		open("/x_snc_lxp_sysauto_script_rest.do?sys_id=" + job_sys_id);
		timers.waitUntilDOMReady(20);
		By hereLink = By.xpath(".//div[@id='nav_message']//a[text()='here']");
		findElement(hereLink).click();
		String originalDateTime = field.getTextFieldValue("last_pull");
		String time_input = getGMTDateAfterBeforeCurrent("yyyy-MM-dd", 0) + " 00:00:00";
		field.setTextField("last_pull", time_input);
		contextMenu.click("Save");
		pauseMe(2);
		button.click("execute");
		int timerCount = 4;
		do {
			pauseMe(timerCount);
			originalDateTime = glideRecordValue("kryterion profile", "u_email", "userName", "last_pull");
			timerCount += 2;
		} while (originalDateTime == time_input && timerCount <= 60);
		pauseMe(2);
	}

	public String getInstanceEndpointURL() {
		RemoteGlideRecord rgr = getRemoteGlideRecord("sys_properties");
		rgr.addEncodedQuery("name=x_snc_lxp.rest.endpoint_instance");
		rgr.query();
		rgr.next();
		return rgr.getValue("value");
	}

	public String insertGlideRecordValue(String tableName, Map<String, String> mapObj, String... url) {
		RemoteGlideRecord testRecord = null;

		testRecord = url.length != 0 ? getRemoteGlideRecord(url[0], tableName) : getRemoteGlideRecord(tableName);
		for (String str : mapObj.keySet()) {
			testRecord.setValue(str, mapObj.get(str));
		}
		testRecord.insert();
		return testRecord.getValue("sys_id");
	}

	public String getSingleGlideRecord(String TableName, String FieldName, String FldValue, String FieldValueToGet,
			String... url) {

		String result = null;
		RemoteGlideRecord testRecord;
		testRecord = url.length != 0 ? getRemoteGlideRecord(url[0], TableName) : getRemoteGlideRecord(TableName);
		testRecord.addQuery("active", true);
		testRecord.addQuery(FieldName, FldValue);
		testRecord.orderByDesc("sys_created_on");
		testRecord.query();
		testRecord.next();
		result = testRecord.getValue(FieldValueToGet);
		return result;
	}

	public String getRecordValueBasedonEncQuery(String TableName, String encQuery, String FieldValueToGet,
									   String... url) {

		String result = null;
		RemoteGlideRecord testRecord;
		testRecord = url.length != 0 ? getRemoteGlideRecord(url[0], TableName) : getRemoteGlideRecord(TableName);
		testRecord.addQuery("active", true);
		testRecord.addEncodedQuery(encQuery);
		testRecord.query();
		testRecord.next();
		result = testRecord.getValue(FieldValueToGet);
		return result;
	}

	public void runBatchJobs(String jobName) {

		String job_sys_id = getGlideRecordEncodedQuery("nameSTARTSWITH" + jobName, "sys_id",
				"x_snc_lxp_sysauto_script_rest"); 
 
		 open("/x_snc_lxp_sysauto_script_rest.do?sys_id=" + job_sys_id); 

		pauseMe(2);
		item.click(".//a[@href ='javascript: void(0);']");
		String time_input = getCurrentDate("yyyy-mm-dd") + " 00:00:00";

		field.setTextField("last_pull", time_input); 
		contextMenu.click("Save");
		button.click("execute");

	}

	public void runBatchJob(String tableName, String jobName) {
		RemoteGlideRecord rgr = getRemoteGlideRecord(tableName);
		rgr.addEncodedQuery("active=true^name=" + jobName);
		rgr.query();
		rgr.next();
		String superscript = rgr.getValue("script");
		Script sc = new Script(superscript);
		sc.run();  

	}
  
	

	public String getSingleField_AccEncodedQuerySurf(String TableName, String FieldValueToGet,
			String encodedQuery, String... url) {
		//ArrayList<String> result = new ArrayList<String>();
		String result = new String();
		String query = encodedQuery;
		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord = url.length != 0 ? getRemoteGlideRecord(url[0], TableName) : getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		testRecord.orderByDesc("sys_created_on");
		testRecord.query();
		testRecord.next();
		if(!testRecord.getValue(FieldValueToGet).toString().equalsIgnoreCase("null"))
		{
		result = testRecord.getValue(FieldValueToGet);
		return result;
		}
		else
		{
			result = testRecord.getDisplayValue(FieldValueToGet);
			return result;
		}
	}
	
	public String getGlideRecordEncodedQueryDisplyvalue(String TableName, String FieldValueToGet, String encodedQuery) {
		String result = null;
		String query = encodedQuery;

		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		testRecord.orderByDesc("sys_created_on");
		testRecord.query();

		testRecord.next();
		
		result = testRecord.getDisplayValue(FieldValueToGet);
		return result;

	}

	public String getLastWeekSunday(String datePattern) {

		Calendar cal1 = Calendar.getInstance();
		int dayOfTheWeek = cal1.get( Calendar.DAY_OF_WEEK );
		cal1.add( Calendar.DAY_OF_WEEK, Calendar.SUNDAY - dayOfTheWeek-7 );
		System.out.println(cal1.get(Calendar.DATE));
		DateFormat df1=new SimpleDateFormat(datePattern);
		System.out.println(df1.format(cal1.getTime()));
		return df1.format(cal1.getTime());
	}

    public void executeActivityJobForUser(String encQuery,String sysId) {
		String scriptToExecute =
				 "var metric_definitions = new GlideRecord('x_snc_lxp_metric_definition');\n"
		        + "metric_definitions.addQuery('active', true);\n"
				+ "metric_definitions.addEncodedQuery('"+encQuery+"');\n"
				+ "metric_definitions.query();\n"
				+"var metricCalc = new x_snc_lxp.MetricDefinitionCalculator();\n"
				+ "if(metric_definitions.next()) {\n" +
				" var user = new GlideRecord('sys_user');\n" +
				//"user.get('"+sysId+"');\n" +
			 	" user.addQuery('sys_id','"+sysId+"');\n" +
				" user.query();\n" +
				" user.next();\n" +
				" metricCalc.evaluateMetricForUser(metric_definitions, user);\n" +
				"}\n"
				+"var populateRule = new x_snc_lxp.PopulateActivityUserRule();\n" +
				"populateRule.populate('"+encQuery+"');";
		try {
			Script obj = new Script(scriptToExecute);
			String reqSysID = obj.run();
			System.out.println("Value returned is: "+reqSysID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getGlideRecordBasedOnScriptexec( String table, String encQuery) {
		String sysId = null;
		String scriptToExecute = "var record =new GlideRecord('" + table + "');"
				+ "record.addEncodedQuery('" + encQuery + "');record.query();"
				+ "record.next();";
		try {
			Script obj = new Script(scriptToExecute);
			sysId = obj.run();
		} catch (Exception e) {

		}
		return sysId;
	}

	public List<String> getSysIdSForGivenEncQuery(String TableName, String query) {
		List<String> aList = new ArrayList<>();
		
		String encQuery ;
		int iRows=0;
		

		RemoteGlideRecord testRecord = getRemoteGlideRecord(TableName);
		testRecord.addEncodedQuery(query);
		//testRecord.setLimit(500);
		testRecord.query();

		while (testRecord.next()) {
			aList.add(testRecord.getValue("sys_id"));
		}
		return aList;		

	}


	public String fetchCourseIdBasedOnModulesAvailable(String table,List<String> listSysIDs) {
		int iRows = 0;
		String reqSysID = null;
		List<String> moduleNames = new ArrayList<>();
		String encQuery ;
		for(String sysId : listSysIDs) {
			encQuery = "course="+sysId+"^moduleISNOTEMPTY";
			iRows = getGlideRecordCount(table,encQuery);
			if(iRows>=1) {
				reqSysID = sysId;
				break;
			}
			
		}
		String query = "course="+reqSysID+"^moduleISNOTEMPTY";
		RemoteGlideRecord testRecord = getRemoteGlideRecord(table);
		testRecord.addEncodedQuery(query);
		testRecord.query();
		while (testRecord.next()) {
			moduleNames.add(testRecord.getDisplayValue("module"));
		}
		return reqSysID;
	}
	
	
//---------------------------------------------------------------------------------------------
	
	public String fetchCourseIdBasedOnModulesandassessmentsAvailable(String table,List<String> listSysIDs) {
		int iRows = 0;
		String reqSysID = null;
		List<String> coursesWithModules = new ArrayList<>();   
		String encQuery ;
		for(String sysId : listSysIDs) 
		{
			encQuery = "course="+sysId+"^moduleISNOTEMPTY";
			iRows = getGlideRecordCount("x_snc_lxp_m2m_modules_courses",encQuery);
			if(iRows>=1){
				coursesWithModules.add(sysId);
			}
		}
			
		for(String s1:coursesWithModules) 
		{
			encQuery= "sys_idLIKE"+s1;
			RemoteGlideRecord testRecord = getRemoteGlideRecord("x_snc_lxp_course");
			testRecord.addEncodedQuery(encQuery);
			testRecord.query();
			while (testRecord.next())
			{
				String assessment= testRecord.getValue("exam");
				if(!assessment.isEmpty()) 
				{
					reqSysID = s1;
						break;
			    }
			}			
		}
		return reqSysID;
	}

//---------------------------------------------------------------------------------------------	

	private RemoteGlideRecord SelectRecordsInputStream(Object tableName, String encQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	public int validateEmailReceivedForCaseCreated(String ticketNumber) {
		String query = "subjectLIKECase "+ticketNumber+" has been assigned to";
		int iRecordCount;
		int iCounter = 0;
		do {
			iRecordCount = getGlideRecordCount("sys_email",query);
			pauseMe(6); iCounter++;
		} while(iRecordCount<1 && iCounter<10);
		return iRecordCount;
	}

	public String getRecipientGroupName(String ticketNumber) {
		String query = "subjectLIKECase "+ticketNumber+" has been assigned to";
		String recipients = getSysIDOfRecord("sys_email",query,"recipients");
		return recipients;
	}

	public String fetchPathSysIdBasedOnCoursesAvailable(String table,List<String> listSysIDs) {
		int iRows = 0;
		String reqSysID = null;
		List<String> moduleNames = new ArrayList<>();
		String encQuery ;
		for(String sysId : listSysIDs) {
			encQuery = "path="+sysId+"^courseISNOTEMPTY";
			iRows = getGlideRecordCount(table,encQuery);
			if(iRows>=2 && iRows<=5) {
				reqSysID = sysId;
				break;
			}
		}
		String query = "path="+reqSysID+"^courseISNOTEMPTY";
		RemoteGlideRecord testRecord = getRemoteGlideRecord(table);
		testRecord.addEncodedQuery(query);
		testRecord.query();
		while (testRecord.next()) {
			moduleNames.add(testRecord.getDisplayValue("module"));
		}
		return reqSysID;
	}
	
	
	
	

}
