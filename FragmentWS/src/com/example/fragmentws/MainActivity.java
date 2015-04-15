package com.example.fragmentws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

















//import android.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//stopService(new Intent(getBaseContext(),MyServices.class));
		finish();
	}



	private List<Car> myCars = new ArrayList<Car>();
	
	HttpClient client;
	final static String URL = "http://192.168.0.103:8080/CarsWS/webresources/entities.cars/";
	JSONArray json;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		nm.cancelAll();
		
		
		client = new DefaultHttpClient();
		
		new LongRunningGetIO().execute(); 
		
	//	populateCarList();
		//populateListView();
		//registerClickCallBack();
	}
	
	
	
	public JSONArray lastTweet () throws ClientProtocolException,IOException,JSONException{
		
		StringBuilder url = new StringBuilder(URL);
		
		
		HttpGet get = new HttpGet(url.toString());
		HttpResponse r = client.execute(get);
		int status = r.getStatusLine().getStatusCode();
		if (status == 200) {
			HttpEntity entity = r.getEntity();
			String data = EntityUtils.toString(entity);
			JSONArray timeline = new JSONArray(data); // return all the result into a JSON Array
			//JSONObject last = timeline.getJSONObject(0); // return the first record of the JSON Array 
			return timeline;
		}
		Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT);
		return null;
	}
	
	private void populateListView() {
		// TODO Auto-generated method stub
		ArrayAdapter<Car> adapter = new MyListAdapter();
		ListView list = (ListView)findViewById(R.id.carsListView); 
		list.setAdapter(adapter);
		
	}
	private void populateCarList() {
		// TODO Auto-generated method stub
		
	/*	myCars.add(new Car("Kia",2013,R.drawable.misc_kia));
		myCars.add(new Car("Renault",2012,R.drawable.misc_kia));
		myCars.add(new Car("Peugeot",2015,R.drawable.misc_kia));
		myCars.add(new Car("Subaru",2012,R.drawable.misc_kia));
		myCars.add(new Car("BMW",2017,R.drawable.misc_kia));
		myCars.add(new Car("Mercedes",2018,R.drawable.misc_kia));
		myCars.add(new Car("Hummer",2015,R.drawable.misc_kia));
		myCars.add(new Car("Toyota",2012,R.drawable.misc_kia));
		myCars.add(new Car("Honda",2011,R.drawable.misc_kia));
		myCars.add(new Car("Hyundai",2014,R.drawable.misc_kia));*/
		
		
		//myCars.add(new Car("Kia",2013,R.drawable.misc_kia));
		
	}
	
	
	private void registerClickCallBack() {
		// TODO Auto-generated method stub
		ListView list = (ListView)findViewById(R.id.carsListView); 
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				// TODO Auto-generated method stub
				Car currentcar = myCars.get(position);
				String message = "This car is a " + currentcar.getMake() + " and its model is "+currentcar.getYear();
				Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
	}
	
	
	private class MyListAdapter extends  ArrayAdapter<Car>{
		

		public MyListAdapter() {
			super(MainActivity.this,R.layout.item_view,myCars);
			
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			

			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.item_view, parent,false);
			}
			
			Car currentCar = myCars.get(position);
		
			
		/*	Uri otherPath = Uri.parse(""+R.drawable.+"bmw");
			System.out.println(otherPath);
			//File imgFile = new  File("res/drawable/"+(currentCar.getMake()).toLowerCase()+".png");
			File imgFile = new  File(otherPath.toString());
			

			if(imgFile.exists()){
					System.out.println("FET");
			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
			    

			    imageView.setImageBitmap(myBitmap);

			}*/
			
			
			String imageName = currentCar.getMake().toLowerCase();
			int resID = getResources().getIdentifier(imageName, "drawable", "com.example.fragmentws");
			ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
			imageView.setImageResource(resID);
			
			
			
			TextView txtYear = (TextView)itemView.findViewById(R.id.item_txtYear);
			txtYear.setText(Integer.toString(currentCar.getYear()));
			
			TextView txtMake = (TextView)itemView.findViewById(R.id.item_txtMake);
			txtMake.setText(currentCar.getMake());
			
			
			
			return itemView;
		}
		
	
		
	}
	
	
	
	class LongRunningGetIO extends  AsyncTask <Void, Void, String>{
		
		
	


		@Override
		protected String doInBackground(Void... params) {
		//protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				json = lastTweet();
				JSONObject last;
				
				
				for (int i = 0 ; i<json.length(); i++) {
				
				
				last = json.getJSONObject(i);
				
			
				
				
				
				
				myCars.add(new Car(last.getString("brand"),last.getInt("year")));
				 
				
				}
				
				
				
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String results) {
			
			populateListView();
			registerClickCallBack();
			
		//	Thread timer = new Thread() {
			//	public void run() {
				//	try{
			//			sleep(4000);
			//		}catch(InterruptedException e){
				//		e.printStackTrace();
				//	}finally {
			System.out.println(System.currentTimeMillis());
			
						AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
						Intent notiIntent = new Intent();
						notiIntent.setClass(getBaseContext(), MyNotificationService.class);
						PendingIntent pi = PendingIntent.getService(getBaseContext(), 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
					//	startService(new Intent(getBaseContext(),MyNotificationService.class));
					//	alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, pi);
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,10000, pi);
						
					    
					//}
				//}
			//};
			
			
			
		//	timer.start();
			

		
		}




		}
	
	
	
}
