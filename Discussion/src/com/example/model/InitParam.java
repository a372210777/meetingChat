package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

public interface InitParam {

	
	public void init(List<BasicNameValuePair> pairs);
	public String getUrl();
}
