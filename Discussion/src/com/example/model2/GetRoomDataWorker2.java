package com.example.model2;

import android.app.Activity;
import android.content.Intent;

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
public class GetRoomDataWorker2 implements Worker{

	private RoomData rd;
	private Activity activity;
	
	
	public GetRoomDataWorker2(RoomData rd,Activity activity){
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
				Tool.showToast("投票还未开始!无法获取结果!", activity);
			}else if(!room.getVoteStart().equals(room.getVoteEnd())){
				Tool.showToast("投票进行中!无法获取结果!", activity);
			}else if(room.getVoteNum().equals("0")){
				Tool.showToast("投票人数为0!无法获取结果!", activity);
			}else{
				 //切换到展示结果界面
				Intent it=new Intent(activity,ShowResultActivity.class);
				it.putExtra("identity", Constant.AUDIENT);
				activity.startActivity(it);
			}
			
			
		}else{
			Tool.showToast("操作失败!"+json.getReason(), activity);
		}
		
	}

}
