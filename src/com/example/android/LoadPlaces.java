package com.example.android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpRequest;





import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;




import com.example.android.SinglePlaceActivity;
import com.example.android.PlacesList;
import com.example.android.GooglePlaces;
import com.example.android.Place;

import com.example.android.MainActivity;

import com.example.android.R;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;



 
import android.util.Log;

@SuppressWarnings("unused")
public class LoadPlaces extends Activity   {
	
	// Google Places
		GooglePlaces googlePlaces;

		// Places List
		PlacesList nearPlaces;

		MainActivity main;


		
		
		// Places Listview
		ListView lv;
		
		// ListItems data
		ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
		
		
		// KEY Strings
		public static String KEY_REFERENCE = "reference"; // id of the place
		public static String KEY_NAME = "name"; // name of the place
		public static String KEY_VICINITY = "vicinity"; // Place area name
	

        double latitude ;
        double longitude ;	

	
	
	 String uriString;	
		
	 
		
		
@Override	
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.lists);
	 lv= (ListView) findViewById(R.id.list);

  Intent i = getIntent();
  uriString = i.getStringExtra("text_label");
  
  main=new MainActivity();
 latitude=main.getLatitude();
  longitude=main.getLongitude();
  
   
  
  Button btt = (Button)findViewById(R.id.btn_show_map);
  btt.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
	    	int a=1;
	    	Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
			
	    	if (nearPlaces != null)
	    	 {
			// passing near places to map activity
			i.putExtra("near_places", nearPlaces);
			i.putExtra("lock", a);
			// staring activity
			startActivity(i);
	    	
	    	
	    	
	    	 }
	    	
	    	
	     }
	});
  lv.setOnItemClickListener(new OnItemClickListener() {
	  
      public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
      	// getting values from selected ListItem
          String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();
          
          // Starting new intent
          Intent in = new Intent(getApplicationContext(),SinglePlaceActivity.class);
          
           
          in.putExtra(KEY_REFERENCE, reference);
          startActivity(in);
      }
  });
  new Load().execute(uriString);
 }

class Load extends AsyncTask<String, String, String> {

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		
	}

	/**
	 * getting Places JSON
	 * */
	protected String doInBackground(String... args) {
		
		googlePlaces = new GooglePlaces();
		
		try {
			
			
			// get nearest places
			nearPlaces = googlePlaces.search(uriString, latitude, longitude);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * and show the data in UI
	 * Always use runOnUiThread(new Runnable()) to update UI from background
	 * thread, otherwise you will get error
	 * **/
	protected void onPostExecute(String file_url) {
		// dismiss the dialog after getting all products
		
		// updating UI from Background Thread
		runOnUiThread(new Runnable() {
			public void run() {
				/**
				 * Updating parsed Places into LISTVIEW
				 * */
				// Get json response status
				String status = nearPlaces.status;
				
				// Check for all possible status
				if(status.equals("OK")){
					// Successfully got places details
					if (nearPlaces.results != null) {
						// loop through each place
						for (Place p : nearPlaces.results) {
							HashMap<String, String> map = new HashMap<String, String>();
							
							// Place reference won't display in listview - it will be hidden
							// Place reference is used to get "place full details"
							map.put(KEY_REFERENCE, p.reference);
							
							// Place name
							map.put(KEY_NAME, p.name);
							
							
							// adding HashMap to ArrayList
							placesListItems.add(map);
						}
						// list adapter
						ListAdapter adapter = new SimpleAdapter(LoadPlaces.this, placesListItems,
				                R.layout.list_item,
				                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
				                        R.id.reference, R.id.name });
						
						// Adding data into listview
						lv.setAdapter(adapter);
					}
				}
				
			}
		});

	}

}





}
  
 
 
 


 
 
 
 
 
 
  
 
 
 