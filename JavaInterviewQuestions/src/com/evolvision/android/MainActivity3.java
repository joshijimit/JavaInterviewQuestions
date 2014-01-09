package com.evolvision.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.evolvision.android.model.CurrentRowHolder;
import com.evolvision.android.model.Item;
import com.evolvision.android.model.Questions;
import com.evolvision.android.sqlite.MySQLiteHelper;

public class MainActivity3 extends ListActivity {

	private boolean isFromFavourite = false;
	private int queFontSize = 20;
	private int ansFontSize = 18;
	ArrayList<Item> items = new ArrayList<Item>();
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		getActionBar().setDisplayHomeAsUpEnabled(true);		
		
		String isFavourite = getIntent().getStringExtra("favourite");
		if (isFavourite != null) {
			setTitle("My Favourite");
			items = loadFavourites();
			MyAdapter adapter = new MyAdapter(this, items, true,queFontSize,ansFontSize);
			isFromFavourite = true;
			setListAdapter(adapter);
		} else {
			setTitle("Questions");
			// if extending Activity
			// setContentView(R.layout.activity_main);
			String cat = getIntent().getStringExtra("selectedCategory");
			
			items = loadQuestionsFromDB(cat);
			// 1. pass context and data to the custom adapter
			MyAdapter adapter = new MyAdapter(this, items,
					true,queFontSize,ansFontSize);

			// if extending Activity 2. Get ListView from activity_main.xml
			// ListView listView = (ListView) findViewById(R.id.listview);

			// 3. setListAdapter
			// listView.setAdapter(adapter); if extending Activity
			setListAdapter(adapter);
		}

	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.menu_increase:
				queFontSize = queFontSize + 2;
				ansFontSize = ansFontSize + 2;
				MyAdapter adapter = new MyAdapter(this, items,
						true,queFontSize,ansFontSize);
				setListAdapter(adapter);
			return true;

			case R.id.menu_decrease:
				queFontSize = queFontSize - 2;
				ansFontSize = ansFontSize - 2;
				adapter = new MyAdapter(this, items,
						true,queFontSize,ansFontSize);
				setListAdapter(adapter);
			return true;

		}

		if (isFromFavourite) {
			Intent myIntent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivityForResult(myIntent, 0);
		} else {
			Intent myIntent = new Intent(getApplicationContext(),
					MainActivity2.class);
			startActivityForResult(myIntent, 0);
		}

		return true;

	}

	private ArrayList<Item> loadQuestionsFromDB(String categoryName) {

		ArrayList<Item> items = new ArrayList<Item>();
		MySQLiteHelper db = new MySQLiteHelper(this);
		try {
			db.createDataBase();
			db.openDataBase();
			List<Questions> list = db.getQustionsByCategory(categoryName);

			for (Questions question : list) {
				items.add(new Item(question.get_ID(), question.getQuestion(),
						question.getAnswer(), question.getIsFavourite()));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return items;

	}

	private ArrayList<Item> loadFavourites() {

		ArrayList<Item> items = new ArrayList<Item>();
		MySQLiteHelper db = new MySQLiteHelper(this);
		try {
			db.createDataBase();
			db.openDataBase();
			List<Questions> list = db.getFavouriteQustions();

			for (Questions question : list) {
				items.add(new Item(question.get_ID(), question.getQuestion(),
						question.getAnswer(), question.getIsFavourite()));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return items;

	}

	public void showHideAnsOnClickHandler(View view) {

		CurrentRowHolder holder = (CurrentRowHolder) view.getTag();
		Button button = holder.getButton();
		TextView textView = holder.getValue();
		if (button.getText().equals("Show Answer")) {
			textView.setVisibility(View.VISIBLE);
			button.setText("Hide Answer");
		} else {
			textView.setVisibility(View.GONE);
			button.setText("Show Answer");
		}

	}

	public void onToggleStar(View view) {

		MySQLiteHelper db = new MySQLiteHelper(this);
		try {
			db.createDataBase();
			db.openDataBase();

			CurrentRowHolder holder = (CurrentRowHolder) view.getTag();
			ImageButton buttonFav = holder.getButtonFav();
			if (buttonFav.isSelected()) {
				buttonFav.setSelected(false);
				db.updateFavourite(holder.getQuestionID(), 0);
				// Toast.makeText(this, "Favourite removed successfully",
				// Toast.LENGTH_SHORT).show();
			} else {
				buttonFav.setSelected(true);
				db.updateFavourite(holder.getQuestionID(), 1);
				// Toast.makeText(this, "Favourite added successfully",
				// Toast.LENGTH_SHORT).show();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

}