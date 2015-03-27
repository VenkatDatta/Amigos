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
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class App extends Activity{

	private static String result;
	static String data;
	private Trie t1;
	private LocationManager locationManager;
	 static double lat;
 static double lng;
	private String url1;
	private TextView textDetail;
	private TextView display;
	private Location l;
	private RadioGroup rangeCheck;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app);
data = getIntent().getExtras().getString("Phone");
		
		t1=new Trie();
	    textDetail = (TextView) findViewById(R.id.textView3);
		readContacts();
		Button next=(Button)findViewById(R.id.button4);
		
		
		
		
		
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent loadmap=new Intent(App.this,MapActivity.class);
				loadmap.putExtra("Phone", data);
				startActivity(loadmap);
			}
		});
		
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
	    
	     l = locationManager.getLastKnownLocation(provider);

	    updateWithNewLocation(l);
	    
	    locationManager.requestLocationUpdates(provider, 2000, 10,
	                                           locationListener);
		 display=(TextView)findViewById(R.id.textView4);
	 	url1="http://10.0.0.5:80/online.php?param1="+data+"&param2="+lat+"&param3="+lng;
        new HttpAsyncTask().execute(url1);
		  display.setText("");
		 Toast.makeText(getBaseContext(), "Received! "+data, Toast.LENGTH_LONG).show();
	
		 url1="http://10.0.0.5:80/retrive.php?param1="+data;
	        new HttpAsyncTask().execute(url1);
	
		
	}
	
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
		double[] rng;
	    boolean checked = ((RadioButton) view).isChecked();
	    rng=ContactLatLong.ranges;
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radioButton1:
	            if (checked)
	                for(int i=0;i<rng.length;i++){
	                	if(rng[i]<5){
	                		System.out.println("RANGE1 5kms "+rng[i]+"    "+ContactLatLong.latitude[i]);
	                	}
	                }
	            break;
	        case R.id.radioButton2:
	            if (checked)
	            	for(int i=0;i<rng.length;i++){
	                	if(rng[i]>5 && rng[i]<10){
	                		System.out.println("RANGE1 10kms "+rng[i]+"    "+ContactLatLong.latitude[i]);
	                	}
	                }
	            break;
	    }
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
	private String[] phone;
	private String[] lati;
	private String[] lngi;
	private int count;

	  
	  public void readContacts() { 
			StringBuffer sb = new StringBuffer();
			sb.append("......Contact Details....."); 
			ContentResolver cr = getContentResolver();
			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
			String phone = null; 
			String emailContact = null; 
			String emailType = null;
			String image_uri = "";
			Bitmap bitmap = null;
			if (cur.getCount() > 0) 
			{
				while (cur.moveToNext()) 
				{
					String id = cur.getString(cur .getColumnIndex(ContactsContract.Contacts._ID));
					String name = cur .getString(cur .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)); 
					image_uri = cur .getString(cur .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
					if (Integer .parseInt(cur.getString(cur .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
					{
						System.out.println("name : " + name + ", ID : " + id); 
						sb.append("\n Contact Name:" + name);
						Cursor pCur = cr.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null); while (pCur.moveToNext()) 
						{	
							phone = pCur .getString(pCur .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							t1.insert(phone);
							sb.append("\n Phone number:" + phone);
							System.out.println("phone" + phone); 
							}
						pCur.close();
									 }
					textDetail.setText(sb);
					if(t1.search("+919632773866"))
					Log.d("qwerty", "present tenzin");
									 }
				
			}
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
         private ContactLatLong obj;
		@Override
         protected String doInBackground(String... urls) {
  
             return GET(urls[0]);
         }
         // onPostExecute displays the results of the AsyncTask.
         @Override
         protected void onPostExecute(String result) {
             Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
             stringsplitter();
             obj=new ContactLatLong(phone,lati,lngi);
             obj.fndrange();
             display.setText(phone[0]);
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
     public void stringsplitter(){
    	 String[] results=result.split(":");
    	 
    	  count=results.length;
    	 System.out.println("count ******** "+count);
    	 phone=new String[count/3];
    	 lati=new String[count/3];
    	 lngi=new String[count/3];
    	 int a=0,b=0,c=0;
    	 for(int i=0;i<count;i++){
    		 if(i%3==0){
    			 phone[a]=results[i];
    			 a++;
    		 }else if(i%3==1){
    			 lati[b]=results[i];
    			 b++;
    		 }else{
    			 lngi[c]=results[i];
    			 c++;
    		 }
    	 }
     }


}
