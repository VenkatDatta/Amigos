package com.example.amigos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

//import com.example.amigo2.ContactLatLong;
//import com.example.amigo2.R;
//import com.example.amigo2.MapDemoActivity.HttpAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
@SuppressLint("NewApi")
public class MapActivity extends FragmentActivity{
	private static String result;
	private GoogleMap googleMap;
	private LocationManager locationManager;
	private String data;

	//private GoogleMap googleMap;
	//@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		 data = getIntent().getExtras().getString("Phone");

			
		LatLng position = new LatLng(17.385044, 78.486671);
		LatLng position1 = new LatLng(16.385044, 58.486671);
		
		// Instantiating MarkerOptions class
		MarkerOptions options = new MarkerOptions();
		MarkerOptions marker = new MarkerOptions().position(new LatLng(16.385044, 58.486671)).title("Hello Maps ").snippet("Latitude:"+16.385044+",Longitude:"+52.486671);
		
		
		// Setting position for the MarkerOptions
		options.position(position);
		
		// Setting title for the MarkerOptions
		options.title("Position");
		
		// Setting snippet for the MarkerOptions
		options.snippet("Latitude:"+17.385044+",Longitude:"+78.486671);
		//options1.snippet("Latitude:"+16.385044+",Longitude:"+78.486671);
		
		// Getting Reference to SupportMapFragment of activity_map.xml
		SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
		
		// Getting reference to google map
		 googleMap = fm.getMap();
		 
		 
		 
		 
		 
		 
		 String svcName = Context.LOCATION_SERVICE;
		    locationManager = (LocationManager)getSystemService(svcName);

		    Criteria criteria = new Criteria();
		    criteria.setAccuracy(Criteria.ACCURACY_FINE);
		    criteria.setPowerRequirement(Criteria.POWER_LOW);
		    criteria.setAltitudeRequired(false);
		    criteria.setBearingRequired(false);
		    criteria.setSpeedRequired(false);
		    criteria.setCostAllowed(true);
		    String provider = locationManager.getBestProvider(criteria, true);
		    
		    Location l = locationManager.getLastKnownLocation(provider);

	// Adding Marker on the Google Map
		 for(int i=0;i<ContactLatLong.count;i++){
				googleMap.addMarker(new MarkerOptions().position(new LatLng(ContactLatLong.latitude[i], ContactLatLong.longitude[i])).title(ContactLatLong.phonenum[i]).snippet("Latitude:"+ContactLatLong.latitude[i]+",Longitude:"+ContactLatLong.longitude[i]));
				System.out.println("Count "+ContactLatLong.count+" lat "+ContactLatLong.latitude[i]);	
			}
		 CircleOptions circleOptions = new CircleOptions().fillColor(0x40ff0000)//Color.parseColor("#00FFCC"))
				    .center(new LatLng(l.getLatitude(), l.getLongitude())).strokeWidth(0F)
				    .radius(1000);
	 				googleMap.addCircle(circleOptions);
		googleMap.setOnMapClickListener(new OnMapClickListener() {
			
			PolylineOptions rectOptions = new PolylineOptions();
			

			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub
				 rectOptions.add(point);
				 googleMap.addPolyline(rectOptions);
				 
				//googleMap.addMarker(new MarkerOptions().position(point).title("Here !!  ").snippet("Latitude:"+point.latitude+",Longitude:"+point.longitude));
			}
		});			
		googleMap.addMarker(options);
		googleMap.addMarker(marker);
		
		googleMap.setMyLocationEnabled(true);
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(l.getLatitude(), l.getLongitude())).zoom(12).build();
 
       googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
       
	}
    
	
	
	public void onPause(){
	  super.onPause();
	  Toast.makeText(getBaseContext(), "Received!P "+data, Toast.LENGTH_LONG).show();
		
	  String url2="http://10.0.0.5:80/offline.php?param1="+data;
   	 new HttpAsyncTask().execute(url2);
       
}


 @Override
public void onStop()
{
   super.onStop();
   Toast.makeText(getBaseContext(), "Received!S "+data, Toast.LENGTH_LONG).show();
	
   String url2="http://10.0.0.5:80/offline.php?param1="+data;
	 new HttpAsyncTask().execute(url2);
 //  finish();
   //Do whatever you want to do when the application stops.
}
@Override
public void onDestroy()
{
   super.onDestroy();
   Toast.makeText(getBaseContext(), "Received!D "+data, Toast.LENGTH_LONG).show();
	
   //Do whatever you want to do when the application is destroyed.
   String url3="http://10.0.0.5:80/offline.php?param1="+data;
	 new HttpAsyncTask().execute(url3);
   
}



public static String GET(String url){
    InputStream inputStream = null;
    String result = "";
    try {

        // create HttpClient
        HttpClient httpclient = new DefaultHttpClient();

        // make GET request to the given URL
        HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

        // receive response as inputStream
        inputStream = httpResponse.getEntity().getContent();

        // convert inputstream to string
        if(inputStream != null)
            result = convertInputStreamToString(inputStream);
        else
            result = "Did not work!";

    } catch (Exception e) {
        Log.d("InputStream", e.getLocalizedMessage());
    }

    return result;
}


public boolean isConnected(){
    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) 
            return true;
        else
            return false;   
}
private class HttpAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

        return GET(urls[0]);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
        //stringsplitter();
        //ContactLatLong obj=new ContactLatLong(phone,lati,lngi);
       // display.setText(phone[0]);
   }
}
private static String convertInputStreamToString(InputStream inputStream) throws IOException{
    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
    String line = "";
    result = "";
    while((line = bufferedReader.readLine()) != null)
        result += line;

    inputStream.close();
    return result;

}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 
}
