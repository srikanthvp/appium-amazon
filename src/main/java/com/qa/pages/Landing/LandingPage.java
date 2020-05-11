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

	@AndroidFindBy (id = "action_bar_burger_icon")
	private MobileElement barBurgerIcon;

	public Boolean landingPageHomeIconIsPresent() {
		return isElementVisible(landingPageHomeIcon);
	}

	public Boolean landingPageSearchFldPresent() {
		return isElementVisible(searchItemsFld);
	}

	public Boolean landingPageBarBurgerIconPresent() {
		return isElementVisible(barBurgerIcon);
	}

	public SearchResultsPage landingPageSearch(String data){
		searchItemsFld.clear();
		searchItemsFld.sendKeys(data);
		searchItemsFld.sendKeys(Keys.ENTER);
		return new SearchResultsPage();
	}
}
