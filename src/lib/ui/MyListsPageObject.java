package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import lib.Platform;

import java.util.ArrayList;
import java.util.List;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            ARTICLE_TITLE_IN_LIST;


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
        this.waitForElementPresent(folder_name_xpath, "Cannot find folder by name " + name_of_folder, 10);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                10
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        //String article_xpath = getFolderXpathByName(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                5
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
//        String article_xpath = getFolderXpathByName(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
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

        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article ");
        }

        this.waitForArticleToDisappearByTitle(article_title);
    }

    public WebElement getLastArticleInTheList() {
        return waitForElementPresent(
                ARTICLE_TITLE_IN_LIST,
                "there is no article left",
                5);
    }

    public List<WebElement> getAllArticlesFromTheList() {
        return waitForElementsPresent(
                ARTICLE_TITLE_IN_LIST,
                "there is no article left",
                5);
    }



    public String getNameOfTheLastArticleInTheList() {
        if (Platform.getInstance().isAndroid()) {
            return getLastArticleInTheList().getText();
        }else {
            return getLastArticleInTheList().getAttribute("name");
        }
    }

    public List <String> getListOfTitlesOfRemainingArticles() {

        List<String> remainingArticleTitles = new ArrayList<>();

        for (WebElement element : getAllArticlesFromTheList()) {
            remainingArticleTitles.add(element.getText());
        }

        return remainingArticleTitles;
    }
}
