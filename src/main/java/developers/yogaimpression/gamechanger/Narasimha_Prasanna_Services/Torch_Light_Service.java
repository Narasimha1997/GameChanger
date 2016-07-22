package developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import developers.yogaimpression.gamechanger.R;

public class Torch_Light_Service extends Service {
    SensorManager sensorPack1;
    Sensor Light1;
    TelephonyManager tm;
    CameraManager cameraManager;
    File CALL_LOG;
    NotificationManager notifier;
    int NotifyID=3;
    Default_Functions GAME_CHANGER_FUNCTIONS=new Default_Functions();
    boolean GLOBAL_FLASH_CONDITION=false;
    public Torch_Light_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        try {
            long[] vibrate={100,300,400};
            tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            sensorPack1=(SensorManager)getSystemService(SENSOR_SERVICE);
            Light1=sensorPack1.getDefaultSensor(Sensor.TYPE_LIGHT);
            CALL_LOG=new File(getFilesDir(),"CALL_LOG.txt");
            tm.listen(listenState,PhoneStateListener.LISTEN_CALL_STATE);
            Notification.Builder build=new Notification.Builder(this);
            build.setContentTitle("GameChanger");
            build.setContentText("GameChanger now activates torch on Incoming calls");
            build.setSmallIcon(R.drawable.notif);
            build.setVibrate(vibrate);
            notifier=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notifier.notify(NotifyID,build.build());
            Toast.makeText(Torch_Light_Service.this, "Service started", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Toast.makeText(Torch_Light_Service.this, "Initialisation failed.", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onDestroy()
    {
        notifier.cancel(NotifyID);
    }
    PhoneStateListener listenState=new PhoneStateListener()
    {
      @Override
      public void onCallStateChanged(int State, String incoming)
      {
          switch (State)
          {
              case TelephonyManager.CALL_STATE_RINGING:
                  try {
                      sensorPack1.registerListener(getOpticalDensity, Light1, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
                      int logger = GAME_CHANGER_FUNCTIONS.CUSTOM_CALL_LOGGER(CALL_LOG, incoming);
                      if (logger != 0)
                          Toast.makeText(Torch_Light_Service.this, "Call logged", Toast.LENGTH_SHORT).show();
                      else
                          Toast.makeText(Torch_Light_Service.this, "Unable to log the call", Toast.LENGTH_SHORT).show();
                  }catch (Exception e)
                  {
                      Toast.makeText(Torch_Light_Service.this, "Unable to connect to Sensor Manager", Toast.LENGTH_SHORT).show();
                  }
                  break;
              case TelephonyManager.CALL_STATE_IDLE:
                  FLASH_OFF();
                  break;

          }
      }
    };
    SensorEventListener getOpticalDensity=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.values[0]<=2.0)
            {
                try {
                    cameraManager=(CameraManager)getSystemService(CAMERA_SERVICE);
                    String[] m = cameraManager.getCameraIdList();
                    GLOBAL_FLASH_CONDITION=true;
                    cameraManager.setTorchMode(m[0],GLOBAL_FLASH_CONDITION);
                    sensorPack1.unregisterListener(getOpticalDensity);
                } catch (Exception e) {
                    Toast.makeText(Torch_Light_Service.this, "Illegal access to camera.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    public void FLASH_OFF()
    {
        if(GLOBAL_FLASH_CONDITION)
        {
            cameraManager=(CameraManager)getSystemService(CAMERA_SERVICE);
            try {
                String[] ID=cameraManager.getCameraIdList();
                GLOBAL_FLASH_CONDITION=false;
                cameraManager.setTorchMode(ID[0],GLOBAL_FLASH_CONDITION);
            } catch (CameraAccessException e) {
                Toast.makeText(Torch_Light_Service.this, "Unable to turn off flash", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
