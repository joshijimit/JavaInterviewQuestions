package com.evolvision.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evolvision.android.model.Categories;
import com.evolvision.android.sqlite.MySQLiteHelper;
import com.google.analytics.tracking.android.EasyTracker;

import com.xbjjupcqtl.hkxcebjbjr178191.AdListener;
import com.xbjjupcqtl.hkxcebjbjr178191.AdView;


public class MainActivity2 extends ListActivity {

	private EditText et;	
	private ArrayList<String> CATEGORIES = new ArrayList<String>();

	private ArrayList<String> array_sort;
	int textlength = 0;
	
	AdView adView;
	LinearLayout layout;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		
		if(MainActivity.isNightMode){
			setTheme(android.R.style.Theme_Black);
		}
		
		super.onCreate(icicle);

		setContentView(R.layout.activity_main2);
		
		setTitle("Categories");
		if(!MainActivity.isNightMode){
			getActionBar().setDisplayHomeAsUpEnabled(true);			
		}

		et = (EditText) findViewById(R.id.editText1);		

		loadCategoriesFromDB();

		array_sort = new ArrayList<String>(CATEGORIES);
		setListAdapter(new bsAdapter(this));

		et.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// Abstract Method of TextWatcher Interface.
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Abstract Method of TextWatcher Interface.
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textlength = et.getText().length();
				array_sort.clear();
				for (int i = 0; i < CATEGORIES.size(); i++) {
					if (textlength <= CATEGORIES.get(i).length()) {

						/***
						 * If you choose the below block then it will act like a
						 * Like operator in the Mysql
						 */

						if (CATEGORIES
								.get(i)
								.toLowerCase()
								.contains(
										et.getText().toString().toLowerCase()
												.trim())) {
							array_sort.add(CATEGORIES.get(i));
						}
					}
				}
				AppendList(array_sort);
			}
		});

		getListView().setTextFilterEnabled(true);
		DynamicBannerAd();

	}
	
	/*
	 * Airpush Banner Ad renders bottom of the screen
	 */
	@SuppressWarnings("deprecation")
	void DynamicBannerAd() {
		layout = new LinearLayout(MainActivity2.this);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setGravity(Gravity.BOTTOM);
		MainActivity2.this.addContentView(layout, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		adView = new AdView(MainActivity2.this, AdView.BANNER_TYPE_IN_APP_AD,
				com.xbjjupcqtl.hkxcebjbjr178191.AdView.PLACEMENT_TYPE_INTERSTITIAL, false, false,
				AdView.ANIMATION_TYPE_LEFT_TO_RIGHT);
		layout.addView(adView);

		// set Callback listener for Dynamic Banner(This method is optional ).
		adView.setAdListener(adlistener);
	}

	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}
	
	private void loadCategoriesFromDB() {

		MySQLiteHelper db = new MySQLiteHelper(this);
		try {
			db.createDataBase();
			db.openDataBase();
			List<Categories> list = db.getAllCategories();

			for (Categories book : list) {
				CATEGORIES.add(book.getCategoryName());
			}			
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			db.close();
		}

	}

	public void AppendList(ArrayList<String> str) {
		setListAdapter(new bsAdapter(this));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		String cat = (String) l.getAdapter().getItem(position);
		Log.i("clicks", "You Clicked category item" + cat);
		Intent i = new Intent(MainActivity2.this, MainActivity3.class);
		Bundle b = new Bundle();
		b.putString("selectedCategory", cat);
		i.putExtras(b);
		startActivity(i);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(),
				MainActivity.class);		
		startActivityForResult(myIntent, 0);
		return true;

	}

	public class bsAdapter extends BaseAdapter {
		Activity cntx;

		public bsAdapter(Activity context) {
			// TODO Auto-generated constructor stub
			this.cntx = context;

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return array_sort.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return array_sort.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return array_sort.size();
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View row = null;

			LayoutInflater inflater = cntx.getLayoutInflater();
			row = inflater.inflate(R.layout.search_list_item, null);

			TextView tv = (TextView) row.findViewById(R.id.title);
			
			if(MainActivity.isNightMode)
				tv.setTextColor(Color.WHITE);
			else
				tv.setTextColor(Color.BLACK);
			
			tv.setText(array_sort.get(position));

			return row;
		}
	}
	
	/*
	 * Airpush banner Ad Listener
	 */

	AdListener.MraidAdListener adlistener = new AdListener.MraidAdListener() {

		@Override
		public void noAdAvailableListener() {
			Toast.makeText(getApplicationContext(), "No Ad Available",
					Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onAdClickListener() {
			Toast.makeText(getApplicationContext(), "Ad click",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdExpandedListner() {
			Toast.makeText(getApplicationContext(), "Ad Expanded",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdLoadedListener() {
			Toast.makeText(getApplicationContext(), "Ad Loaded",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAdLoadingListener() {
			Toast.makeText(getApplicationContext(), "Ad's loading listener",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCloseListener() {
			Toast.makeText(getApplicationContext(), "Ad's close Listener",
					Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onErrorListener(String arg0) {
			Toast.makeText(getApplicationContext(), "Banner Error : " + arg0,
					Toast.LENGTH_SHORT).show();
		}
	};

			
}
