package developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import developers.yogaimpression.gamechanger.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GameChanger_InstantCall_SERVICE extends Service {
    SensorManager STD_PROXIMITY_SENSOR, STD_OPTICAL_SENSOR;
    Sensor PROXIMITY, OPTICAL;
    Default_Functions GameChanger_StdFunctions = new Default_Functions();
    float DEFAULT_LIGHT_DENSITY=0f;
    NotificationManager Std_Notifier; int notifyID =1;

    public GameChanger_InstantCall_SERVICE() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        try {
            long[] array={100,200,300};
            STD_PROXIMITY_SENSOR = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            PROXIMITY = STD_PROXIMITY_SENSOR.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            STD_OPTICAL_SENSOR=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
            OPTICAL=STD_OPTICAL_SENSOR.getDefaultSensor(Sensor.TYPE_LIGHT);
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            telephonyService.listen(detectCallChange, PhoneStateListener.LISTEN_CALL_STATE);
            Notification.Builder mNotifier=new Notification.Builder(this);
            Std_Notifier=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mNotifier.setSmallIcon(R.drawable.notif);
            mNotifier.setContentTitle("GameChanger");
            mNotifier.setAutoCancel(false);
            mNotifier.setVibrate(array);
            mNotifier.setContentText("GameChanger is currently working on Instant_Caller, you can cancel the service by tapping on \"Kill\" button in the app.");
            Std_Notifier.notify(notifyID, mNotifier.build());
        } catch (Exception e) {
            Toast.makeText(GameChanger_InstantCall_SERVICE.this, "Unable to initialise the service", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        Toast.makeText(GameChanger_InstantCall_SERVICE.this, "Service stopped", Toast.LENGTH_SHORT).show();
        Std_Notifier.cancel(notifyID);

    }

    PhoneStateListener detectCallChange = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    STD_PROXIMITY_SENSOR.registerListener(getProximityChange, PROXIMITY, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
                    STD_OPTICAL_SENSOR.registerListener(getOpticalData,OPTICAL,SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
                    break;
                case (TelephonyManager.CALL_STATE_RINGING):
                    STD_PROXIMITY_SENSOR.unregisterListener(getProximityChange);
                    Toast.makeText(GameChanger_InstantCall_SERVICE.this, "Call service stopped", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    SensorEventListener getProximityChange = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float ProximityValue = event.values[0];
            StringBuffer sb = new StringBuffer();
            try {
                    BufferedReader b = new BufferedReader(new InputStreamReader(openFileInput("PhoneNo.txt")));
                    int reader = GameChanger_StdFunctions.STD_READER(b, sb);
                if(DEFAULT_LIGHT_DENSITY>10) {
                    if (reader != 0) {
                        if ((ProximityValue <= 3) && (ProximityValue != 1)) {
                            String PhoneNumber = sb.toString();
                            Toast.makeText(GameChanger_InstantCall_SERVICE.this, PhoneNumber, Toast.LENGTH_SHORT).show();
                            try {
                                Intent STD_CALLER = new Intent(Intent.ACTION_CALL);
                                STD_CALLER.setData(Uri.parse("tel:" + PhoneNumber));
                                STD_CALLER.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                int Permission = Context.CONTEXT_IGNORE_SECURITY;
                                STD_CALLER.addFlags(Permission);
                                startActivity(STD_CALLER);
                            } catch (SecurityException e) {
                                Intent STD_DIALER = new Intent(Intent.ACTION_DIAL);
                                STD_DIALER.setData(Uri.parse("tel: " + PhoneNumber));
                                startActivity(STD_DIALER);
                            }
                        }
                    } else
                        Toast.makeText(GameChanger_InstantCall_SERVICE.this, "Cannot retrieve phone number", Toast.LENGTH_SHORT).show();
                }
                } catch (Exception e) {
                    Toast.makeText(GameChanger_InstantCall_SERVICE.this, "Unable to place a call", Toast.LENGTH_SHORT).show();
                }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    SensorEventListener getOpticalData=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            DEFAULT_LIGHT_DENSITY=event.values[0];
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


}
