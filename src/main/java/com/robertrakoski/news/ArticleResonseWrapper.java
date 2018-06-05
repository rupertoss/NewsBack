package com.robertrakoski.news;

import java.util.List;

class ArticleResponseWrapper {
	
	private String country;
	private String category;
	private List<Article> articles;
	
	ArticleResponseWrapper(String country, String category, List<Article> articles) {
		this.country = country;
		this.category = category;
		this.articles = articles;
	}
	
	String getCountry() {
		return country;
	}
	
	String getCategory() {
		return category;
	}
	
	List<Article> getArticles() {
		return articles;
	}
}
