package com.robertrakoski.news;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ControllerIntegrationTest {

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
	public void testGetArticlesByCountryAndCategory_shouldRespondStatus200_whenValidRequest() throws Exception {
		String country = "pl";
		String category = "technology";
		
		String url = "/news/{country}/{category}";
		
		this.mvc.perform(RestDocumentationRequestBuilders.get(url, country, category))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andDo(document("get-articles-by-country-and-category",
					responseFields(
							fieldWithPath("country").description("The country of the articles"),
							fieldWithPath("category").description("The category of the articles"),
							fieldWithPath("articles").description("The list of articles"),
							fieldWithPath("articles[].author").description("The author of the article"),
							fieldWithPath("articles[].title").description("The title of the article"),
							fieldWithPath("articles[].description").description("The description of the article"),
							fieldWithPath("articles[].articleUrl").description("The hyperlink to the article"),
							fieldWithPath("articles[].imageUrl").description("The hyperlink to the image of the article"),
							fieldWithPath("articles[].date").description("The published date of the article"),
							fieldWithPath("articles[].sourceName").description("The source of the article")),
					pathParameters(
							parameterWithName("country").description("The requested articles's country"),
							parameterWithName("category").description("The requested articles's category"))));
	}
	
	@Test
	public void testGetArticlesByQuery_shouldRespondStatus200_whenValidRequest() throws Exception {
		String query = "world";
		
		String url = "/news/{query}";
		
		this.mvc.perform(RestDocumentationRequestBuilders.get(url, query))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andDo(document("get-articles-by-query",
				responseFields(
						fieldWithPath("query").description("The query request"),
						fieldWithPath("articles").description("The list of articles"),
						fieldWithPath("articles[].author").description("The author of the article"),
						fieldWithPath("articles[].title").description("The title of the article"),
						fieldWithPath("articles[].description").description("The description of the article"),
						fieldWithPath("articles[].articleUrl").description("The hyperlink to the article"),
						fieldWithPath("articles[].imageUrl").description("The hyperlink to the image of the article"),
						fieldWithPath("articles[].date").description("The published date of the article"),
						fieldWithPath("articles[].sourceName").description("The source of the article")),
				pathParameters(
						parameterWithName("query").description("The query request"))));
	}
	
	@Test
	public void testGetArticles_shouldRespondStatus500_whenInvalidRequest() throws Exception {
		String country = "unknownCountry";
		String category = "unknownCategory";
		
		String url = "/news/{country}/{category}";
		
		this.mvc.perform(get(url, country, category))
			.andExpect(status().isInternalServerError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andDo(document("error-when-invalid-request",
					responseFields(
							fieldWithPath("message").description("The error message"),
							fieldWithPath("errorClass").description("The error Class"))));
	}
}
