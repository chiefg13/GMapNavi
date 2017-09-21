package com.example.android;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.google.gson.Gson;


public class MainActivity extends MapActivity implements OnMenuItemClickListener, LocationListener {
	MapView mapView;

	GeoPoint p;
	private Projection projection;  
	MapController mc;
	 LocationManager manager;
	 
  
    // Button
    Button btnShowOnMap;
    List<Overlay> mapOverlays;

	AddItemizedOverlay itemizedOverlay;
	AddItemizedOverlay2 itemizedOverlay2;
	PlacesList nearPlaces;
	
	OverlayItem overlayitem;
    
	double start_lat;
	double start_lng;
	double end_lat;
	double end_lng;
     
     
    // Places Listview
    ListView lv;
   double latitude ;
   double longitude ;
   public static double lat;
   public static double lng;
   String tower;
	class MapOverlay extends com.google.android.maps.Overlay
    {
       
        
		
		
		
        @Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
            //---when user lifts his finger---
            if (event.getAction() == 1) {                
            	 GeoPoint  p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
 
                Geocoder geoCoder = new Geocoder(
                    getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(
                        p.getLatitudeE6()  / 1E6, 
                        p.getLongitudeE6() / 1E6, 1);
 
                    String add = "";
                    if (addresses.size() > 0) 
                    {
                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                             i++)
                           add += addresses.get(0).getAddressLine(i) + "\n";
                    }
 
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {                
                    e.printStackTrace();
                }   
                return true;
            }
            else                
                return false;
        }   
        
       
        
    } 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        
        
       
   
       
    
       mapView = (MapView) findViewById(R.id.mapView);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
       
		View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        mapView.setStreetView(true);
        
        mc = mapView.getController();
        manager =(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
     	  Criteria crit=new Criteria(); 
     	  
     	  tower=manager.getBestProvider(crit, false);
         
          
          
          Location location =manager.getLastKnownLocation(tower);
          
          
          if(location!=null){
          	lat=location.getLatitude();
          	lng=location.getLongitude();
          	   p = new GeoPoint(
                     (int) (lat * 1E6), 
                     (int) (lng * 1E6));
                 mc.setZoom(17);
                
                    
                  mc.animateTo(p);
                 
                
                 mapOverlays = mapView.getOverlays();
                 Drawable drawable_user = this.getResources()
         				.getDrawable(R.drawable.mark_blue);
         		
         		itemizedOverlay2 = new AddItemizedOverlay2(drawable_user, this);
         		
         		// Map overlay item
         		overlayitem = new OverlayItem(p, "Your Location",
         				"That is you!");

         		itemizedOverlay2.addOverlay(overlayitem);
         		
         		mapOverlays.add(itemizedOverlay2);
         		itemizedOverlay2.populateNow();
         		
         		
         		
               
                 mc=mapView.getController();
                 
                 
                 mapView.invalidate();
          	
          }
 
       
        
        
        Intent in = getIntent();
    	Serializable a=in.getSerializableExtra("lock");
        nearPlaces = (PlacesList) in.getSerializableExtra("near_places");
        
        Drawable drawable = this.getResources().getDrawable(R.drawable.mark_red);
		
		itemizedOverlay = new AddItemizedOverlay(drawable, this);
        mc = mapView.getController();
        
        if(a != null){
    	int minLat = Integer.MAX_VALUE;
		int minLong = Integer.MAX_VALUE;
		int maxLat = Integer.MIN_VALUE;
		int maxLong = Integer.MIN_VALUE;
		
		SourceOverlay  rad = new SourceOverlay();
	 	rad.setSource(p ,Float.parseFloat(properties.radius));
		
		 mapOverlays.add(rad);
		// itemizedOverlay2.populateNow();
		itemizedOverlay.populateNow();
		
		
		
      
        mc=mapView.getController();
        
        
        mapView.invalidate();
		
		
		if (nearPlaces.results != null) {
			for (Place place : nearPlaces.results) {
				latitude = place.geometry.location.lat; // latitude
				longitude = place.geometry.location.lng; // longitude
				GeoPoint p2 = new GeoPoint((int) (latitude * 1E6),
						(int) (longitude * 1E6));
				 
				 
				 Gson gson = new Gson();
			 
			
				 new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(place.icon);
			    
			
			 
				 
				 // Map overlay item
			 overlayitem = new OverlayItem(p2,place.name, "Rating:"+place.rating+"\nAdress:"+place.formatted_address+"\nType:"+gson.toJson(place.types)+"\nLatitude:"+place.geometry.location.lat+"\nLongitude:"+place.geometry.location.lng);
				itemizedOverlay.addOverlay(overlayitem);
				// calculating map boundary area
				minLat  = Math.min( p2.getLatitudeE6(), minLat );
			    minLong = Math.min( p2.getLongitudeE6(), minLong);
			    maxLat  = Math.max( p2.getLatitudeE6(), maxLat );
			    maxLong = Math.max( p2.getLongitudeE6(), maxLong );
			}
			mapOverlays.add(itemizedOverlay);
			
			// showing all overlay items
			itemizedOverlay.populateNow();
			
		}
		// Adjusting the zoom level so that you can see all the markers on map
				mapView.getController().zoomToSpan(Math.abs( minLat - maxLat ), Math.abs( minLong - maxLong ));
				
				// Showing the center of the map
				mc.animateTo(new GeoPoint((maxLat + minLat)/2, (maxLong + minLong)/2 ));
				mapView.postInvalidate();
       }
        
        
        
        Intent in2 = getIntent();
        Serializable b1=in2.getSerializableExtra("lock2");
        String jsonArray = in2.getStringExtra("jsonArray");
        
    	 if(b1 !=null) {
        
    		     
    	          
    	             try{
    	            	 JSONArray steps = new JSONArray(jsonArray);
                         mapView.getOverlays().add(new RoutePathOverlay(LoadGoogleDirections.pointToDraw));

    	            for(int i = 0; i < steps.length(); i++){
            
			 
	             		 JSONObject c = steps.getJSONObject(i);
	             		 JSONObject c2=c.getJSONObject("start_location");
                          start_lat=  c2.getDouble("lat");
                          start_lng=c2.getDouble("lng");

                       //   System.out.println(start_lng);
                          
                           
                       JSONObject c3=c.getJSONObject("end_location");
                         end_lat=c3.getDouble("lat");
                          end_lng=c3.getDouble("lng");
                        
                          String message=c.getString("html_instructions" ).replaceAll("<[^>]*>", "");  
                       //   System.out.println(end_lat);
                        //  System.out.println(end_lng); 
                              
                          mapOverlays = mapView.getOverlays();
                          
                          projection = mapView.getProjection();
                          
             		     GeoPoint gP1 = new GeoPoint(
             	                  (int) ( start_lat * 1E6), 
             	                     (int) ( start_lng * 1E6));
             	     
             	    		  
             	    		   GeoPoint gP2 = new GeoPoint(
             	                      (int) ( end_lat * 1E6), 
             	                     (int) ( end_lng * 1E6));
                          
                         // mapView.getOverlays().add(new pathOverlay(gP1, gP2));
                         
                          if (i==steps.length()-1){
                        	    drawable = this.getResources().getDrawable(R.drawable.mark_red);
                  		
                  		itemizedOverlay2 = new AddItemizedOverlay2(drawable, this);
                  		mapOverlays.add(itemizedOverlay2);
            			
                  		
                  		overlayitem = new OverlayItem(gP2, "Your Destination",
                				"You must go there!");
            			// showing all overlay items
                  		itemizedOverlay2.addOverlay(overlayitem);
                		
                		mapOverlays.add(itemizedOverlay2);
                		
            			itemizedOverlay2.populateNow();}
                          else if(i<steps.length()-1){
                              drawable = this.getResources().getDrawable(R.drawable.marker2);
                             
                             itemizedOverlay2 = new AddItemizedOverlay2(drawable, this);
                             mapOverlays.add(itemizedOverlay2);
                        
                             
                             
                             overlayitem = new OverlayItem(gP1, "Step Point", message);
                        // showing all overlay items
                             itemizedOverlay2.addOverlay(overlayitem);
                           
                           mapOverlays.add(itemizedOverlay2);
                           
                        itemizedOverlay2.populateNow();
                              
                             }

			       
			        
	                
	                }  
    	            
    	        
    	          
	              } catch (JSONException e) {
	            e.printStackTrace();
	             }	 
    		 
        
    	 }
        
        
        
    }
	
	
	
	
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		manager.removeUpdates(this);
	}





	@Override
	protected void onResume() {
		 super.onResume();
       manager.requestLocationUpdates(tower, 500, 1,  this);
    
    
    }





	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
              
    }
	
    
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i=new Intent(this,Search.class);
                startActivity(i);
                return true;
            case R.id.item2:
            	if(!mapOverlays.isEmpty()) 
                { 
                mapOverlays.clear(); 
                  p = new GeoPoint(
                        (int) (lat * 1E6), 
                        (int) (lng * 1E6));

                Drawable drawable_user = this.getResources()
        				.getDrawable(R.drawable.mark_blue);
        		
        		itemizedOverlay2 = new AddItemizedOverlay2(drawable_user, this);
                
                overlayitem = new OverlayItem(p, "Your Location",
                	    "That is you!");
                itemizedOverlay2.addOverlay(overlayitem);
        		
        		mapOverlays.add(itemizedOverlay2);
        		itemizedOverlay2.populateNow();
                	  mapView.invalidate();
            }
                return true;
            case R.id.item3:
            	Intent i3=new Intent(this,properties.class);
                startActivity(i3);
                return true;    
            default:
                return false;
        }
    }
    
   public static double getLatitude(){
      
    	
		return lat;
    	
    }
    public static double getLongitude(){
        
    	return lng;
     	
     }
    

   

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		  Log.d("LOCATION CHANGED", location.getLatitude() + "");
          Log.d("LOCATION CHANGED", location.getLongitude() + "");
		lat=location.getLatitude();
      	lng=location.getLongitude();
      	 GeoPoint p = new GeoPoint(
                 (int) (lat * 1E6  ), 
                 (int) (lng * 1E6 ));
		  
		mc.animateTo(p);
	        mc.setZoom(17);
	        mapView.getOverlays().remove(itemizedOverlay2);
	     //   mapOverlays.removeOverlay(overlayitem);
	        mapOverlays = mapView.getOverlays();
	        Drawable drawable_user = this.getResources()
					.getDrawable(R.drawable.mark_blue);
			
			itemizedOverlay2 = new AddItemizedOverlay2(drawable_user, this);
			//itemizedOverlay.removeOverlay(overlayitem);
			// Map overlay item
			 
			overlayitem = new OverlayItem(p, "Your Location",
					"That is you!");

			itemizedOverlay2.addOverlay(overlayitem);
			
			mapOverlays.add(itemizedOverlay2);
			itemizedOverlay2.populateNow();
			 mapView.invalidate();
			 // mapView.displayZoomControls(true);
	}
	





	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}





	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}





	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
    
    
   
	
	
	
	
	 private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
   	  ImageView bmImage;

   	  public DownloadImageTask(ImageView bmImage) {
   	      this.bmImage = bmImage;
   	  }

   	  protected Bitmap doInBackground(String... urls) {
   	      String urldisplay = urls[0];
   	      Bitmap mIcon11 = null;
   	      try {
   	        InputStream in = new java.net.URL(urldisplay).openStream();
   	        mIcon11 = BitmapFactory.decodeStream(in);
   	      } catch (Exception e) {
   	          Log.e("Error", e.getMessage());
   	          e.printStackTrace();
   	      }
   	      return mIcon11;
   	  }

   	  protected void onPostExecute(Bitmap result) {
   		  AddItemizedOverlay.getimage(result);
   	  }
   	}
    
    
}

    









    