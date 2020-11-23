package com.snc.surf.marketing.NLearning.utils.Entitlements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.glide.communications.RemoteGlideRecord;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.openqa.selenium.By;

import com.snc.glide.it.Script;
import com.snc.selenium.core.SNCTest;
import com.snc.surf.marketing.NLearning.base.NLEPageBase;

import com.snc.surf.marketing.NLearning.utils.GlideRecordBase;
import com.snc.surf.marketing.NLearning.utils.NLEUtils;

public class NLEntitlement extends GlideRecordBase {
	public ArrayList<String> guestcontent = new ArrayList<String>();
	ArrayList<CardObject> contentComparator = new ArrayList();
	public int contentCount;
	public String simulatorExam, simulatorsysid, courseSysid;
	protected int courseCount, assessmentCount, certificationCount = 0;
	protected ArrayList<String> pathAssesments = new ArrayList<String>();
	protected ArrayList<String> pathCourses = new ArrayList<String>();
	protected String certype = "";
	protected NLEUtils leutils = new NLEUtils();
	public String courseUrl = "/lxp?id=overview&sys_id=%s&type=course";
	public String pageUrl = "/lxp?id=overview&sys_id=%s&type=path";
	public By cancel = By.xpath("//*[contains(@ng-click,'cancelCase()')]");
	public By unpause = By.xpath("//*[contains(@ng-click,'UnPauseSim()')]");
	public By openSim = By.xpath("//*[contains(@ng-click,'openSimulator')]");
	public By ok = By.xpath("//button[contains(text(),'OK')]");
	public By cancl = By.xpath("//button[contains(text(),'Cancel')]");
	public By reEnroll = By.xpath("//*[contains(@ng-click,'reEnrollSimulator')]");
	public By model = By.xpath("//*[@id='modal-title']");
	public By enrol = By.cssSelector("button[ng-click*='c.enroll']");
	public String contentCompletion = "Great job on finishing contenttype";
	public String badgeCompletion = "Congrats on earning the badgetype badge";

	public ArrayList<String> getGuestContent() {
		return guestcontent;
	}
	

	public String getUserCritera() {

		StringJoiner criteria = new StringJoiner(",");
		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_user_rule");
		rgr.addEncodedQuery("state=published^userLIKE0586816c1b43b7443a2310ad2d4bcb8b^ORuser_conditionsLIKEguest");

		rgr.query();
		if (rgr.getRowCount() > 1) {
			while (rgr.next()) {

				criteria.add(rgr.getValue("user_criteria"));

			}
			return criteria.toString();
		}
		rgr.next();
		return rgr.getValue("user_criteria");

	}

	public void getCount(String table, String query) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(table);
		rgr.addEncodedQuery(query);
		rgr.setLimit(1000);
		rgr.query();

		contentCount = rgr.getRowCount();

	}

	public void getGuestUserInfo(String sectionName, String countContent, String... certSub) {
		guestcontent.clear();
		String query = "";
		switch (sectionName.toLowerCase()) {

		case "all":
			query = "active=true^associated_user_criteria=" + getUserCritera();
			setCountOrContent(countContent, query, "x_snc_lxp_base_lxp");
			break;

		case "featured":
			query = "active=true^associated_user_criteria=" + getUserCritera() + "^featured=true";
			setCountOrContent(countContent, query, "x_snc_lxp_base_lxp");
			break;
		case "simulator":
			query = "active=true^category=" + fetchCategory("Simulator") + "^associated_user_criteria="
					+ getUserCritera();
			setCountOrContent(countContent, query, "x_snc_lxp_course");
			break;
		case "live classes":
			query = "active=true^category=" + fetchCategory("Live Class") + "^associated_user_criteria="
					+ getUserCritera();
			setCountOrContent(countContent, query, "x_snc_lxp_course");
			break;
		case "certification":
			if (certSub.length < 1) {
				query = "category=" + fetchCategory("Certification") + "^active=true^associated_user_criteria="
						+ getUserCritera();
				setCountOrContent(countContent, query, "x_snc_lxp_path");
				break;
			} else {
				query = "category=" + fetchCategory("Certification") + "^active=true^persona="
						+ personasSysID(certSub[0]) + "^associated_user_criteria=" + getUserCritera();
				setCountOrContent(countContent, query, "x_snc_lxp_path");
				break;
			}

		default:
			Assert.assertTrue("Sectionname passed is  incorrect", false);

		}
	}

	public String personasSysID(String name) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_persona");
		rgr.addEncodedQuery("active=true^name=" + name);
		rgr.query();
		if (rgr.getRowCount() < 1)
			Assert.assertTrue("Role passed is not available in backend", false);
		rgr.next();

		return rgr.getValue("sys_id");

	}

	public void getGuestData(String table, String query) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(table);
		rgr.addEncodedQuery(query);
		rgr.orderByDesc("sys_updated_on");
		rgr.query();
		if (rgr.getRowCount() > 0) {
			while (rgr.next()) {
				guestcontent.add(rgr.getValue("name"));
			}
		} else
			Assert.assertTrue("No macting results in backend", false);
	}

	public void setCountOrContent(String countContent, String query, String tablname) {
		if (countContent.toLowerCase().contains("count"))
			getCount(tablname, query);
		else
			getGuestData(tablname, query);
	}

	public boolean validateCardName(String cardName) {
		return guestcontent.stream().anyMatch(card -> card.contains(cardName));
	}

	public String fetchCategory(String catName) {

		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_category");
		rgr.addEncodedQuery("name=" + catName + "^active=true");
		rgr.query();
		if (rgr.getRowCount() < 1) {
			Assert.assertTrue("Category passed is not available in backend", false);
		}
		rgr.next();
		return rgr.getValue("sys_id");
	}

	public void buildGlobalResults(String keywrd, String sortOrder) {
		guestcontent.clear();
		String exactQuery = "active=true^name=" + keywrd + "^associated_user_criteria=" + getUserCritera();

		String query = "active=true^nameLIKE" + keywrd + "^ORshort_descriptionLIKE" + keywrd + "^ORdescriptionLIKE"
				+ keywrd + "^associated_user_criteria=" + getUserCritera();

		if (recordExists("x_snc_lxp_base_lxp", exactQuery))

			setCountOrContent("data", exactQuery, "x_snc_lxp_base_lxp");

		else if (sortOrder.equalsIgnoreCase("most recent")) {

			setCountOrContent("data", query, "x_snc_lxp_base_lxp");

		}

		else if (sortOrder.equalsIgnoreCase("most viewed")) {
			getSortedByViews(query);
		}

		else
			Assert.assertTrue("Sort order passed is incorrect", false);
	}

	public boolean recordExists(String tablename, String query) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(tablename);
		rgr.addEncodedQuery(query);
		rgr.query();
		if (rgr.getRowCount() == 1) {
			return true;
		}
		return false;

	}

	public void getSortedByViews(String query) {

		RemoteGlideRecord path = getRemoteGlideRecord("x_snc_lxp_path");
		path.addEncodedQuery(query);
		path.query();
		while (path.next()) {

			contentComparator.add(new CardObject(Integer.parseInt(path.getValue("views")), path.getValue("name")));
		}
		RemoteGlideRecord course = getRemoteGlideRecord("x_snc_lxp_course");
		course.addEncodedQuery(query);
		course.query();
		while (course.next()) {

			contentComparator.add(new CardObject(Integer.parseInt(course.getValue("views")), course.getValue("name")));
		}

		Collections.sort(contentComparator, new ViewNameComparator());
		Iterator<CardObject> itr = contentComparator.iterator();

		while (itr.hasNext()) {
			CardObject card = (CardObject) itr.next();
			guestcontent.add(card.name);

		}
	}

	public String getRandomValue(String tablname) {
		List<String> randomList = new ArrayList<String>();
		Random rand = new Random();
		RemoteGlideRecord rgr = getRemoteGlideRecord(tablname);
		rgr.addEncodedQuery("active=true");
		rgr.query();
		if (rgr.getRowCount() < 1) {
			Assert.assertTrue("There are no active reocrds in the table : " + tablname, false);
		}
		while (rgr.next()) {
			randomList.add(rgr.getValue("sys_id"));

		}
		return randomList.get(rand.nextInt(randomList.size()));
	}

	public void getAssociatedSubContentPath(String name) {

		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_m2m_course_path");
		rgr.addEncodedQuery("path=" + getSysyID("x_snc_lxp_path", name));
		rgr.query();
		if (rgr.getRowCount() > 0) {
			while (rgr.next()) {
				Optional<String> courseopt = Optional.ofNullable(rgr.getValue("course"));
				Optional<String> certificationopt = Optional.ofNullable(rgr.getValue("certification"));
				Optional<String> assessmentopt = Optional.ofNullable(rgr.getValue("assessment"));
				String course = courseopt.orElse("");
				String assesment = assessmentopt.orElse("");
				String certification = certificationopt.orElse("");

				if (course.isEmpty() && certification.isEmpty() && assesment.length() > 0) {
					assessmentCount++;
					pathAssesments.add(getName("asmt_metric_type", rgr.getValue("assessment")));

				} else if (assesment.isEmpty() && certification.isEmpty() && course.length() > 0) {

					courseCount++;
					pathCourses.add(getName("x_snc_lxp_course", rgr.getValue("course")));
					RemoteGlideRecord rgrNew = getRemoteGlideRecord("x_snc_lxp_course");
					rgrNew.addEncodedQuery("examISNOTEMPTY^sys_idLIKE" + course);
					rgrNew.query();
					if (rgrNew.getRowCount() > 0) {
						assessmentCount++;

					}
				} else if (course.isEmpty() && assesment.isEmpty()) {
					certificationCount++;
					if (getpathType("x_snc_lxp_path", certification))
						certype = "Certification";
					else {
						getCertificateType("x_snc_lxp_certification", certification);
					}
				}
			}
		}
	}

	public String getSysyID(String tablename, String name) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(tablename);
		rgr.addEncodedQuery("name=" + name);
		rgr.query();

		if (rgr.getRowCount() < 1)
			Assert.assertTrue("Record not present in backend", false);
		if (rgr.getRowCount() > 1)
			Assert.assertTrue("Multiple Records are present in backend", false);
		rgr.next();
		return rgr.getValue("sys_id");

	}

	public String getSysyIDUser(String tablename, String name) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(tablename);
		rgr.addEncodedQuery("active=true^email=" + name);
		rgr.query();

		if (rgr.getRowCount() < 1)
			Assert.assertTrue("Record not present in backend", false);
		if (rgr.getRowCount() > 1)
			Assert.assertTrue("Multiple Records are present in backend", false);
		rgr.next();
		return rgr.getValue("sys_id");

	}

	public String getName(String tablename, String sysId) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(tablename);
		rgr.addEncodedQuery("active=true^sys_id=" + sysId);
		rgr.query();

		if (rgr.getRowCount() < 1)
			Assert.assertTrue("Record not present in backend", false);
		if (rgr.getRowCount() > 1)
			Assert.assertTrue("Multiple Records are present in backend", false);
		rgr.next();

		return rgr.getValue("name");

	}

	public String getCertificateType(String tablename, String sysId) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(tablename);
		rgr.addEncodedQuery("active=true^sys_id=" + sysId);
		rgr.query();

		if (rgr.getRowCount() < 1)
			Assert.assertTrue("Record not present in backend", false);
		if (rgr.getRowCount() > 1)
			Assert.assertTrue("Multiple Records are present in backend", false);
		rgr.next();
		if (rgr.getValue("type").equalsIgnoreCase("mainline"))
			certype = "Certification";
		else if (rgr.getValue("type").equalsIgnoreCase("micro"))
			certype = "Micro-Certification";
		return certype;
	}

	public boolean getpathType(String tablename, String sysId) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(tablename);
		rgr.addEncodedQuery("active=true^sys_id=" + sysId + "^category=" + fetchCategory("Certification")
				+ "^certification_type=Delta");
		rgr.query();

		if (rgr.getRowCount() > 0)
			return true;
		return false;
	}

	public int getRating(String tablename, String name) {

		RemoteGlideRecord rgr = getRemoteGlideRecord(tablename);
		rgr.addEncodedQuery("active=true^name=" + name);
		rgr.query();

		if (rgr.getRowCount() < 1)
			Assert.assertTrue("Record not present in backend", false);
		if (rgr.getRowCount() > 1)
			Assert.assertTrue("Multiple Records are present in backend", false);
		rgr.next();
		Optional<String> courseopt = Optional.ofNullable(rgr.getValue("rating"));
		String rates = courseopt.isPresent() ? rgr.getValue("rating") : "0";

		return Math.round(Float.parseFloat(rates));

	}

	public String getPathNameWithDAta() {

		ArrayList<String> list = new ArrayList<String>();
		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_path");
		rgr.addEncodedQuery("nameISNOTEMPTY^short_descriptionISNOTEMPTY^"
				+ "descriptionISNOTEMPTY^category=3b27aed0dbfb6f00a23dd1c2ca9619ea^skillsISNOTEMPTY^u_durationISNOTEMPTY");
		rgr.query();

		while (rgr.next()) {

			if (rgr.getValue("name").length() > 0) {

				list.add(rgr.getValue("name"));
			}
		}
		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));
	}

	public void setFilters(String name) {

		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_path");
		rgr.addEncodedQuery("active=true^name=" + name);
		rgr.query();
		rgr.next();
		rgr.setValue("persona", getRandomValue("x_snc_lxp_persona"));
		rgr.setValue("product", getRandomValue("x_snc_lxp_product"));
		rgr.setValue("tags", getRandomValue("x_snc_lxp_tag"));
		rgr.update();
	}

	public CardObject setCardObject() {
		String pathName = getPathNameWithDAta();

		setFilters(pathName);
		getAssociatedSubContentPath(pathName);

		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_path");
		rgr.addEncodedQuery("active=true^name=" + pathName);
		rgr.query();
		rgr.next();

		CardObject card = new CardObject(pathName, rgr.getValue("description"), rgr.getValue("short_description"),
				rgr.getDisplayValue("u_duration"), (getName("x_snc_lxp_badge", rgr.getValue("badge"))),
				rgr.getValue("paid"), getName("x_snc_lxp_category", rgr.getValue("category")),
				(rgr.getValue("skills").replace("<p>", "").replace("</p>", "")), certype,
				getName("x_snc_lxp_tag", rgr.getValue("tags")), (getRating("x_snc_lxp_path", pathName)),
				assessmentCount, certificationCount, courseCount, pathAssesments, pathCourses,
				rgr.getDisplayValue("persona"), rgr.getDisplayValue("level"), rgr.getDisplayValue("product"),
				rgr.getDisplayValue("tags"));
		return card;
	}

	public void pausMeJOb() {
		try {
			String superScript = "var lpt = new GlideRecord('x_snc_sims_lpt'); \n" +

					" lpt.addEncodedQuery('pause_enabled=true^simulator.pause=true^state=in progress^instance_state=running^sys_updated_onRELATIVELE@minute@ago@0.1'); \n"
					+ " lpt.query(); \n" + " while(lpt.next()){  \n"
					+ "  new x_snc_sims.SimUtils().pauseInstance(lpt)};";

			System.out.println(superScript);
			Script sc = new Script(superScript);
			sc.run();
		} catch (Exception err) {
		}
	}

	public boolean waitPause(String lpt_sys_id, String state, String field) {

		int i = 0;
		while (i < 120) {
			i++;
			String instance = leutils.getGlideRecordEncodedQuery("x_snc_sims_lpt", field, "sys_id=" + lpt_sys_id);
			if (instance.equalsIgnoreCase(state))
				return true;
			pauseMe(1);
		}
		return false;

	}

	public String setExpirationnDate(String lpt_sys, int days) throws ParseException {

		SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
		dateParser.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = (DateUtils.addDays(new Date(), +days));
		dateParser.setTimeZone(TimeZone.getTimeZone("GMT"));

		String dateTime = dateParser.format(date);

		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_sims_lpt");
		rgr.addEncodedQuery("sys_id=" + lpt_sys);
		rgr.query();
		rgr.next();
		rgr.setValue("expiration", dateTime);
		rgr.update();
		return dateTime;

	}

	public void runJob(String jobname, String... restimport) {
		String table = "sysauto_script";
		try {
			if (restimport.length > 0) {
				updateLastPullDate(jobname);
				table = "x_snc_lxp_sysauto_script_rest";
			}
			String superScript = " var rec = new GlideRecord('" + table + "'); \n" +

					" rec.get('name', '" + jobname + "'); \n" + "  SncTriggerSynchronizer.executeNow(rec);";

			System.out.println(superScript);
			Script sc = new Script(superScript);
			sc.run();
			Assert.assertTrue(jobname + " is still running...Aborting",
					jobRunStatus(getSysyID("sysauto_script", jobname)));
			pauseMe(2);
		} catch (Exception err) {
		}
	}

	public boolean jobRunStatus(String jobsys_id) {
		int i = 0;
		do {
			i++;
			pauseMe(9);
			RemoteGlideRecord rgr = getRemoteGlideRecord("sys_trigger");
			rgr.addEncodedQuery("state=1^document_key=" + jobsys_id);
			rgr.query();
			if (rgr.getRowCount() < 1) {
				return true;
			}
		} while (i < 22);

		return false;

	}

	public boolean verifyEmailVerification(String email, String subject) {

		int i = 0;
		while (i < 8) {
			RemoteGlideRecord rgr = getRemoteGlideRecord("sys_email");
			rgr.addEncodedQuery("recipients=" + email + "^subjectLIKE" + subject);

			rgr.query();
			rgr.next();

			if ((rgr.getRowCount() < 1)) {
				pauseMe(5);
				i++;

			} else
				return true;

		}
		return false;

	}

	public void deleteRecord(String sys_id, String table) {
		String encQuery = "sys_id=" + sys_id;
		String superScript = " var rec = new GlideRecord('" + table + "'); \n" +

				"rec.get('" + sys_id + "');\n" + "  rec.deleteRecord();";

		Script sc = new Script(superScript);
		sc.run();
		System.out.println(superScript);
		pauseMe(2);
		if ((leutils.getGlideRecordCount("x_snc_lxp_transcript", "sys_id=" + sys_id) > 0))
			Assert.assertFalse(true);

	}

	public void swtichBack(String parentWindowHandler) {
		getDriver().close();
		getDriver().switchTo().window(parentWindowHandler);
		getDriver().navigate().refresh();
	}

	public void updateStateLPT(String lptsysid, String state, String table, String field) {
		String key = "sys_id=";
		if (table.equalsIgnoreCase("x_snc_sims_lptask")) {

			key = "learning_path=";
		}
		RemoteGlideRecord rgr = getRemoteGlideRecord(table);
		rgr.addEncodedQuery(key + lptsysid);
		rgr.query();

		while (rgr.next()) {
			rgr.setValue(field, state);
			rgr.update();
		}

	}

	public void updateLastPullDate(String jobname) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime now1 = now.minusDays(2);
		String lastPull = dtf.format(now1);
		updateStateLPT(getSysyID("sysauto_script", jobname), lastPull, "x_snc_lxp_sysauto_script_rest", "last_pull");

	}

	public String getExamPath(String pathType, String table, String examType) {
		String mainQuery = "assessment.u_quiz_type=exam^assessment.u_exam.type=";
		String subQuery = "^ORassessment.u_exam.type=";
		if (!(pathType.equalsIgnoreCase("micro") || pathType.equalsIgnoreCase("delta"))) {
			Assert.assertTrue("PathType is not valid", false);
		}
		if (examType.equalsIgnoreCase("simulator")) {
			mainQuery = "course.active=true^course.simulator.exam.type=";
			subQuery = "^ORcourse.simulator.exam.type=";
		}
		String pathSysid = "";
		RemoteGlideRecord rgr = getRemoteGlideRecord(table);
		rgr.addEncodedQuery("path.active=true^" + mainQuery + pathType);
		rgr.query();
		rgr.orderByDesc("sys_created_on");

		while (rgr.next()) {
			RemoteGlideRecord rgr1 = getRemoteGlideRecord(table);
			rgr1.addEncodedQuery("path=" + rgr.getValue("path"));
			rgr1.query();
			if (rgr1.getRowCount() == 2) {
				RemoteGlideRecord rgr2 = getRemoteGlideRecord(table);
				rgr2.addEncodedQuery(
						"certification.type=" + pathType + subQuery + pathType + "^path=" + rgr.getValue("path"));

				rgr2.query();

				if (rgr2.getRowCount() == 2) {
					pathSysid = rgr.getValue("path");
					courseSysid = leutils.getGlideRecordEncodedQuery(table, "course",
							"path=" + pathSysid + "^courseISNOTEMPTY");
					simulatorsysid = examType.equalsIgnoreCase("simulator")
							? leutils.getGlideRecordEncodedQuery("x_snc_lxp_course", "simulator", "sys_id="+courseSysid)
							: " ";
					simulatorExam = examType.equalsIgnoreCase("simulator")
							? getName("x_snc_lxp_exam",
									leutils.getGlideRecordEncodedQuery("x_snc_sims_sim", "exam",
											"sys_id=" + simulatorsysid))
							: "";
					break;
				}
			}
		}

		return pathSysid.length() > 0 ? pathSysid : null;
	}

}
