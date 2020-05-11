package com.qa.pages.Login;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SignInPage extends BaseTest {
	TestUtils utils = new TestUtils();

	@AndroidFindBy (id = "sign_in_button")
	private MobileElement signInBtn;

	@AndroidFindBy (id = "signin_to_yourAccount")
	private MobileElement signInBarTxt;

	public LoginUserNamePage pressSignInBtn() {
		click(signInBtn);
		return new LoginUserNamePage();
	}

	public String getSignInBarTxt() {
		return getText(signInBarTxt, "");
	}
}
