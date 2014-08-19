package com.example.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView tvOutput;
	EditText etInputText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOutput = (TextView) findViewById(R.id.tvOutputLabel);
        etInputText = (EditText) findViewById(R.id.etInputWords);
    }
    public void SubmitAction(View v) {
    	String inputWords = etInputText.getText().toString();
    	Toast t = Toast.makeText(this, inputWords, Toast.LENGTH_SHORT);
    	t.show();
    	tvOutput.setText(inputWords);
    }
}
