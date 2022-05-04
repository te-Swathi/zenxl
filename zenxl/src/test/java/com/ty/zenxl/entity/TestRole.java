package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestRole {

	private ObjectMapper mapper = new ObjectMapper();

	Role role = Role.builder().roleId(1).roleName("TEST").build();

	String jsonRole = "{\"roleId\":1,\"roleName\":\"TEST\",\"user\":null}";

	@Test
	void testSerialization() throws JsonProcessingException, JSONException {

		String writeValueAsString = mapper.writeValueAsString(role);
		JSONAssert.assertEquals(jsonRole, writeValueAsString, false);
	}
	
	@Test
	void testDeserialization() throws JsonMappingException, JsonProcessingException {
		
		Role readValue = mapper.readValue(jsonRole, Role.class);
		assertEquals(role.getRoleName(), readValue.getRoleName());
	}

}
