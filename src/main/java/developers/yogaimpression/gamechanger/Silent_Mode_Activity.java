package developers.yogaimpression.gamechanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Silent_Mode_Background;
import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Torch_Light_Service;

public class Silent_Mode_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silent__mode_);
    }
    public void START1(View v){
        startService(new Intent(this,Silent_Mode_Background.class));
    }
    public void START2(View v){
        startService(new Intent(this,Silent_Mode_Background.class));
        startService(new Intent(this,Torch_Light_Service.class));
    }
    public void STOP1(View v){
        boolean b=stopService(new Intent(this,Silent_Mode_Background.class));
        if(!b) Toast.makeText(Silent_Mode_Activity.this, "Unable to stop the service, Maybe the service is not running", Toast.LENGTH_SHORT).show();
    }
    public void STOP2(View v){
        boolean a=stopService(new Intent(this,Silent_Mode_Background.class));
        boolean c=stopService(new Intent(this,Torch_Light_Service.class));
        if(!(a&c)) Toast.makeText(Silent_Mode_Activity.this, "Unable to stop the service, Maybe anyone of the service is not running", Toast.LENGTH_SHORT).show();
    }

}
