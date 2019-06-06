package com.example.schatrijk_app.Systems;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.schatrijk_app.Data.Compass;
import com.example.schatrijk_app.TreasureHuntFragment;

public class CompassSystem implements SensorEventListener {
    Location phoneLocation = new Location("phone");

    private Compass compass;
    private int compassAngle;
    private int resultAngle;

    private Activity listenedActivity;
    private TreasureHuntFragment listendFragment;

    int mAzimuth;
    private SensorManager mSensorManager;
    private Sensor mRotationV, mAccelerometer, mMagnetometer;
    boolean haveSensor = false, haveSensor2 = false;
    float[] rMat = new float[9];
    float[] orientation = new float[3];
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;

    public CompassSystem(Compass compass, SensorManager sensorManager, TreasureHuntFragment listenedFragment) {
        this.compass = compass;
        this.listendFragment = listenedFragment;
        this.listenedActivity = listendFragment.getActivity();
        mSensorManager = sensorManager;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rMat, event.values);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(rMat, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(rMat, orientation);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;
        }

        try {
            phoneLocation = compass.getLocation();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }

        if (phoneLocation != null) {
            compassAngle = (int) this.compass.angleBetweenLocations(phoneLocation);
            double lat = phoneLocation.getLatitude();
            double lon = phoneLocation.getLongitude();
            System.out.println("Lat: " + lat + "Lon: " + lon);
            Toast.makeText(listenedActivity, "Lat: " + lat + "Lon: " + lon, Toast.LENGTH_SHORT).show();
            int distance = (int) this.compass.distanceBetweenLocations(phoneLocation);
        }

        mAzimuth = Math.round(mAzimuth);

        resultAngle = compassAngle - mAzimuth;
        //arrowImg.setRotation(-mAzimuth);

        this.listendFragment.onSensorChanged(resultAngle, mAzimuth, compass.distanceBetweenLocations(phoneLocation));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void start() {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
            if ((mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) || (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null)) {
                noSensorsAlert();
            } else {
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                haveSensor = mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
                haveSensor2 = mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
            }
        } else {
            mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            haveSensor = mSensorManager.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void noSensorsAlert() {
        final Activity listendActivity = this.listenedActivity;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.listenedActivity);
        alertDialog.setMessage("Jouw apparaat ondersteund dit kompas niet!")
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listendActivity.finish();
                    }
                });
        alertDialog.show();
    }

    public void stop() {
        if (haveSensor) {
            mSensorManager.unregisterListener(this, mRotationV);
        } else {
            mSensorManager.unregisterListener(this, mAccelerometer);
            mSensorManager.unregisterListener(this, mMagnetometer);
        }
    }

    public Location getPhoneLocation() {
        return phoneLocation;
    }

    //    @Override
//    protected void onPause() {
//        super.onPause();
//        stop();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        start();
//    }
}
