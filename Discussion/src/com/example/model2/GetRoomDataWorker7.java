package com.example.model2;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.datamodel.Room;
import com.example.meeting.ShowResultActivity;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.utilities.Constant;
import com.example.utilities.Tool;
import com.example.utilities.Worker;
/**
 * 用户请求显示投票结果
 * */
public class GetRoomDataWorker7 implements Worker{

	private RoomData rd;
	private Activity activity;
	
	
	public GetRoomDataWorker7(RoomData rd,Activity activity){
		this.rd=rd;
		this.activity=activity;
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
			if(room.getVoteStart().equals("0")){
				Tool.showToast("投票还未开始!!", activity);
				return;
			}
			Toast.makeText(activity, "当前投票人数:"+room.getVoteNum(), Toast.LENGTH_LONG).show();
		}else{
			Tool.showToast("操作失败!"+json.getReason(), activity);
		}
		
	}

}
