package com.example.meeting;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.Window;
import com.example.datamodel.Idea;
import com.example.datamodel.Room;
import com.example.discuss.R;
import com.example.model.RoomData;
import com.example.model2.GetIdeaWork;
import com.example.model2.GetRoomDataWorker;
import com.example.model2.GetRoomDataWorker2;
import com.example.model2.GetRoomDataWorker7;
import com.example.utilities.NetWork;
import com.example.utilities.Tool;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class LookIdeaActivity extends SherlockActivity {

	private PullToRefreshListView listView;//
	private AdapterOfIdeaLv adapter;
	private ArrayList<Idea> list = new ArrayList<Idea>();// 观点
	private Room room;

	private boolean isVoted=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_lookidea);
		getSupportActionBar().setTitle("查看观点");
		setSupportProgressBarIndeterminateVisibility(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		room = (Room) getIntent().getSerializableExtra("room");
		adapter = new AdapterOfIdeaLv(list, this);
		listView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						LookIdeaActivity.this);
				builder.setTitle("评分");
				builder.setItems(R.array.score,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								list.get(position - 1).setScore(which+1);
								adapter.notifyDataSetChanged();
							}
						});
				builder.create().show();
			}
		});

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				RoomData gi = new RoomData();
				GetIdeaWork worker = new GetIdeaWork(gi, LookIdeaActivity.this,
						list, listView, adapter);
				new NetWork(worker, LookIdeaActivity.this).execute("");
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				RoomData gi = new RoomData();
				GetIdeaWork worker = new GetIdeaWork(gi, LookIdeaActivity.this,
						list, listView, adapter);
				new NetWork(worker, LookIdeaActivity.this).execute("");
			}
		});

		RoomData gi = new RoomData();
		GetIdeaWork worker = new GetIdeaWork(gi, this, list, listView, adapter);
		new NetWork(worker, this).execute("");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		SubMenu subMenu2 = menu.addSubMenu("Action Item");
		subMenu2.add("提交投票");
		subMenu2.add("获取结果");
		subMenu2.add("投票人数");

		MenuItem subMenu1Item2 = subMenu2.getItem();
		subMenu1Item2.setIcon(R.drawable.ic_compose_inverse);
		subMenu1Item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			this.finish();
			break;
		}
		if (item.getTitle().equals("提交投票")) {
			if (list.size() == 0) {
				Tool.showToast("还未有用户发表观点! 无法提交!", LookIdeaActivity.this);
				return  super.onOptionsItemSelected(item);
			}
			if(isVoted){
				Tool.showToast("你已经投过票了!", LookIdeaActivity.this);
				return  super.onOptionsItemSelected(item);
			}
			
			for(int i=0;i<list.size();i++){
				if(list.get(i).getScore()==0){
					Tool.showToast("请给所有观点打分", LookIdeaActivity.this);
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
			GetRoomDataWorker worker=new GetRoomDataWorker(rd,this,ideaIds.toString(),scores.toString());
			new NetWork(worker, this).execute("");
			
		} else if (item.getTitle().equals("获取结果")) {
			RoomData rd=new RoomData(); 
			GetRoomDataWorker2 worker=new GetRoomDataWorker2(rd,this);
			new NetWork(worker, this).execute("");
		} else if(item.getTitle().equals("投票人数")){
			
			RoomData rd=new RoomData();
			GetRoomDataWorker7 worker=new GetRoomDataWorker7(rd,this);
			new NetWork(worker, this).execute("");
		}

		return super.onOptionsItemSelected(item);
	}

	public void setVoted(boolean isVoted) {
		this.isVoted = isVoted;
	}

}
