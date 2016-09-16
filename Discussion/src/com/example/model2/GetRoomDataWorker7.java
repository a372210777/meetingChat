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
 * �û�������ʾͶƱ���
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
			Tool.showToast("�����쳣���޷�ͶƱ", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("1")){
			json.getRoomData(room);
			if(room.getVoteStart().equals("0")){
				Tool.showToast("ͶƱ��δ��ʼ!!", activity);
				return;
			}
			Toast.makeText(activity, "��ǰͶƱ����:"+room.getVoteNum(), Toast.LENGTH_LONG).show();
		}else{
			Tool.showToast("����ʧ��!"+json.getReason(), activity);
		}
		
	}

}
