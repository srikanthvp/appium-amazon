package com.qa.tests;

import com.qa.Exceptions.loginFailedException;
import com.qa.CommonPage;
import com.qa.pages.Landing.LandingPage;
import com.qa.pages.Login.LoginPasswordPage;
import com.qa.tests.helper.LandingPageHelper;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;

// Landing page after successful login
public class LandingPageTests extends CommonPage {
	LoginPasswordPage loginPasswordPage;
	LandingPage landingPage;
	JSONObject loginUsers;
	LandingPageHelper landingPageHelper;
	TestUtils utils = new TestUtils();

	  @BeforeClass
	  public void beforeClass() throws Exception {
		  loginUsers = dataProvider("data/loginUsers.json");
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


	  @Test(description = "validate the landing page after the user successfully logs in into the application", dataProvider = "")
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
