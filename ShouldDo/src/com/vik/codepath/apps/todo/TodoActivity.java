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
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void addTodoItem(View v) {
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	saveItems();
    	etNewItem.setText("");
    }
    
    private void setupListViewListener() {
 
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String item_desc = items.get(position);
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
				String item_description = items.get(position);
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				Toast.makeText(getApplication(), "Removed " + item_description, Toast.LENGTH_SHORT).show();
				return false;
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
    		String item_desc = data.getExtras().getString("updated_item_description");
    		int position = data.getExtras().getInt("item_position");
    		items.set(position, item_desc);
    		itemsAdapter.notifyDataSetChanged();
    		saveItems();
    	}
    }
    
    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todolist.txt");
    	try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
    }
    
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todolist.txt");
    	try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
