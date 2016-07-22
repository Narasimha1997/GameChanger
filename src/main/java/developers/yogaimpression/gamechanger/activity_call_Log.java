package developers.yogaimpression.gamechanger;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Default_Functions;

public class activity_call_Log extends Activity {
    Default_Functions Game_Changer_defaults=new Default_Functions();
    TextView List;
    BufferedReader b;
    StringBuilder stringCallLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_call__log);
        List=(TextView)findViewById(R.id.Call_Log);
        Display_CallLog(true);
    }
    public void Display_CallLog(boolean activation)
    {
        if(activation)
        {
            try {
                b=new BufferedReader(new InputStreamReader(openFileInput("CALL_LOG.txt")));
                stringCallLog=new StringBuilder();
                int err=Game_Changer_defaults.STD_READER_STRINGBUILDER(b,stringCallLog);
                if(err==1)
                {
                    List.setText(stringCallLog.toString());
                    Toast.makeText(activity_call_Log.this, "Read complete", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(activity_call_Log.this, "Null pointer at java.io.BufferedReader", Toast.LENGTH_SHORT).show();
            }catch (Exception e)
            {
                Toast.makeText(activity_call_Log.this,e.toString() , Toast.LENGTH_SHORT).show();
            }

        }
    }

}
