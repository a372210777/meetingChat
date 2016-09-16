package com.example.model2;

import android.app.Activity;
import android.content.Intent;

import com.example.meeting.ShowResultActivity;
import com.example.model.ParseJson;
import com.example.model.ShowResult;
import com.example.utilities.Constant;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class AnounceResultWorker implements Worker{

	private ShowResult rs;
	private Activity activity;
	
	public AnounceResultWorker(ShowResult rs,Activity activity){
		this.rs=rs;
		this.activity=activity;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(rs, activity);
	}

	@Override
	public void parseResult(String result) {
		
		if(result==null){
			Tool.showToast("网络异常！", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("操作失败!"+json.getReason(), activity);
			return;
		}else{
			Intent it=new Intent(activity,ShowResultActivity.class);
			it.putExtra("identity", Constant.HOST);
			activity.startActivity(it);
			Tool.showToast("操作成功！", activity);
		}
	}

}
