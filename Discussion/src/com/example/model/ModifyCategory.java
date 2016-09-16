package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

/**
 * ����������
 * */
public class ModifyCategory implements InitParam{

	private String name;
	private String categoryId;
	public ModifyCategory(String name,String categoryId){
		this.name=name;
		this.categoryId=categoryId;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("name ", name));
		pairs.add(new BasicNameValuePair("categoryid", categoryId));
	}
	

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Constant.MN_modifyCategory;
	}

}
