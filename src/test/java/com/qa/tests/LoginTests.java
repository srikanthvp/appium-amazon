package com.qa.tests;

import com.qa.pages.Landing.LandingPage;
import com.qa.pages.Login.LoginPasswordPage;
import com.qa.pages.Login.LoginUserNamePage;
import com.qa.pages.Login.SignInPage;
import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.asserts.SoftAssert;

public class LoginTests extends BaseTest{
	LoginUserNamePage loginUserNamePage;
	LoginPasswordPage loginPasswordPage;
	LandingPage landingPage;
	JSONObject loginUsers;
	SignInPage signInPage;
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
		  loginUserNamePage = new LoginUserNamePage();
		  signInPage = new SignInPage();
		  launchApp();
	  }

	  @AfterMethod
	  public void afterMethod() {
	  	closeApp();
	  }

	@Test(description = "validate user should not be able to login with blank user name")
	public void blankUserName(){
		String expectedSignInBarText = getStrings().get("signIn_bar_text");

		String actualSignInText = signInPage.getSignInBarTxt();
		Assert.assertEquals(actualSignInText, expectedSignInBarText);

		loginUserNamePage = signInPage.pressSignInBtn();

		Assert.assertTrue(loginUserNamePage.welcomeMsgIsPresent());

		loginUserNamePage.pressLoginRadioBtn();
		loginUserNamePage.pressContinueBtn();

		Assert.assertTrue(loginUserNamePage.blankUserNameErrorMsgTxtIsPresent());
	}

	  @Test(description = "validate user is not able to login with invalid user name")
	  public void invalidUserName(){
	  	String expectedSignInBarText = getStrings().get("signIn_bar_text");

		  String actualSignInText = signInPage.getSignInBarTxt();
		  Assert.assertEquals(actualSignInText, expectedSignInBarText);

		  loginUserNamePage = signInPage.pressSignInBtn();

		  Assert.assertTrue(loginUserNamePage.welcomeMsgIsPresent());

		  loginUserNamePage.pressLoginRadioBtn();
		  loginUserNamePage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
		  loginUserNamePage.pressContinueBtn();

		  Assert.assertTrue(loginUserNamePage.invalidUserNameErrorMsgTxtIsPresent());
	  }

	  @Test(description = "validate user is not able to login with blank password")
	  public void blankPassword() {
		String expectedSignInBarText = getStrings().get("signIn_bar_text");

		String actualSignInText = signInPage.getSignInBarTxt();
		Assert.assertEquals(actualSignInText, expectedSignInBarText);

		loginUserNamePage = signInPage.pressSignInBtn();

		Assert.assertTrue(loginUserNamePage.welcomeMsgIsPresent());

		loginUserNamePage.pressLoginRadioBtn();
		loginUserNamePage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPasswordPage=loginUserNamePage.pressContinueBtn();

		Assert.assertTrue(loginPasswordPage.loginPageBannerTextIsPresent());

		loginPasswordPage.pressLoginBtn();
		Assert.assertTrue(loginPasswordPage.blankPasswordErrorMsgTxtIsPresent());
		}

		@Test(description = "validate user is not able to login with invalid password")
		public void invalidPassword() {
		String expectedSignInBarText = getStrings().get("signIn_bar_text");

		String actualSignInText = signInPage.getSignInBarTxt();
		Assert.assertEquals(actualSignInText, expectedSignInBarText);

		loginUserNamePage = signInPage.pressSignInBtn();

		Assert.assertTrue(loginUserNamePage.welcomeMsgIsPresent());

		loginUserNamePage.pressLoginRadioBtn();
		loginUserNamePage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPasswordPage=loginUserNamePage.pressContinueBtn();

		Assert.assertTrue(loginPasswordPage.loginPageBannerTextIsPresent());

		loginPasswordPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		loginPasswordPage.pressLoginBtn();

		Assert.assertTrue(loginPasswordPage.incorrectPasswordErrorMsgIsPresent());
		}

		@Test(description = "validate user is able to successfully login with valid credentials")
		public void successfulLogin() {
		String expectedSignInBarText = getStrings().get("signIn_bar_text");

		String actualSignInText = signInPage.getSignInBarTxt();
		Assert.assertEquals(actualSignInText, expectedSignInBarText);

		loginUserNamePage = signInPage.pressSignInBtn();

		Assert.assertTrue(loginUserNamePage.welcomeMsgIsPresent());

		loginUserNamePage.pressLoginRadioBtn();
		loginUserNamePage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
		loginPasswordPage=loginUserNamePage.pressContinueBtn();

		Assert.assertTrue(loginPasswordPage.loginPageBannerTextIsPresent());

		loginPasswordPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		loginPasswordPage.pressLoginBtn();
		}

}
