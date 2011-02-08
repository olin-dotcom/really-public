package edu.olin.dotcomclass.Feedback;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FeedbackActivity extends ListActivity
{
    public static final String TAG = "FEEDBACK";
    private HttpClient client;
    EditText IpEditText;
    ArrayAdapter<String> noteAdapter;
    ArrayList<String> noteArray;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        IpEditText = (EditText) findViewById(R.id.IpEditText);
        client = new DefaultHttpClient();
        noteArray = new ArrayList<String>();
        noteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteArray);
        setListAdapter(noteAdapter);

    }

    public void doRefresh(View theButton) {
        Log.i(TAG, "Refresh");
        String urlText = IpEditText.getText().toString();
        String url = "http://" + urlText + ":3000/notes.json";
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
