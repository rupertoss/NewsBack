package com.robertrakoski.news;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class ArticleFetcherTest {

	private ArticleFetcher artFetch = new ArticleFetcher();
	
	@Test
	public void testGetArticlesByCountryAndCategory_shouldReturnArticleList_whenValidRequest() throws Exception {
		String country = "pl";
		String category = "technology";
		List<Article> articles = artFetch.getArticlesByCountryAndCategory(country, category).getArticles();
		
		assertTrue(articles.size() > 0);
	}
	
	@Test
	public void testGetArticlesByQuery_shouldReturnArticleList_whenValidRequest() throws Exception {
		String query = "world";
		List<Article> articles = artFetch.getArticlesByQuery(query).getArticles();
		
		assertTrue(articles.size() > 0);
	}
	
	@Test(expected = Exception.class)
	public void shouldThrowException_whenInvalidRequest() throws Exception {
		String country = "unexistingCountry";
		String category = "unexistingCategory";
		artFetch.getArticlesByCountryAndCategory(country, category);
	}
	
}
