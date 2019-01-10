import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/victoria/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    /*
    Lesson 2 Ex 2
     */

    @Test
    public void testCheckTextInSearchField(){
        //click on 'Search Wiki' field
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        //get the input field element
        WebElement element = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find Search Line",
                5);

        //check the text in the input field
        Assert.assertEquals("Cannot find 'Search...' text in Search field",
                "Search…",
                element.getText());

    }

    @Test
    public void firstTest(){

        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        waitForElementByXpathAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "cannot find 'Object-oriented programming language' topic searching by Java",
                15
                );
    }

    @Test
    public void testCancelSearch(){
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementByXpathAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);

        waitForElementPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Result list wasn't found",
                5);

        waitForElementAndClick(By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(By.id("org.wikipedia:id/page_list_item_container"),
                "Result is still on the page",
                5
        );

    }


    @Test
    public void testCompareArticleTitle(){
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);
        waitForElementByXpathAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5);
        WebElement title_element = waitForElementPresent(
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
    public void testCheckAllResults(){
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementByXpathAndSendKeys(By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "cannot find search input",
                5);
        List<WebElement> result_list = waitForElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Result list wasn't found",
                5
        );
        List <String> titles_of_results = new ArrayList<>();
        for (WebElement element:result_list){
            titles_of_results.add(element.findElement(By.id("org.wikipedia:id/page_list_item_title")).getText());
        }

        for (int i = 0; i < titles_of_results.size(); i++){
            Assert.assertTrue("There is no Java at " + i + " line",
                    titles_of_results.get(i).contains("Java")||titles_of_results.get(i).contains("java"));
        }
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds ){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by,error_message,5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementByXpathAndSendKeys(By by, String value, String erroe_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, erroe_message, timeoutInSeconds);
        element.sendKeys(value );
        return element;
    }


    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by ));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.clear();
        return element;
    }

    private List<WebElement> waitForElementsPresent(By by, String error_message, long timeoutInSeconds ){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

}