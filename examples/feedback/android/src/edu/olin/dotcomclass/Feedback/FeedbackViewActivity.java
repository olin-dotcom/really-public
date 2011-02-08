package edu.olin.dotcomclass.Feedback;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class FeedbackViewActivity extends Activity {

    TextView noteText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_view);
        noteText = (TextView) findViewById(R.id.notetext);
        noteText.setText(getIntent().getStringExtra(FeedbackActivity.ID_EXTRA));
    }
}