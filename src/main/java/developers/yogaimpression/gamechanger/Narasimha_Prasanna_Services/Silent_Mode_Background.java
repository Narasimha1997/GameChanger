package developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.Toast;

import developers.yogaimpression.gamechanger.R;

public class Silent_Mode_Background extends Service {
    SensorManager Module; Sensor Light;
    AudioManager AUDIO_STATE;
    NotificationManager Notification; int ID;
    public Silent_Mode_Background() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        try{
            Notification.Builder displayNotification=new Notification.Builder(this);
            displayNotification.setContentTitle("GameChanger");
            displayNotification.setContentText("Automatic Silent mode enabled");
            long[] array={100,200,300};
            ID=3;
            displayNotification.setSmallIcon(R.drawable.notif);
            displayNotification.setVibrate(array);
            Notification=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            Notification.notify(ID,displayNotification.build());
            Module=(SensorManager)getSystemService(SENSOR_SERVICE);
            Light=Module.getDefaultSensor(Sensor.TYPE_LIGHT);
            AUDIO_STATE=(AudioManager)getSystemService(AUDIO_SERVICE);
            Module.registerListener(listener,Light,SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
            Toast.makeText(Silent_Mode_Background.this, "Service started in background", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(Silent_Mode_Background.this, "Failed to build the service", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy()
    {
        Notification.cancel(ID);
        if((AUDIO_STATE.getRingerMode())!=AudioManager.RINGER_MODE_NORMAL){
        AUDIO_STATE.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        Module.unregisterListener(listener);
        Toast.makeText(Silent_Mode_Background.this, "Service stopped", Toast.LENGTH_SHORT).show();
    }
    SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.values[0]<=1.0f){
                if((AUDIO_STATE.getRingerMode()!=AudioManager.RINGER_MODE_SILENT)){
                    AUDIO_STATE.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    Toast.makeText(Silent_Mode_Background.this, "Silent mode ON", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(Silent_Mode_Background.this, "Ringer already in Silent Mode", Toast.LENGTH_SHORT).show();
            }else {
                if((AUDIO_STATE.getRingerMode())!=AudioManager.RINGER_MODE_NORMAL){
                    AUDIO_STATE.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
