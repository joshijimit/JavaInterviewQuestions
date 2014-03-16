package com.evolvision.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.evolvision.android.utility.AppRater;
import com.google.analytics.tracking.android.EasyTracker;
import com.startapp.android.publish.StartAppAd;

public class MainActivity extends Activity implements OnClickListener {

	private static boolean isNewStart = true;
	public static boolean isNightMode = false;
	// private StartAppAd startAppAd = new StartAppAd(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB){
			isNightMode = true;
			
		}
		
		StartAppAd.init(this, "101473701", "201025652");
		setContentView(R.layout.activity_main);

		Button mBtn1 = (Button) findViewById(R.id.button1);
		mBtn1.setOnClickListener(this);
		if (isNewStart) {
			AppRater.app_launched(this);
			isNewStart = false;
		}		

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

	/*
	 * @Override public void onResume() { super.onResume();
	 * startAppAd.onResume(); }
	 */

	@Override
	public void onClick(View v) {
		Log.i("clicks", "You Clicked Start");
		Intent i = new Intent(MainActivity.this, MainActivity2.class);
		startActivity(i);
	}

	public void showFavorite(View v) {
		Log.i("clicks", "You Clicked Show Favourit");
		Intent i = new Intent(MainActivity.this, MainActivity3.class);
		Bundle b = new Bundle();
		b.putString("Favorite", "true");
		i.putExtras(b);
		startActivity(i);
	}
	
	public void changeToNightMode(View v) {
		Log.i("clicks", "You Clicked night mode");
		
		Button butNightMode = (Button) findViewById(R.id.nightMode); 
		
		if(isNightMode){
			isNightMode = false;
			butNightMode.setText("Turn night mode ON");
			Toast.makeText(getApplicationContext(), "Night Mode Deactivated", Toast.LENGTH_SHORT).show();
		}else{
			isNightMode = true;
			butNightMode.setText("Turn night mode OFF");
			Toast.makeText(getApplicationContext(), "Night Mode Activated.", Toast.LENGTH_SHORT).show();
		}
		
		//Intent i = new Intent(MainActivity.this, MainActivity2.class);
		//startActivity(i);
	}

	public void searchQuestions(View v) {
		Log.i("clicks", "You Clicked search");
		// startAppAd.showAd(); // show the ad
		// startAppAd.loadAd(); // load the next ad
		Intent i = new Intent(MainActivity.this, MainActivity4.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		isNewStart = true;
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menuhome, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_rateit:
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse("market://details?id=" + AppRater.APP_PNAME)));
			return true;

		case R.id.menu_feedback:
			Intent Email = new Intent(Intent.ACTION_SEND);
			Email.setType("text/email");
			Email.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "visionevolving@gmail.com" });
			Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
			startActivity(Intent.createChooser(Email, "Send Feedback:"));
			return true;

		}

		return true;

	}

}
