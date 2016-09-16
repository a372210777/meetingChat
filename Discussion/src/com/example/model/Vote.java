package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class Vote implements InitParam{

	private String ideaIds;
	private String scores;
	
	public Vote(String ideaIds,String scores){
		this.ideaIds=ideaIds;
		this.scores=scores;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("ideaid",ideaIds ));
		pairs.add(new BasicNameValuePair("score", scores));
	}

	@Override
	public String getUrl() {
		return Constant.MN_vote;
	}

}
