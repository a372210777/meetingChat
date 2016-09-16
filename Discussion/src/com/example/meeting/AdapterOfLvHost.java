package com.example.meeting;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.datamodel.Idea;
import com.example.discuss.R;

public class AdapterOfLvHost extends BaseAdapter {

	private ArrayList<Idea> list;
	private LayoutInflater mInflater;
	private Context context;
	
	public AdapterOfLvHost(ArrayList<Idea> list ,Context context){
		this.context=context;
		this.list=list;
		this.mInflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	//getView所展现的任何数据应绑定在外部对象中
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listview_item_idea_h, null);
			viewHolder = new ViewHolder();
			viewHolder.opinion_tv=(TextView) convertView.findViewById(R.id.opinion_tv);
			viewHolder.author=(TextView) convertView.findViewById(R.id.userName_tv);
			viewHolder.rb= (RatingBar) convertView.findViewById(R.id.ratingBar1);
			viewHolder.category_et= (TextView) convertView.findViewById(R.id.cate_et);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.opinion_tv.setText(list.get(position).getContent());
		viewHolder.author.setText(list.get(position).getAuthor());
		if(list.get(position).getBar()==null){
			list.get(position).setBar(viewHolder.rb);
		}
		
		viewHolder.rb.setRating(list.get(position).getScore());
		if(list.get(position).getCate__et()==null){
			list.get(position).setCate__et(viewHolder.category_et);
		}
		if(list.get(position).getCateName()==null || list.get(position).getCateName().equals("")){
			viewHolder.category_et.setText("无分类");
//			list.get(position).getCate__et().setText("无分类");
		}else{
			viewHolder.category_et.setText(list.get(position).getCateName());
//			list.get(position).getCate__et().setText(list.get(position).getCateName());
		}
		
		return convertView;
	}

	static class ViewHolder {
		public TextView opinion_tv;
		public TextView author;
		public TextView category_et;
		public RatingBar rb;
	}


	
}
