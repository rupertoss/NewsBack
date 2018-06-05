package com.robertrakoski.news;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

@Service
class ArticleFetcher {

	private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines?category=technology";
	private static final String API_KEY = "0d75e948f6d94287b63e485b74145b79";
	
	List<Article> fetchArticles() throws Exception {
		String url = NEWS_API_URL + "&apiKey=" + API_KEY;
		JsonObject jsonObject = readURLtoJsonObject(url);
		List<Article> fetchedArticles = new LinkedList<>();
		fetchedArticles = convertJsonObjectToArticles(jsonObject);
		return fetchedArticles;
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
	
	private List<Article> convertJsonObjectToArticles(JsonObject jsonObject)  throws Exception {
		List<Article> articles = new LinkedList<>();
		JsonArray results;
		results = jsonObject.getJsonArray("articles");
		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
			 String json = result.toString();
			 Article article = mapFromJson(json, Article.class);
			 articles.add(article);
		}
		return articles;
	}
	
    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }
}
