package com.qa.pages.Login;

import com.qa.BaseTest;
import com.qa.Exceptions.loginFailedException;
import com.qa.pages.Landing.LandingPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class LoginPasswordPage extends BaseTest {
	TestUtils utils = new TestUtils();
	JSONObject loginUsers;
	SignInPage signInPage = new SignInPage();
	LoginUserNamePage loginUserNamePage = new LoginUserNamePage();
	LoginPasswordPage loginPasswordPage;
	LandingPage landingPage  = new LandingPage();

	@AndroidFindBy (id = "ap_email_login")
	private MobileElement usernameTxtFld;

	@AndroidFindBy (xpath = "//*[@text='Enter your password']")
	private MobileElement blankPasswordErrorMsgTxt;

	@AndroidFindBy (xpath = "//android.widget.Button[@text='Login']")
	private MobileElement loginBtn;

	@AndroidFindBy (xpath = "//android.view.View[2][@text='Login']")
	private MobileElement loginPageBannerTxt;

	@AndroidFindBy (className = "android.widget.EditText")
	private MobileElement passwordTxtFld;

	@AndroidFindBy (xpath = "//*[@text='Your password is incorrect']")
	private MobileElement incorrectPasswordErrorMsg;

	public Boolean blankPasswordErrorMsgTxtIsPresent(){
		return isElementVisible(blankPasswordErrorMsgTxt);
	}

	public LandingPage pressLoginBtn() {
		click(loginBtn);
		return new LandingPage();
	}

	public Boolean loginPageBannerTextIsPresent(){
		return isElementVisible(loginPageBannerTxt);
	}

	public Boolean incorrectPasswordErrorMsgIsPresent(){
		return isElementVisible(incorrectPasswordErrorMsg);
	}

	public LoginPasswordPage enterPassword(String username) {
		clear(passwordTxtFld);
		sendKeys(passwordTxtFld, username, "login with " + username);
		return this;
	}

	public LandingPage loginWith(String userName, String password) throws loginFailedException {

		loginUserNamePage = signInPage.pressSignInBtn();
		Boolean bool = loginUserNamePage.welcomeMsgIsPresent();

		try {
			if(bool) {
				loginUserNamePage.pressLoginRadioBtn();
				loginUserNamePage.enterUserName(userName);
				loginPasswordPage = loginUserNamePage.pressContinueBtn();

				if (loginPasswordPage.loginPageBannerTextIsPresent()){
					loginPasswordPage.enterPassword(password);
					loginPasswordPage.pressLoginBtn();
					landingPage.selectLanguage();
				}
			}
		}catch (Exception e){
			throw new loginFailedException();
		}
		return new LandingPage();
	}
}
