package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.List;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");

    }

    @Test
    public void testCheckTextInSearchField() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String textFromSearchLine = searchPageObject.getTextFromSearchLine();

        assertEquals(
                "Cannot find 'Search...' text in Search field",
                "Search…",
                textFromSearchLine);

    }

    @Test
    public void testCheckAllResults() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        List<String> titles_of_results = searchPageObject.getListOfTitlesOfResults();

        for (int i = 0; i < titles_of_results.size(); i++) {
            assertTrue("There is no Java at " + i + " line",
                    titles_of_results.get(i).contains("Java") || titles_of_results.get(i).contains("java"));
        }
    }


    @Test
    public void testCancelSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testAmountOfNotEmptySearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String search_line = "Linkin Park diskography";
        searchPageObject.typeSearchLine(search_line);
        int amount_of_search_result = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We fount too few results",
                amount_of_search_result > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        String search_line = "sdfdfhhfhg";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testArticleAndDescription(){

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("red");

        searchPageObject.waitForElementByTitleAndDescription("Red Hot Chili Peppers","American rock band");
        searchPageObject.waitForElementByTitleAndDescription("Reddit","Online news aggregator");
        searchPageObject.waitForElementByTitleAndDescription("Red hair","Hair color");


    }


}

