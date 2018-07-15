package com.robertrakoski.news;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
@SqlGroup({
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTest.sql"),
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTest.sql")
})
public class UserControllerIntegrationTest extends AbstractTest {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.build();
	}
	
	@Test
	public void testGetUser_whenValidRequest_shouldRespondStatus200() throws Exception {
		
		Long userId = 1L;
		
		String url = "/users/{userId}";
		
		this.mvc.perform(RestDocumentationRequestBuilders.get(url, userId))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andDo(document("get-user",
					responseFields(
							fieldWithPath("id").description("The id of the user"),
							fieldWithPath("username").description("The name of the user"),
							fieldWithPath("favouriteCategory").description("Favourite user's articles category")),
					pathParameters(
							parameterWithName("userId").description("The requested user's id"))));
	}
	
	@Test
	public void testGetUser_whenInvalidUserId_shouldRespondStatus404() throws Exception {
		
		Long userId = Long.MAX_VALUE;
		
		String url = "/users/{userId}";
		
		this.mvc.perform(RestDocumentationRequestBuilders.get(url, userId))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andDo(document("get-user-invalid-userId",
				responseFields(
						fieldWithPath("message").description("The error message"),
						fieldWithPath("errorClass").description("The error Class"))));
	}
	
	@Test
	public void testCreateUser_shouldRespondStatus201() throws Exception {
		
		User user = new User(null, "New", "password", "technology");
		String inputJson = mapToJson(user);
		
		String url = "/users";
		
		this.mvc.perform(RestDocumentationRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON_UTF8_VALUE).content(inputJson))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andDo(document("get-user-invalid-userId",
				responseFields(
						fieldWithPath("message").description("The error message"),
						fieldWithPath("errorClass").description("The error Class"))));
	}
	
}
