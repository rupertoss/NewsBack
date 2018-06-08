package com.robertrakoski.news;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

class Source {
	
	@JsonIgnore
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("sourceName")
	@JsonAlias("name")
	private String name;
	
	Source() {
	}

	Source(String id, String name) {
		this.id = id;
		this.name = name;
	}

	String getId() {
		return id;
	}

	String getName() {
		return name;
	}
}