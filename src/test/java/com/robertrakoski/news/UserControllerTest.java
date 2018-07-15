package com.robertrakoski.news;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.persistence.EntityNotFoundException;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureWebMvc
public class UserControllerTest extends AbstractTest {
	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	UserService userService;
	
	@InjectMocks
	UserController userController;
	
	@Test
	public void testGetUser_whenValidRequest_shouldRespondStatus200() throws Exception {
		
		User user = getUserStub1();
		String stubAsString = mapToJson(user);
		Long userId = 1L;
		
		when(userService.getById(userId)).thenReturn(user);
		
		String url = "/users/{userId}";
		
		MvcResult result = mvc.perform(get(url, userId)).andReturn();
		
		int status = result.getResponse().getStatus();
		String content = result.getResponse().getContentAsString();
		
		assertEquals("expected HTTP status 200", 200, status);
		assertTrue("expected HTTP content length > 0", content.trim().length() > 0);
		assertTrue("expected response body to match", content.equals(stubAsString));
	}
	
	@Test
	public void testGetUser_whenInvalidUserId_shouldRespondStatus404() throws Exception {
		
		Long userId = Long.MAX_VALUE;
		
		when(userService.getById(userId)).thenThrow(new EntityNotFoundException());
		
		String url = "/users/{userId}";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url, userId)).andReturn();
		
		int status = result.getResponse().getStatus();
		String content = result.getResponse().getContentAsString();
		
		assertEquals("expected HTTP status 404", 404, status);
		assertTrue("expected HTTP content length > 0", content.trim().length() > 0);
	}
	
	@Test
	public void testCreateUser_shouldRespondStatus201() throws Exception {
		User user = getUserStub1();
		
		when(userService.create(any(User.class))).thenReturn(user);
		
		String url = "/users";
		String inputJson = mapToJson(user);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
								.post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
								.accept(MediaType.APPLICATION_JSON_VALUE)
								.content(inputJson))
								.andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		assertEquals("expected HTTP status 201", 201, status);
		assertTrue("expected HTTP content length > 0", content.trim().length() > 0);
		
		User createdUser = mapFromJson(content, User.class);
		
		assertNotNull("expected not null entity", createdUser);
		assertTrue("expected body to match", user.toString().equals(createdUser.toString()));
	}
}
