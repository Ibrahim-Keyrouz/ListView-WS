package com.example.fragmentws;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyNotificationService extends Service{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Toast.makeText(getBaseContext(), "On create Service", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println("bob");
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Toast.makeText(getBaseContext(), "On start Service", Toast.LENGTH_SHORT).show();
		
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		Intent notiIntent = new Intent(this,MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, notiIntent, 0);
		int icon = R.drawable.ic_launcher_app;
		long when = System.currentTimeMillis();
		String body = "You are here ";
		
		Notification n = new Notification(icon,body,when);
		n.defaults=Notification.DEFAULT_ALL;
		n.setLatestEventInfo(this, "Hi", "Hello World", pi);
		nm.notify(123, n);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(getBaseContext(), "Service Destroyed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
