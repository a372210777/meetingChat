package com.example.meeting;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.Window;
import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.datamodel.Room;
import com.example.discuss.R;
import com.example.model.AddCategory;
import com.example.model.EndVote;
import com.example.model.RoomData;
import com.example.model.ShowResult;
import com.example.model.StartVote;
import com.example.model.Vote;
import com.example.model2.AddCategoryWorker;
import com.example.model2.AnounceResultWorker;
import com.example.model2.CommitVoteWorker;
import com.example.model2.EndVoteWorker;
import com.example.model2.GetCategoryWork;
import com.example.model2.GetCategoryWorker2;
import com.example.model2.GetIdeaWork;
import com.example.model2.GetIdeaWorker2;
import com.example.model2.GetRoomDataWorker;
import com.example.model2.GetRoomDataWorker4;
import com.example.model2.GetRoomDataWorker5;
import com.example.model2.GetRoomDataWorker7;
import com.example.model2.StartVoteWork;
import com.example.utilities.Constant;
import com.example.utilities.NetWork;
import com.example.utilities.Tool;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

/**
 * 房主查看观点切换到该页
 * */
public class HostActivity extends SherlockActivity {

	private Room room;

	private ArrayList<Idea> list = new ArrayList<Idea>();// 观点
	private ArrayList<Group> parents = new ArrayList<Group>();//观点分类
	private PullToRefreshListView listView;//
	private AdapterOfLvHost adapter;

	private boolean isVote=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_host);
		getSupportActionBar().setTitle("查看观点");
		setSupportProgressBarIndeterminateVisibility(true);

		room = (Room) getIntent().getSerializableExtra("room");
		listView=(PullToRefreshListView) findViewById(R.id.pull_refresh_list_host);
		adapter = new AdapterOfLvHost(list, this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				Tool.createAlert2(HostActivity.this, parents, list, position,adapter);
			}
		});

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				RoomData gi = new RoomData();
				GetIdeaWorker2 worker = new GetIdeaWorker2(gi, HostActivity.this, list, listView, adapter,parents);
				new NetWork(worker, HostActivity.this).execute("");
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
			}
		});
		
		//获取idea
		RoomData gi = new RoomData();
		GetIdeaWorker2 worker = new GetIdeaWorker2(gi, this, list, listView, adapter,parents);
		new NetWork(worker, this).execute("");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Action Item");
		subMenu1.add("开始投票");
		subMenu1.add("结束投票");
		subMenu1.add("展示结果");
		subMenu1.add("添加分类");
		subMenu1.add("查看分类");
		subMenu1.add("投票人数");
		subMenu1.add("投票");

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item.setIcon(R.drawable.ic_compose_inverse);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("查看结果")) {
			
			
		} else if (item.getTitle().equals("开始投票")) {
			
			RoomData rd=new RoomData();
			GetRoomDataWorker4 worker=new GetRoomDataWorker4(this, rd, Constant.host_startVote);
			new NetWork(worker, this).execute("");
			
		} else if (item.getTitle().equals("结束投票")) {
			
			RoomData rd=new RoomData();
			GetRoomDataWorker4 worker=new GetRoomDataWorker4(this, rd, Constant.host_endVote);
			new NetWork(worker, this).execute("");
			

		} else if (item.getTitle().equals("展示结果")) {
			
			RoomData rd=new RoomData();
			GetRoomDataWorker4 worker=new GetRoomDataWorker4(this, rd, Constant.host_showResult);
			new NetWork(worker, this).execute("");
			
		} else if (item.getTitle().equals("添加分类")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = this.getLayoutInflater();
			View v = inflater.inflate(R.layout.dialog_layout, null);
			final EditText type_et = (EditText) v
					.findViewById(R.id.groudType_et);
			builder.setView(v);
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							if (type_et.getText() == null
									|| type_et.getText().toString().trim()
											.equals("")) {
								Tool.showToast("请输入分类", HostActivity.this);
								return;
							}
							AddCategory cate = new AddCategory(type_et
									.getText().toString().trim(), room.getId());
							AddCategoryWorker cworker = new AddCategoryWorker(
									cate, HostActivity.this,parents,list,adapter);
							new NetWork(cworker, HostActivity.this).execute("");
						}
					}).setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
			builder.create().show();
		}else if(item.getTitle().equals("查看分类")){
			Tool.showCate(this, parents, list);
		}else if(item.getTitle().equals("投票人数")){
			
			RoomData rd=new RoomData();
			GetRoomDataWorker7 worker=new GetRoomDataWorker7(rd,this);
			new NetWork(worker, this).execute("");
			
		}else if(item.getTitle().equals("投票")){
			if(isVote){
				Tool.showToast("您已经投过票了", this);
				return super.onOptionsItemSelected(item);
			}
			for(int i=0;i<list.size();i++){
				if(list.get(i).getScore()==0){
					Tool.showToast("请给所有观点打分", this);
					return super.onOptionsItemSelected(item);
				}
			}
			StringBuffer scores=new StringBuffer();
			StringBuffer ideaIds=new StringBuffer();
			for(int i=0;i<list.size();i++){
				if(i==list.size()){
					scores.append(list.get(i).getScore());
					ideaIds.append(list.get(i).getId());
				}else{
					scores.append(list.get(i).getScore()+",");
					ideaIds.append(list.get(i).getId()+",");
				}
			}
			
			RoomData rd=new RoomData(); 
			GetRoomDataWorker5 worker=new GetRoomDataWorker5(rd,this,ideaIds.toString(),scores.toString());
			new NetWork(worker, this).execute("");
		}
		return super.onOptionsItemSelected(item);
	}
	public void setVoted(boolean voted){
		this.isVote=voted;
	}
}
