package com.example.android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpRequest;



import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Search extends Activity  {
	private static final String PLACES_SEARCH_URL =  "https://maps.googleapis.com/maps/api/place/search/json?";
	  
	private static final boolean PRINT_AS_STRING = false;
 
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.search);
  final EditText et = (EditText) findViewById(R.id.tx);
 String theText = et.getText().toString();
  


  Button btt = (Button)findViewById(R.id.btt);
  btt.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
	    	 String theText = et.getText().toString();
	    	 Intent in = new Intent(getApplicationContext(),LoadPlaces.class);
	    	 in.putExtra("text_label", theText);
	       Search.this.startActivity(in);
	    	
	    	
	    }
	});
  
  
 
 }

 
 }
 
 

 
 
 
