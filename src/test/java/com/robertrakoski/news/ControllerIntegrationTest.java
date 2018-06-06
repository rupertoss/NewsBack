package com.robertrakoski.news;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testGetArticlesByCountryAndCategory_shouldRespondStatus200_whenValidRequest() throws Exception {
		String country = "pl";
		String category = "technology";
		
		String url = "/news/{country}/{category}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url, country, category)).andReturn();
		
		int status = result.getResponse().getStatus();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
	}
	
	@Test
	public void testGetArticlesByQuery_shouldRespondStatus200_whenValidRequest() throws Exception {
		String query = "ohio";
		
		String url = "/news/{query}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url, query)).andReturn();
		
		int status = result.getResponse().getStatus();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(200, status);
		assertTrue(content.trim().length() > 0);
	}
	
	@Test
	public void testGetArticles_shouldRespondStatus500_whenInvalidRequest() throws Exception {
		String country = "pl";
		String category = "unknownCategory";
		
		String url = "/news/{country}/{category}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url, country, category)).andReturn();
		
		int status = result.getResponse().getStatus();
		String content = result.getResponse().getContentAsString();
		
		assertEquals(500, status);
		assertTrue(content.trim().length() > 0);
	}
}
