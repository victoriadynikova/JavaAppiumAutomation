package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath:(//XCUIElementTypeSearchField[@name='Search Wikipedia'])";
        //SEARCH_LINE_ELEMENT = "id:org.wikipedia:id/search_src_text";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        //SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
        //        "/*[./*[@resource-id='org.wikipedia:id/page_list_item_title'" +
        //        " and @text='{TITLE}'] and ./*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";
    }

    public IOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
