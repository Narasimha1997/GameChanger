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
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import developers.yogaimpression.gamechanger.R;

public class GameChanger_Auto_Service extends Service {
    TelephonyManager Auto_AnswerModule;
    SensorManager SensorModule, SensorModule2;
    Sensor PROXIMITY, LIGHT;
    NotificationManager NotificationBuild; int NotifyID=3;
    Default_Functions GameChanger_Std_functions=new Default_Functions();
    String telephone;
    public GameChanger_Auto_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        try {
            Auto_AnswerModule = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            Notification.Builder mNotification=new Notification.Builder(this);
            mNotification.setSmallIcon(R.drawable.notif);
            mNotification.setContentTitle("GameChanger");
            mNotification.setContentText("Auto call answer service running");
            NotificationBuild=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationBuild.notify(NotifyID,mNotification.build());
            SensorModule=(SensorManager)getSystemService(SENSOR_SERVICE);
            PROXIMITY=SensorModule.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            SensorModule2=(SensorManager)getSystemService(SENSOR_SERVICE);
            LIGHT=SensorModule2.getDefaultSensor(Sensor.TYPE_LIGHT);
            Auto_AnswerModule.listen(detectCallState,PhoneStateListener.LISTEN_CALL_STATE);
            Toast.makeText(GameChanger_Auto_Service.this, "Service started in background", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(GameChanger_Auto_Service.this, "Unable to initialise the service", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy()
    {
        NotificationBuild.cancel(NotifyID);
        Toast.makeText(GameChanger_Auto_Service.this, "Service stopped", Toast.LENGTH_SHORT).show();
    }
    PhoneStateListener detectCallState=new PhoneStateListener()
    {
        @Override
        public void onCallStateChanged(int state, String incomingNumber)
        {
            switch (state)
            {
                case TelephonyManager.CALL_STATE_RINGING :
                    SensorModule2.registerListener(getOpticalDensity,LIGHT,SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
                    File file=new File(getFilesDir(),"CALL_LOG.txt");
                    int n=GameChanger_Std_functions.CUSTOM_CALL_LOGGER(file,incomingNumber);
                    telephone=incomingNumber;
                    if(n!=0)
                        Toast.makeText(GameChanger_Auto_Service.this, "Call logged", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(GameChanger_Auto_Service.this, "Unable to log the call", Toast.LENGTH_SHORT).show();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    SensorModule2.unregisterListener(getOpticalDensity);
                    SensorModule.unregisterListener(getProximityValue);
            }
        }
    };
    SensorEventListener getProximityValue=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float ProximityValue=event.values[0];
            if(ProximityValue<=3)
            {
                Auto_Answer(telephone);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    SensorEventListener getOpticalDensity=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.values[0]>=1.0) {
                SensorModule.registerListener(getProximityValue, PROXIMITY, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    public void Auto_Answer(String telephoneNo) {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService = m.invoke(tm);
            if(telephoneNo!=null)
            {
                Class c2 = Class.forName(telephonyService.getClass().getName());
                Method m2 = c2.getDeclaredMethod("answerRingingCall");
                m2.setAccessible(true);
                m2.invoke(telephonyService);
            }
        } catch (ClassNotFoundException e) {
            Toast.makeText(GameChanger_Auto_Service.this, "Cannot connect to com.android.internal.telephony", Toast.LENGTH_SHORT).show();
        } catch (NoSuchMethodException e) {
            Toast.makeText(GameChanger_Auto_Service.this, "No call method found", Toast.LENGTH_SHORT).show();
        } catch (InvocationTargetException e) {
            Toast.makeText(GameChanger_Auto_Service.this, "Cannot invoke the target", Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) {
            Toast.makeText(GameChanger_Auto_Service.this, "System Generating Illegal Access Error", Toast.LENGTH_SHORT).show();
        }
    }

}
