package com.example.student;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentDBHelper extends SQLiteOpenHelper {

	private static final String TAG = "Andy";
	public static final String DB_NAME = "student_manager.db";
	public static final int VERSION = 1;
	
	public StudentDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	public StudentDBHelper(Context context){
		this(context,DB_NAME,null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.v("TAG", this.getClass().getName()+" :onCreate");
		db.execSQL("Create table "+TableContanst.STUDENT_TABLE+"(_id Integer primary key AUTOINCREMENT,"
		+"name char,age integer, sex char, likes char,phone_number char,train_date date,"
		+"modify_time DATETIME)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.v("TAG", this.getClass().getName()+" :onUpgrade");
	}

}
