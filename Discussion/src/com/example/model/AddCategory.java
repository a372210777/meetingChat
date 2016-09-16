package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

/**
 * 添加观点分类
 * */
public class AddCategory implements InitParam{

	private String categoryName;
	private String roomId;
	public AddCategory(String categoryName,String roomId){
		this.categoryName=categoryName;
		this.roomId=roomId;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("name", categoryName));
//		pairs.add(new BasicNameValuePair("roomid", roomId));
//		pairs.add(new BasicNameValuePair("step", ""));
	}

	@Override
	public String getUrl() {
		return Constant.MN_addCategory;
	}

}
