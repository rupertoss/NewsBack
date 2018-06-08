package com.robertrakoski.news;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
@AutoConfigureWebMvc
public class ControllerTest extends AbstractTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	ArticleFetcher artFetch;
	
	@InjectMocks
	Controller controller;
	
	@Test
	public void testGetArticlesByCountryAndCategory_shouldRespondStatus200_whenValidRequest() throws Exception {
		String country = "pl";
		String category = "technology";
		
		ArticleWrapper articleWrapper = getStubArticleWrapperByCountryAndCategory();
		String stubAsString = mapToJson(articleWrapper);
		
		when(artFetch.getArticlesByCountryAndCategory(country, category)).thenReturn(articleWrapper);
		
		String url = "/news/{country}/{category}";
		
		MvcResult result = mvc.perform(get(url, country, category)).andReturn();
		
		int status = result.getResponse().getStatus();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
		assertTrue(content.equals(stubAsString));
	}
	
	@Test
	public void testGetArticlesByQuery_shouldRespondStatus200_whenValidRequest() throws Exception {
		String query = "ohio";
		
		ArticleWrapper articleWrapper = getStubArticleWrapperByQuery();
		String stubAsString = mapToJson(articleWrapper);
		
		when(artFetch.getArticlesByQuery(query)).thenReturn(articleWrapper);
		
		String url = "/news/{query}";
		
		MvcResult result = mvc.perform(get(url, query)).andReturn();
		
		int status = result.getResponse().getStatus();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
		assertTrue(content.equals(stubAsString));
		
	}
	
	@Test
	public void testGetArticles_shouldRespondStatus500_whenExceptionIsThrown() throws Exception {
		String country = "pl";
		String category = "abcdefg";
		
		when(artFetch.getArticlesByCountryAndCategory(country, category)).thenThrow(Exception.class);
		
		String url = "/news/{country}/{category}";
		
		MvcResult result = mvc.perform(get(url, country, category)).andReturn();
		
		int status = result.getResponse().getStatus();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(500, status);
		assertTrue(content.trim().length() > 0);
	}
}
