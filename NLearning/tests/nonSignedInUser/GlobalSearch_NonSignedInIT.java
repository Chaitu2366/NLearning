package com.snc.surf.marketing.NLearning.tests.nonSignedInUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

import org.junit.runner.RunWith;
import org.testng.Assert;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")
public class GlobalSearch_NonSignedInIT extends NLETestBase {
	String keyword = "security";
	String filter;
	List<String> courseNameList_actual = new ArrayList<String>();

    @Step(value =01, info ="Non Signed In User - global Search results based on Most Recent Sort")
    public void performGlobalSearch_MostRecentSort() {
    	filter = "Most Recent";
        courseNameList_actual = getActualSearchedResults(keyword,filter); 
        List<String> courseNameList_expected = getExpectedSearchedResults(keyword, filter, courseNameList_actual.size());
        Assert.assertEquals(courseNameList_actual, courseNameList_expected);
    }
    @Step(value =05, info ="Non Signed In User - global Search results based on Most Viewed Sort")
    public void performGlobalSearch_MostViewedSort() {
    	filter = "Most Viewed";
        List<String> courseNameList_actual = getActualSearchedResults(keyword,filter); 
        List<String> courseNameList_expected = getExpectedSearchedResults(keyword, filter, courseNameList_actual.size());
        Assert.assertEquals(courseNameList_actual, courseNameList_expected);
    }
    
    @Step(value =10, info ="Non Signed In User - global Search results based on Exact Name")
    public void performGlobalSearch_ExactContentMatch() {
    	String contentName = courseNameList_actual.get(0);
    	performExactNameSearch(contentName);
        List<String> courseNameList_actual = glbSearchPage.getContentName();
        Assert.assertTrue(courseNameList_actual.size()==1);
        Assert.assertTrue(courseNameList_actual.get(0).equalsIgnoreCase(contentName));
    }
}
