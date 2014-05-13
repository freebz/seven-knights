package com.freebz.sevenknight.model;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freebz.sevenknight.CombinationActivity;
import com.freebz.sevenknight.R;
import com.freebz.sevenknight.ReinforceActivity;

public class CardAdapter extends CursorAdapter {
	
	private int selectedId;

	@SuppressWarnings("deprecation")
	public CardAdapter(Context context, Cursor cursor) {
		super(context, cursor);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		ImageView imgStar = (ImageView) view.findViewById(R.id.img_star);
		int star = cursor.getInt(cursor.getColumnIndex("STAR"));
		int resId = context.getResources().getIdentifier("star_" + star, "drawable", "com.freebz.sevenknight");
		imgStar.setImageResource(resId);
		
		TextView name = (TextView) view.findViewById(R.id.name);
		name.setText(cursor.getString(cursor.getColumnIndex("NAME")));
		name.setTextColor(Constance.nameColors[star - 1]);
		
		TextView level = (TextView) view.findViewById(R.id.level);
		int lv = cursor.getInt(cursor.getColumnIndex("LEVEL"));
		if (lv > 0) {
			level.setText("+" + lv);
		}
		else {
			level.setText("");
		}
		
		int id = cursor.getInt(cursor.getColumnIndex("_id"));
		CheckBox check = (CheckBox) view.findViewById(R.id.check);
		check.setTag(id);
		check.setChecked(id == selectedId);
		check.setOnClickListener(mClickListener);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.card_list_item, parent, false);
		return view;
	}
	
	public int getSelectedId() {
		return selectedId;
	}
	
	OnClickListener mClickListener = new OnClickListener() {
		public void onClick(View view) {
			switch(view.getId()) {
			case R.id.check:
				selectedId = (Integer) view.getTag();
				notifyDataSetChanged();
				
				if (view.getContext() instanceof ReinforceActivity) {
					((ReinforceActivity) view.getContext()).refreshInfo();
				}
				else if(view.getContext() instanceof CombinationActivity) {
					((CombinationActivity) view.getContext()).refresh();
				}
				
				break;
			}
		}
	};

}
