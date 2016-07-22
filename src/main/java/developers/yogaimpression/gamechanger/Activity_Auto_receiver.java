package developers.yogaimpression.gamechanger;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.view.View;
import android.widget.Toast;

import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Call_Service_Automated;
import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Default_Functions;
import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.GameChanger_Auto_Service;

public class Activity_Auto_receiver extends Activity {
    Default_Functions STD_GAMECHANGER_FUNCTIONS=new Default_Functions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__auto_receiver);
    }
    public void ACTIVATE_AUTO_RECEIVE(View v)
    {
        startService(new Intent(this, GameChanger_Auto_Service.class));
    }
    public void STOP_SERVICE(View v) {
        boolean b = stopService(new Intent(this, GameChanger_Auto_Service.class));
        if (!b) Toast.makeText(Activity_Auto_receiver.this, "Unable to stop the service, Maybe the service is not running.", Toast.LENGTH_SHORT).show();
    }

}
