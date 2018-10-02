package ru.obrubov.laba4;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView accX;
    TextView accY;
    TextView accZ;
    TextView magnetX;
    TextView magnetY;
    TextView magnetZ;
    TextView proximity;
    TextView light;
    TextView Zrotation;
    TextView side;

    ImageView imageView;

    SensorManager sensorManager;
    Sensor accSensor;
    Sensor magnetSensor;
    Sensor proximitySensor;
    Sensor lightSensor;

    float[] accValues;
    float[] magnetValues;
    float[] rotationMatrix;
    float[] orientationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accX = findViewById(R.id.accXcoord);
        accY = findViewById(R.id.accYcoord);
        accZ = findViewById(R.id.accZcoord);
        magnetX = findViewById(R.id.magnetXcoord);
        magnetY = findViewById(R.id.magnetYcoord);
        magnetZ = findViewById(R.id.magnetZcoord);
        proximity = findViewById(R.id.proximitySensorData);
        light = findViewById(R.id.lightSensorData);
        Zrotation = findViewById(R.id.ZrotationValue);
        side = findViewById(R.id.sideText);
        imageView = findViewById(R.id.imageView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager != null) {
            accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        accValues = new float[3];
        magnetValues = new float[3];
        rotationMatrix = new float[16];
        orientationData = new float[3];
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accX.setText(String.valueOf(event.values[0]));
            accY.setText(String.valueOf(event.values[1]));
            accZ.setText(String.valueOf(event.values[2]));
            accValues = event.values.clone();
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnetX.setText(String.valueOf(event.values[0]));
            magnetY.setText(String.valueOf(event.values[1]));
            magnetZ.setText(String.valueOf(event.values[2]));
            magnetValues = event.values.clone();
        }
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximity.setText(String.valueOf(event.values[0]));
        }
        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
            light.setText(String.valueOf(event.values[0]));
        }
        SensorManager.getRotationMatrix(rotationMatrix, null, accValues, magnetValues);
        SensorManager.getOrientation(rotationMatrix, orientationData);
        double zDegrees = Math.toDegrees(orientationData[0]);
        Zrotation.setText(String.valueOf(zDegrees));

        boolean isNorth = zDegrees > -45 && zDegrees < 45;
        boolean isEast = zDegrees > 45 && zDegrees < 135;
        boolean isSouth = (zDegrees >= -180 && zDegrees < -135) || (zDegrees > 135 && zDegrees <= 180);
        boolean isWest = zDegrees > -135 && zDegrees < -45;

        if(isNorth) {
            side.setText("Север");
        } else if (isEast) {
            side.setText("Восток");
        } else if (isSouth) {
            side.setText("Юг");
        } else if (isWest) {
            side.setText("Запад");
        }
        imageView.animate().x(orientationData[0]).y(orientationData[1]).start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, accSensor);
        sensorManager.unregisterListener(this, magnetSensor);
        sensorManager.unregisterListener(this, proximitySensor);
        sensorManager.unregisterListener(this, lightSensor);
    }
}
