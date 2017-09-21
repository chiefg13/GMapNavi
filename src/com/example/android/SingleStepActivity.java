package com.example.android;

 

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.android.LoadGoogleDirections.Load;

import android.app.Activity;
import android.app.ProgressDialog;
 
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SingleStepActivity extends Activity {

	GoogleDirections googleDirections;
	String URL;
	public JSONObject json;
	public String message1 = null;
	public String message2=null;
	public String message3=null;
	
	TextView text;
	TextView text3;
	TextView text2;
	public int step;
	
	 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.single_step);
		
		
		Intent in = getIntent();		
		
		 step = in.getIntExtra("step",0);
		  URL = in.getStringExtra("url");
		
		 
		
		
		  new Load().execute(URL);
		
		 
		
	  

	}

	class Load extends AsyncTask<String, String, String> {

		 
		
		@Override
		protected String doInBackground(String... arg0) {
			 googleDirections = new GoogleDirections();
				
			   json = googleDirections.getJSONFromUrl(URL);
			   return null;
		}
		protected void onPostExecute(String file_url) {
		runOnUiThread(new Runnable() {
			public void run() {
				  text=(TextView) findViewById(R.id.instuctions);
				  text2=(TextView) findViewById(R.id.distance);
				  text3=(TextView) findViewById(R.id.duration);
			  
				  
				  String Status = null;
					try {
						Status = json.getString("status");
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if( Status.equals("OK") ){
						Log.d("Places Status", "OK" );
				  
				  
				  try{
					  
					JSONArray	routes = json.getJSONArray("routes");
					JSONObject jsonObject = routes.getJSONObject(0);
					JSONArray	legs = jsonObject.getJSONArray("legs");
					JSONObject jsonObject2 = legs.getJSONObject(0);
				
					 
					
					JSONArray 	steps = jsonObject2.getJSONArray("steps");
					
				
				 
				 for(int i = 0; i < steps.length(); i++){
		              
					 if(i==step){
						
						
						JSONObject c = steps.getJSONObject(i);
					        
		                 
						 text.setText(c.getString("html_instructions").toString().replaceAll("<[^>]*>", "")+"  ");
		                   
		                  JSONObject c1 = c.getJSONObject("distance");
		                  text2.setText(c1.getString("text"));
					        JSONObject c2 = c.getJSONObject("duration");
					        text3.setText(c2.getString("text"));
					        
					     
			                
			                
				 }       
			 
						         
					}  
			        } catch (JSONException e) {
			            e.printStackTrace();
			        }
			   
			   
			  
					}
			
			}
		
	});
}
}
	
	
 
	
	
	
}
