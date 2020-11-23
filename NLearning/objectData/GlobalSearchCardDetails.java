package com.snc.surf.marketing.NLearning.objectData;

public class GlobalSearchCardDetails {

    public String getCardTypeStr() {
        return cardTypeStr;
    }

    public void setCardTypeStr(String cardTypeStr) {
        this.cardTypeStr = cardTypeStr;
    }

    public String getCardNameStr() {
        return cardNameStr;
    }

    public void setCardNameStr(String cardNameStr) {
        this.cardNameStr = cardNameStr;
    }

    public String getCategoryNameStr() {
        return categoryNameStr;
    }

    public void setCategoryNameStr(String categoryNameStr) {
        this.categoryNameStr = categoryNameStr;
    }

    public String getCourseCountStr() {
        return courseCountStr;
    }

    public void setCourseCountStr(String courseCountStr) {
        this.courseCountStr = courseCountStr;
    }

    public String getCourseCostStr() {
        return courseCostStr;
    }

    public void setCourseCostStr(String courseCostStr) {
        this.courseCostStr = courseCostStr;
    }

    private String cardTypeStr,cardNameStr,categoryNameStr,courseCountStr,courseCostStr;

    public GlobalSearchCardDetails() {}

    public GlobalSearchCardDetails(String cardType, String cardname, String categoryname, String courseCount, String courseCost)
    {
        setCardTypeStr(cardType);
        setCardNameStr(cardname);
        setCategoryNameStr(categoryname);
        setCourseCountStr(courseCount);
        setCourseCostStr(courseCost);
    }
}
