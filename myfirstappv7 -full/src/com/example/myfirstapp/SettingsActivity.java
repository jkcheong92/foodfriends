package com.example.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusOneButton;

public class SettingsActivity extends ActionBarActivity implements 
ConnectionCallbacks,OnConnectionFailedListener{
	
	private GoogleApiClient mGoogleApiClient;
	
	// JK: +1 button
	private PlusOneButton mPlusOneButton;
	private static final int PLUS_ONE_REQUEST_CODE = 0;
	
	//!! JK: Change URL to our app package name
	private String url = "com.whatsapp&hl=en";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setTitle("Settings");
		
		//building the mobile google api client and calling its methods
        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();
        mGoogleApiClient.connect();
        
        // JK: +1 button
        mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	// JK: +1 button
	@Override
	protected void onResume() {
	    super.onResume();
	    mPlusOneButton.initialize("http://foodfriends92.wix.com/index", PLUS_ONE_REQUEST_CODE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_settings,
					container, false);
			return rootView;
		}
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {		
		
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void review(View view){
		Intent i = new Intent(Intent.ACTION_VIEW);
		// Error catching: If Google Play app is not installed, open with browser instead
		try{		
			// i.setData(Uri.parse("market://details?id=" + url));
			i.setData(Uri.parse("http://foodfriends92.wix.com/index"));
			startActivity(i);
		}
		catch(android.content.ActivityNotFoundException anfe) { 
			i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + url));
			startActivity(i);
		}
	}

}
