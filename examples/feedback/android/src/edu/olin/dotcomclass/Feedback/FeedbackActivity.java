package edu.olin.dotcomclass.Feedback;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

public class FeedbackActivity extends ListActivity
{
    public static final String TAG = "FEEDBACK";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
