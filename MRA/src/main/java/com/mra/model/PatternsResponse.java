package com.mra.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatternsResponse {
	
	private int id;
	private String name;
	private String content_type;
	private long size;
	private String url;

}
