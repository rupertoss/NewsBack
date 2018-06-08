package com.robertrakoski.news;

import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
class ArticleFetcher {

	private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines";
	private static final String API_KEY = "0d75e948f6d94287b63e485b74145b79";
	
	ArticleWrapper getArticlesByCountryAndCategory(String country, String category) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(NEWS_API_URL)
			.append("?pagesize=100")
			.append("&country=").append(country)
			.append("&category=").append(category)
			.append("&apiKey=").append(API_KEY);
		String url = sb.toString();
		
		return fetchArticles(url);
	}
	
	ArticleWrapper getArticlesByQuery(String query) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(NEWS_API_URL)
			.append("?pagesize=100")
			.append("&q=").append(query)
			.append("&apiKey=").append(API_KEY);
		String url = sb.toString();
		
		return fetchArticles(url);
	}
	
	private ArticleWrapper fetchArticles(String urlString) throws Exception {
			URL url = new URL(urlString);
			ArticleWrapper articleWrapper = new ObjectMapper().readValue(url, ArticleWrapper.class);
			
			if(articleWrapper.getArticles().size() == 0) 
				throw new Exception("Wrong query, articles list empty");
			
		return articleWrapper;
	}
}
