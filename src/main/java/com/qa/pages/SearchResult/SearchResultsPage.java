package com.qa.pages.SearchResult;

import com.qa.BaseTest;
import com.qa.MenuPage;
import com.qa.pages.Product.ProductInfoPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;

// Search Results page Mobile elements
public class SearchResultsPage extends MenuPage {
	TestUtils utils = new TestUtils();

	@AndroidFindBy (id = "action_bar_home_logo")
	private MobileElement landingPageHomeIcon;

	@AndroidFindBy (id = "rs_search_src_text")
	private MobileElement searchItemsFld;

	public Boolean landingPageHomeIconIsPresent() {
		return isElementVisible(landingPageHomeIcon);
	}

	public Boolean SearchFldPresent() {
		return isElementVisible(searchItemsFld);
	}

	public SearchResultsPage landingPageSearch(String data){
		searchItemsFld.clear();
		searchItemsFld.sendKeys(data);
		searchItemsFld.sendKeys(Keys.ENTER);
		return new SearchResultsPage();
	}

	public ProductInfoPage searchResultListByItemName(String Item) {
		MobileElement element = scrollToElementText(Item);
		if (element != null){
			element.click();
		}else {
			throw new ElementNotVisibleException("Product Searched not found!");
		}
		return new ProductInfoPage();
	}
}
