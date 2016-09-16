package com.example.utilities;


import com.actionbarsherlock.app.SherlockActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * 异步网络请求 类
 * **/
public class NetWork2 extends AsyncTask<String, Integer, String>{

	private Worker worker;
	private Activity activity;
	ProgressDialog dialog;
	
	private boolean  isShowDialog=true;
	public NetWork2(Worker worker ,Activity activity){
		this.worker=worker;
		this.activity=activity;
	}
	public NetWork2(Worker worker ,Activity activity,boolean isShowDialog){
		this.worker=worker;
		this.activity=activity;
		this.isShowDialog=isShowDialog;
	}
	@Override
	protected void onPreExecute() {
		if(isShowDialog){
			dialog = ProgressDialog.show(activity, "", "请求数据中..", true,
					true);
		}
		super.onPreExecute();
	}
	@Override
	protected String doInBackground(String... params) {
		return worker.getResponse();
	}

	@Override
	protected void onPostExecute(String result) {
		if(isShowDialog){
			dialog.dismiss();
		}
		worker.parseResult(result);
		super.onPostExecute(result);
	}

}
