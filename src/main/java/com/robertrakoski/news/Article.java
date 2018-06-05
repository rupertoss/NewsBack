package com.robertrakoski.news;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

class Article {
	
	@JsonIgnore
	private Source source;
	@JsonProperty("author")
	private String author;
	@JsonProperty("title")
	private String title;
	@JsonProperty("description")
	private String description;
	@JsonProperty("articleUrl")
	private String url;
	@JsonProperty("imageUrl")
	private String urlToImage;
	@JsonProperty("date")
	@JsonSerialize(using = InstantSerializer.class)
	private Instant publishedAt;
	
	@JsonProperty("sourceName")
	String getSourceName() {
		return source.getName();
	}

	protected Article(Source source, String author, String title, String description, String url, String urlToImage,
			Instant publishedAt) {
		this.source = source;
		this.author = author;
		this.title = title;
		this.description = description;
		this.url = url;
		this.urlToImage = urlToImage;
		this.publishedAt = publishedAt;
	}

	Source getSource() {
		return source;
	}

	String getAuthor() {
		return author;
	}

	String getTitle() {
		return title;
	}

	String getDescription() {
		return description;
	}

	String getUrl() {
		return url;
	}

	String getUrlToImage() {
		return urlToImage;
	}

	Instant getPublishedAt() {
		return publishedAt;
	}
}

class Source {
	private String id;
	private String name;
	
	Source(String id, String name) {
		this.id = id;
		this.name = name;
	}

	String getId() {
		return id;
	}

	String getName() {
		return name;
	}
}