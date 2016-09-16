/**
 * 
 */
package com.example.model2;

import android.app.Activity;

import com.example.datamodel.Room;
import com.example.model.EndVote;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.model.ShowResult;
import com.example.model.StartVote;
import com.example.utilities.Constant;
import com.example.utilities.NetWork;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

/**
 * @author Administrator
 *
 */
public class GetRoomDataWorker4 implements Worker{

	private Activity activity;
	private RoomData rd;
	
	private int flag;
	
	public GetRoomDataWorker4(Activity activity,RoomData rd,int flag){
		this.activity=activity;
		this.rd=rd;
		this.flag=flag;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(rd, activity);
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("�����쳣���޷�����", activity);
			return;
		}
		Room room=new Room();
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("1")){	
			json.getRoomData(room);
			String startVote=room.getVoteStart();
			String endVote=room.getVoteEnd();
			switch(flag){
			case Constant.host_startVote:
				if(startVote.equals(endVote)){
					
					StartVote vote = new StartVote(room.getId());
					StartVoteWork worker = new StartVoteWork(vote, activity);
					new NetWork(worker, activity,false).execute("");
					
				}else{
					Tool.showToast("��ǰ����ͶƱ����..", activity);
				}
				break;
			case Constant.host_endVote:
				if(startVote.equals("0") || startVote.equals(endVote)){
					Tool.showToast("��δ��ʼͶƱ,�޷�ִ�в���", activity);
				}else {
					
					EndVote ev = new EndVote();
					EndVoteWorker worker = new EndVoteWorker(ev, activity);
					new NetWork(worker, activity,false).execute("");
				}
				
				break;
			case Constant.host_showResult:
				if(startVote.equals("0")){
					Tool.showToast("��δ��ʼͶƱ,�޷�ִ�в���", activity);
				}else if(!startVote.equals(endVote)){
					Tool.showToast("����ͶƱ����,��ִ�н���ͶƱ!", activity);
				}else{
					
					ShowResult rs=new ShowResult();
					AnounceResultWorker worker=new AnounceResultWorker(rs,activity);
					new NetWork(worker, activity,false).execute("");
				}
				break;
			}
			
		}else{
			Tool.showToast("��ȡ������Ϣʧ��!�޷�����", activity);
		}
	}

}
