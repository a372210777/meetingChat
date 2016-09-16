package com.example.model2;

import android.app.Activity;

import com.example.model.ParseJson;
import com.example.model.StartVote;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class StartVoteWork implements Worker{

	private StartVote startVote;
	private Activity activity;
	
	public  StartVoteWork(StartVote startVote,Activity activity){
		this.startVote=startVote;
		this.activity=activity;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(startVote, activity);
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
			Tool.showToast("开始投票！", activity);
		}
	}

}
