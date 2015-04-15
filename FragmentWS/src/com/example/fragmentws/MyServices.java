package com.example.fragmentws;

import java.net.URL;






import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

public class MyServices extends Service {
	
	NotificationManager nm;
    final static int uniqueID = 123344;
    Intent intent;
    PendingIntent pi;
    Thread timer;
    int b ;
    boolean a = true ;
    
    
    public void init() {
		b = 1;
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		nm.cancel(uniqueID);
		
		
		 intent = new Intent(this,MainActivity.class);
		 pi = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
		
		
		
		
		
	}
    
  

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//timer.interrupt();
		//b = 0 ;
		System.out.println("Destroyed");
		//Toast.makeText(this, "Bye Service", Toast.LENGTH_SHORT).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//stopSelf();
		stopSelf();
		System.out.println("Started");
		init();
		System.out.println("init Started");
		new DoBackgroundTask().execute();
			
		return START_STICKY;
	}
	
	private class DoBackgroundTask extends AsyncTask <URL, Integer,Long > {

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			//doInBackground();
		}

		@Override
		protected Long doInBackground(URL... params) {
			// TODO Auto-generated method stub
			//Toast.makeText(getBaseContext(), "Hello man", Toast.LENGTH_SHORT).show();
	
				
				 timer = new Thread() {
					public void run() {
						while (b == 1 ){
							String body = "Thanks Jesus";
							String title ="Hi";
							Notification n = new Notification(R.drawable.ic_launcher,body,System.currentTimeMillis());
							//n.setLatestEventInfo(this, title, body, pi);
							n.setLatestEventInfo(getBaseContext(), title, body, pi);
							n.defaults=Notification.DEFAULT_ALL;
							nm.notify(uniqueID, n);
						try{
							sleep(10000);
						}catch(InterruptedException e){
							e.printStackTrace();
						}finally {
						//	startService(new Intent(getBaseContext(),MyServices.class));
							//System.out.println("Hello bob");
						}}
						System.out.println("Finish timer");
					}
				};
				
				
				if (!timer.isAlive()){
					timer.start();
				System.out.println("1");}
				else{
					System.out.println("2");
					timer.destroy();}
				
				return null;
				
				
			
			
			
		}

	
		
			
		}
		
		
		
	
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
