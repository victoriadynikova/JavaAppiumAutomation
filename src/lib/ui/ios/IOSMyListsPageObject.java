package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
        //ARTICLE_TITLE_IN_LIST = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title']";

    }
    public IOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
