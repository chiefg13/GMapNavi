package com.example.android;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.io.InputStream;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;

public class properties extends Activity implements OnCheckedChangeListener{
	
	//oi static metablites pou 8a xreiastoun sto LoadPlaces einai :radius , lan , measure
	
	
	//default
	 public static String radius="1000";
	 private static String prevrad;
	 public static String lan;
	  //need to be fixed
	 private static String measurem="imperial";//miles
	 private static String measurek="metric";//kilometers
	 public static String kmORmiles;
	 public static String theText;
	 
	 public static String measure;
	 RadioGroup rb,rb1;
	 
	 
	 
	 protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.properties);
		  final EditText et = (EditText) findViewById(R.id.radius);
		 // String theText = et.getText().toString();
		 
		  lan="en";
		  String kmORmiles="km";
		  prevrad=radius; //krataei thn proigoumenh timh tou radius
		  measure=measurek;
		  
		  Button btt = (Button)findViewById(R.id.confirm);
		  rb =(RadioGroup)findViewById (R.id.measure);
		  rb1=(RadioGroup)findViewById (R.id.lang);
		  
		  rb.setOnCheckedChangeListener(this);
		  rb1.setOnCheckedChangeListener(this);
		  
		  
		  btt.setOnClickListener(new OnClickListener() {
			    public void onClick(View view) {
			  
			    	
			    	 if (et.getText().toString().isEmpty())
			    		 radius=prevrad;
			    	 else 
			    		 {  theText = et.getText().toString();
			    	 radius=theText;
			    	 System.out.println(radius);}
			    	 
			    	 
			    	 //Intent in = new Intent(getApplicationContext(),MainActivity.class);
			    	 //in.putExtra("text_label", theText);
			       //properties.this.startActivity(in);
			    
			    	 finish();
			   //  yourActivity.onBackPressed();
			    	 
			   
			    
			    }
			    });
		  
		  
		 
		 }

	 

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(checkedId) {
        case R.id.radiometr:
           
            	measure=measurek;
            	kmORmiles="km";
            break;
        case R.id.radiomil:
        	kmORmiles="miles";
            	measure=measurem;
            break;
        case R.id.langen:
            
            	lan="en";
            break;
        case R.id.langgr:
             
            	lan="el";
            break; 
    }// System.out.println(measure);
	}
	

}
