package com.vik.codepath.apps.todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	private final int REQUEST_CODE = 32;
	//ArrayList<String> items;
	//ArrayAdapter<String> itemsAdapter;
	private TodoItemDataAdaptor itemsAdapter;
	ListView lvItems;
	//DataAccessHelper itemsDAO;
	//ArrayList<TodoItem> TodoItemsList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        lvItems = (ListView)findViewById(R.id.lvItems);
        itemsAdapter = new TodoItemDataAdaptor(getApplicationContext(), R.id.lvItems, R.id.cbListItemDoneStatus);
        itemsAdapter.readItems();
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void addTodoItem(View v) {
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.addTodoItem(etNewItem.getText().toString());
    	etNewItem.setText("");
    }
    
    private void setupListViewListener() {
 
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String item_desc = itemsAdapter.getItem(position).getDescription();
				Intent editItemIntent = new Intent(TodoActivity.this, EditItemActivity.class);
				editItemIntent.putExtra("item_description", item_desc);
				editItemIntent.putExtra("item_position", position);
				startActivityForResult(editItemIntent, REQUEST_CODE);	
			}
		});
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item_description = itemsAdapter.getItem(position).getDescription();
				itemsAdapter.removeTodoItem(position);
				itemsAdapter.notifyDataSetChanged();
				Toast.makeText(getApplication(), "Removed!", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
    		String item_desc = data.getExtras().getString("updated_item_description");
    		int position = data.getExtras().getInt("item_position");
    		TodoItem t = itemsAdapter.getItem(position);
    		itemsAdapter.updateItem(t, item_desc, false);
    		itemsAdapter.notifyDataSetChanged();
    		
    	}
    }
   
    
}
