package cl.cutiko.geodata;

import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import cl.cutiko.geodata.data.Nodes;
import cl.cutiko.geodata.models.GeoPod;

/**
 * Created by cutiko on 17-08-17.
 */

public class PopulatePods implements ChildEventListener {

    private final static double RADIUS = 0.02 ;
    private PodsCallback callback;
    private Query reference;
    private double currentLongitude;

    public PopulatePods(PodsCallback callback) {
        this.callback = callback;
    }

    public void getNearPods(TelephonyManager telephonyManager) {
        //TODO get the latitude and longitude
        double currentLat = -33.429072;
        currentLongitude = -70.603748;
        reference = new Nodes().nearLatitudes(telephonyManager.getNetworkCountryIso(), currentLat, RADIUS);
        reference.addChildEventListener(this);
    }

    public void clean() {
        reference.removeEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        GeoPod geoPod = dataSnapshot.getValue(GeoPod.class);
        double longitude = geoPod.getLongitude();
        double minLongitude = currentLongitude - RADIUS;
        double maxLongitude = currentLongitude + RADIUS;

        if (longitude <= maxLongitude && longitude >= minLongitude) {
            Log.d("POD_ACCEPTED", geoPod.getName());
            callback.addPod(geoPod);
        } else {
            Log.d("POD_REJECTED", geoPod.getName());
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

}
