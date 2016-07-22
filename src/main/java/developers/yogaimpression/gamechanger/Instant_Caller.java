package developers.yogaimpression.gamechanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Call_Service_Automated;
import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Default_Functions;
import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.GameChanger_InstantCall_SERVICE;

public class Instant_Caller extends Activity {
    Default_Functions GameChanger_Std_Functions=new Default_Functions();
    EditText Phone_No_Container;
    String GLOBAL_NUMBER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant__caller);
        Phone_No_Container=(EditText)findViewById(R.id.Incoming_number_holder);
    }
    public void Save_Data(View v)
    {
        GLOBAL_NUMBER=Phone_No_Container.getText().toString();
        File Phone_No_File=new File(getFilesDir(),"PhoneNo.txt");
        boolean FileSaved=GameChanger_Std_Functions.Std_FileWriter(Phone_No_File,GLOBAL_NUMBER);
        if(FileSaved)
        {
            try {
                StringBuffer b=new StringBuffer();
                BufferedReader s=new BufferedReader(new InputStreamReader(openFileInput("PhoneNo.txt")));
                int fileRead=GameChanger_Std_Functions.STD_READER(s,b);
                if(fileRead!=0)
                    Toast.makeText(Instant_Caller.this, "Number: "+b.toString(), Toast.LENGTH_SHORT).show();
                else Toast.makeText(Instant_Caller.this, "Cannot read data file", Toast.LENGTH_SHORT).show();
            }
           catch (Exception e)
           {
               Toast.makeText(Instant_Caller.this, "Sorry something went wrong", Toast.LENGTH_SHORT).show();
           }
        }
        else
            Toast.makeText(Instant_Caller.this, "Cannot save file", Toast.LENGTH_SHORT).show();
    }
    public void DELETE_PREVIOUS(View v)
    {
        File file=new File(getFilesDir(),"PhoneNo.txt");
        int FileDelete=GameChanger_Std_Functions.File_Delete(file);
        if(FileDelete!=0) Toast.makeText(Instant_Caller.this, "File deleted", Toast.LENGTH_SHORT).show();
        else Toast.makeText(Instant_Caller.this, "Unable to delete previous file", Toast.LENGTH_SHORT).show();
    }
    public void SHOW_PREVIOUS(View v)
    {
        File file=new File(getFilesDir(),"PhoneNo.txt");
        boolean FileCheck=GameChanger_Std_Functions.FileCheck_(file);
        if(FileCheck)
        {
            StringBuffer b=new StringBuffer();
            BufferedReader s= null;
            try {
                s = new BufferedReader(new InputStreamReader(openFileInput("PhoneNo.txt")));
                int fileRead=GameChanger_Std_Functions.STD_READER(s,b);
                if(fileRead!=0) Toast.makeText(Instant_Caller.this, "Previous number: "+b.toString(), Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(Instant_Caller.this, "Unable to read file", Toast.LENGTH_SHORT).show();
            }
        }
        else Toast.makeText(Instant_Caller.this, "Unable to read file", Toast.LENGTH_SHORT).show();
    }
    public void START_SERVICE(View v)
    {
        startService(new Intent(this, GameChanger_InstantCall_SERVICE.class));
        Toast.makeText(Instant_Caller.this, "Service started in background", Toast.LENGTH_SHORT).show();    }
    public void KILL_SERVICE(View v)
    {
        boolean stopped=stopService(new Intent(this,GameChanger_InstantCall_SERVICE.class));
        if(stopped) Toast.makeText(Instant_Caller.this, "Service stopped", Toast.LENGTH_SHORT).show();
        else Toast.makeText(Instant_Caller.this, "Unable to stop service, Maybe the service is not running", Toast.LENGTH_SHORT).show();
    }

}
