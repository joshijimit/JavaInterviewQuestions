package com.evolvision.android;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evolvision.android.model.CurrentRowHolder;
import com.evolvision.android.model.Item;
import com.evolvision.android.utility.CommonFunctions;

public class MyAdapter extends ArrayAdapter<Item> {

	
	private final Context context;
	private final ArrayList<Item> itemsArrayList;
	private final boolean showNoRecordFound;
	private final int queFontSize;
	private final int ansFontSize;
	private String searchText = null;
	
	public MyAdapter(Context context, ArrayList<Item> itemsArrayList,boolean showNoRecordFound, int queFontSize,int ansFontSize) {

		super(context, R.layout.row, itemsArrayList);
		this.showNoRecordFound = showNoRecordFound;
		this.context = context;
		this.itemsArrayList = itemsArrayList;
		this.queFontSize=queFontSize;
		this.ansFontSize =ansFontSize;		
	}

	@Override
    public int getCount() 
    {
        if(itemsArrayList.size()==0)
            return 1;
        else
            return itemsArrayList.size();
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// 1. Create inflater
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (itemsArrayList.size() == 0 && showNoRecordFound) {
			TextView message = new TextView(context);
			message.setText("No Favorite found.");
			message.setTextSize(16);
			message.setTextColor(Color.RED);
			message.setLayoutParams(new RelativeLayout.LayoutParams(
					(int) RelativeLayout.LayoutParams.WRAP_CONTENT,
					(int) RelativeLayout.LayoutParams.WRAP_CONTENT));
			View emptyView = inflater.inflate(
					R.layout.empty_list_item, parent,false);
			((RelativeLayout) emptyView).setGravity(Gravity.CENTER);
			((RelativeLayout) emptyView).addView(message);
			return emptyView;
		} else if(itemsArrayList.size() != 0) {
			
			// 2. Get rowView from inflater
			View rowView = inflater.inflate(R.layout.row, parent, false);

			// 3. Get the two text view from the rowView
			TextView labelView = (TextView) rowView.findViewById(R.id.label);
			TextView valueView = (TextView) rowView.findViewById(R.id.value);
			Button button = (Button) rowView.findViewById(R.id.showHideAns);
			ImageButton buttonFav = (ImageButton) rowView
					.findViewById(R.id.favorite_button);

			buttonFav.setImageDrawable(getContext().getResources().getDrawable(
					R.drawable.btn_star));
			if (itemsArrayList.get(position).getIsFavorite() == 1) {
				buttonFav.setSelected(true);
			}
			
			if(searchText != null){
				// 4. Set the text for textView
				labelView.setText(CommonFunctions.highlight(searchText, itemsArrayList.get(position).getTitle()));
			}else{
				labelView.setText(itemsArrayList.get(position).getTitle());
			}
			labelView.setTextSize(queFontSize);
			labelView.setTextColor(Color.BLUE);
			
			if(searchText != null){
				// 4. Set the text for textView				
				valueView.setText(CommonFunctions.highlight(searchText, itemsArrayList.get(position).getDescription()));
			}else{
				valueView.setText(itemsArrayList.get(position).getDescription());
			}
			valueView.setTextSize(ansFontSize);
			valueView.setTextColor(Color.BLACK);
			
			if(searchText != null){
				button.setVisibility(View.GONE);
				valueView.setVisibility(View.VISIBLE);
			}
			
			CurrentRowHolder holder = new CurrentRowHolder();
			holder.setQuestionID(itemsArrayList.get(position).getID());
			holder.setButton(button);
			holder.setButtonFav(buttonFav);
			holder.setValue(valueView);

			button.setTag(holder);
			buttonFav.setTag(holder);
			// 5. retrn rowView
			return rowView;
		}else{
			View emptyView = inflater.inflate(
					R.layout.empty_list_item, parent,false);
			return emptyView; 
		}
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	

}
