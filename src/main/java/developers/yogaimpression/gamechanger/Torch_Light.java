package developers.yogaimpression.gamechanger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import developers.yogaimpression.gamechanger.Narasimha_Prasanna_Services.Torch_Light_Service;

public class Torch_Light extends Activity {
    Context context;
    TextView Camera_Defaults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torch__light);
        Camera_Defaults=(TextView)findViewById(R.id.Camera_defaults);
        try {
            Camera_Defaults.setText("Cameras connected: \n");
            Camera_Default_Load();
        }catch (Exception e)
        {
            Toast.makeText(Torch_Light.this, "Initialisation problem", Toast.LENGTH_SHORT).show();
        }

    }
    public void Start_Service_Torch(View v)
    {
        startService(new Intent(this, Torch_Light_Service.class));
    }
    public void Stop_Service_Torch(View v)
    {
        if(!stopService(new Intent(this,Torch_Light_Service.class)))
            Toast.makeText(Torch_Light.this, "Unable to stop the service", Toast.LENGTH_SHORT).show();
        else Toast.makeText(Torch_Light.this, "Service stopped", Toast.LENGTH_SHORT).show();
    }
    public void Camera_Default_Load()
    {
        CameraManager cameraManager=(CameraManager)getSystemService(CAMERA_SERVICE);
        try {
            String[] n=cameraManager.getCameraIdList();
            for(int i=0; i<10; i++)
            {
                if(n[i]!=null)
                {
                    Camera_Defaults.append(n[i]+"\n");
                }
                else break;
            }
        } catch (CameraAccessException e) {
            Toast.makeText(Torch_Light.this, "Unable to access camera", Toast.LENGTH_SHORT).show();
        }
    }
}
