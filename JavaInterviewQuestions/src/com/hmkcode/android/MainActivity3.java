package com.hmkcode.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hmkcode.android.model.CurrentRowHolder;
import com.hmkcode.android.model.Item;
import com.hmkcode.android.model.Questions;
import com.hmkcode.android.sqlite.MySQLiteHelper;

public class MainActivity3 extends ListActivity {
    
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);		
		
		getActionBar().setDisplayHomeAsUpEnabled(true);	
		
		// if extending Activity
		//setContentView(R.layout.activity_main);
		String cat = getIntent().getStringExtra("selectedCategory");
		// 1. pass context and data to the custom adapter
		MyAdapter adapter = new MyAdapter(this, loadQuestionsFromDB(cat));
		
		// if extending Activity 2. Get ListView from activity_main.xml
		//ListView listView = (ListView) findViewById(R.id.listview);
		
		// 3. setListAdapter
		//listView.setAdapter(adapter); if extending Activity
		setListAdapter(adapter);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
	    Intent myIntent = new Intent(getApplicationContext(), MainActivity2.class);
	    startActivityForResult(myIntent, 0);
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
				items.add(new Item(question.getQuestion(),question.getAnswer()));
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
		if(button.getText().equals("Show Answer")){			
			textView.setVisibility(View.VISIBLE);
			button.setText("Hide Answer");	
		}else{
			textView.setVisibility(View.GONE);
			button.setText("Show Answer");
		}
		
	}

} 
