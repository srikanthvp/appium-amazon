package com.qa.pages.SearchResult;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.Keys;

public class SearchResultsPage extends BaseTest {
	TestUtils utils = new TestUtils();

	@AndroidFindBy (id = "action_bar_home_logo")
	private MobileElement landingPageHomeIcon;

	@AndroidFindBy (id = "rs_search_src_text")
	private MobileElement searchItemsFld;

	public Boolean landingPageHomeIconIsPresent() {
		return isElementVisible(landingPageHomeIcon);
	}

	public Boolean landingPageSearchFldPresent() {
		return isElementVisible(searchItemsFld);
	}

	public SearchResultsPage landingPageSearch(String data){
		searchItemsFld.clear();
		searchItemsFld.sendKeys(data);
		searchItemsFld.sendKeys(Keys.ENTER);
		return new SearchResultsPage();
	}
}
