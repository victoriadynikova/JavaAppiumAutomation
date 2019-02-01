package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPageObject extends MainPageObject {

    private final static String
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]",
            SEARCH_LINE_ELEMENT = "id:org.wikipedia:id/search_src_text",
            SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "/*[./*[@resource-id='org.wikipedia:id/page_list_item_title'" +
                    " and @text='{TITLE}'] and ./*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]";
            //SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[android.widget.TextView[@text='{TITLE}'] and android.widget.TextView[@text='{DESCRIPTION}']]"),

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */

    private String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private String getResultSearchElementByTitleAndDescription(String title, String description){
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}",title).replace("{DESCRIPTION}",description);
    }

    /* TEMPLATES METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and init Search Init Element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find Search input after clicking Search Init Element");
    }

    public String getTextFromSearchLine() {
        return this.waitForElementPresent(SEARCH_LINE_ELEMENT, "Cannot find Search Line", 5).getText();
    }


    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into Search input", 5);

    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find Search Cancel Button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search Cancel Button is still present", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click SearchCancel Button", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find Search Result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click Search Result with substring " + substring, 10);
    }

    public List<WebElement> getListOfResults() {
        return waitForElementsPresent(
                SEARCH_RESULT_ELEMENT,
                "Result list wasn't found",
                5);
    }

    public List<String> getListOfTitlesOfResults() {
        List<WebElement> result_list = getListOfResults();
        List<String> titles_of_results = new ArrayList<>();
        for (WebElement element : result_list) {
            titles_of_results.add(element.findElement(By.id("org.wikipedia:id/page_list_item_title")).getText());
        }
        return titles_of_results;
    }

    public int getAmountOfFoundArticles() {

        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {

        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results");
    }

    public void waitForElementByTitleAndDescription(String title, String description){
        waitForElementPresent(
                getResultSearchElementByTitleAndDescription(title,description),
                "Article with title " + title + " and description " + description + " wasn't found",
                5
        );

    }
}
