package com.snc.surf.marketing.NLearning.tests.signedInUser;

import com.snc.glide.it.runners.Step;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.core.NavMenu;
import com.snc.selenium.core.SNCTest;
import com.snc.selenium.core.TestEnvironment;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.crm.ObjectData;
import com.snc.surf.crm.UserData;
import com.snc.surf.marketing.NLearning.pageclass.*;
import com.snc.surf.marketing.NLearning.utils.Sabautils;
import com.snc.surf.marketing.NLearning.utils.Entitlements.NLEntitlement;
import com.snc.surf.marketing.NLearning.utils.NLEUtils;

import com.snc.surf.mrkt.LPTLibrary;
import com.snc.util.RestUtil;
import com.snc.util.ScreenShotUtil;
import com.snc.util.SurfUiRunner;

import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.framework.MessageLogger;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

import org.jfree.util.Log;
import org.junit.Assert;
import org.junit.runner.RunWith;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")


public class SABAtoNLUsersUpdate_IT extends  NLETestBase{

	Sabautils sabautil = new Sabautils();
	RestUtil restCall = new RestUtil();
	@Step(value = 1, info = "SABA course creation")
	public void CreateSABACourse() {

		
}
	
}
