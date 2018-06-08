package com.robertrakoski.news;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

class SourceSerializer extends JsonSerializer<Source> {

	@Override
	public void serialize(Source source, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(source.getName());
	}
}
