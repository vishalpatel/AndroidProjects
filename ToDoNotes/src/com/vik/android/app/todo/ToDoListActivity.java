package com.vik.android.app.todo;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ToDoListActivity extends Activity {
	private TodoDataAdapter adapter;
	private ListView lvItemList;
	private EditText etTodoItem;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        lvItemList = (ListView) findViewById(R.id.lvItemList);
        etTodoItem = (EditText) findViewById(R.id.etAddTODO);
    
        adapter = new TodoDataAdapter(this, R.id.lvItemList, R.id.cbListItemDoneStatus);
        lvItemList.setAdapter(adapter);
        
    }
    public void AddBtnClickAction(View v) {
    	String itemValueString = etTodoItem.getText().toString();
    	if (!itemValueString.isEmpty()){
    		Task newTask = new Task(itemValueString);
    		adapter.addTask(newTask);
    		adapter.notifyDataSetChanged();
    		etTodoItem.setText(R.string.empty_string);
    	}else {
    		Toast.makeText(this, R.string.empty_text_msg, Toast.LENGTH_SHORT).show();
    	}
    }
}
