package com.example.fragmentws;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity{

	MediaPlayer ourSound;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.splash);
		//SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		//boolean music = getPrefs.getBoolean("checkbox", true);
		//if (music == true) {
		ourSound = MediaPlayer.create(Splash.this,R.raw.file);
		ourSound.start();
	//	}
		Thread timer = new Thread() {
			public void run() {
				try{
					sleep(4000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally {
					Intent startMain = new Intent("com.example.fragmentws.MAINACTIVITY");
					startActivity(startMain);
				}
			}
		};
		
		timer.start();
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSound.release();
		finish();
	}
	
	
	
	
	
	

}
