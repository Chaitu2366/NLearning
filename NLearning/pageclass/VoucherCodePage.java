package com.snc.surf.marketing.NLearning.pageclass;

import com.snc.surf.marketing.NLearning.base.NLEPageBase;
import org.openqa.selenium.By;

public class VoucherCodePage extends NLEPageBase {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
    protected By expdate = By.id("x_snc_lxp_voucher_code.expiry_date");

    public void createVoucherCode(String code, String certificate, String courseName, String expiryDate  ) {
        String script = "g_form.setValue('course_path','%s');";
        script = String.format(script,courseName);
        field.setTextField("code",code);
        field.setRefField("exam_valid_for",certificate);
        fExecutor.execSync(script);
        field.setTextField("expiry_date",expiryDate);
        findElement(By.id("sys_display.x_snc_lxp_voucher_code.transcript")).click();
        button.click("sysverb_insert");
        timers.waitUntilDOMReady(20);
    }



    public String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
