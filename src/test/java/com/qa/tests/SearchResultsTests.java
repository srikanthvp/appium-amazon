package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.Landing.LandingPage;
import com.qa.tests.helper.LandingPageHelper;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.lang.reflect.Method;

public class SearchResultsTests extends BaseTest{
	LoginTests loginPage;
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
		  loginPage = new LoginTests();
		  landingPage = new LandingPage();
		  landingPageHelper = new LandingPageHelper();
		  launchApp();
	  }

	  @AfterMethod
	  public void afterMethod() {
	  	closeApp();
	  }


	  @Test
	  public void landingPageSearchTV(){
		  loginPage.successfulLogin();

		  Assert.assertTrue(landingPage.landingPageHomeIconIsPresent());
		  Assert.assertTrue(landingPage.landingPageSearchFldPresent());

		  landingPage.landingPageSearch(landingPageHelper.searchTV);
	  }

}
