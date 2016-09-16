package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class DownLoadRecord implements InitParam{

	@Override
	public void init(List<BasicNameValuePair> pairs) {
		
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return Constant.MN_downLoadRecord;
	}

}
