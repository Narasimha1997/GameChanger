package developers.yogaimpression.gamechanger;
import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Call_Service_Automated;
import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Default_Functions;
import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Service_Automated_Call_Handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Call_Handler_Settings extends Activity {
    Default_Functions Custom_GameChanger_Functions = new Default_Functions();
    EditText textMessage;
    File MessageStringFile;
    File fileCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__handler__settings);
        textMessage = (EditText) findViewById(R.id.Text_String_Container);
        MessageStringFile = new File(getFilesDir(), "Message.txt");

    }

    public void Save_String_Message(View v) throws IOException {
        String TextMessage = textMessage.getText().toString();
        if (MessageStringFile.exists()) {
            Toast.makeText(Call_Handler_Settings.this, "Please delete the previous message .", Toast.LENGTH_SHORT).show();

        } else {
            boolean FileCreated = MessageStringFile.createNewFile();
            if (FileCreated) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(MessageStringFile));
                writer.write(TextMessage);
                writer.flush();
                writer.close();
            }
        }
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Message.txt")));
        String lines;
        if ((lines = reader.readLine()) != null) {
            buffer.append(lines);
        }
        reader.close();
        Toast.makeText(Call_Handler_Settings.this, buffer.toString(), Toast.LENGTH_SHORT).show();
    }

    public void Start_Service(View v) throws IOException {
        fileCon=new File(getFilesDir(),"Con.txt");
        boolean m=Custom_GameChanger_Functions.Std_FileWriter(fileCon,"true");
        if(m) {
            startService(new Intent(this, Call_Service_Automated.class));
            Toast.makeText(Call_Handler_Settings.this, "Service started in background", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(Call_Handler_Settings.this, "Unable to start service", Toast.LENGTH_SHORT).show();
        }


    public void Kill_Service(View v) {
        boolean n = stopService(new Intent(this, Call_Service_Automated.class));
        if (n)
            Toast.makeText(Call_Handler_Settings.this, "Service successfully stopped.", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(Call_Handler_Settings.this, "Unable to stop the service, Maybe the service is not running.", Toast.LENGTH_SHORT).show();
        }
    }

    public void DEL_PREVIOUS(View v) {
        File file = new File(getFilesDir(), "Message.txt");
        int check = Custom_GameChanger_Functions.File_Delete(file);
        if (check != 0)
            Toast.makeText(Call_Handler_Settings.this, "Message deleted successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(Call_Handler_Settings.this, "Unable to delete the previous message", Toast.LENGTH_SHORT).show();
    }

    public void PREVIOUS(View v) throws IOException {
        StringBuffer r = new StringBuffer();
        String s;
        BufferedReader read = new BufferedReader(new InputStreamReader(openFileInput("Message.txt")));
        while ((s = read.readLine()) != null) {
            r.append(s);
        }
        Toast.makeText(Call_Handler_Settings.this, r.toString(), Toast.LENGTH_SHORT).show();
    }
    public void DISABLE_AUTO_MESSAGE(View v)
    {
        fileCon=new File(getFilesDir(),"Con.txt");
        boolean b=Custom_GameChanger_Functions.Std_FileWriter(fileCon,"false");
        if(b){
            startService(new Intent(this,Call_Service_Automated.class));
            Toast.makeText(Call_Handler_Settings.this, "Service started successfully with Message disabled", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(Call_Handler_Settings.this, "Unable to start service", Toast.LENGTH_SHORT).show();
    }
}
