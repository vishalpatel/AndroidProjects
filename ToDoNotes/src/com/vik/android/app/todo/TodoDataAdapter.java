package com.vik.android.app.todo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class TodoDataAdapter extends ArrayAdapter<Task>{
	private Context ctx;
	private List<Task> taskList = new ArrayList<Task>();
	
	
	public TodoDataAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		this.ctx = context;
	}
	
	@Override
	public View getView(int position, View containerView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.simple_list_item, parent, false);
		CheckBox cb = (CheckBox)rowView.findViewById(R.id.cbListItemDoneStatus);
		Task t = taskList.get(position);
		cb.setText(t.getDescription());
		if (t.getDone())
			cb.setTextColor(Color.GRAY);
		
		cb.setChecked(t.getDone());
		cb.setTag(t);
		
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CheckBox c = (CheckBox)buttonView;
				Task referenced_task = (Task)c.getTag();
				if (referenced_task != null)
					referenced_task.setDone(isChecked);
					
				if (isChecked) {
					c.setTextColor(Color.GRAY);
					Toast.makeText(getContext(), "Great!", Toast.LENGTH_SHORT).show();
				}else {
					c.setTextColor(Color.BLACK);
				}
				
			}
		});
		return rowView;
	}
	
	public void addTask(Task t) {
		taskList.add(0, t);
		this.add(t);
	}
	
	
}
