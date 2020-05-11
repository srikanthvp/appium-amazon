package com.qa.pages.Login;

import com.qa.BaseTest;
import com.qa.pages.Landing.LandingPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPasswordPage extends BaseTest {
	TestUtils utils = new TestUtils();

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

}
