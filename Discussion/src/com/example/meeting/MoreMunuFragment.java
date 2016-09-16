package com.example.meeting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.discuss.DiscussApp;
import com.example.discuss.EnterRoomActivity;
import com.example.discuss.LoginActivity;
import com.example.discuss.R;
import com.example.model.DownLoadRecord;
import com.example.model2.DownLoadWorker;
import com.example.utilities.Constant;
import com.example.utilities.NetWork;
import com.example.utilities.NetWork2;

public class MoreMunuFragment extends SherlockFragment implements
		OnClickListener {

	private boolean isExitRoom;
	private boolean isLogOut;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_munu, container, false);
		v.findViewById(R.id.roomMes_btn).setOnClickListener(this);
		v.findViewById(R.id.lookIdea_btn).setOnClickListener(this);
		v.findViewById(R.id.sendIdea_btn).setOnClickListener(this);
		v.findViewById(R.id.download_btn).setOnClickListener(this);
		v.findViewById(R.id.exitRoom_btn).setOnClickListener(this);
		v.findViewById(R.id.logOut_btn).setOnClickListener(this);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.roomMes_btn://房间信息
			Intent it1=new Intent(getActivity(),RoomInfoActivity.class);
			it1.putExtra("room", ((ChatActivity)getActivity()).getRoom());
			startActivity(it1);
			break;
		case R.id.lookIdea_btn://查看观点
			
			int identity= getActivity().getIntent().getIntExtra("identity", Constant.AUDIENT);
			switch(identity){
			case Constant.HOST:
				Intent it=new Intent(getActivity(),HostActivity.class);
				it.putExtra("room", ((ChatActivity)getActivity()).getRoom());
				startActivity(it);
				break;
			case Constant.AUDIENT:
				Intent it2=new Intent(getActivity(),LookIdeaActivity.class);
				it2.putExtra("room", ((ChatActivity)getActivity()).getRoom());
				startActivity(it2);
				break;
			}
			
		
			break;
		case R.id.sendIdea_btn://发送观点
			Intent it3=new Intent(getActivity(),SendIdeaActivity.class);
			it3.putExtra("room", ((ChatActivity)getActivity()).getRoom());
			startActivity(it3);
			break;
		case R.id.download_btn:
			DownLoadRecord dr=new DownLoadRecord();
			DownLoadWorker worker=new DownLoadWorker(getActivity(), dr);
			new NetWork2(worker,getActivity()).execute("");
			break;
		case R.id.exitRoom_btn:
			if(isExitRoom==true){
				Toast.makeText(getActivity(), "正在退出房间..", Toast.LENGTH_SHORT).show();
			}else{
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						DiscussApp app=(DiscussApp) (getActivity().getApplication());
						Intent it=new Intent(getActivity(),EnterRoomActivity.class);
						getActivity().startActivity(it);
						getActivity().finish();
						
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
				builder.setTitle("确认退出");
				builder.create().show();
				
			}
			break;
		case R.id.logOut_btn:
			if(isLogOut==true){
				Toast.makeText(getActivity(), "正在退出登陆..", Toast.LENGTH_SHORT).show();
			}else{
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						DiscussApp app=(DiscussApp) (getActivity().getApplication());
						app.setPHPSESSID("");
						Intent it=new Intent(getActivity(),LoginActivity.class);
						getActivity().startActivity(it);
						getActivity().finish();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
				builder.setTitle("确认退出");
				builder.create().show();
				
			}
			break;
		}
	}

	
}
