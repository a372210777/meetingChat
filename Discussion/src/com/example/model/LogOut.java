package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class LogOut implements InitParam{

	@Override
	public void init(List<BasicNameValuePair> pairs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUrl() {
		return Constant.MN_logOut;
	}

}
