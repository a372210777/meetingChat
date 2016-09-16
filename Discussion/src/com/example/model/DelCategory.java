package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

/**
 * É¾³ý·ÖÀà
 * */
public class DelCategory implements InitParam{

	private String categoryId;
	public DelCategory(String categoryId){
		this.categoryId=categoryId;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("id", categoryId));
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Constant.MN_delCategory;
	}

}
