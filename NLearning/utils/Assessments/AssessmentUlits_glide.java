package com.snc.surf.marketing.NLearning.utils.Assessments;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;
import com.glide.communications.RemoteGlideRecord;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.utils.GlideRecordBase;
import com.snc.util.SurfUiRunner;
import com.snc.util.SurfUtils;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class AssessmentUlits_glide extends GlideRecordBase {
	SurfUtils surfUtils = new SurfUtils();

	/**
	 * @param name : Name of the Assessment metric
	 * @return the Sys ID of the Assessment record
	 */
	public String getAssesssmentSysID(String name) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_metric_type");
		rgr.addEncodedQuery("evaluation_method=quiz^active=true^name="+name);
		rgr.query();
		rgr.next();
		return rgr.getValue("sys_id");
	}

	/**
	 * @param assessmentsysID
	 * @return the number of questions associated to it
	 */
	public int getNumberOfQuestions(String assessmentsysID) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_metric");
		String categorySysid = surfUtils.getSingleGlideRecEncodedQuery("asmt_metric_category", "metric_type="+assessmentsysID, "sys_id");
		rgr.addEncodedQuery("category="+categorySysid);
		rgr.query();
		return rgr.getRowCount();
	}

	/**
	 * @param questionSysID
	 * @return the number of choices associated to a question
	 */
	public int getNumberOfChoice(String questionSysID) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_metric_definition");
		rgr.addEncodedQuery("metric.sys_id="+questionSysID);
		rgr.query();
		return rgr.getRowCount();
	}

	/**
	 * @param assessmentsysID 
	 * @param question
	 * @return
	 */
	public Map<String, String> getQuestionInfo_glide(String assessmentsysID, String question) {
		Map<String, String> questionInfo = new HashMap<String, String>();
		String categorySysid = surfUtils.getSingleGlideRecEncodedQuery("asmt_metric_category", "metric_type="+assessmentsysID, "sys_id");
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_metric");
		rgr.addEncodedQuery("category="+categorySysid+"^questionSTARTSWITH"+question);
		rgr.query();
		if(rgr.getRowCount()==1) {
			rgr.next();

			questionInfo.put("questionSysID", rgr.getValue("sys_id"));
			questionInfo.put("questionText", rgr.getValue("question"));
			questionInfo.put("questionType", rgr.getValue("datatype"));
			if (questionInfo.get("questionType").equalsIgnoreCase("boolean"))
				questionInfo.put("correctAnswer",rgr.getDisplayValue("correct_answer_yesno"));
			else
				questionInfo.put("correctAnswer",rgr.getDisplayValue("correct_answer_choice"));
		}
		else if (rgr.getRowCount()==0)
			Assert.fail("No matching question found");
		else if (rgr.getRowCount()>1)
			Assert.fail("Multiple matching questions found");
		return questionInfo;
	}

	public String getAssessmentResult(String transcriptNumber, String examName ) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_assessment_instance");
		rgr.addEncodedQuery("u_transcript.number="+transcriptNumber+"^metric_type.name="+ examName);
		rgr.query();
		rgr.next();
		return rgr.getValue("u_result");
	}

	public String getAssessmentResultacc(String transcriptNumber, String examName ) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_assessment_instance");
		rgr.addEncodedQuery("u_transcript="+transcriptNumber+"^metric_type.name="+ examName);
		rgr.query();
		rgr.next();
		return rgr.getValue("u_result");
	}
	public String getTranscriptNumber(String studentSysID,String contentType, String contentSysID ) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_transcript");
		if(contentType.equalsIgnoreCase("path"))
			rgr.addEncodedQuery("student="+studentSysID+"^path="+contentSysID);
		else
			rgr.addEncodedQuery("student="+studentSysID+"^course="+contentSysID);
		rgr.query();
		rgr.next();
		return rgr.getValue("number");
	}

	/**
	 * @param userName 
	 * @return userSysID
	 */
	public String getUserSysID(String userName) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("sys_user");
		rgr.addEncodedQuery("emailSTARTSWITH"+userName);
		rgr.query();
		rgr.next();
		String userSysID = rgr.getValue("sys_id");
		Assert.assertNotNull("user_name is null for - " + userName, userSysID);
		Assert.assertFalse("user_name is empty for - " + userName, userSysID.isEmpty());
		return userSysID;
	}

	public String getAssessmentPassScore(String name) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_metric_type");
		rgr.addEncodedQuery("evaluation_method=quiz^active=true^name="+name);
		rgr.query();
		rgr.next();
		return rgr.getValue("u_pass_score").trim();
	}

	public Map<String, String> getAssessmentValues(String asmntName) {
		Map<String, String> asmtValues = new HashMap<String, String>();
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_metric_type");
		rgr.addEncodedQuery("evaluation_method=quiz^active=true^name="+asmntName);
		rgr.query();
		rgr.next();
		asmtValues.put("sysId", rgr.getValue("sys_id"));
		asmtValues.put("Assessment Duration",rgr.getDisplayValue("duration"));
		asmtValues.put("Pass Score",rgr.getValue("u_pass_score").trim());
		asmtValues.put("Quiz Type",rgr.getValue("asmt_metric_type"));
		asmtValues.put("Allow retake",rgr.getDisplayValue("allow_retake"));
		asmtValues.put("Set retake limit",rgr.getDisplayValue("u_set_limit"));
		asmtValues.put("Retake limit",rgr.getValue("u_retake_limit"));
		asmtValues.put("Set retake wait time",rgr.getDisplayValue("u_set_wait_time"));
		asmtValues.put("Retake wait time",rgr.getValue("u_retake_wait_time"));
		return asmtValues;
	}

	public int retakesLeft(String examName, String transcriptNumber) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_assessment_instance");
		rgr.addEncodedQuery("u_transcript.number="+transcriptNumber+"^metric_type.name="+examName);
		rgr.query();
		rgr.next();
		return Integer.parseInt(rgr.getValue("u_retakes_left"));
	}
	public String asmntInstanceState(String asmntName, String transcriptNumber) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_assessment_instance");
		rgr.addEncodedQuery("u_transcript.number="+transcriptNumber+"^metric_type.name="+asmntName);
		rgr.query();
		rgr.next();
		return rgr.getValue("state");
	}
	public String asmntInstanceSysID(String asmntName, String transcriptNumber) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_assessment_instance");
		rgr.addEncodedQuery("u_transcript.number="+transcriptNumber+"^metric_type.name="+asmntName);
		rgr.query();
		rgr.next();
		return rgr.getValue("sys_id");
	}
	public void disableAsmntWaitTime(String examName) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_metric_type");
		rgr.addEncodedQuery("evaluation_method=quiz^active=true^name="+examName);
		rgr.query();
		rgr.next();
		if(rgr.getRowCount()==1) {
			rgr.setValue("u_set_wait_time", false);
			rgr.setValue("u_retake_wait_time", "");
			rgr.update();
		}
		else
			Assert.assertTrue("Multiple Records are present in backend", false);
	}
	public void updateAsmntRetakeLimit(String examName, String limitToBeUpdated) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("asmt_metric_type");
		rgr.addEncodedQuery("evaluation_method=quiz^active=true^name="+examName);
		rgr.query();
		rgr.next();
		if(rgr.getRowCount()==1) {
			rgr.setValue("u_retake_limit",limitToBeUpdated );
			rgr.update();
		}
		else
			Assert.assertTrue("Multiple Records are present in backend", false);
	}
	/**
	 * @param type : prove the type of assessment required i.e. quiz, exam or Knowledge check
	 * @return  sys_id of the course
	 */
	public String getCourseWithAssessment(String type) {
		RemoteGlideRecord rgr = getRemoteGlideRecord("x_snc_lxp_course");
		rgr.addEncodedQuery("active=true^exam!=NULL^exam.u_quiz_type="+type);
		rgr.query();
		if(rgr.getRowCount() < 1)
			Assert.assertTrue("Record not present in backend", false);
		else
			rgr.next();
		return rgr.getValue("sys_id");
	}
}

