package com.example.myfirstapp;


import java.io.InputStream;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.Moments.LoadMomentsResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.moments.MomentBuffer;


public class DisplayProfileActivity extends ActionBarActivity 
implements ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<LoadMomentsResult>
{
	// JK: Fetch moments
	private GoogleApiClient mGoogleApiClient;
	private ArrayList<String> myFoodList = new ArrayList<String>();
	private String personID;
	
	private static final int PROFILE_PIC_SIZE = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_profile);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// JK: Fetch moments
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)	
        .addScope(Plus.SCOPE_PLUS_LOGIN)
		.build();
		mGoogleApiClient.connect();
		
		Intent intent = getIntent();
		getProfileDetailsFromIntent(intent);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.action_buttons, menu); // loads the action buttons @action_buttons.xml
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Handle presses on the action bar items
        switch (id) {
            case R.id.action_friends:
	            //need to create a friend button activity
            	
	            return true;
	        case R.id.action_my_profile:
	            //need to create a my_profile button activity
	        	//Intent intent = new Intent(this, Profile.class);
		    	//intent.putExtra(EXTRA_PERSON_NAME, personName);
		    	//intent.putExtra(EXTRA_PERSON_GOOGLE_PLUS_PROFILE, personGooglePlusProfile);
		    	//startActivity(intent);
	            return true;
        
            case R.id.action_chat:
                //need to create a chat button activity
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
			View rootView = inflater.inflate(R.layout.fragment_display_profile,
					container, false);
			return rootView;
		}
	}
	
	private void getProfileDetailsFromIntent(Intent intent){
		String personName = intent.getStringExtra(MainActivity.EXTRA_PERSON_NAME);
		String personGooglePlusProfile = intent.getStringExtra(MainActivity.EXTRA_PERSON_GOOGLE_PLUS_PROFILE);
		String personPhotoUrl = intent.getStringExtra(MainActivity.EXTRA_PERSON_PHOTO);
		ImageView profilePic = (ImageView) findViewById(R.id.profile_pic);
		
		// JK: Fetch moments
		personID = personGooglePlusProfile.replaceAll("\\D+","");
		Log.d("ID", personID);
			
		TextView username = (TextView) findViewById(R.id.personName);
		username.setText(personName);
		
		TextView googlePlusProfile = (TextView) findViewById(R.id.personGooglePlusProfile);
		googlePlusProfile.setText(personGooglePlusProfile);
		
		// by default the profile url gives 50x50 px image only
        // we can replace the value with whatever dimension we want by
        // replacing sz=X
        personPhotoUrl = personPhotoUrl.substring(0,personPhotoUrl.length() - 2)
        		+ PROFILE_PIC_SIZE;
      
        new LoadProfileImage(profilePic).execute(personPhotoUrl);
	}
	
	
	/**
	 * Background Async task to load user profile picture from url
	 * */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;
	 
	    public LoadProfileImage(ImageView bmImage) {
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
	        bmImage.setImageBitmap(result);
	    }
	}
	
	// JK: Create Moments
    /** Called when the user clicks the Add button */
    public void addFood(View view) {
    	Intent intent = new Intent(this, MainActivity.class);
    	EditText editText = (EditText) findViewById(R.id.food);
    	String food = editText.getText().toString();
    	intent.putExtra("EXTRA_FOOD", food);
    	startActivity(intent);
    }
    
	// JK: Fetch moments
	@Override
	public void onResult(LoadMomentsResult result) {
		myFoodList.clear();
		  if (result.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
		    MomentBuffer momentBuffer = result.getMomentBuffer();
		    try {
		    	int count = momentBuffer.getCount();
		    	for(int i=0; i<count; i++) {
		    		String food = momentBuffer.get(i).getTarget().getName();
		    		// Log.d("momentName", "Moment Name: " + food);
		    		myFoodList.add(food);
		    	}
		    } finally {
		      momentBuffer.close();
		    }
		  } else {
		    Log.e("error", "Error when loading moments: " + result.getStatus().getStatusCode());
		  }
		  // display moments
		  	ListView foodlist = (ListView) findViewById(R.id.myfoodlist);
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFoodList);
			foodlist.setAdapter(arrayAdapter);
		}
	
	@Override
	public void onConnected(Bundle connectionHint) {
		Plus.MomentsApi.load(mGoogleApiClient).setResultCallback(this);
		//Plus.MomentsApi.load(mGoogleApiClient, 20, "next", null, "http://schemas.google.com/AddActivity", "116272059231340270090").setResultCallback(this);
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
