package com.example.utilities;

public class Constant {

	public static final String MN_LOGIN="http://brainstorming.sinaapp.com/user/login";
	public static final String MN_REGISTER="http://brainstorming.sinaapp.com/user/register";
	public static final String MN_ENTERROOM="http://brainstorming.sinaapp.com/user/enterroom";
	public static final String MN_createRoom="http://brainstorming.sinaapp.com/user/createroom";
	//请求系列数据 房间 消息 idea idea分裂
	public static final String MN_roomInfo="http://brainstorming.sinaapp.com/vuser/requestdata";
	public static final String MN_sendMessage="http://brainstorming.sinaapp.com/user/addmessage";
	public static final String MN_getMessage="http://brainstorming.sinaapp.com/user/getmessages";
	public static final String MN_sendIdea="http://brainstorming.sinaapp.com/vuser/addidea";
	public static final String MN_getIdea="http://brainstorming.sinaapp.com/user/getideas";
	public static final String MN_delIdea="http://brainstorming.sinaapp.com/host/deleteidea";
	public static final String MN_moveIdea="http://brainstorming.sinaapp.com/host/movetocategory";
	public static final String MN_addCategory="http://brainstorming.sinaapp.com/host/addcategory";
	public static final String MN_delCategory="http://brainstorming.sinaapp.com/host/deletecategory";
	public static final String MN_modifyCategory="http://brainstorming.sinaapp.com/host/renamecategory";
	//房主发出开始投票
	public static final String MN_startVote="http://brainstorming.sinaapp.com/host/startvote";
	//议员投票提交
	public static final String MN_vote="http://brainstorming.sinaapp.com/vuser/vote";
	//房主结束投票
	public static final String MN_endVote="http://brainstorming.sinaapp.com/host/endvote";
	//房主宣布结果
	public static final String MN_annouceRes="http://brainstorming.sinaapp.com/host/announceresults";
	public static final String MN_exitRoom="http://brainstorming.sinaapp.com/user/exitroom";
	public static final String MN_logOut="http://brainstorming.sinaapp.com/user/logout";
	
	public static final String MN_downLoadRecord="http://brainstorming.sinaapp.com/user/record";
	
	
	public static final int HOST=0;
	public static final int AUDIENT=1;
	
	public static final int host_startVote=1;
	public static final int host_endVote=2;
	public static final int host_showResult=3;
	
	public static final String Dir="/discussApp/";
	
	public static final int EnterRoom=10;
}
