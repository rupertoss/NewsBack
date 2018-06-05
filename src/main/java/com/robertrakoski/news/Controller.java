package com.robertrakoski.news;

import java.util.List;

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
public class Controller {
	
	@Autowired
	ArticleFetcher articleFetcher;
	
	@GetMapping(value = "/{country}/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleResponseWrapper> getStatus(
			@PathVariable(value = "country") String country, @PathVariable(value = "category") String category) throws Exception {
		List<Article> articles = articleFetcher.fetchArticles(country, category);
		ArticleResponseWrapper response = new ArticleResponseWrapper(country, category, articles);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
