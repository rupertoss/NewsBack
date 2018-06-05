package com.robertrakoski.news;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

class InstantSerializer extends JsonSerializer<Instant> {

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        String str = format.format(value);
        gen.writeString(str);
    }
}


