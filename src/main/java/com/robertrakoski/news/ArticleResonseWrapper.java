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

	public String getCountry() {
		return country;
	}

	public String getCategory() {
		return category;
	}

	public List<Article> getArticles() {
		return articles;
	}
}
