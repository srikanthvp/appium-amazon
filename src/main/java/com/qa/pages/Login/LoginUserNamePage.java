package com.qa.pages.Login;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginUserNamePage extends BaseTest {
	TestUtils utils = new TestUtils();

	@AndroidFindBy (xpath = "//android.view.View[@text='Login. Already a customer?']")
	private MobileElement loginRadioBtn;

	@AndroidFindBy (className = "android.widget.EditText")
	private MobileElement usernameTxtFld;

	@AndroidFindBy (className = "android.widget.Button")
	private MobileElement continueBtn;

	@AndroidFindBy (xpath = "//*[@text='There was a problem']")
	private MobileElement errorMsgTxt;

	@AndroidFindBy (xpath = "//*[@text='Welcome']")
	private MobileElement welcomMsgTxt;

	@AndroidFindBy (xpath = "//*[@text='Enter your email or mobile phone number']")
	private MobileElement blankUserNameErrorMsg;


	public Boolean welcomeMsgIsPresent(){
		return isElementVisible(welcomMsgTxt);
	}

	public void pressLoginRadioBtn(){
			click(loginRadioBtn);
	}

	public LoginUserNamePage enterUserName(String username) {
		clear(usernameTxtFld);
		sendKeys(usernameTxtFld, username, "login with " + username);
		return this;
	}

	public LoginPasswordPage pressContinueBtn(){
		click(continueBtn);
		return new LoginPasswordPage();
	}

	public Boolean invalidUserNameErrorMsgTxtIsPresent(){
		return isElementVisible(errorMsgTxt);
	}

	public Boolean blankUserNameErrorMsgTxtIsPresent(){
		return isElementVisible(blankUserNameErrorMsg);
	}

}
