package com.qa.tests;

import android.util.EventLogTags;
import com.qa.BaseTest;
import com.qa.Exceptions.loginFailedException;
import com.qa.MenuPage;
import com.qa.pages.Landing.LandingPage;
import com.qa.pages.Login.LoginPasswordPage;
import com.qa.tests.helper.LandingPageHelper;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.lang.reflect.Method;

// Landing page after successful login
public class LandingPageTests extends MenuPage {
	LoginPasswordPage loginPasswordPage;
	LandingPage landingPage;
	JSONObject loginUsers;
	LandingPageHelper landingPageHelper;
	TestUtils utils = new TestUtils();

	//TODO : Data provider has to be improvised
	  @BeforeClass
	  public void beforeClass() throws Exception {
		  softAssert = new SoftAssert();
			InputStream datais = null;
		  try {
			  String dataFileName = "data/loginUsers.json";
			  datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			  JSONTokener tokener = new JSONTokener(datais);
			  loginUsers = new JSONObject(tokener);
		  } catch(Exception e) {
			  e.printStackTrace();
			  throw e;
		  } finally {
			  if(datais != null) {
				  datais.close();
			  }
		  }

	  }

	  @AfterClass
	  public void afterClass() {
	  }
	  
	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");
		  loginPasswordPage = new LoginPasswordPage();
		  landingPage = new LandingPage();
		  landingPageHelper = new LandingPageHelper();
	  }

	  @AfterMethod
	  public void afterMethod() {
	  }


	  @Test(description = "validate the landing page after the user successfully logs in into the application")
	  public void landingPage() throws loginFailedException {

		  landingPage = loginPasswordPage.loginWith(loginUsers.getJSONObject("validUser").getString("username"),
				  loginUsers.getJSONObject("validUser").getString("password"));

		  Assert.assertTrue(landingPage.landingPageHomeIconIsPresent());
		  Assert.assertTrue(landingPage.landingPageSearchFldPresent());
		  Assert.assertTrue(barBurgerIconPresent());
		  Assert.assertTrue(checkoutCartIconPresent());
		  Assert.assertTrue(voiceBtnIconPresent());
	  }

}
