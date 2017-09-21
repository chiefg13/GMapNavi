package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class SetGoogleInstructions extends Activity implements OnCheckedChangeListener{
	
	
	
	
	
	RadioGroup rb;
	EditText text1;
	EditText text2;
	String mode;
	String start;
	String end;
	 
	EditText et1;
	EditText et2;
	 String theText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.instructions);
	      
	    
	     Button btt = (Button)findViewById(R.id.gpssend);
		  rb =(RadioGroup)findViewById (R.id.radioGroup);
	      rb.setOnCheckedChangeListener(this);
	         et1 = (EditText) findViewById(R.id.start);
	         et2 = (EditText) findViewById(R.id.end);
	    
	         Intent in=getIntent();
	         
	         double lat=in.getDoubleExtra("lat", 0);
	         double lng=in.getDoubleExtra("lng", 0);
	         double lat2=in.getDoubleExtra("lat2", 0);
	         double lng2=in.getDoubleExtra("lng2", 0);
	         
	         et1.setText(lat + "," + lng);
	         et2.setText(lat2+","+lng2);
	      
	      btt.setOnClickListener(new OnClickListener (){
	    	 
			public void onClick(View v) {
				start = et1.getText().toString();
				end = et2.getText().toString();
				Intent i=new Intent(getApplicationContext(),LoadGoogleDirections.class);
				i.putExtra("Start" , start);
				i.putExtra("End" , end);
				i.putExtra("Mode" , mode);
				
				startActivity(i);
				
				 
				 
         
				
				
			}
	    	  
	      });
	
	}
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(checkedId) {
        case R.id.radioButton1:
            mode="driving";
            	 
            	
            break;
        case R.id.radioButton2:
        	 mode="bicycling";
            	 
            break;
        case R.id.radioButton3:
        	 mode="walking";
            	 
            break;
        
    }
	}
	
	

}
