package com.evolvision.android;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.evolvision.android.model.Item;

public class CategoryAdapter extends ArrayAdapter<String> implements Filterable {

		private final Context context;
		private final ArrayList<String> itemsArrayList;

		public CategoryAdapter(Context context, ArrayList<String> itemsArrayList) {
			
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
		    		    
		    // 4. Set the text for textView 
		    labelView.setText(itemsArrayList.get(position).getBytes().toString());
		    labelView.setTextSize(14);
		    labelView.setTextColor(Color.BLUE);
		    
		    // 5. retrn rowView
		    return rowView;
		}
		
}
