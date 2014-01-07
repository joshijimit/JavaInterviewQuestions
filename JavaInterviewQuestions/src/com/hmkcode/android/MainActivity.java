package com.hmkcode.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hmkcode.android.utility.AppRater;


public class MainActivity extends Activity implements OnClickListener {

	private static boolean isNewStart = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
						
		Button mBtn1 = (Button) findViewById(R.id.button1);
        mBtn1.setOnClickListener(this);
        if(isNewStart){
        	AppRater.app_launched(this);
        	isNewStart = false;
        }
        
	}
	
	
	@Override
	public void onClick(View v) {
		Log.i("clicks","You Clicked Start");
        Intent i=new Intent(MainActivity.this, MainActivity2.class);
        startActivity(i);
	}
		
	public void showFavourite(View v) {
		Log.i("clicks","You Clicked Start");
        Intent i=new Intent(MainActivity.this, MainActivity3.class);
        Bundle b = new Bundle();
		b.putString("favourite", "true");
		i.putExtras(b);
        startActivity(i);
	}
	
	public void searchQuestions(View v) {
		Log.i("clicks","You Clicked Start");
        Intent i=new Intent(MainActivity.this, MainActivity4.class);        
        startActivity(i);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		isNewStart = true;
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.homemenu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.menu_rateit:				
				AppRater.app_launched(this);	        	
				return true;
			
			default:
				return true;

		}
	}

	
}
