package com.example.model2;

import android.app.Activity;

import com.example.model.DownLoadRecord;
import com.example.model.ParseJson;
import com.example.utilities.Constant;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class DownLoadWorker implements Worker{

	private Activity activity;
	private DownLoadRecord dr;
	
	public DownLoadWorker(Activity activity,DownLoadRecord dr){
		this.activity=activity;
		this.dr=dr;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(dr, activity);
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("网络异常！无法下载!", activity);
			return;
		}else{
			Tool.SavedToText(activity, result, Constant.Dir);
		}
	}

}
