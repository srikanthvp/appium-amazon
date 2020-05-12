package com.qa.pages.Product;

import com.qa.BaseTest;
import com.qa.pages.Login.LoginUserNamePage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

public class ProductInfoPage extends BaseTest {
	TestUtils utils = new TestUtils();

	@AndroidFindBy (id = "sign_in_button")
	private MobileElement signInBtn;

	@AndroidFindBy (id = "signin_to_yourAccount")
	private MobileElement signInBarTxt;

	@AndroidFindBy (id = "loc_ux_update_current_pin_code")
	private MobileElement useCurrentLocation;

	public LoginUserNamePage pressSignInBtn() {
		click(signInBtn);
		return new LoginUserNamePage();
	}

	public void useCurrentLocationIgnore(){
		if(isElementVisible(useCurrentLocation)) {
			backButtonTap();
		}
	}

	public boolean productTitle(String Item) {
		String xpath = "//*[@text='"+Item+"']";
		List list = getDriver().findElementsByXPath(xpath);
		if(list.size() == 0) {
			return false;
		}else {
			return true;
		}
	}
}
