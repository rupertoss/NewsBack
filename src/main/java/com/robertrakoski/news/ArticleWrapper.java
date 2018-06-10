package com.robertrakoski.news;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class ArticleWrapper {
	
	private List<Article> articles;
	private String country;
	private String category;
	private String query;

	ArticleWrapper() {
	}

	ArticleWrapper(List<Article> articles) {
		this.articles = articles;
	}
	
	ArticleWrapper setCountry(String country) {
		this.country = country;
		return this;
	}
	
	ArticleWrapper setCategory(String category) {
		this.category = category;
		return this;
	}

	ArticleWrapper setQuery(String query) {
		this.query = query;
		return this;
	}
	
	public List<Article> getArticles() {
		return articles;
	}

	public String getCountry() {
		return country;
	}

	public String getCategory() {
		return category;
	}

	public String getQuery() {
		return query;
	}
}
