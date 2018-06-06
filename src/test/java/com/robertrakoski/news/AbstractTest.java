package com.robertrakoski.news;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractTest {

	Article getStub1Article() {
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
	
	Article getStub2Article() {
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
	
	ArticleWrapper getStubArticleWrapperByCountryAndCategory() {
		String country = "pl";
		String category = "technology";
		List<Article> articles = new LinkedList<>();
		articles.add(getStub1Article());
		articles.add(getStub2Article());
		return new ArticleWrapper(articles).country(country).category(category);
	}
	
	ArticleWrapper getStubArticleWrapperByQuery() {
		String query = "ohio";
		List<Article> articles = new LinkedList<>();
		articles.add(getStub1Article());
		articles.add(getStub2Article());
		return new ArticleWrapper(articles).query(query);
	}
	
    String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
