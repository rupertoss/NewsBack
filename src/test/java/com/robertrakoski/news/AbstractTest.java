package com.robertrakoski.news;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractTest {
	
    String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
    
    <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

	Article getArticleStub1() {
		String id = "gram.pl";
		String name = "Gram.pl";
		Source source = new Source(id, name);
        String author = "Katarzyna Trzyna";
        String title = "Rainbow Six: Siege - Operation Para Bellum wystartuje w najbliższym czwartek";
        String description = "Data premiery Rainbow Six: Siege - Operation Para Bellum.";
        Instant date = Instant.parse("2018-06-05T14:30:19Z");
        String articleUrl = "http://www.gram.pl/news/2018/06/05/rainbow-six-siege-operation-para-bellum-wystartuje-w-najblizszy-czwartek.shtml";
        String imageUrl = "//img6.gram.pl/dash/20180605155527.jpg";
		return new Article(source, author, title, description, articleUrl, imageUrl, date);
	}
	
	Article getArticleStub2() {
		String id = "interia.pl";
		String name = "Interia.pl";
		Source source = new Source(id, name);
        String author = "Karol Kunon";
        String title = "Oto data premiery nakładki Xiaomi MIUI 10";
        String description = "Kliknij i zobacz więcej.";
        Instant date = Instant.parse("2018-06-05T17:25:19Z");
        String articleUrl = "http://mobtech.interia.pl/news-oto-data-premiery-nakladki-xiaomi-miui-10,nId,2590224";
        String imageUrl = "https://i.iplsc.com/-/0007EIC457J8IUSF-C411.jpg";
		return new Article(source, author, title, description, articleUrl, imageUrl, date);
	}
	
	ArticleWrapper getArticleWrapperByCountryAndCategoryStub() {
		String country = "pl";
		String category = "technology";
		List<Article> articles = new LinkedList<>();
		articles.add(getArticleStub1());
		articles.add(getArticleStub2());
		return new ArticleWrapper(articles).setCountry(country).setCategory(category);
	}
	
	ArticleWrapper getArticleWrapperByQueryStub() {
		String query = "ohio";
		List<Article> articles = new LinkedList<>();
		articles.add(getArticleStub1());
		articles.add(getArticleStub2());
		return new ArticleWrapper(articles).setQuery(query);
	}
	
	User getUserStub1() {
		return new User(1L, "Number1", "password", "health");
	}
	
	User getUserStub2() {
		return new User(2L, "SecondUser", "abcdef", "business");
	}

	User getUserStub3() {
		return new User(3L, "NoFavouriteCategory", "1234456789", null);
	}
	
	User getUserStub4() {
		return new User(4L, "TheFourth", "asdffghl", "sports");
	}
}
