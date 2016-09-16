package com.example.meeting;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.datamodel.Idea;
import com.example.discuss.R;
import com.example.utilities.Tool;

public class AdapterOfIdeaLv extends BaseAdapter implements RatingBar.OnRatingBarChangeListener{

	private ArrayList<Idea> list;
	private LayoutInflater mInflater;
	private Context context;
	
	public AdapterOfIdeaLv(ArrayList<Idea> list ,Context context){
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
			convertView = mInflater.inflate(R.layout.liatview_item_idea, null);
			viewHolder = new ViewHolder();
			viewHolder.opinion_tv=(TextView) convertView.findViewById(R.id.opinion_tv);
			viewHolder.author=(TextView) convertView.findViewById(R.id.userName_tv);
			viewHolder.rb= (RatingBar) convertView.findViewById(R.id.ratingBar1);
			viewHolder.cate_tv=(TextView) convertView.findViewById(R.id.cate_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.opinion_tv.setText(list.get(position).getContent());
		viewHolder.author.setText(list.get(position).getAuthor());
		viewHolder.cate_tv.setText(list.get(position).getCateName());
		if(list.get(position).getBar()==null){
			list.get(position).setBar(viewHolder.rb);
		}
		viewHolder.rb.setRating(list.get(position).getScore());
		
		return convertView;
	}

	static class ViewHolder {
		public TextView opinion_tv;
		public TextView author;
		public TextView cate_tv;
		public RatingBar rb;
	}
	 class RatingBarListener implements RatingBar.OnRatingBarChangeListener{

		private int position;
		public RatingBarListener(int position){
			this.position=position;
		}
		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
			Tool.showToast(""+position+"分数:"+rating, (Activity)context);
		}  
	        
	  }  
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		
	}
	
}
