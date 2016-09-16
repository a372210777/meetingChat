package com.example.meeting;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.discuss.R;
import com.example.indexlistview.SideBar;
import com.example.model.DelIdea;
import com.example.model.RoomData;
import com.example.model2.DelIdeaWorker2;
import com.example.model2.GetRoomDataWorker3;
import com.example.utilities.Constant;
import com.example.utilities.NetWork;

public class ShowResultActivity extends SherlockActivity {

	private ListView listview;
	private SideBar indexBar;
	private WindowManager mWindowManager;
	private TextView mDialogText;

	private ArrayList<Idea> listIdea = new ArrayList<Idea>();
	private ArrayList<Group> listGroup = new ArrayList<Group>();

	private ResultListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showresult);
		getSupportActionBar().setTitle("½á¹û");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		listview = (ListView) this.findViewById(R.id.lvContact);
		indexBar = (SideBar) findViewById(R.id.sideBar);
		adapter = new ResultListAdapter(this, listIdea);
		listview.setAdapter(adapter);
		indexBar.setListView(listview);
		mDialogText = (TextView) LayoutInflater.from(this).inflate(
				R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);
		indexBar.setTextView(mDialogText);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				int identity = getIntent().getIntExtra("identity",
						Constant.AUDIENT);
				if (identity == Constant.HOST) {
					String array[] = { "É¾³ý" };
					new AlertDialog.Builder(ShowResultActivity.this)
							.setTitle("Ñ¡Ôñ")
							.setItems(array, new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									DelIdea di = new DelIdea(listIdea.get(
											position - 1).getId());
									DelIdeaWorker2 worker = new DelIdeaWorker2(
											di, ShowResultActivity.this,
											adapter, listIdea, position);
									new NetWork(worker, ShowResultActivity.this)
											.execute("");

								}
							}).create().show();
				}

			}
		});

		RoomData rd = new RoomData();
		GetRoomDataWorker3 worker = new GetRoomDataWorker3(rd, this, listIdea,
				listGroup, adapter);
		new NetWork(worker, this).execute("");

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	

}
