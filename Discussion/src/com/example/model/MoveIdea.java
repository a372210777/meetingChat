package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class MoveIdea implements InitParam{

	private String ideaId;
	private String cateId;
	public MoveIdea(String ideaId,String cateId ){
		this.ideaId=ideaId;
		this.cateId=cateId;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("id", ideaId));
		pairs.add(new BasicNameValuePair("categoryid", cateId));
	}

	@Override
	public String getUrl() {
		return Constant.MN_moveIdea;
	}

}
