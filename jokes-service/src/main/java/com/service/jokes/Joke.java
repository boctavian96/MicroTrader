package com.service.jokes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Joke {
	private String joke;
	
	public Joke(String joke) {
		this.joke = joke;
	}
	
	// Accessors
	
	@JsonProperty
	String getJoke() {
		return joke;
	}
}
