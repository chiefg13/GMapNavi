package com.example.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LoadGoogleDirections extends Activity {

	GoogleDirections googleDirections;
	 private static String url = "http://maps.googleapis.com/maps/api/directions/json?origin=";
	 
	 static List<GeoPoint> pointToDraw;
 public String URL;
	
	
	String text = null;
	String text2 = null;
	String text3 = null;
//	int log[];
//	int lat[];
	
	String list;
	 JSONObject json;
	
	 
	static JSONArray	steps;
	 
	 
	 
	MainActivity main;
	 private static final String var1 = "instructions"; 
	 private static final String var2 = "instructions2"; 
	 private static final String var3 = "instructions3"; 
	 
	 
	 
	ArrayList<HashMap<String, String>>  placelist = new ArrayList<HashMap<String,String>>();
	
	ListView lv;
	String start,end,mode;
	
	TextView message1,message2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instruct_list);
		  
		
		lv = (ListView) findViewById(R.id.list2);
	    Button button=(Button)findViewById(R.id.btn_route_map);
		
	    
		
		   Intent i = getIntent();
		   start = i.getStringExtra("Start");
		   end = i.getStringExtra("End");
		   mode = i.getStringExtra("Mode");
	    
		   
		   
		   
		   button.setOnClickListener(new OnClickListener() {
			    public void onClick(View view) {
			    	 
			         int  b=1;
			    	 Intent i = new Intent(getApplicationContext(),MainActivity.class);
			    	 i.putExtra("lock2",b);
			    	 i.putExtra("jsonArray", steps.toString());
			    	startActivity(i);
			    }
			});
		   
		   
		   

		   lv.setOnItemClickListener(new OnItemClickListener() {
				  
			      public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			      	 
			    	  Intent in = new Intent(getApplicationContext(),SingleStepActivity.class);
			    	  
			    	  in.putExtra("step",position);
			    	   
			    	  in.putExtra("url",URL);
			    	 
			    	  startActivity(in);
			      
			    	  
			      
			      
			      
			      }
			  });
			  new Load().execute(start,end,mode);
	
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
			
			 
			
			
			StringBuilder s = new StringBuilder(url);
			s.append(start);
			s.append("&");
			s.append("destination=");
			s.append(end);
			s.append("&");
			s.append("sensor=");
			s.append("false");
		    s.append("&");
		    s.append("units=");
		    s.append(properties.measure);
		    s.append("&");
			s.append("language=");
			s.append(properties.lan); 
		 //	s.append("&region=gr");
			
			 
	        
	        
			
			URL=s.toString();
			
			
			
			 
			googleDirections = new GoogleDirections();
			
			   json = googleDirections.getJSONFromUrl(s.toString());
			
			   
			

			 
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
						
						JSONObject overviewPolylines = jsonObject.getJSONObject("overview_polyline");
						String encodedString = overviewPolylines.getString("points");
					 	  pointToDraw = decodePoly(encodedString);
						JSONObject jsonObject2 = legs.getJSONObject(0);
					//	mapView.getOverlays().add(new RoutePathOverlay(pointToDraw));
						 message1=(TextView) findViewById(R.id.text_instruct1);
						   message2=(TextView) findViewById(R.id.text_instruct2);
						
						message1.setText("The Destination is : "+jsonObject2.getString("end_address").toString());
						
						JSONObject jsonObject3 = jsonObject2.getJSONObject("distance");
						JSONObject jsonObject4 = jsonObject2.getJSONObject("duration");
						message2.setText(jsonObject3.getString("text").toString()+ " - " +jsonObject4.getString("text").toString() + " away.");
						
						
				 	 	steps = jsonObject2.getJSONArray("steps");
						for(int i = 0; i < steps.length(); i++){
			                JSONObject c = steps.getJSONObject(i);
						        
			             //    object[i]=i;
			                
			                
			                
			                  text = c.getString("html_instructions").toString().replaceAll("<[^>]*>", "");
			                   
			                  JSONObject c1 = c.getJSONObject("distance");
						        text2=c1.getString("text");
						        JSONObject c2 = c.getJSONObject("duration");
						        text3=c2.getString("text");
						        
						     
				                // creating new HashMap
				                HashMap<String, String> map = new HashMap<String, String>();
				 
				                // adding each child node to HashMap key => value
				                map.put(var1, text);
				                map.put(var2, text2);
				                map.put(var3, text3);
				                 
				                
				                 
				 
				                // adding HashList to ArrayList
				                placelist.add(map);
						}  
				        } catch (JSONException e) {
				            e.printStackTrace();
				        }
							
							// list adapter
							ListAdapter adapter = new SimpleAdapter(LoadGoogleDirections.this,  placelist,
					                R.layout.list_item_2,
					                new String[] { var1     }, new int[] {
					                        R.id.km     });
							
							// Adding data into listview
							lv.setAdapter(adapter);
						 
					
					
					
				}
				}
			});

		}}
		private List<GeoPoint> decodePoly(String encoded) {

		    List<GeoPoint> poly = new ArrayList<GeoPoint>();
		    int index = 0, len = encoded.length();
		    int lat = 0, lng = 0;

		    while (index < len) {
		        int b, shift = 0, result = 0;
		        do {
		            b = encoded.charAt(index++) - 63;
		            result |= (b & 0x1f) << shift;
		            shift += 5;
		        } while (b >= 0x20);
		        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		        lat += dlat;

		        shift = 0;
		        result = 0;
		        do {
		            b = encoded.charAt(index++) - 63;
		            result |= (b & 0x1f) << shift;
		            shift += 5;
		        } while (b >= 0x20);
		        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		        lng += dlng;

		        GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6), (int) (((double) lng / 1E5) * 1E6));
		        poly.add(p);
		      
		    }
		  
		    return poly;
		}
	





	}


















