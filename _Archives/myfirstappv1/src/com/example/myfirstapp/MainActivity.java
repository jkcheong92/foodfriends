package com.example.myfirstapp;



// import android.R;
import java.util.ArrayList;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;
import com.google.android.gms.plus.model.people.PersonBuffer;

// Get Friends
public class MainActivity extends ActionBarActivity implements 
		ConnectionCallbacks,OnConnectionFailedListener,View.OnClickListener, ResultCallback<LoadPeopleResult>
{
	
	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean mSignInClicked;
	
	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */
	private ConnectionResult mConnectionResult;
	
	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;
	
	/* A flag indicating that a PendingIntent is in progress and prevents
	 * us from starting further intents.
	 */
	private boolean mIntentInProgress;	
	
	//EXTRA_MESSAGE definition 
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	private static final String TAG = null;

	public static final String EXTRA_PERSON_NAME = "com.example.myfirstapp.PERSON_NAME";
	public static final String EXTRA_PERSON_GOOGLE_PLUS_PROFILE = "com.example.myfirstapp.PERSON_GOOGLE_PLUS_PROFILE";
	public static final String EXTRA_PERSON_PHOTO = "com.example.myfirstapp.PERSON_PHOTO";
	
	// Get Friends
	// public static final String EXTRA_FRIENDS_NAME = "com.example.myfirstapp.FRIENDS_NAME";
	// public static final ArrayList<String> EXTRA_FRIENDS_NAME = "com.example.myfirstapp.FRIENDS_NAME";
	
	//private Person currentPerson;
    private String personName;
    private Image personPhoto;
    private String personGooglePlusProfile;
    
    // Get Friends
    // private String friendsName; 
    public static final ArrayList<String> friendsName = new ArrayList<String>();
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //setup the onclicklistener for button use and take note if the button is in the 
       // setcontentview referring layout from above 
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        
        //building the mobile google api client and calling its methods
        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)		// runs error if it is .addApi(Plus.API, null)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        // Set the google sign in button visibility to be gone if the user is already connected

        
       
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
            case R.id.action_friends:{
	            //need to create a friend button activity. Get Friends
            	Intent intent = new Intent(this, DisplayFriendsActivity.class);
		    	//intent.putStringArrayListExtra(EXTRA_FRIENDS_NAME, friendsName);
		    	intent.putStringArrayListExtra("EXTRA_FRIENDS_NAME", friendsName);
		    	startActivity(intent);	
	            return true;
            }
	        case R.id.action_my_profile:{
	            //need to create a my_profile button activity
	        	Intent intent = new Intent(this, DisplayProfileActivity.class);
		    	intent.putExtra(EXTRA_PERSON_NAME, personName);
		    	intent.putExtra(EXTRA_PERSON_GOOGLE_PLUS_PROFILE, personGooglePlusProfile);
		    	String url = personPhoto.getUrl();
		    	intent.putExtra(EXTRA_PERSON_PHOTO,url);
		    	startActivity(intent);
	            return true;
	        }
        
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    @Override
	protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
              }

      @Override
	protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
          mGoogleApiClient.disconnect();
        }
      }
      
	@Override
	public void onClick(View view) {
		//sign in  
		if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {
		    mSignInClicked = true;
		    resolveSignInError();
		    
		    findViewById(R.id.sign_in_button).setVisibility(View.GONE);
	        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
		  }
		
		  //sign out
		else if (view.getId() == R.id.sign_out_button) {
			    if (mGoogleApiClient.isConnected()) {
			      Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			      Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
			      mGoogleApiClient.disconnect();
			      mGoogleApiClient.connect();
			    }
			    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
		        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
			    // Get friends: Clear arrayList
		        friendsName.clear();
			  }
		}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		  if (!mIntentInProgress) {
		    // Store the ConnectionResult so that we can use it later when the user clicks
		    // 'sign-in'.
		    mConnectionResult = result;

		    if (mSignInClicked) {
		      // The user has already clicked 'sign-in' so we attempt to resolve all
		      // errors until the user is signed in, or they cancel.
		      resolveSignInError();
		    }
		  }
		}
	
	@Override
	// When the user has successfully signed in, your onConnected handler will be called.
	// At this point, you are able to retrieve the user’s account name or make authenticated requests.
	public void onConnected(Bundle connectionHint) {
	  mSignInClicked = false;
	  Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
      
	  //set the visibility of the sign-in button to be gone
	  findViewById(R.id.sign_in_button).setVisibility(View.GONE);
      
	  
	// Get Profile information 
	   if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
		    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
		    personName = currentPerson.getDisplayName();
		    personPhoto = currentPerson.getImage();
		    personGooglePlusProfile = currentPerson.getUrl();
		    
		    Log.d(personName, personName);
		    Log.d(personGooglePlusProfile, personGooglePlusProfile);   	
		  }
	// Get Friends
	   Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
	      .setResultCallback(this);

	}
	

	@Override
	public void onConnectionSuspended(int cause) {
		  mGoogleApiClient.connect();

		}
	// reset the state of the flags when control returns to your `Activity` in `onActivityResult`.
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		  if (requestCode == RC_SIGN_IN) {
		    if (responseCode != RESULT_OK) {
		      mSignInClicked = false;
		    }

		    mIntentInProgress = false;

		    if (!mGoogleApiClient.isConnecting()) {
		      mGoogleApiClient.connect();
		    }
		  }
		}
    
	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
	  if (mConnectionResult.hasResolution()) {
	    try {
	      mIntentInProgress = true;
	      mConnectionResult.startResolutionForResult(this, // your activity
                  RC_SIGN_IN);
	    } catch (SendIntentException e) {
	      // The intent was canceled before it was sent.  Return to the default
	      // state and attempt to connect to get an updated ConnectionResult.
	      mIntentInProgress = false;
	      mGoogleApiClient.connect();
	    }
	  }
	}
	
	// Get friends
	@Override
	public void onResult(LoadPeopleResult peopleData) {
		  if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS && friendsName.size()==0) {
		    PersonBuffer personBuffer = peopleData.getPersonBuffer();
		    try {
		      int count = personBuffer.getCount();
		      for (int i = 0; i < count; i++) {
		    	// For Activity later
		    	// friendsName = personBuffer.get(i).getDisplayName();
		    	friendsName.add(personBuffer.get(i).getDisplayName());
		        Log.d(TAG, "Display name: " + personBuffer.get(i).getDisplayName());
		      }
		    } finally {
		      personBuffer.close();
		    }
		  } else {
		    Log.e(TAG, "Error requesting visible circles: " + peopleData.getStatus());
		  }
	}
}
