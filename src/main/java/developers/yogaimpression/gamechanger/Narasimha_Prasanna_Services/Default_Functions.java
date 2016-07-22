package developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import developers.yogaimpression.gamechanger.R;

public class Default_Functions extends Activity {
    public boolean FileCheck_(File _file) {
        if (_file.exists()) {
            return true;
        } else return false;
    }

    public int File_Delete(File _file) {
        boolean i = FileCheck_(_file);
        if (i) {
            boolean b = _file.delete();
            if (b) return 1;
            else return 0;
        } else return 0;
    }

    public boolean STD_MESSAGING_SERVICE(String phoneNo, String _Content, boolean Activation) {
        //Pass phoneNo as null disable MessageService during Testing phase//
        if (Activation) {
            if (phoneNo == null)
                return false;
            else {
                try {
                    SmsManager getInstance = SmsManager.getDefault();
                    getInstance.sendTextMessage(phoneNo, null, _Content, null, null);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        } else return false;
    }

    public int STD_READER(BufferedReader b, StringBuffer buffer) {
        String lines;
        try {
            while ((lines = b.readLine()) != null) {
                buffer.append(lines);
            }
            b.close();
            return 1;
        } catch (IOException e) {
            return 0;
        }
    }

    public int CUSTOM_CALL_LOGGER(File _file, String _PHONE_NO) {
        if (!_file.exists()) {
            try {
                boolean file = _file.createNewFile();
                if (file) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(_file));
                    writer.write(_PHONE_NO + "\n");
                    writer.flush();
                    writer.close();
                    return 1;
                } else return 0;
            } catch (IOException e) {
                return 0;
            }
        } else try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(_file));
            wr.append(_PHONE_NO + "\n");
            wr.flush();
            wr.close();
            return 1;
        } catch (IOException e) {
            return 0;
        }
    }

    public boolean STD_FILE_INITIALISE(File _file) {
        boolean b = FileCheck_(_file);
        if (b) {
            boolean m = _file.delete();
            if (m) {
                try {
                    _file.createNewFile();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            } else return false;
        } else {
            try {
                _file.createNewFile();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    public boolean Std_FileWriter(File _file, String _data) {
        boolean check_init = STD_FILE_INITIALISE(_file);
        if (check_init) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(_file));
                writer.write(_data);
                writer.flush();
                writer.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else return false;
    }
    public void STD_CALL_RECEIVE(int _ACTIVATION)
    {
        if(_ACTIVATION!=0)
        {
            try {
                Intent STD_ANSWER=new Intent(Intent.ACTION_ANSWER);
                STD_ANSWER.setFlags(Context.CONTEXT_IGNORE_SECURITY);
                startActivity(STD_ANSWER);

            }catch (Exception e)
            {
                Toast.makeText(Default_Functions.this, "Unable to answer the call", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void STD_NOTIFIER(String _Head, String _Body, boolean _Activation)
    {

        Notification.Builder mNotifier=new Notification.Builder(this);
        NotificationManager Std_Notifier=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        int notifID=1;
        if(_Activation)
        {
            mNotifier.setSmallIcon(R.drawable.notif);
            mNotifier.setContentTitle(_Head);
            mNotifier.setContentText(_Body);
            mNotifier.setAutoCancel(false);
            Std_Notifier.notify(notifID, mNotifier.build());
        }
        else Std_Notifier.cancelAll();
    }
    public float Auto_Adjust_Optical(float _OPTICAL_DENSITY)
    {
        if((_OPTICAL_DENSITY<=10)&&(_OPTICAL_DENSITY>=5))
            return 2.0f;
        else if((_OPTICAL_DENSITY<=20)&&(_OPTICAL_DENSITY>=10))
            return 11.0f;
        else if((_OPTICAL_DENSITY<=30)&&(_OPTICAL_DENSITY>=20))
            return 23;
        else if((_OPTICAL_DENSITY<=40)&&(_OPTICAL_DENSITY>=30))
            return 32;
        else return 100;

    }
    public boolean STD_SERVICE_CHECK(Class service)
    {
        ActivityManager manager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo: manager.getRunningServices(Integer.MAX_VALUE))
        {
            if(service.getName().equals(serviceInfo.service.getClassName())) return true;
        }
        return false;
    }
    public int STD_READER_STRINGBUILDER(BufferedReader b, StringBuilder buffer) {
        String lines;
        try {
            while ((lines = b.readLine()) != null) {
                buffer.append(lines);
            }
            b.close();
            return 1;
        } catch (IOException e) {
            return 0;
        }
    }
}
