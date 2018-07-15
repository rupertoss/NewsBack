package com.robertrakoski.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*")
public class ArticleController {
	
	@Autowired
	ArticleFetcher articleFetcher;
	
	@GetMapping(value = "/{country}/{category}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ArticleWrapper> getArticles(
			@PathVariable(value = "country") String country, @PathVariable(value = "category") String category) throws Exception {
		ArticleWrapper articleWrapper = articleFetcher.getArticlesByCountryAndCategory(country, category);
		articleWrapper.setCountry(country).setCategory(category);
		return new ResponseEntity<>(articleWrapper, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{query}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ArticleWrapper> getArticlesByQuery(
			@PathVariable(value = "query") String query) throws Exception {
		ArticleWrapper articleWrapper = articleFetcher.getArticlesByQuery(query);
		articleWrapper.setQuery(query);
		return new ResponseEntity<>(articleWrapper, HttpStatus.OK);
	}
	
}
