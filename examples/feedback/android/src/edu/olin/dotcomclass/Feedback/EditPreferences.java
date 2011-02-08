package edu.olin.dotcomclass.Feedback;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by IntelliJ IDEA.
 * User: mchang
 * Date: 2/8/11
 * Time: 1:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class EditPreferences extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);
    }
}