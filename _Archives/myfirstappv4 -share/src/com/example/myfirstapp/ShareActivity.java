package com.example.myfirstapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;

public class ShareActivity extends ActionBarActivity implements 
ConnectionCallbacks,OnConnectionFailedListener,View.OnClickListener  
{
	
	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;
	
	// JK: Share
	private Button mShareButton;
	
	// JK: Media
	private static final int REQ_SELECT_PHOTO = 1;
	private static final int REQ_START_SHARE = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
	    
        //building the mobile google api client and calling its methods
        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();
        mGoogleApiClient.connect();
        Log.d("connected","connected");
        
	    if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share, menu);
		return true;
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
			View rootView = inflater.inflate(R.layout.fragment_share,
					container, false);
			return rootView;
		}
	}
	
	@Override
	public void onConnected(Bundle connectionHint) {		
		// JK: Share
	    mShareButton = (Button) findViewById(R.id.share_button);
	    mShareButton.setOnClickListener(this);
	    Log.d("share","share");
	    		
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	// @Override
	public void onClick(View view) {
	  switch (view.getId()) {
	    case R.id.share_button:
/*	      PlusShare.Builder builder = new PlusShare.Builder(this);

	      // Set call-to-action metadata.
	      builder.addCallToAction(
	          "RECOMMEND", // call-to-action button label
	          Uri.parse("http://plus.google.com"), // call-to-action url (for desktop use) 
	           "/" // call to action deep-link ID (for mobile use), 512 characters or fewer
	          );

	      // Set the content url (for desktop use).
	      builder.setContentUrl(Uri.parse("http://plus.google.com"));

	      // Set the target deep-link ID (for mobile use).
	      builder.setContentDeepLinkId("/",null, null, null);

	      // Set the share text.
	      builder.setText("Check this out!");

	      startActivityForResult(builder.getIntent(), 0);
*/	    
	      Intent photoPicker = new Intent(Intent.ACTION_PICK);
	      photoPicker.setType("image/*");
	      startActivityForResult(photoPicker, REQ_SELECT_PHOTO);

	      break;
	  }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	  super.onActivityResult(requestCode, resultCode, intent);

	  if(requestCode == REQ_SELECT_PHOTO) {
	    if(resultCode == RESULT_OK) {
	      Uri selectedImage = intent.getData();
	      ContentResolver cr = this.getContentResolver();
	      String mime = cr.getType(selectedImage);

	      PlusShare.Builder share = new PlusShare.Builder(this);
	      share.setText("Sent from FoodFriends app!");
	      share.addStream(selectedImage);
	      share.setType(mime);
	      startActivityForResult(share.getIntent(), REQ_START_SHARE);
	    }
	  }
	}



}
