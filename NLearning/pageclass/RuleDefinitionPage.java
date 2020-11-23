package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.openqa.selenium.By;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RuleDefinitionPage extends NLEPageBase {
    By selectChoice = By.cssSelector("table[id*='x_snc_lxp_rule_definition.filter_conditions'] span[id*='select2-chosen']");
    By submitBtn = By.cssSelector("#sysverb_insert_bottom");
    By andBtn = By.cssSelector("table[id*='x_snc_lxp_rule_definition.filter_conditions'] button[data-original-title='Add AND condition']");
    By choice2 = By.xpath("(.//table[contains(@id,'x_snc_lxp_rule_definition.filter_conditions')]//span[contains(@id,'select2-chosen')])[2]");
    By input = By.cssSelector("div#select2-drop input");
    By optionSelect = By.cssSelector("select[aria-label*='Choose option for field: Last Login']");
    By optionValue = By.xpath(".//select[@aria-label='Choose option for field: Last Login']/option[text()='Yesterday']");

    public void createRuleDefinition(String ruleName, String recurrenceVal, String badgeName,String metricUsed,String filterCondition) {
        uiUtil.waitForEleVisibility(By.id("x_snc_lxp_rule_definition.name"),20);

        field.setTextField("name",ruleName);
        field.setBooleanField("active",true);
        field.setTextField("recurrence",recurrenceVal);
        field.setRefFieldWithKeys("badge",badgeName);

        String script = "g_form.setValue('metrics_used', '%s');";
        script = String.format(script,metricUsed);
        fExecutor.execSync(script);
        uiUtil.typeAndSelectValueFromPlatformDropdown(selectChoice,input,filterCondition);
        pauseMe(2);
        if(filterCondition.contains("Last Login")) {
           /* uiUtil.clickUsingJs(optionSelect);
            uiUtil.waitForEleVisibility(optionValue,7);
            uiUtil.clickUsingJs(optionValue);*/
            uiUtil.selectOptionByVisibleText(optionSelect,"Today");
        }
        uiUtil.clickEle(submitBtn);
        timers.waitUntilDOMReady(20);
    }

    public void addFilterConditions(String[] filterCondition) {

        uiUtil.typeAndSelectValueFromPlatformDropdown(selectChoice,input,filterCondition[0]);
        uiUtil.clickUsingJs(andBtn);
        pauseMe(2);
        uiUtil.waitForEleVisibility(choice2,7);
        uiUtil.typeAndSelectValueFromPlatformDropdown(choice2,input,filterCondition[1]);
        pauseMe(2);
        uiUtil.clickUsingJs(andBtn);
        uiUtil.clickUsingJs(optionSelect);
        uiUtil.waitForEleVisibility(optionValue,7);
        uiUtil.clickUsingJs(optionValue);
    }

    public void createEntriesInLoginTracker(String user, String datePattern)
    {
        Map<String, String> hMap = null ;
        String table = "x_snc_lxp_logintracker";
        String userSysId = leUtils.getSysIDOfRecord("sys_user","email="+user,"sys_id");
        List<String> listStr = getDates(datePattern);
        String hourMinutes = leUtils.getCurrentDate("HH:mm:ss");

        for(String str : listStr) {
            hMap = new HashMap<>();
            hMap.put("user",userSysId);
            hMap.put("login_date",str);
            hMap.put("login_date_time",str+" "+hourMinutes);
            pauseMe(3);
            leUtils.insertGlideRecordValue(table,hMap);
        }
    }

    public void createEntryforSpecificUser(String user, String date)
    {
        Map<String, String> hMap = null ;
        String table = "x_snc_lxp_logintracker";
        String userSysId = leUtils.getSysIDOfRecord("sys_user","email="+user,"sys_id");

        hMap = new HashMap<>();
        hMap.put("user",userSysId);
        hMap.put("login_date",date);
        hMap.put("login_date_time",date+" 09:47:19");
        leUtils.insertGlideRecordValue(table,hMap);
    }

    public List<String> getDates(String datePattern) {
        List<String> listStr = new ArrayList<>();

        for(int iCounter=7; iCounter>0;iCounter--) {
            Calendar cal1 = Calendar.getInstance();
            int dayOfTheWeek = cal1.get( Calendar.DAY_OF_WEEK );
            cal1.add( Calendar.DAY_OF_WEEK, Calendar.SUNDAY - dayOfTheWeek-iCounter);
            System.out.println(cal1.get(Calendar.DATE));
            DateFormat df1=new SimpleDateFormat(datePattern);
            System.out.println(df1.format(cal1.getTime()));
            String date = df1.format(cal1.getTime());
            listStr.add(date);
        }return listStr;
    }


}
