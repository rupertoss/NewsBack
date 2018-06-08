package com.robertrakoski.news;

import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.stereotype.Service;

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
		
		try {
			JsonObject jsonObject = readURLtoJsonObject(url);
			articles = convertJsonObjectToArticles(jsonObject);
		} catch (Exception e) {
			throw new Exception("Could not read data");
		} finally {
			if(articles.size() == 0)
				throw new Exception("Could not read data");
		}
		
		return articles;
	}

	private JsonObject readURLtoJsonObject(String urlString) throws Exception {
		URL url = new URL(urlString);
		JsonObject jsonObject;
		
		try(InputStream inputStream = url.openStream();
			JsonReader jsonReader = Json.createReader(inputStream)) {
			jsonObject = jsonReader.readObject();
		} 
		
		return jsonObject;
	}
	
	private List<Article> convertJsonObjectToArticles(JsonObject jsonObject) {
		List<Article> articles = new LinkedList<>();
		JsonArray results;
		results = jsonObject.getJsonArray("articles");
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
			String id = result.getJsonObject("source").getString("id", "");
			String name = result.getJsonObject("source").getString("name");
			Source source = new Source(id, name);
			String author = result.getString("author", "");
			String title = result.getString("title");
			String description = result.getString("description", "");
			Instant date = OffsetDateTime.parse(result.getString("publishedAt")).toInstant();
			String articleUrl = result.getString("url");
			String imageUrl = result.getString("urlToImage", "");
			articles.add(new Article(source, author, title, description, articleUrl, imageUrl, date));
		}
		return articles;
	}
}
