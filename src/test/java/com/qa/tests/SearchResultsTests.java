package com.qa.tests;

import com.qa.BaseTest;
import com.qa.Exceptions.loginFailedException;
import com.qa.pages.Landing.LandingPage;
import com.qa.pages.Login.LoginPasswordPage;
import com.qa.pages.Product.ProductInfoPage;
import com.qa.pages.SearchResult.SearchResultsPage;
import com.qa.tests.helper.LandingPageHelper;
import com.qa.tests.helper.SearchResultsHelper;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;

// search results page - user searches for a product in the landing page searchBar
public class SearchResultsTests extends BaseTest {
	LoginTests loginPage;
	LoginPasswordPage loginPasswordPage;
	LandingPage landingPage;
	JSONObject loginUsers;
	LandingPageHelper landingPageHelper;
	SearchResultsPage searchResultsPage;
	SearchResultsHelper searchResultsHelper;
	ProductInfoPage productInfoPage;
	TestUtils utils = new TestUtils();

	//TODO : Data provider has to be improvised
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
		  loginPage = new LoginTests();
		  landingPage = new LandingPage();
		  loginPasswordPage = new LoginPasswordPage();
		  landingPageHelper = new LandingPageHelper();
		  launchApp();
	  }

	  @AfterMethod
	  public void afterMethod() {
	  	closeApp();
	  }


	  @Test(description = "search items result page assertions")
	  public void landingPageSearchTV() throws loginFailedException {
		  landingPage = loginPasswordPage.loginWith(loginUsers.getJSONObject("validUser").getString("username"),
				  loginUsers.getJSONObject("validUser").getString("password"));

		  searchResultsPage = landingPage.landingPageSearchItem(landingPageHelper.searchTV);

		  //SearchResultPage Assertions
		  Assert.assertTrue(searchResultsPage.SearchFldPresent());

		  //Menu Items Assertion
		  Assert.assertTrue(searchResultsPage.barBurgerIconPresent());
		  Assert.assertTrue(searchResultsPage.checkoutCartIconPresent());
		  Assert.assertTrue(searchResultsPage.voiceBtnIconPresent());


	  }

}
