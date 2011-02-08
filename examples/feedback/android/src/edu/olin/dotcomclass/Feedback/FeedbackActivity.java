package edu.olin.dotcomclass.Feedback;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedbackActivity extends ListActivity
{
    public static final String TAG = "FEEDBACK";
    public static final String ID_EXTRA = "edu.olin.dotcomclass.notedescription";
    private HttpClient client;
    ArrayAdapter<String> noteAdapter;
    ArrayList<String> noteArray;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        client = new DefaultHttpClient();

        ListView noteListView = getListView();

        noteArray = new ArrayList<String>();
        noteAdapter = new ArrayAdapter<String>(this, R.layout.note_item, noteArray);
        setListAdapter(noteAdapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Log.i(TAG, "Selected note: " + position);

                Intent i = new Intent(getApplicationContext(), FeedbackViewActivity.class);
                i.putExtra(ID_EXTRA, ((TextView)view).getText().toString() );
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
            case R.id.refresh:
                doRefresh();
                return true;
            case R.id.preferences:
                startActivity(new Intent(this, EditPreferences.class));
                return true;
            case R.id.post:
                startActivity(new Intent(this, NewPostActivity.class ));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void doRefresh() {
        String ipAddress;

        Log.i(TAG, "Refresh");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String prefsIpAddress = prefs.getString("ip_address", "<unset>");

        Log.i(TAG, "Preferences IP Address: " + prefsIpAddress);

        if( prefsIpAddress.equals("<unset>") ) {
            ipAddress = "dotcom-feedback.heroku.com";
        } else {
            ipAddress = prefsIpAddress;
        }
        String url = "http://" + ipAddress + "/notes.json";
        HttpGet httpGet = new HttpGet(url);

        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = client.execute(httpGet, responseHandler);
            Log.i(TAG, responseBody);
            parseResponse(responseBody);
        } catch( Throwable t ) {
            Log.e(TAG, "doRefresh exception",t );
            Toast.makeText(this, "Refresh failed " + t.toString(), Toast.LENGTH_LONG);
        }
    }

    public void parseResponse(String response) {
        try {
            Log.i(TAG, "Parsing JSON");
            noteArray.clear();
            JSONArray jsonArray = new JSONArray(response);
            Log.i(TAG, "Parsed " + jsonArray.length() + " entries");
            for( int i=0; i<jsonArray.length(); i++ ) {
                JSONObject noteRecord = jsonArray.getJSONObject(i);
                JSONObject note = noteRecord.getJSONObject("note");
                String noteDescription = note.getString("description");
                Log.i(TAG, noteDescription );
                noteArray.add(noteDescription);
            }

            noteAdapter.notifyDataSetChanged();
        } catch( JSONException j ) {
            Log.e(TAG, "JSON Exception", j );
            Toast.makeText(this, "JSON Parsing error " + j.toString(), Toast.LENGTH_LONG);
        }
    }
}
