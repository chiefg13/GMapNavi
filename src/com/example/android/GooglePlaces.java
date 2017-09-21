package com.example.android;

import org.apache.http.client.HttpResponseException;

import android.content.Context;
import android.util.Log;

import com.example.android.PlaceDetails;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.example.android.MainActivity;



@SuppressWarnings("deprecation")
public class GooglePlaces {

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	// Google API Key
	private static final String API_KEY = "AIzaSyALUUndP1MCcJqxBuJbDjY5AtyXv6MrD3s"; // place your API key here

	// Google Places serach url's
	

	
	
	/**
	 * Searching places
	 * @param key
	 * @param sensor
	 * @param latitude - latitude of place
	 * @params longitude - longitude of place
	 * @param radius - radius of searchable area
	 * @param query - type of place to search
	 * @return list of places
	 * */
	private double _latitude;
	private double _longitude;
	private String _radius=properties.radius;
	private String _language=properties.lan;
	public PlacesList search(String query, double latitude, double longitude) throws Exception {
        
		 this._latitude=latitude;
		 this._longitude=longitude;
		
        
		try {

			HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
			HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl("https://maps.googleapis.com/maps/api/place/textsearch/json?"));
			
			request.getUrl().put("query", query);
			request.getUrl().put("location", _latitude + "," + _longitude);
			request.getUrl().put("sensor", "false");
			
			request.getUrl().put("radius", _radius );
			request.getUrl().put("language",_language);
			request.getUrl().put("key", API_KEY);
			 
			
			
		
		//	if(types != null)
				//request.getUrl().put("types", types);

			PlacesList list = request.execute().parseAs(PlacesList.class);
			// Check log cat for places response status
			Log.d("Places Status", "" + list.status);
			return list;

		} catch (HttpResponseException e) {
			Log.e("Error:", e.getMessage());
			return null;
		}

	}

	/**
	 * Searching single place full details
	 * @param refrence - reference id of place
	 * 				   - which you will get in search api request
	 * */
	public PlaceDetails getPlaceDetails(String reference) throws Exception {
		try {

			HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
			HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl("https://maps.googleapis.com/maps/api/place/details/json?"));
			request.getUrl().put("key", API_KEY);
			request.getUrl().put("reference", reference);
			request.getUrl().put("sensor", "false");

			PlaceDetails place = request.execute().parseAs(PlaceDetails.class);
			
			return place;

		} catch (HttpResponseException e) {
			Log.e("Error in Perform Details", e.getMessage());
			throw e;
		}
	}

	/**
	 * Creating http request Factory
	 * */
	public static HttpRequestFactory createRequestFactory(
			final HttpTransport transport) {
		return transport.createRequestFactory(new HttpRequestInitializer() {
			public void initialize(HttpRequest request) {
				GoogleHeaders headers = new GoogleHeaders();
				headers.setApplicationName("AndroidHive-Places-Test");
				request.setHeaders(headers);
				JsonHttpParser parser = new JsonHttpParser(new JacksonFactory());
				request.addParser(parser);
			}
		});
	}

	

}
