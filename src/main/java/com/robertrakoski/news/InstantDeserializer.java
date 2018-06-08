package com.robertrakoski.news;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

class InstantDeserializer extends JsonDeserializer<Instant> {

	@Override
	public Instant deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		return OffsetDateTime.parse(p.getText()).toInstant();
	}
}
