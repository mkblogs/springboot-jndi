package com.tech.mkblogs.controller;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tech.mkblogs.model.Account;

import lombok.extern.log4j.Log4j2;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Log4j2
public class AccountControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@BeforeAll
	public static void beforeEachTest() throws Exception {
		log.info("==================================================================================");
		log.info("This is executed before each Test");
	}

	@AfterAll
	public static void afterEachTest() {		
		log.info("This is exceuted after each Test");
		log.info("==================================================================================");
	}
	
	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper.readValue(json, clazz);
	}
	
	@Test
	public void testAccountSaveObject() throws Exception {
		String url = "/api/account";
		String jsonString = "{" 
								+ " \"accountName\": \"TestController\"," 
								+ " \"accountType\": \"Saving\","
								+ " \"amount\":100," 
								+ " \"createdBy\":1," 
								+ " \"createdName\": \"test\" " 
							+ " } ";
		log.info(jsonString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		Account account = mapFromJson(result.getResponse().getContentAsString(),
				Account.class);
		log.info("==================================================================================");
		log.info(account);
		log.info("==================================================================================");
	}
}
