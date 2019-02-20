package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "Dynikova",
            password = "Qwerty123";


    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticleToMySaved();
            if (Platform.getInstance().isIOS()) {
                articlePageObject.closeArticle(); //because ios app offers to login and we can skip it clicking anywhere on the screen
            }
        }

        if (Platform.getInstance().isMw()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    articlePageObject.getArticleTitle()
            );

            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        }

        myListsPageObject.swipeByArticleToDelete(article_title);

    }


    @Test
    public void testSaveTwoArticlesToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();
        String first_article_title = articlePageObject.getArticleTitle();

        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticleToMySaved();
            if (Platform.getInstance().isIOS()) {
                articlePageObject.closeArticle(); //because ios app offers to login and we can skip it clicking anywhere on the screen
            }
        }

        if (Platform.getInstance().isMw()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    articlePageObject.getArticleTitle()
            );

            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        if (Platform.getInstance().isIOS()) {
            searchPageObject.clearSearchLine();
        }
        searchPageObject.typeSearchLine("Python");
        String second_article_title = "ython (programming language)";

        //second_article_desc added for mobile-web
        String second_article_desc = "eneral-purpose, high-level programming language";
        searchPageObject.clickByArticleWithSubstring(second_article_desc);


        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();


        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(name_of_folder);
        }

        myListsPageObject.swipeByArticleToDelete(first_article_title);


        List<String> remainingArticles = myListsPageObject.getListOfTitlesOfRemainingArticles();

        Assert.assertTrue("We still have 2 articles in the list", remainingArticles.size() == 1);

        for (String articleTitle : remainingArticles) {
            Assert.assertFalse(first_article_title + " article still present in the list ",
                    articleTitle.contains(first_article_title));
            Assert.assertTrue("Remaining article doesn't contain second article title " + second_article_title,
                    articleTitle.contains(second_article_title));

        }

    }

}

