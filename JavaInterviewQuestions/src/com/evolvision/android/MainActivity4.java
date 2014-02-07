package com.evolvision.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.evolvision.android.model.CurrentRowHolder;
import com.evolvision.android.model.Item;
import com.evolvision.android.model.Questions;
import com.evolvision.android.sqlite.MySQLiteHelper;
import com.evolvision.android.utility.CommonFunctions;
import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity4 extends ListActivity {

	private EditText et;
	private ArrayList<Item> questions = new ArrayList<Item>();
	private ArrayList<Item> array_sort = new ArrayList<Item>();
	private int queFontSize = 20;
	private int ansFontSize = 18;

	// Adapter for myList
	private MyAdapter adapter;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		
		if(MainActivity.isNightMode){
			setTheme(android.R.style.Theme_Black);
		}
		
		super.onCreate(icicle);

		setContentView(R.layout.activity_main4);
		setTitle("Search");
		if(!MainActivity.isNightMode){
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}


		et = (EditText) findViewById(R.id.searchQue);
		questions = loadAllQuestionsFromDB();

		adapter = new MyAdapter(this, array_sort, false, queFontSize,
				ansFontSize);
		setListAdapter(adapter);

		AlterAdapter();

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

				AlterAdapter();
			}
		});

		getListView().setTextFilterEnabled(true);

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_increase:
			queFontSize = queFontSize + 2;
			ansFontSize = ansFontSize + 2;
			MyAdapter adapter = new MyAdapter(this, array_sort, true,
					queFontSize, ansFontSize);
			setListAdapter(adapter);
			return true;

		case R.id.menu_decrease:
			queFontSize = queFontSize - 2;
			ansFontSize = ansFontSize - 2;
			adapter = new MyAdapter(this, array_sort, true, queFontSize,
					ansFontSize);
			setListAdapter(adapter);
			return true;

		}

		Intent myIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		startActivityForResult(myIntent, 0);

		return true;

	}

	private ArrayList<Item> loadAllQuestionsFromDB() {

		ArrayList<Item> items = new ArrayList<Item>();
		MySQLiteHelper db = new MySQLiteHelper(this);
		try {
			db.createDataBase();
			db.openDataBase();
			List<Questions> list = db.getAllQustions();

			for (Questions question : list) {
				items.add(new Item(question.get_ID(), question.getQuestion(),
						question.getAnswer(), question.getIsFavorite()));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		return items;

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
				db.updateFavorite(holder.getQuestionID(), 0);
				// Toast.makeText(this, "Favorite removed successfully",
				// Toast.LENGTH_SHORT).show();
			} else {
				buttonFav.setSelected(true);
				db.updateFavorite(holder.getQuestionID(), 1);
				// Toast.makeText(this, "Favorite added successfully",
				// Toast.LENGTH_SHORT).show();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}

	}
	
	public void onShareQuestion(View view){
		
		CurrentRowHolder holder = (CurrentRowHolder) view.getTag();
		
		TextView question = holder.getQuestion();
		TextView ans = holder.getValue();
		
		Intent Email = new Intent(Intent.ACTION_SEND);
	    Email.setType("text/email");
	    
	    Email.putExtra(Intent.EXTRA_SUBJECT, "Sharing Interview Question");	
	    Email.putExtra(Intent.EXTRA_TEXT, CommonFunctions.buildShareQuestion(question.getText().toString(), ans.getText().toString()));
	    startActivity(Intent.createChooser(Email, "Share through eMail"));
		
	}

	private void AlterAdapter() {
		if (et.getText().toString().equals("")) {
			array_sort.clear();			
			adapter.notifyDataSetChanged();
		} else {
			adapter.setSearchText(et.getText().toString());
			array_sort.clear();
			for (int i = 0; i < questions.size(); i++) {
				if (questions.get(i).getTitle().toUpperCase()
						.contains(et.getText().toString().toUpperCase()) ||
					questions.get(i).getDescription().toUpperCase()
						.contains(et.getText().toString().toUpperCase())) {
					array_sort.add(questions.get(i));
				}
				adapter.notifyDataSetChanged();
			}
		}
	}

}
