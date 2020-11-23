package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;

public class ContentCreationPlatformView extends NLEPageBase {
//	protected final ApiExecutor fExecutor = ApiExecutor.getInstance();
	
	public String createLesson(String lessonType,String quizName) {
		open("/x_snc_lxp_lesson.do");
		String lessonName = "AutoLesson - "+leUtils.getCurrentDateWithMins_NoSpace();
		field.setTextField("name", lessonName);
		field.setSelectField("type", lessonType);
		field.setTextField("description", "AutoLesson01");
		if(!(quizName==null))
			field.setRefField("quiz", quizName);
		if(lessonType.equals("RichText"))
			field.setHTMLField("content", "Lesson Content");
		button.click("sysverb_insert");
		timers.waitUntilDOMReady(30);
		return lessonName;
	}
	
	public String createModule() {
		open("/x_snc_lxp_module.do");
		String moduleName = "AutoModule - "+leUtils.getCurrentDateWithMins_NoSpace();
		field.setTextField("name", moduleName);
		field.setCheckbox("active");
		field.setTextField("description", "Test Desc for " + moduleName);
		button.click("sysverb_insert");
		timers.waitUntilDOMReady(30);
		return moduleName;
	}
	
	public String createCourse(String level, String badgeName) {
		open("/x_snc_lxp_course.do");
		String courseName = "AutoCourse - "+leUtils.getCurrentDateWithMins_NoSpace();
		field.setTextField("name", courseName);
		field.setSelectField("level", level);
		field.setTextField("short_description", "SD for " + courseName);
		field.setTextField("description", "Test Desc for " + courseName);
		field.setCheckbox("active");
		//set watchlist field values
		fExecutor.execSync("g_form.setValue('audience', 'c41c79aedbcf7b00a23dd1c2ca96198d');");//sysid id for all users
		fExecutor.execSync("g_form.setValue('persona', 'fa50e6cddbc1730069a56a5b8a96196c');"); //sysid for Administrator
		field.setRefField("image", "10off.png");

		tab.view("Details");
		field.setSelectField("source", "LXP");
		field.setHTMLField("skills", "Test learning objective"); //learning objective
		fExecutor.execSync("g_form.setValue('tags', '7efc27bc1b5ebf403a2310ad2d4bcbb8');");//sysid for Action Designer tag
		
		tab.view("Outcomes");
		field.setRefField("badge", badgeName);
		
		button.click("sysverb_insert");
		timers.waitUntilDOMReady(30);
		return courseName;
	}
	
	public String createPath(String level, String badgeName) {
		open("/x_snc_lxp_path.do");
		String pathName = "AutoPath - "+leUtils.getCurrentDateWithMins_NoSpace();
		field.setTextField("name", pathName);
		fExecutor.execSync("g_form.setValue('release', '7a76b9d6dbd72300a23dd1c2ca96195f');");//sysid for New York
		field.setSelectField("level", level);
		fExecutor.execSync("g_form.setValue('audience', 'c41c79aedbcf7b00a23dd1c2ca96198d');");//sysid id for all users
		field.setTextField("short_description", "SD for " + pathName);
		field.setTextField("description", "Test Desc for " + pathName);
		//set watchlist field values
		field.setRefField("category", "Featured");
		fExecutor.execSync("g_form.setValue('product', 'f877711adbd72300a23dd1c2ca96194e');");//sysid id for HR Service delivery
		fExecutor.execSync("g_form.setValue('persona', 'fa50e6cddbc1730069a56a5b8a96196c');"); //sysid for Administrator
		field.setRefField("image", "10off.png");

		tab.view("Details");
		field.setHTMLField("skills", "Test learning objective"); //learning objective
		fExecutor.execSync("g_form.setValue('tags', '7efc27bc1b5ebf403a2310ad2d4bcbb8');");//sysid for Action Designer tag
		
		tab.view("Outcomes");
		field.setRefField("badge", badgeName);
		
		button.click("sysverb_insert");
		timers.waitUntilDOMReady(30);
		return pathName;
	}
}
