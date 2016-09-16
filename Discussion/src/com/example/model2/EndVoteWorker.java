package com.example.model2;

import android.app.Activity;

import com.example.model.EndVote;
import com.example.model.ParseJson;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class EndVoteWorker implements Worker{

	private EndVote end;
	private Activity activity;
	
	public EndVoteWorker(EndVote end, Activity activity){
		this.end=end;
		this.activity=activity;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(end, activity);
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
			Tool.showToast("操作成功！", activity);
		}
	}

}
