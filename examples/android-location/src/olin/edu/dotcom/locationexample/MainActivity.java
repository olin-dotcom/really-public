package olin.edu.dotcom.locationexample;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "LOCATION_EXAMPLE";
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    LocationManager locationManager;
    LocationListener gpsLocationListener;
    LocationListener networkLocationListener;
    Location lastLocation;

    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v(TAG, "onResume");

        startLocationManager();

        // get last known to start the updates
        Location lastNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location lastGPSLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        // show the cached locations
        if( lastNetworkLocation != null ) {
            Log.v(TAG, "Got cached network location");
            if( isBetterLocation(lastNetworkLocation, null) ) {
                displayLocation(lastNetworkLocation);
            }
        }

        if( lastGPSLocation != null ) {
            Log.v(TAG, "Got cached GPS location");
            if(isBetterLocation(lastGPSLocation,lastNetworkLocation)) {
                displayLocation(lastGPSLocation);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.v(TAG, "onPause");
        locationManager.removeUpdates(gpsLocationListener);
        locationManager.removeUpdates(networkLocationListener);

        locationManager = null;
        gpsLocationListener = null;
        networkLocationListener = null;
    }

    private void displayLocation(Location location) {
        if( isBetterLocation(location, lastLocation)) {
            Log.v(TAG, "New better location");

            TextView textView = (TextView) findViewById(R.id.locationText);

            StringBuilder text = new StringBuilder();
            text.append("Lat: ");
            text.append(location.getLatitude());
            text.append("\nLong: ");
            text.append(location.getLongitude());
            text.append("\nAccuracy: ");
            text.append(location.getAccuracy());
            text.append("\nProvider: ");
            text.append(location.getProvider());

            textView.setText(text.toString());

            lastLocation = location;
        } else {
            Log.v(TAG, "Rejected worse location");
        }

    }


    private void startLocationManager() {
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        gpsLocationListener = new MyLocationListener();
        networkLocationListener = new MyLocationListener();

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkLocationListener);

    }

    /**
     * Determines whether one Location reading is better than the current Location fix
     *
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }


    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            displayLocation(location);
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String s) {
        }

        public void onProviderDisabled(String s) {
        }
    }
}
