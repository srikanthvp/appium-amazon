package com.qa.pages.Landing;

import com.qa.BaseTest;
import com.qa.pages.Login.LoginUserNamePage;
import com.qa.pages.SearchResult.SearchResultsPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.Keys;

public class LandingPage extends BaseTest {
	TestUtils utils = new TestUtils();

	@AndroidFindBy (id = "action_bar_home_logo")
	private MobileElement landingPageHomeIcon;

	@AndroidFindBy (id = "rs_search_src_text")
	private MobileElement searchItemsFld;

	@AndroidFindBy (xpath = "//*[@text='English - EN']")
	private MobileElement selectLanguage;

	@AndroidFindBy (xpath = "//*[@text='Save Changes']")
	private MobileElement saveChangesBtn;

	@AndroidFindBy (xpath = "//*[@text='Fresh']")
	private MobileElement freshItemsIcon;


	public LandingPage selectLanguage(){
		click(selectLanguage);
		click(saveChangesBtn);
		return new LandingPage();
	}

	public Boolean landingPageHomeIconIsPresent() {
		return isElementVisible(landingPageHomeIcon);
	}

	public Boolean landingPageSearchFldPresent() {
		return isElementVisible(searchItemsFld);
	}

	public SearchResultsPage landingPageSearchItem(String Item){
		click(searchItemsFld);
		if (isElementVisible(searchItemsFld)) {
			sendKeysAndPressEnter(searchItemsFld, Item);
		}
		return new SearchResultsPage();
	}

//	public SearchResultsPage landingPageSearchItem(String Item){
//		sendKeysAndPressEnter(Item);
//		return new SearchResultsPage();
//	}

	public Boolean freshItemsIconPresent(){
		return isElementVisible(freshItemsIcon);
	}
}
