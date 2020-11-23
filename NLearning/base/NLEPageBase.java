package com.snc.surf.marketing.NLearning.base;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.snc.surf.marketing.NLearning.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.snc.selenium.core.SNCTest;
import com.snc.surf.marketing.NLearning.pageclass.Constants;
import com.snc.surf.marketing.NLearning.utils.NLEUtils;
import com.snc.surf.marketing.NLearning.utils.UiUtility;

public class NLEPageBase extends SNCTest implements Constants{

    protected NLEUtils leUtils;
    protected UiUtility uiUtil;
    protected WaitUtils wait;
    protected By closeWarning = By.cssSelector("span.closebtn");
    protected By loadSpinner = By.cssSelector("div.km-loader");

    public NLEPageBase() {
        uiUtil = new UiUtility();
        leUtils = new NLEUtils();
        wait = new WaitUtils();
    }

    private List< String > getTextValues(
            By loc, Function<WebElement,String > pred) {
        List< WebElement > elements = getDriver().findElements(loc);
        List< String > values = elements.stream().map(pred)
                .collect(Collectors.toList());
        return values;
    }

    protected List< String > getElementTextValues(By loc) {
        return getTextValues(loc, e -> e.getText());
    }

    protected List< String > getElementTextValuesWithSplit(By loc, String splitString) {
        return getTextValues(loc, e -> e.getText().split(splitString)[1]);
    }

    protected int getCountValue(By byLoc)
    {
        String text  = uiUtil.getText(byLoc);
        text = text.replaceAll("^[0-9]","");
        int iVal = Integer.parseInt(text);
        return iVal;
    }

    public void closeWarningAlert()
    {
        try {
            findElement(closeWarning).click();
        }
        catch(Exception err) {}
    }

    public void waitForKmSpinnerToDisappear()
    {
        uiUtil.waitForElementInvisibility(loadSpinner,25);
    }


}
