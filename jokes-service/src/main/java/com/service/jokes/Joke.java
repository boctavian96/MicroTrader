package com.service.jokes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Joke {
	private int id;
	private String joke;

	public Joke(int id, String joke) {
		this.id = id;
		this.joke = joke;
	}

	// Accessors

	@JsonProperty
	public int getId() {
		return id;
	}

	@JsonProperty
	public String getJoke() {
		return joke;
	}

}
