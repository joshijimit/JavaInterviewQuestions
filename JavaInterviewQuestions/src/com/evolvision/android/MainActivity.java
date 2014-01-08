package com.evolvision.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.evolvision.android.utility.AppRater;
import com.startapp.android.publish.StartAppAd;

public class MainActivity extends Activity implements OnClickListener {

	private static boolean isNewStart = true;
	//private StartAppAd startAppAd = new StartAppAd(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		StartAppAd.init(this, "101473701", "201025652");
		setContentView(R.layout.activity_main);

		Button mBtn1 = (Button) findViewById(R.id.button1);
		mBtn1.setOnClickListener(this);
		if (isNewStart) {
			AppRater.app_launched(this);
			isNewStart = false;
		}

	}

	/*@Override
	public void onResume() {
		super.onResume();
		startAppAd.onResume();
	}*/

	@Override
	public void onClick(View v) {
		Log.i("clicks", "You Clicked Start");
		Intent i = new Intent(MainActivity.this, MainActivity2.class);
		startActivity(i);
	}

	public void showFavourite(View v) {
		Log.i("clicks", "You Clicked Start");
		Intent i = new Intent(MainActivity.this, MainActivity3.class);
		Bundle b = new Bundle();
		b.putString("favourite", "true");
		i.putExtras(b);
		startActivity(i);
	}

	public void searchQuestions(View v) {
		Log.i("clicks", "You Clicked Start");
		//startAppAd.showAd(); // show the ad
		//startAppAd.loadAd(); // load the next ad
		Intent i = new Intent(MainActivity.this, MainActivity4.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		isNewStart = true;		
		finish();
	}

}
