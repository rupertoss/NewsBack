package com.robertrakoski.news;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
class ArticleFetcher {

	private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines";
	private static final String API_KEY = "0d75e948f6d94287b63e485b74145b79";
	
	List<Article> getArticlesByCountryAndCategory(String country, String category) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(NEWS_API_URL)
			.append("?pagesize=100")
			.append("&country=").append(country)
			.append("&category=").append(category)
			.append("&apiKey=").append(API_KEY);
		String url = sb.toString();
		
		return fetchArticles(url);
	}
	
	List<Article> getArticlesByQuery(String query) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(NEWS_API_URL)
			.append("?pagesize=100")
			.append("&q=").append(query)
			.append("&apiKey=").append(API_KEY);
		String url = sb.toString();
		
		return fetchArticles(url);
	}
	
	private List<Article> fetchArticles(String url) throws Exception {
		List<Article> articles = new LinkedList<>();
			articles = urlJsontoArticles(new URL(url));
		
		return articles;
	}

	private List<Article> urlJsontoArticles(URL url) throws Exception {
		ArticleWrapper wrapper = new ObjectMapper().readValue(url, ArticleWrapper.class);
		return wrapper.getArticles();
	}
}
