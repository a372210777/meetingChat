package com.example.meeting;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.discuss.R;

public class ExpandableAdapter extends BaseExpandableListAdapter {

	private List<Group> groupArray;
	private List<List<Idea>> childArray;

	private Activity activity;
	private LayoutInflater mInflater;

	public ExpandableAdapter(ArrayList<Group> groupArray,
			ArrayList<List<Idea>> childArray, HostActivity hostActiviy) {
		this.groupArray = groupArray;
		this.childArray = childArray;
		this.activity = hostActiviy;
		mInflater=hostActiviy.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childArray.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
//		Idea element = childArray.get(groupPosition).get(childPosition);
//		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
//				ViewGroup.LayoutParams.FILL_PARENT, 64);
//		TextView text = new TextView(activity);
//		text.setLayoutParams(layoutParams);
//		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//		text.setPadding(36, 0, 0, 0);
//		text.setText(element);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.liatview_item_idea, null);
			viewHolder = new ViewHolder();
			viewHolder.opinion_tv=(TextView) convertView.findViewById(R.id.opinion_tv);
			viewHolder.author=(TextView) convertView.findViewById(R.id.userName_tv);
			viewHolder.rb= (RatingBar) convertView.findViewById(R.id.ratingBar1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Idea idea = childArray.get(groupPosition).get(childPosition);
		viewHolder.opinion_tv.setText(idea.getContent());
		viewHolder.author.setText(idea.getAuthor());
		viewHolder.rb.setRating(idea.getScore());
		
		if(idea.getBar()==null){
			idea.setBar(viewHolder.rb);
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		public TextView opinion_tv;
		public TextView author;
		public RatingBar rb;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childArray.get(groupPosition).size();
	}

	// ----------父组构造-------------
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupArray.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupArray.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 64);
		TextView text = new TextView(activity);
		text.setLayoutParams(layoutParams);
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		text.setPadding(36, 0, 0, 0);
		text.setText(groupArray.get(groupPosition).getName());
		
//		LayoutInflater inflater=activity.getLayoutInflater();
//		View newView=inflater.inflate(R.layout.groud_parent_layout, null);
//		((TextView)newView.findViewById(R.id.parentElement)).setText(groupArray.get(groupPosition));
//		ImageView arrow=(ImageView) newView.findViewById(R.id.arrow);
//		if(isExpanded){
//			arrow.setBackgroundResource(R.drawable.list_indicator_right);
//		}
		
		return text;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	//子项是否响应点击事件
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
