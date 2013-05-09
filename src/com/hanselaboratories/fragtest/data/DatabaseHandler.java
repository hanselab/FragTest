package com.hanselaboratories.fragtest.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	// Static variables
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "orderDbManager";
	public static final String TABLE_ORDERS = "Orders";
	/*
	 * Orders table columns names
	 */
	private static final String KEY_ID = "id";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_HU = "handling_unit";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String SQL_CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "(" 
				+ KEY_ID + " INTEGER PRIMARY KEY, " 
				+ KEY_DESCRIPTION + " TEXT, " 
				+ KEY_HU + " INTEGER)";
		
		db.execSQL(SQL_CREATE_ORDERS_TABLE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
		
		onCreate(db);
	}
	
	/*
	 * adding new order
	 */
	public void addOrder(Order order) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_ID, order.getID());
		values.put(KEY_DESCRIPTION, order.getDescription());
		values.put(KEY_HU, order.getHandlingUnit());
		
		try {
			db.insertOrThrow(TABLE_ORDERS, null, values);
		} catch (SQLException e) {
			Log.e("Hanselog", "DB: " + DATABASE_NAME + e.toString());
		}
		
		db.close();
	}
	
	/*
	 * read single order filtered by ID
	 */
	public Order getOrder(int idOrder) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_ORDERS, new String[] {KEY_ID, KEY_DESCRIPTION, KEY_HU}, 
				KEY_ID + "=?", new String[] { String.valueOf(idOrder)}, null, null, null, null);
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
			Order order = new Order(Integer.parseInt(cursor.getString(0)), 
				cursor.getString(1), Integer.parseInt(cursor.getString(2)));

			return order;
		} else {
			return null;
		}
	}

}
