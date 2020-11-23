package com.snc.surf.marketing.NLearning.objectData;

public class CardDetails {

    private String categoryTypeStr,durationStr,cardTypeStr,certificationStr,cardNameStr,cardDescrStr,cardValueStr;
    private int notRatedStarsStr,courses;


    public CardDetails()
    {}

    public CardDetails(String catgryType, String duration,String cardType,String certfcationType, int notRatedStars,String crdName,String crdDesc,String crdValue,int coursesNum)
    {
        setCategoryTypeStr(catgryType);
        setDurationStr(duration);
        setCardTypeStr(cardType);
        setCertificationStr(certfcationType);
        setNotRatedStarsStr(notRatedStars);
        setCardNameStr(crdName);
        setCardDescrStr(crdDesc);
        setCardValueStr(crdValue);
        setCoursesCount(coursesNum);
    }
    
 //--------------------------------------------------------
    public CardDetails(String catgryType,int notRatedStars,String crdName,String crdDesc)
    {
        setCategoryTypeStr(catgryType);
        //setDurationStr(duration);
        //setCardTypeStr(cardType);
        //setCertificationStr(certfcationType);
        setNotRatedStarsStr(notRatedStars);
        setCardNameStr(crdName);
        setCardDescrStr(crdDesc);
       // setCardValueStr(crdValue);
       //  setCoursesCount(coursesNum);
    }
    
 //--------------------------------------------------------

    public void setCoursesCount(int coursesNum )
    {
        this.courses = coursesNum;
    }

    public int getCourses() {
        return this.courses;
    }

    public String getCategoryTypeStr() {
        return categoryTypeStr;
    }

    public void setCategoryTypeStr(String categoryTypeStr) {
        this.categoryTypeStr = categoryTypeStr;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(String durationStr) {
        this.durationStr = durationStr;
    }

    public String getCardTypeStr() {
        return cardTypeStr;
    }

    public void setCardTypeStr(String cardTypeStr) {
        this.cardTypeStr = cardTypeStr;
    }

    public String getCertificationStr() {
        return certificationStr;
    }

    public void setCertificationStr(String certificationStr) {
        this.certificationStr = certificationStr;
    }

    public int getNotRatedStarsStr() {
        return (5-notRatedStarsStr);
    }

    public void setNotRatedStarsStr(int notRatedStarsStr) {
        this.notRatedStarsStr = notRatedStarsStr;
    }

    public String getCardNameStr() {
        return cardNameStr;
    }

    public void setCardNameStr(String cardNameStr) {
        this.cardNameStr = cardNameStr;
    }

    public String getCardDescrStr() {
        return cardDescrStr;
    }

    public void setCardDescrStr(String cardDescrStr) {
        this.cardDescrStr = cardDescrStr;
    }

    public String getCardValueStr() {
        return cardValueStr;
    }

    public void setCardValueStr(String cardValueStr) {
        this.cardValueStr = cardValueStr;
    }

}
