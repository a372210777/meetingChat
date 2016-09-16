package com.example.model2;

import android.app.Activity;

import com.example.meeting.HostActivity;
import com.example.meeting.LookIdeaActivity;
import com.example.model.ParseJson;
import com.example.model.Vote;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

/**
 * ��Ա�ύͶƱ
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
			Tool.showToast("�����쳣��", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("ͶƱʧ��!"+json.getReason(), activity);
			return;
		}else{
			if(isHost){
				((HostActivity)activity).setVoted(true);
			}else{
				((LookIdeaActivity)activity).setVoted(true);
			}
			
			Tool.showToast("ͶƱ�ɹ���", activity);
		}
	}

}
