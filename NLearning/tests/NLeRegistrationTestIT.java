package com.snc.surf.marketing.NLearning.tests;

import org.junit.runner.RunWith;

import com.snc.glide.it.runners.Step;
import com.snc.glide.it.runners.Timeout;
import com.snc.selenium.runner.WithUser;
import com.snc.surf.marketing.NLearning.base.NLETestBase;
import com.snc.util.SurfUiRunner;

@Timeout(-1)
@RunWith(SurfUiRunner.class)
@WithUser(defaultUser = "")

public class NLeRegistrationTestIT extends NLETestBase

{

	@Step(value =0, info ="registration")
	public void navRegisterpage() 
	{
		
	}
	
	
	
}
