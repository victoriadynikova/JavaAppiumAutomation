package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();

        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyList(name_of_folder);
        } else{
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(name_of_folder);
        }

        myListsPageObject.swipeByArticleToDelete(article_title);

    }



    @Test
    public void testSaveTwoArticlesToMyList() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        articlePageObject.waitForTitleElement();
        String first_article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyList(name_of_folder);
        } else{
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
        }
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        if (Platform.getInstance().isIOS()) {
            searchPageObject.clearSearchLine();
        }
        searchPageObject.typeSearchLine("Python");
        String second_article_title = "Python (programming language)";
        searchPageObject.clickByArticleWithSubstring(second_article_title);

        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToExistingList(name_of_folder);
        } else{
            articlePageObject.addArticleToMySaved();
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(name_of_folder);
        }

        myListsPageObject.swipeByArticleToDelete(first_article_title);


        List<String> remainingArticles =  myListsPageObject.getListOfTitlesOfRemainingArticles();

        Assert.assertTrue("We still have 2 articles in the list", remainingArticles.size() == 1);

        for (String articleTitle: remainingArticles){
            Assert.assertFalse(first_article_title + " article still present in the list ",
                    articleTitle.contains(first_article_title));
            Assert.assertTrue("Remaining article doesn't contain second article title " + second_article_title,
                    articleTitle.contains(second_article_title));
        }

        /* Assert.assertTrue("Remaining article doesn't contain second article title " + second_article_title,
                remainingArticles.contains(second_article_title));
        Assert.assertFalse(first_article_title + " article still present in the list ",
                remainingArticles.contains(first_article_title));
                */

        /*
        String remaining_article_name_in_list = myListsPageObject.getNameOfTheLastArticleInTheList();
        System.out.println("remaining article " + remaining_article_name_in_list);
        searchPageObject.clickByArticleWithSubstring(remaining_article_name_in_list);

        Assert.assertFalse(first_article_title + " article still present in the list ",
                remaining_article_name_in_list.contains(first_article_title));
        Assert.assertTrue("Remaining article doesn't contain second article title " + second_article_title,
                remaining_article_name_in_list.contains(second_article_title));

        if (Platform.getInstance().isAndroid()) {
            String actual_article_name = articlePageObject.getArticleTitle();
            assertEquals(
                    "Article Title in the folder and Article Title on the page are different",
                    remaining_article_name_in_list,
                    actual_article_name
            );
        }
        */

    }

}

