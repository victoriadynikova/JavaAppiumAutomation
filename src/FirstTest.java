import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject mainPageObject;

    protected void setUp()throws Exception {
        super.setUp();

        mainPageObject = new MainPageObject(driver);
    }


    @Test
    public void testSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testCheckTextInSearchField() {
        //click on 'Search Wiki' field
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        //get the input field element
        WebElement element = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find Search Line",
                5);

        //check the text in the input field
        Assert.assertEquals("Cannot find 'Search...' text in Search field",
                "Search…",
                element.getText());

    }

    @Test
    public void testCancelSearch() {
        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);

        mainPageObject.waitForElementPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Result list wasn't found",
                5);

        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        mainPageObject.waitForElementNotPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Result is still on the page",
                5
        );

    }


    @Test
    public void testCompareArticleTitle() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot click on article",
                5);
        WebElement title_element = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }

    @Test
    public void testCheckAllResults() {
        mainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);
        List<WebElement> result_list = mainPageObject.waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Result list wasn't found",
                5
        );
        List<String> titles_of_results = new ArrayList<>();
        for (WebElement element : result_list) {
            titles_of_results.add(element.findElement(By.id("org.wikipedia:id/page_list_item_title")).getText());
        }

        for (int i = 0; i < titles_of_results.size(); i++) {
            Assert.assertTrue("There is no Java at " + i + " line",
                    titles_of_results.get(i).contains("Java") || titles_of_results.get(i).contains("java"));
        }
    }


    @Test
    public void testSwipeArticle() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "cannot find search input",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Search Wikipedia' input",
                5);
        WebElement title_element = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find 'Appium' article in Search",
                15
        );
        mainPageObject.swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );

    }

    @Test
    public void testSaveFirstArticleToMyList() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find a button to open article options",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );
        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );

        String name_of_folder = "Learning programming";

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder name",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );
        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );
        mainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Saved article not deleted",
                5
        );

    }

    /*
    Lesson 3 Ex 5
     */

    @Test
    public void testSaveTwoArticlesToMyList() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find a button to open article options",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );
        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );

        String name_of_folder = "Java and Appium Articles";

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder name",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "cannot find search input",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']"),
                "Cannot find the required article about Appium",
                5);
        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find a button to open article options",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot pick the right folder",
                5);

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );
        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );
        mainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Saved article not deleted",
                5
        );


        String remaining_article_name = mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "there is no article left",
                5)
                .getText();

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + remaining_article_name + "']"),
                "cannot click on " + remaining_article_name + " article",
                5
        );

        WebElement title_element = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "Article Title in the folder and Article Title on the page are different",
                remaining_article_name,
                article_title
        );

    }


    @Test
    public void testAmountOfNotEmptySearch() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String search_line = "Linkin Park diskography";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "cannot find search input",
                5);

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
                "/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        mainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request: " + search_line,
                15
        );

        int amount_of_search_result = mainPageObject.getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue("We fount too few results", amount_of_search_result > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        String search_line = "sdfdfhhfhg";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "cannot find search input",
                5);

        String search_result_locator = "org.wikipedia:id/page_list_item_container";
        String empty_result_label = "//*[@text='No results found']";

        mainPageObject.waitForElementsPresent(
                By.xpath(empty_result_label),
                "Cannot find Empty result label by request " + search_line,
                15
        );
        mainPageObject.assertElementNotPresent(
                By.id(search_result_locator),
                "We've found some results by request " + search_line
        );

    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {

            mainPageObject.waitForElementAndClick(
                    By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                    "Cannot find 'Search Wikipedia' input",
                    5);
            String search_line = "Java";
            mainPageObject.waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text,'Search…')]"),
                    search_line,
                    "cannot find search input",
                    5);
            mainPageObject.waitForElementAndClick(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                    "Cannot find ' Object-oriented programming language' topic by " + search_line,
                    5);

            String title_before_rotation = mainPageObject.waitForElementPresentAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "Cannot find title of article",
                    15
            );

            driver.rotate(ScreenOrientation.LANDSCAPE);

            String title_after_rotation = mainPageObject.waitForElementPresentAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "Cannot find title of article",
                    15
            );

            Assert.assertEquals(
                    "Article title have been changed after screen  rotation ",
                    title_before_rotation,
                    title_after_rotation
            );

            driver.rotate(ScreenOrientation.PORTRAIT);

            String title_after_second_rotation = mainPageObject.waitForElementPresentAndGetAttribute(
                    By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "Cannot find title of article",
                    15
            );

            Assert.assertEquals(
                    "Article title have been changed after screen  rotation ",
                    title_before_rotation,
                    title_after_second_rotation
            );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);
        mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5);
        driver.runAppInBackground(2);
        mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'article after returning from background",
                5);
    }

    @Test
    public void testTitlePresent() {
        mainPageObject.waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        mainPageObject.waitForElementAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot click on article",
                5);
        mainPageObject.assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "Article Title not found");

    }

}