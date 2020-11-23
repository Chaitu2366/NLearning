package com.snc.surf.marketing.NLearning.utils;

import com.glide.communications.RemoteGlideRecord;
import com.snc.glide.it.GlideNode;
import com.snc.selenium.core.SNCTest;
import com.snc.selenium.framework.MessageLogger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class GlideRecordBase extends SNCTest {

    protected Properties prop;

    public GlideRecordBase() {
        prop = new Properties();
        initializePropertiesFile();
    }

    private void initializePropertiesFile() {
        try {
            InputStream input = null;
            input = new FileInputStream("glide.it.nowLearning.properties");
            prop.load(input);
        } catch (Exception err) {
            MessageLogger.annotate("Exception Observed: " + err.getMessage());
            err.printStackTrace();
        }
    }

    // Overriden method which consumes Nl creds for authentication
    public RemoteGlideRecord getRemoteGlideRecord(String tableName) {
        RemoteGlideRecord rgr = new RemoteGlideRecord(GlideNode.getDefault().getUrl(), tableName);
        // rgr.setBasicAuth(TestEnvironment.get().getProperty("adminUser"),
        // TestEnvironment.get().getProperty("adminPassword"));
        String uName = (String) prop.get("automation_username");
        String pwd = (String) prop.get("automation_password");
        rgr.setBasicAuth(uName, pwd);
        return rgr;
    }
}
