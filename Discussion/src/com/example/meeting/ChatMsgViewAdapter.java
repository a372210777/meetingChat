package com.example.meeting;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.datamodel.CommonMes;
import com.example.discuss.R;

/**
 * 
 * 
 * 
 */
public class ChatMsgViewAdapter extends BaseAdapter {

	private ArrayList<ProgressBar> pbList = new ArrayList<ProgressBar>();
	private ArrayList<ImageView> imgList = new ArrayList<ImageView>();

	private LayoutInflater mInflater;
	// private ArrayList<String> list;

	private ArrayList<CommonMes> list;

	public ChatMsgViewAdapter(Context context, ArrayList<CommonMes> coll) {
		this.list = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.chatting_item, null);

			viewHolder = new ViewHolder();

			viewHolder.left = (RelativeLayout) convertView
					.findViewById(R.id.left_layout);
			viewHolder.right = (RelativeLayout) convertView
					.findViewById(R.id.right_layout);

			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView
					.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.tvUserName_right = (TextView) convertView
					.findViewById(R.id.tv_username_right);
			viewHolder.tvContent_right = (TextView) convertView
					.findViewById(R.id.tv_chatcontent_right);
			viewHolder.pb = (ProgressBar) convertView
					.findViewById(R.id.send_pb);
			viewHolder.warm_img = (ImageView) convertView
					.findViewById(R.id.warm_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (list.get(position).isSender()) {
			viewHolder.left.setVisibility(View.INVISIBLE);
			viewHolder.right.setVisibility(View.VISIBLE);
			viewHolder.tvSendTime.setText(list.get(position).getTime());
			viewHolder.tvUserName_right.setText(list.get(position).getAuthor());
			viewHolder.tvContent_right.setText(list.get(position).getContent());

		} else {
			viewHolder.left.setVisibility(View.VISIBLE);
			viewHolder.right.setVisibility(View.INVISIBLE);
			viewHolder.tvSendTime.setText(list.get(position).getTime());
			viewHolder.tvUserName.setText(list.get(position).getAuthor());
			viewHolder.tvContent.setText(list.get(position).getContent());
		}

		if (list.get(position).isSender()) {
			if (list.get(position).getIsSending()) {// 当前数据横在发送则显示进度圈
				pbList.add(viewHolder.pb);
				imgList.add(viewHolder.warm_img);
				viewHolder.pb.setVisibility(View.VISIBLE);
			} else {
				viewHolder.pb.setVisibility(View.GONE);
			}

		}

		return convertView;
	}

	static class ViewHolder {

		public RelativeLayout left;
		public RelativeLayout right;

		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public TextView tvUserName_right;
		public TextView tvContent_right;
		public ProgressBar pb;
		public ImageView warm_img;
	}

	// 隐藏进度圈
	public void hidePb() {
		pbList.get(0).setVisibility(View.GONE);
		pbList.remove(0);
	}
	public void showWarm(){
		imgList.get(imgList.size()).setVisibility(View.VISIBLE);
	}
}
