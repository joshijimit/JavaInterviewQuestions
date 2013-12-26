package com.hmkcode.android;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hmkcode.android.model.CurrentRowHolder;
import com.hmkcode.android.model.Item;

public class MyAdapter extends ArrayAdapter<Item> {

		private final Context context;
		private final ArrayList<Item> itemsArrayList;

		public MyAdapter(Context context, ArrayList<Item> itemsArrayList) {
			
			super(context, R.layout.row, itemsArrayList);
			
			this.context = context;
			this.itemsArrayList = itemsArrayList;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    
			// 1. Create inflater 
			LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    
			// 2. Get rowView from inflater
			View rowView = inflater.inflate(R.layout.row, parent, false);
		    
			// 3. Get the two text view from the rowView
			TextView labelView = (TextView) rowView.findViewById(R.id.label);
		    TextView valueView = (TextView) rowView.findViewById(R.id.value);
		    Button button = (Button)rowView.findViewById(R.id.showHideAns);
		    // 4. Set the text for textView 
		    labelView.setText(itemsArrayList.get(position).getTitle());
		    labelView.setTextSize(14);
		    labelView.setTextColor(Color.BLUE);
		    
		    valueView.setText(itemsArrayList.get(position).getDescription());
		    valueView.setTextSize(12);
		    valueView.setTextColor(Color.BLACK);
		    
		    CurrentRowHolder holder = new CurrentRowHolder();
		    holder.setButton(button);
		    holder.setValue(valueView);
		    
		    button.setTag(holder);
		    
		    // 5. retrn rowView
		    return rowView;
		}
		
}
