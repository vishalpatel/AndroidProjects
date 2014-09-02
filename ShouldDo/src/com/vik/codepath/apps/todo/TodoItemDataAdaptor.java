package com.vik.codepath.apps.todo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class TodoItemDataAdaptor extends ArrayAdapter<TodoItem>{
	private Context ctx;
	private DataAccessHelper itemsDAO;

	public TodoItemDataAdaptor(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		this.ctx = context;
		itemsDAO = new DataAccessHelper(this.ctx);

	}
	
	@Override
	public View getView(int position, View containerView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.simple_list_item, parent, false);
		CheckBox cb = (CheckBox)rowView.findViewById(R.id.cbListItemDoneStatus);
		TodoItem t = itemsDAO.getAllTodoItems().get(position);
		cb.setText(t.getDescription());
		if(t.isCompleted()) {
			cb.setTextColor(Color.GRAY);
		}else {
			cb.setTextColor(Color.BLACK);
		}
		
		cb.setChecked(t.isCompleted());
		cb.setTag(t);
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CheckBox c = (CheckBox)buttonView;
				TodoItem referenced_task = (TodoItem)c.getTag();
				if (referenced_task != null){
					referenced_task.markCompleted(isChecked);
					itemsDAO.updateTodoItem(referenced_task);
				}

				if (isChecked) {
					c.setTextColor(Color.GRAY);
					Toast.makeText(getContext(), "Great!", Toast.LENGTH_SHORT).show();
				}else {
					c.setTextColor(Color.BLACK);
				}

			}
		});
		rowView.setLongClickable(true);
		rowView.setClickable(true);
		return rowView;
	}

	public void addTodoItem(String string) {
		TodoItem i = new TodoItem(string);
		itemsDAO.addTodoItem(i);
		this.add(i);
	}

	public void removeTodoItem(int position) {
		TodoItem t = this.getItem(position);
		this.removeTodoItem(t);
	}
	
	public void removeTodoItem(TodoItem t) {
		itemsDAO.removeTodoItem(t);
		this.remove(t);
		//this.notifyDataSetChanged();
	}
	
	public void readItems() {
    	for (TodoItem t : itemsDAO.getAllTodoItems()) {
    		this.add(t);
		}
    }
	
	public void updateItem(TodoItem t, String desc, Boolean completed) {
	    	t.setDescription(desc);
	    	t.markCompleted(completed);
	    	itemsDAO.updateTodoItem(t);
	    	this.notifyDataSetChanged();
	    }
}
