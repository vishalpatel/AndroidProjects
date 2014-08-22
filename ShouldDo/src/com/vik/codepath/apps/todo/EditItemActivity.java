package com.vik.codepath.apps.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	EditText etEditItemInput;
	int position = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		etEditItemInput = (EditText)findViewById(R.id.etEditItemInput);
		String input_data = getIntent().getStringExtra("item_description");
		if (input_data == null)
			input_data = "";
		etEditItemInput.setText(input_data);
		position = getIntent().getIntExtra("item_position", -1);
	}
	
	public void onSave(View v)
	{
		Intent data = new Intent();
		data.putExtra("updated_item_description", etEditItemInput.getText().toString());
		data.putExtra("item_position", position);
		setResult(RESULT_OK, data);
		finish();
	}
}
