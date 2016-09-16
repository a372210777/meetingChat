package com.example.meeting;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.datamodel.Idea;
import com.example.discuss.R;

public class ResultListAdapter extends BaseAdapter implements SectionIndexer {
	private Context mContext;

	private List<Idea> list;

	@SuppressWarnings("unchecked")
	public ResultListAdapter(Context mContext, ArrayList<Idea> list) {
		this.mContext = mContext;
		this.list = list;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String cateName = list.get(position).getCateName();
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listview_item_show, null);
			viewHolder = new ViewHolder();
//			viewHolder.rb = (RatingBar) convertView
//					.findViewById(R.id.ratingBar1_rb);
			viewHolder.average_score=(TextView) convertView.findViewById(R.id.average_score);
			viewHolder.score_tv = (TextView) convertView
					.findViewById(R.id.sd_tv);
			viewHolder.top_bg = (TextView) convertView
					.findViewById(R.id.top_bg);
			viewHolder.opinion_tv=(TextView) convertView.findViewById(R.id.opinion_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

//		String catalog = converterToFirstSpell(list.get(position).getCateName())
//				.substring(0, 1);
		String catalog =list.get(position).getCateName().trim();
		
		/**-------Í·²¿×ÖÄ¸µ÷Òþ²ØÏÔÊ¾ÅÐ¶Ï---------*/
		if (position == 0) {
			viewHolder.top_bg.setVisibility(View.VISIBLE);
//			viewHolder.top_bg.setText(catalog);
			viewHolder.top_bg.setText(list.get(position).getCateName());
		} else {
//			String lastCatalog = converterToFirstSpell(
//					list.get(position - 1).getCateName()).substring(0, 1);
			String lastCatalog = list.get(position - 1).getCateName().trim();
			if (catalog.equals(lastCatalog)) {
				viewHolder.top_bg.setVisibility(View.GONE);
			} else {
				viewHolder.top_bg.setVisibility(View.VISIBLE);
//				viewHolder.top_bg.setText(catalog);
				viewHolder.top_bg.setText(list.get(position).getCateName());
			}
		}
		/**------------------------------------------------*/
//		viewHolder.rb.setRating(Float
//				.parseFloat(list.get(position).getScore1()));
		viewHolder.average_score.setText(list.get(position).getScore1());
		viewHolder.score_tv.setText(list.get(position).getSd());
		viewHolder.opinion_tv.setText(list.get(position).getContent());
		return convertView;
	}

	class ViewHolder {
		public RatingBar rb;
		public TextView average_score;
		public TextView opinion_tv;
		public TextView score_tv;
		public TextView top_bg;
	}

	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < list.size(); i++) {
			String l = converterToFirstSpell(list.get(i).getCateName())
					.substring(0, 1);
			char firstChar = l.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	/**
	 * ºº×Ö×ª»»Î»ººÓïÆ´ÒôÊ××ÖÄ¸£¬Ó¢ÎÄ×Ö·û²»±ä
	 * 
	 * @param chines
	 *            ºº×Ö
	 * @return Æ´Òô
	 */
	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}
}