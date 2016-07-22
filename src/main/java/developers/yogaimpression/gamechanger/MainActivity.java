package developers.yogaimpression.gamechanger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Call_Stopper_Settings(View v)
    {
        startActivity(new Intent(this,Call_Handler_Settings.class));
    }
    public void INSTANT_CALLER(View v)
    {
        startActivity(new Intent(this,Instant_Caller.class));
    }
    public void Auto_receiver(View v)
    {
        startActivity(new Intent(this, Activity_Auto_receiver.class));
    }
    public void VIEW_CALL_LOG(View v)
    {
        startActivity(new Intent(this,activity_call_Log.class));
    }
    public void TORCH_LIGHT_ACTIVITY(View v)
    {
        startActivity(new Intent(this, Torch_Light.class));
    }
}
