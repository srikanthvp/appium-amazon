package com.qa;

import com.qa.utils.TestUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

	// This Menu Page class is collection of all web elements common in the App once login is successfull
public class MenuPage extends BaseTest{
	TestUtils utils = new TestUtils();

	@AndroidFindBy (id = "action_bar_burger_icon")
	private MobileElement barBurgerIcon;

	@AndroidFindBy (id = "action_bar_cart_image")
	private MobileElement checkoutCartIcon;

	@AndroidFindBy (id = "voice_btn_icon")
	private MobileElement voiceBtnIcon;

	public Boolean barBurgerIconPresent() {
		return isElementVisible(barBurgerIcon);
	}

	public Boolean checkoutCartIconPresent() {
		return isElementVisible(checkoutCartIcon);
	}

	public Boolean voiceBtnIconPresent() {
		return isElementVisible(voiceBtnIcon);
	}

}
