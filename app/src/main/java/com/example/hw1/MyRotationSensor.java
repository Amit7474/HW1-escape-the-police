/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/12/19 9:24 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/12/19 9:24 AM
 *
 */

package com.example.hw1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

public class MyRotationSensor {

    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private SensorEventListener rotationEventListener;
    private int oneStepCondition;
    private CallBackInterface callBackInterface;

    public MyRotationSensor(Context context, final CallBackInterface _callBackInterface) {
        this.callBackInterface = _callBackInterface;
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (rotationSensor == null) {
            Toast.makeText(context, "The device has no Rotation Sensor", Toast.LENGTH_SHORT).show();
        } else {
            rotationEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float[] rotationMatrix = new float[16];
                    SensorManager.getRotationMatrixFromVector(
                            rotationMatrix, event.values);

                    // Remap coordinate system
                    float[] remappedRotationMatrix = new float[16];
                    SensorManager.remapCoordinateSystem(rotationMatrix,
                            SensorManager.AXIS_X,
                            SensorManager.AXIS_Z,
                            remappedRotationMatrix);

                    // Convert to orientations
                    float[] orientations = new float[3];
                    SensorManager.getOrientation(remappedRotationMatrix, orientations);
                    for (int i = 0; i < 3; i++) {
                        orientations[i] = (float) (Math.toDegrees(orientations[i]));
                    }
                    //If the device is rotated more then 30deg to the RIGHT: move the player's car 1 step RIGHT.
                    if (orientations[2] > 30 && oneStepCondition == 0) {
                        Log.i("Status", "Right");
                        callBackInterface.moveRight();
                        oneStepCondition = 1;


                        //If the device is rotated more then 30deg to the LEFT: move the player's car 1 step LEFT.
                    } else if (orientations[2] < -30 && oneStepCondition == 0) {
                        Log.i("Status", "Left");
                        callBackInterface.moveLeft();
                        oneStepCondition = 1;

                    } else if (Math.abs(orientations[2]) < 10) {
                        oneStepCondition = 0;
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };


        }
    }

    public void setSensor() {
        sensorManager.registerListener(rotationEventListener, rotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stopSensor() {
        sensorManager.registerListener(rotationEventListener, rotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.unregisterListener(rotationEventListener);
    }

}
