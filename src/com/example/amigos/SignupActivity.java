package com.example.amigos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity{

	private LocationManager locationManager;
	private EditText phone;
	private double lat;
	private double lng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_signup);
		
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

	    //updateWithNewLocation(l);
	    
	    locationManager.requestLocationUpdates(provider, 2000, 10,
	                                           locationListener);

		 phone=(EditText)findViewById(R.id.editText2);
		Button submit=(Button)findViewById(R.id.button3);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url="http://10.0.0.5:80/signUp.php?param1="+phone.getText().toString()+"&param2="+lat+"&param3="+lng;
                
           	 new HttpAsyncTask().execute(url);
           	  Toast.makeText(getBaseContext(), "Received! "+url, Toast.LENGTH_LONG).show();
           
				Intent loadapp=new Intent(SignupActivity.this,App.class);
				loadapp.putExtra("Phone", phone.getText().toString());
				startActivity(loadapp);
			}
		});
	}
	
	
	
	private void updateWithNewLocation(Location location) {
		// TODO Auto-generated method stub
		 if (location != null) {
		       lat = location.getLatitude();
		      lng = location.getLongitude();
		 }
	  	  Toast.makeText(getBaseContext(), "Received! Lat "+lat+" Long "+lng, Toast.LENGTH_LONG).show();
	         
	}
	
	
	private final LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	      updateWithNewLocation(location);
	    }

	    

		public void onProviderDisabled(String provider) {}
	    public void onProviderEnabled(String provider) {}
	    public void onStatusChanged(String provider, int status, 
	                                Bundle extras) {}
	  };

	
	
	
	
	
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
             //content.setText(result);
        }
     }
     private static String convertInputStreamToString(InputStream inputStream) throws IOException{
         BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
         String line = "";
         String result = "";
         while((line = bufferedReader.readLine()) != null)
             result += line;
  
         inputStream.close();
         return result;
  
     }

}
