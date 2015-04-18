package com.example.fragmentws;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLCars {

	// SQLiteDatabase.openOrCreateDatabase(String path,
	// SQLiteDatabase.CursorFactory factory)

	public static final String KEY_BRAND = "BRAND";
	//public static final String KEY_PRODUCT = "Description";
	public static final String DATABASE_NAME = "Cars";
	public static final String DATABASE_TABLE = "cars";
	public static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	public SQLCars(Context c) {
		ourContext = c;
	}

	public SQLCars open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

				private static class DbHelper extends SQLiteOpenHelper {
			
					public DbHelper(Context context) {
						super(context, DATABASE_NAME, null, DATABASE_VERSION);
						// TODO Auto-generated constructor stub
					}
			
					@Override
					public void onCreate(SQLiteDatabase db) {
						// TODO Auto-generated method stub
						db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ( " + KEY_BRAND
								+ " varchar2(100) PRIMARY KEY );");
			
					}
			
					@Override
					public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
						// TODO Auto-generated method stub
			
					}
			
				}

	public long createEntry(String vCar) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_BRAND, vCar);
		//cv.put(KEY_PRODUCT, vProd);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public List<String> getData() {
		System.out.println("Fet getdata");
		int i = 0 ;
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_BRAND};
		Cursor c = ourDatabase.query( DATABASE_TABLE, columns, null, null, null, null, null, null);
		
		//String[] result = new String[12];
		
		List<String> result = new ArrayList<String>();
		
		int iRow = c.getColumnIndex(KEY_BRAND);
		//int iName = c.getColumnIndex(KEY_PRODUCT);
		System.out.println(iRow);
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			System.out.println(i);
			//result[i] =c.getString(iRow) ;
			
			//result.set(i, c.getString(iRow));
			result.add(c.getString(iRow));
			i++;
			
		}
		
		return result;
	}

	public String getCar(String info)  throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_BRAND};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_BRAND + "='" + info+"'",null, null, null, null);
		if (c != null ) {
			c.moveToFirst();
			
			int iName = c.getColumnIndex(KEY_BRAND);
			
			return c.getString(iName);
		}
		
		return null;
	}

	/*public long updateColumns(String vID, String vCar)  throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		
		cv.put(KEY_BRAND, vID);
		return ourDatabase.update(DATABASE_TABLE, cv, KEY_BRAND + "='" + vID+"'", null);
		
	}*/

	/*public long deleteColumns(String pID)  throws SQLException{
		// TODO Auto-generated method stub
		
		return ourDatabase.delete(DATABASE_TABLE, KEY_ID + "='" + pID+"'", null);
		
	}*/

}
