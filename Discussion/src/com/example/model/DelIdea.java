package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class DelIdea implements InitParam{

	private String ideaId;
	public DelIdea(String ideaId){
		this.ideaId=ideaId;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("id", ideaId));
	}

	@Override
	public String getUrl() {
		return Constant.MN_delIdea;
	}

}
