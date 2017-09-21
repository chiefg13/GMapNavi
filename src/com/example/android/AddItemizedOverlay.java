package com.example.android;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/**
 * Class used to place marker or any overlay items on Map
 * */
public class AddItemizedOverlay extends ItemizedOverlay<OverlayItem> {
 
       private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
       private int pos=0;
       private Context context;
       String s;
       String stemp;
       String templat,templong;
       static double lt=0,lng=0;
      
       
       
       
       ImageView image;
       static Bitmap bitmap;
       
       public AddItemizedOverlay(Drawable defaultMarker) {
            super(boundCenterBottom(defaultMarker));
       }
 
       public AddItemizedOverlay(Drawable defaultMarker, Context context) {
            this(defaultMarker);
            this.context = context;
       }
       
       @Override
       public boolean onTouchEvent(MotionEvent event, MapView mapView)
       {   
 
           if (event.getAction() == 1) {
               GeoPoint geopoint = mapView.getProjection().fromPixels(
                   (int) event.getX(),
                   (int) event.getY());
               // latitude
             //  double lat = geopoint.getLatitudeE6() / 1E6;
               double lat = geopoint.getLatitudeE6() / 1E6;
               // longitude
             //  double lon = geopoint.getLongitudeE6() / 1E6;
               double lon = geopoint.getLongitudeE6() / 1E6;
               Toast.makeText(context, "Lat: " + lat + ", Lon: "+lon, Toast.LENGTH_SHORT).show();
           }
           return false;
       } 
 
       @Override
       protected OverlayItem createItem(int i) {
          return mapOverlays.get(i);
       }
 
       @Override
       public int size() {
          return mapOverlays.size();
       }
 
       @Override
       protected boolean onTap(int index) {
         OverlayItem item = mapOverlays.get(index);
        s=item.routableAddress();
         final Dialog dialog = new Dialog(this.context);
        
         dialog.setContentView(R.layout.dialog);
         dialog.setTitle(item.getTitle());
         TextView text = (TextView) dialog.findViewById(R.id.text);
         text.setText(item.getSnippet());
       //  System.out.println("."+item.getSnippet()+".");
      //  s=item.getSnippet();
         pos=s.indexOf(",");
        
        if (pos != -1 ){
        
       templat=  s.substring(0,pos);
       templong= s.substring(pos+2,s.length());
         lt=Double.parseDouble(templat);
         lng=Double.parseDouble(templong);
       
     
         }
          image = (ImageView) dialog.findViewById(R.id.image);
          image.setImageBitmap(bitmap);
          
          Button button = (Button) dialog.findViewById(R.id.button1);
          button.setOnClickListener(new View.OnClickListener() 
          {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
         });
  
          
          
          Button button2 = (Button) dialog.findViewById(R.id.button23);
          button2.setOnClickListener(new View.OnClickListener() 
          {

			public void onClick(View arg0) {
				 
				View v = new View(context);
				Context myContext = v.getContext();
			
				Intent i = new Intent();
		        i.setClass(myContext,SetGoogleInstructions.class);
		      
		          i.putExtra("lat", MainActivity.getLatitude());
		          i.putExtra("lng", MainActivity.getLongitude());
		          i.putExtra("lat2", lt);
		          i.putExtra("lng2", lng);
		          myContext.startActivity(i);
			}
         });
          
        
      
         dialog.show();
         return true;
       }
 
       public void addOverlay(OverlayItem overlay) {
          mapOverlays.add(overlay);
       }
       
       public void populateNow(){
    	   this.populate();
       }
       
       public static void getimage(Bitmap bit) {
    	    bitmap=bit;
    	   
       }
       
 
    }