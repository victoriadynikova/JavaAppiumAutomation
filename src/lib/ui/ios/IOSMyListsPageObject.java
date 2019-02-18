package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
        ARTICLE_TITLE_IN_LIST = "xpath://XCUIElementTypeLink";

    }
    public IOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
