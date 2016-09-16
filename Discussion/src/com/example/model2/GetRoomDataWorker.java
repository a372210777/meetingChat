package com.example.model2;

import android.app.Activity;

import com.example.datamodel.Room;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.model.Vote;
import com.example.utilities.NetWork;
import com.example.utilities.Tool;
import com.example.utilities.Worker;
/**
 * 用户提交投票 先获取房间信息
 * */
public class GetRoomDataWorker implements Worker{

	private RoomData rd;
	private Activity activity;
	
	private String ideaIds;
	private String scores;
	
	public GetRoomDataWorker(RoomData rd,Activity activity,String ideaIds,String scores){
		this.rd=rd;
		this.activity=activity;
		this.ideaIds=ideaIds;
		this.scores=scores;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(rd, activity);
	}

	@Override
	public void parseResult(String result) {
		Room room=new Room();
		if(result==null){
			Tool.showToast("网络异常！无法投票", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("1")){
			json.getRoomData(room);
			if(room.getVoteStart().equals(room.getVoteEnd())){
				Tool.showToast("房主还未发起投票,或投票已结束", activity);
				return;
			}
			Vote v=new Vote(ideaIds,scores);
			CommitVoteWorker worker=new CommitVoteWorker(v, activity);
			new NetWork(worker, activity,false).execute("");
		}else{
			Tool.showToast("操作失败!"+json.getReason(), activity);
		}
		
	}

}
