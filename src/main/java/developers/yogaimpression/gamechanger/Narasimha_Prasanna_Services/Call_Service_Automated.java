package developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import developers.yogaimpression.gamechanger.R;


public class Call_Service_Automated extends Service {
    Default_Functions GameChanger_CustomFunctions=new Default_Functions();
    TelephonyManager MANAGE_INCOMING_CALLS; String GLOBAL_NUMBER;
    NotificationManager mNotification; int NotificationID=2;
    public Call_Service_Automated() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }
    @Override
    public void onCreate() {
        try {
            long[] vibrateSequence={100,200,300};
            MANAGE_INCOMING_CALLS = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            MANAGE_INCOMING_CALLS.listen(listen, PhoneStateListener.LISTEN_CALL_STATE);
            Notification.Builder BuildNotification = new Notification.Builder(this);
            BuildNotification.setSmallIcon(R.drawable.notif);
            BuildNotification.setContentTitle("GameChanger");
            BuildNotification.setContentText("Automatic call reject service running in background");
            BuildNotification.setVibrate(vibrateSequence);
            BuildNotification.setAutoCancel(false);
            mNotification=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mNotification.notify(NotificationID,BuildNotification.build());
        } catch (Exception e) {
            Toast.makeText(Call_Service_Automated.this, "Unable to initialise the service", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy()
    {
        Toast.makeText(Call_Service_Automated.this, "I stopped working", Toast.LENGTH_SHORT).show();
        mNotification.cancel(NotificationID);
    }
    PhoneStateListener listen=new PhoneStateListener()
    {
      @Override
      public void onCallStateChanged(int state, String incomingNumber)
      {
          switch(state)
          {
              case TelephonyManager.CALL_STATE_IDLE:
                  Toast.makeText(Call_Service_Automated.this, "Call idle", Toast.LENGTH_SHORT).show();
                  break;
              case TelephonyManager.CALL_STATE_RINGING:
                  if(incomingNumber==null)
                      Toast.makeText(Call_Service_Automated.this, "No incoming number", Toast.LENGTH_SHORT).show();
                  else {
                      Toast.makeText(Call_Service_Automated.this, "Incoming number: "+incomingNumber, Toast.LENGTH_SHORT).show();
                      try {
                          Custom_MESSAGE_JOB("Message.txt", incomingNumber);
                          File file=new File(getFilesDir(),"CALL_LOG.txt");
                          int Dump=GameChanger_CustomFunctions.CUSTOM_CALL_LOGGER(file, incomingNumber);
                          if(Dump!=0)
                              Toast.makeText(Call_Service_Automated.this, "Call successfully logged", Toast.LENGTH_SHORT).show();
                          else
                              Toast.makeText(Call_Service_Automated.this, "Unable to log the call", Toast.LENGTH_SHORT).show();
                      } catch (IOException e) {
                          Toast.makeText(Call_Service_Automated.this, "Unable to invoke Automated_Message module", Toast.LENGTH_SHORT).show();
                      }
                  }
          }
      }
    };
    public void Custom_MESSAGE_JOB(String _FileName, String Phone_NO)throws IOException
    {
        File jobFIle=new File(getFilesDir(),_FileName);
        boolean File_Exists=GameChanger_CustomFunctions.FileCheck_(jobFIle);
        if(File_Exists)
        {
            boolean state;
            BufferedReader b=new BufferedReader(new InputStreamReader(openFileInput("Message.txt")));
            StringBuffer Stringbuffer=new StringBuffer();
            int Read=GameChanger_CustomFunctions.STD_READER(b, Stringbuffer);
            if(Read!=0)
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(openFileInput("Con.txt")));
                StringBuilder Builder=new StringBuilder();
                String s;
                while ((s=reader.readLine())!=null)
                {
                    Builder.append(s);
                }
                String container=Builder.toString();
                if(container.contains("true")) {
                    state = true;
                    Toast.makeText(Call_Service_Automated.this, "Message service enabled", Toast.LENGTH_SHORT).show();
                }
                else {state=false;
                    Toast.makeText(Call_Service_Automated.this, "Message service disabled", Toast.LENGTH_SHORT).show();}
                boolean MessageSent=GameChanger_CustomFunctions.STD_MESSAGING_SERVICE(Phone_NO, Stringbuffer.toString(),state);
                STD_CALL_REJECT(Phone_NO);
                if(MessageSent)
                {
                    Toast.makeText(Call_Service_Automated.this, "Message Sent", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(Call_Service_Automated.this, "Unable to send message, This is caused because you have disabled message service", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(Call_Service_Automated.this, "Cannot read text from file.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(Call_Service_Automated.this, "Cannot load Message file", Toast.LENGTH_SHORT).show();
        }
    }
    public void STD_CALL_REJECT(String incomingNumber)
    {
        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c=Class.forName(tm.getClass().getName());
            Method m=c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService=m.invoke(tm);
            if(incomingNumber!=null) {
                Class c2 = Class.forName(telephonyService.getClass().getName());
                Method m2 = c2.getDeclaredMethod("endCall");
                m2.setAccessible(true);
                m2.invoke(telephonyService);
            }
        } catch (ClassNotFoundException e) {
            Toast.makeText(Call_Service_Automated.this, "Cannot connect to com.android.internal.telephony", Toast.LENGTH_SHORT).show();
        } catch (NoSuchMethodException e) {
            Toast.makeText(Call_Service_Automated.this, "No call method found", Toast.LENGTH_SHORT).show();
        } catch (InvocationTargetException e) {
            Toast.makeText(Call_Service_Automated.this, "Cannot invoke the target", Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) {
            Toast.makeText(Call_Service_Automated.this, "System Generating Illegal Access Error", Toast.LENGTH_SHORT).show();
        }
    }

}
