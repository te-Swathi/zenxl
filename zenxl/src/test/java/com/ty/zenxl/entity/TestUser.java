package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestUser {

	private ObjectMapper mapper = new ObjectMapper();

	User user = User.builder().userId(1).username("abc").email("abc@gmail.com").dateOfBirth(new Date("2020/10/10"))
			.gender("MALE").password("abc").role(Role.builder().roleId(1).roleName("TEST").build()).build();

	String jsonUser = "{\"userId\":1,\"username\":\"abc\",\"email\":\"abc@gmail.com\",\"dateOfBirth\":1602268200000,\"gender\":\"MALE\",\"password\":\"abc\",\"role\":{\"roleId\":1,\"roleName\":\"TEST\",\"user\":null}}";

	@Test
	void testSerialization() throws JsonProcessingException, JSONException {
		String writeValueAsString = mapper.writeValueAsString(user);
		JSONAssert.assertEquals(jsonUser, writeValueAsString, false);
	}

	@Test
	void testDeserialization() throws JsonMappingException, JsonProcessingException {
		User readValue = mapper.readValue(jsonUser, User.class);
		assertEquals(user.getUsername(), readValue.getUsername());
	}

}
