package com.vik.codepath.apps.todo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataAccessHelper extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "TodoDB";
	private static final String TABLE_TASKS = "tasks";
	private static final int COL_ID = 0;
	private static final String KEY_ID = "id";
	private static final int COL_CREATETIME = 1;
	private static final String KEY_CREATETIME = "createtime";
	private static final int COL_STATUS = 2;
	private static final String KEY_STATUS = "status";
	private static final int COL_TASKDESC = 3;	
	private static final String KEY_TASKDESC = "description";
	
	public DataAccessHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION, null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create_query = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_CREATETIME + " INTEGER, "
				+ KEY_STATUS + " INTEGER, "
				+ KEY_TASKDESC + " TEXT" + ")";
		db.execSQL(create_query);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String rm_query = "DROP TABLE IF EXISTS " + TABLE_TASKS;
		db.execSQL(rm_query);
		onCreate(db);
	}
	
	public void addTodoItem(TodoItem t)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_TASKDESC, t.getDescription());
		values.put(KEY_CREATETIME, t.getTimestamp());
		values.put(KEY_STATUS, t.isCompleted() ? 1 : 0);
		db.insert(TABLE_TASKS, null, values);
		db.close();
	}
	
	public void removeTodoItem(TodoItem t)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_ID, t.getID());
		db.delete(TABLE_TASKS, KEY_ID+"=?", new String[]{String.valueOf(t.getID())});
		db.close();
	}
	
	public List<TodoItem> getAllTodoItems(){
		SQLiteDatabase db = this.getReadableDatabase();
		List<TodoItem> itemlist = new ArrayList<TodoItem>();
		String select_query = "SELECT * FROM " + TABLE_TASKS +" ORDER BY " +KEY_CREATETIME +" DESC ;";
		Cursor cursor = db.rawQuery(select_query, null);
		if (cursor.moveToFirst()){
			do {
				TodoItem t = new TodoItem(cursor.getString(COL_TASKDESC));
				t.markCompleted(cursor.getInt(COL_STATUS) == 0? false : true);
				t.setTimestamp(cursor.getLong(COL_CREATETIME));
				t.setID(cursor.getLong(COL_ID));
				itemlist.add(t);
			}while(cursor.moveToNext());
		}
		
		db.close();
		
		return itemlist;
	}
	
	public void updateTodoItem(TodoItem t) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_STATUS, t.isCompleted()?1:0);
		values.put(KEY_TASKDESC, t.getDescription());
		db.update(TABLE_TASKS, values, KEY_ID + "=?", new String[]{String.valueOf(t.getID())});
		db.close();
	}
}
