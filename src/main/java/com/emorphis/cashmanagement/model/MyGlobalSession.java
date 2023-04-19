package com.emorphis.cashmanagement.model;

import org.springframework.stereotype.Component;

@Component
public class MyGlobalSession {

	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) { 
		this.url = url;
	}
	
	
}
