package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MyListsPageObject extends MainPageObject {

    public static final String
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']",
            ARTICLE_TITLE_IN_LIST = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title']";


    public static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    public static String getSavedArticleXpathByTitle(String title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", title);
    }

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                10
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                5
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                5
        );
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article"
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public WebElement getLastArticleInTheList() {
        return waitForElementPresent(
                ARTICLE_TITLE_IN_LIST,
                "there is no article left",
                5);
    }

    public String getNameOfTheLastArticleInTheList() {
        return getLastArticleInTheList().getText();
    }
}
