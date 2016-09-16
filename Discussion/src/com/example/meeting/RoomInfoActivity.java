package com.example.meeting;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.datamodel.Room;
import com.example.datamodel.User;
import com.example.discuss.R;
import com.example.model.RoomData2;
import com.example.model2.DialoDelCate;
import com.example.model2.GetRoomDataWorker6;
import com.example.utilities.NetWork;
import com.example.utilities.Tool;

public class RoomInfoActivity extends SherlockActivity  implements OnClickListener{

	private ArrayList<User> list=new ArrayList<User>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roominfo);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Room room=(Room) getIntent().getSerializableExtra("room");
		((TextView)findViewById(R.id.host_tv)).setText(room.getHost());
		((TextView)findViewById(R.id.roomName_tv)).setText(room.getId());
		((TextView)findViewById(R.id.roomTheme_tv)).setText(room.getTheme());
		TextView online=((TextView)findViewById(R.id.onLineNum_tv));
		online.setOnClickListener(this);
		
		RoomData2 rd=new RoomData2();
		GetRoomDataWorker6 worker=new GetRoomDataWorker6(this, rd,list,online);
		new NetWork(worker,this).execute("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.onLineNum_tv:
				if(list.size()!=0){
					String[] array=new String[list.size()];
					for(int i=0;i<list.size();i++){
						array[i]=list.get(i).getUserName();
					}
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("在线用户");
					builder.setItems(array, null);
					builder.create().show();
				}
			break;
		}
		
	}
}
