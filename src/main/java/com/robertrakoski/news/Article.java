package com.robertrakoski.news;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
class Article {
	
	@JsonProperty("sourceName")
	@JsonAlias("source")
	@JsonSerialize(using = SourceSerializer.class)
	private Source source;
	
	@JsonProperty("author")
	private String author;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("articleUrl")
	@JsonAlias("url")
	private String url;
	
	@JsonProperty("imageUrl")
	@JsonAlias("urlToImage")
	private String urlToImage;
	
	@JsonProperty("date")
	@JsonSerialize(using = InstantSerializer.class)
	@JsonDeserialize(using = InstantDeserializer.class)
	@JsonAlias("publishedAt")
	private Instant publishedAt;

	Article() {
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

