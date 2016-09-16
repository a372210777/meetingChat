package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class GetMessage implements InitParam{

	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("messageid", "2814"));
	}

	@Override
	public String getUrl() {
		return Constant.MN_getMessage;
	}

	
}
