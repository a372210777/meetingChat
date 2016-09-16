package com.example.model2;

import android.app.Activity;

import com.example.meeting.HostActivity;
import com.example.meeting.LookIdeaActivity;
import com.example.model.ParseJson;
import com.example.model.Vote;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

/**
 * 议员提交投票
 * */
public class CommitVoteWorker implements Worker{

	private Vote vote;
	private Activity activity;
	
	private boolean isHost=false;
	
	public CommitVoteWorker(Vote vote,Activity activity){
		this.vote=vote;
		this.activity=activity;
	}
	public CommitVoteWorker(Vote vote,Activity activity,boolean isHost){
		this.vote=vote;
		this.activity=activity;
		this.isHost=isHost;
	}
	@Override
	public String getResponse() {
		String response=Tool.requestData(vote, activity);
		return response;
	}

	@Override
	public void parseResult(String result) {
		
		if(result==null){
			Tool.showToast("网络异常！", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("投票失败!"+json.getReason(), activity);
			return;
		}else{
			if(isHost){
				((HostActivity)activity).setVoted(true);
			}else{
				((LookIdeaActivity)activity).setVoted(true);
			}
			
			Tool.showToast("投票成功！", activity);
		}
	}

}
