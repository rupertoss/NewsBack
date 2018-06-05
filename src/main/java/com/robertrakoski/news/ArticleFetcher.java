package com.robertrakoski.news;

import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
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
	
	List<Article> fetchArticles(String country, String category) throws Exception {
		String url = resolveURL(country, category);
		JsonObject jsonObject = readURLtoJsonObject(url);
		List<Article> fetchedArticles = convertJsonObjectToArticles(jsonObject);
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
			String id = result.getJsonObject("source").getString("id", "");
			String name = result.getJsonObject("source").getString("name");
			Source source = new Source(id, name);
			String author = result.getString("author", "");
			String title = result.getString("title");
			String description = result.getString("description", "");
			Instant date = Instant.parse(result.getString("publishedAt"));
			String articleUrl = result.getString("url");
			String imageUrl = result.getString("urlToImage", "");
			articles.add(new Article(source, author, title, description, articleUrl, imageUrl, date));
//			 String json = result.toString();
//			 Article article = mapFromJson(json, Article.class);
//			 articles.add(article);
		}
		return articles;
	}
	
	private String resolveURL(String country, String category) {
		StringBuilder sb = new StringBuilder();
		sb.append(NEWS_API_URL)
			.append("?country=").append(country)
			.append("&category=").append(category)
			.append("&apiKey=").append(API_KEY);
		return sb.toString();
	}
	
//    private <T> T mapFromJson(String json, Class<T> clazz)
//            throws JsonParseException, JsonMappingException, IOException {
//        ObjectMapper mapper = new ObjectMapper();
//    	mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        return mapper.readValue(json, clazz);
//    }
}
