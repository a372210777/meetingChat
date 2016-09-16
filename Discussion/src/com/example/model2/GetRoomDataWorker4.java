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
			Tool.showToast("网络异常！无法操作", activity);
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
					Tool.showToast("当前正在投票操作..", activity);
				}
				break;
			case Constant.host_endVote:
				if(startVote.equals("0") || startVote.equals(endVote)){
					Tool.showToast("还未开始投票,无法执行操作", activity);
				}else {
					
					EndVote ev = new EndVote();
					EndVoteWorker worker = new EndVoteWorker(ev, activity);
					new NetWork(worker, activity,false).execute("");
				}
				
				break;
			case Constant.host_showResult:
				if(startVote.equals("0")){
					Tool.showToast("还未开始投票,无法执行操作", activity);
				}else if(!startVote.equals(endVote)){
					Tool.showToast("正在投票操作,请执行结束投票!", activity);
				}else{
					
					ShowResult rs=new ShowResult();
					AnounceResultWorker worker=new AnounceResultWorker(rs,activity);
					new NetWork(worker, activity,false).execute("");
				}
				break;
			}
			
		}else{
			Tool.showToast("获取房间信息失败!无法操作", activity);
		}
	}

}
