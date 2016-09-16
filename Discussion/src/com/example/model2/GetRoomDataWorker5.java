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
 * �û��ύͶƱ �Ȼ�ȡ������Ϣ
 * */
public class GetRoomDataWorker5 implements Worker{

	private RoomData rd;
	private Activity activity;
	
	private String ideaIds;
	private String scores;
	
	public GetRoomDataWorker5(RoomData rd,Activity activity,String ideaIds,String scores){
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
			Tool.showToast("�����쳣���޷�ͶƱ", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("1")){
			json.getRoomData(room);
			if(room.getVoteStart().equals(room.getVoteEnd())){
				Tool.showToast("���ȷ���ͶƱ", activity);
				return;
			}
			
			Vote v=new Vote(ideaIds.toString(),scores.toString());
			CommitVoteWorker worker=new CommitVoteWorker(v, activity,true);
			new NetWork(worker, activity,false).execute("");
		}else{
			Tool.showToast("����ʧ��!"+json.getReason(), activity);
		}
		
	}

}
