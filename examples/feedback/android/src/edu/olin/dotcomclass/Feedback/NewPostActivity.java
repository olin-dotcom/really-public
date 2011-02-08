package edu.olin.dotcomclass.Feedback;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mchang
 * Date: 2/8/11
 * Time: 2:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewPostActivity extends Activity implements View.OnClickListener {
    Button postButton;
    EditText postText;
    private static final String TAG = "FEED_POST";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_view);

        postButton = (Button) findViewById(R.id.postbutton);
        postButton.setOnClickListener(this);

        postText = (EditText) findViewById(R.id.posttext);
    }

    public void onClick(View view) {
        HttpClient client = new DefaultHttpClient();
        String ipAddress;
        String postString = postText.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String prefsIpAddress = prefs.getString("ip_address", "<unset>");

        if( prefsIpAddress.equals("<unset>") ) {
            ipAddress = "10.41.88.162";
        } else {
            ipAddress = prefsIpAddress;
        }
        String url = "http://" + ipAddress + ":3000/notes";
        HttpPost httpPost = new HttpPost(url);

        Log.i(TAG, "Posting " + postString + " to " + url);
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("note[description]", postString));
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse httpResponse = client.execute(httpPost);

            Log.i(TAG, httpResponse.getStatusLine().toString());
        } catch( Throwable t ) {
            Log.e(TAG, "Post exception",t );
            Toast.makeText(this, "Post failed " + t.toString(), Toast.LENGTH_LONG);
        }

        finish();
    }
}