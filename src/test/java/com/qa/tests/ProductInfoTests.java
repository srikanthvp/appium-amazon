package com.qa.tests;

import com.qa.BaseTest;
import com.qa.Exceptions.loginFailedException;
import com.qa.MenuPage;
import com.qa.pages.Landing.LandingPage;
import com.qa.pages.Login.LoginPasswordPage;
import com.qa.pages.Product.ProductInfoPage;
import com.qa.pages.SearchResult.SearchResultsPage;
import com.qa.tests.helper.LandingPageHelper;
import com.qa.tests.helper.SearchResultsHelper;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.lang.reflect.Method;

public class ProductInfoTests extends BaseTest {
	LoginTests loginPage;
	LoginPasswordPage loginPasswordPage;
	LandingPage landingPage;
	JSONObject loginUsers, productInfo;
	LandingPageHelper landingPageHelper;
	SearchResultsPage searchResultsPage;
	SearchResultsHelper searchResultsHelper;
	ProductInfoPage productInfoPage;
	TestUtils utils = new TestUtils();

	//TODO : Data provider has to be improvised
	  @BeforeClass
	  public void beforeClass() throws Exception {
		  softAssert = new SoftAssert();
			InputStream datais = null;
		  try {
			  String dataFileName = "data/productInfo.json";
			  datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			  JSONTokener tokener = new JSONTokener(datais);
			  productInfo = new JSONObject(tokener);

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
		  loginPasswordPage = new LoginPasswordPage();
		  landingPageHelper = new LandingPageHelper();
		  launchApp();
	  }

	  @AfterMethod
	  public void afterMethod() {
	  	closeApp();
	  }


	  @Test
	  public void ProductInfoDisplayed() throws loginFailedException {
		  landingPage = loginPasswordPage.loginWith(productInfo.getJSONObject("validUser").getString("username"),
				  productInfo.getJSONObject("validUser").getString("password"));

		  searchResultsPage = landingPage.landingPageSearchItem(landingPageHelper.searchTV);
		  productInfoPage = searchResultsPage.searchResultListByItemName(searchResultsHelper.searcgTVByContext);
		  productInfoPage.useCurrentLocationIgnore();

		  //Product Title Validation
		  Assert.assertTrue(productInfoPage.productTitle(productInfo.getJSONObject("SanyoTv").getString("productTitle")));

		  //Menu Items Assertions
		  Assert.assertTrue(searchResultsPage.barBurgerIconPresent());
		  Assert.assertTrue(searchResultsPage.checkoutCartIconPresent());
		  Assert.assertTrue(searchResultsPage.voiceBtnIconPresent());
	  }

}
