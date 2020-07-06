package sv.edu.ues.fia.eisi.pdm_proyecto2.camaraX;

import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Rational;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;

import sv.edu.ues.fia.eisi.pdm_proyecto2.R;

import static sv.edu.ues.fia.eisi.pdm_proyecto2.MainActivity.mainActivity;

public class CamaraFragment extends Fragment {
    private int REQUEST_CODE_PERMISSIONS=101;
    private String[] REQUIRED_PERMISSONS = new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};
    private View v;
    public static int contador=0;

    TextureView textureView;

    public CamaraFragment() {
        // Required empty public constructor
    }

     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_camara, container, false);
        v=vista;

        textureView=(TextureView) vista.findViewById(R.id.view_finder);

        if(allPermissionGranted()){
            startCamara();
        }else{
            ActivityCompat.requestPermissions(mainActivity,REQUIRED_PERMISSONS,REQUEST_CODE_PERMISSIONS);
        }

        return vista;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startCamara() {
        CameraX.unbindAll();

        Rational aspectRatio=new Rational(textureView.getWidth(),textureView.getHeight());
        Size screen=new Size(textureView.getWidth(),textureView.getHeight());

        PreviewConfig previewConfig=new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview=new Preview(previewConfig);

        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener(){

                    @Override
                    public void onUpdated(Preview.PreviewOutput output) {
                        ViewGroup parent=(ViewGroup) textureView.getParent();
                        parent.removeView(textureView);
                        parent.addView(textureView);

                        textureView.setSurfaceTexture(output.getSurfaceTexture());
                        updateTransfrom();
                    }
                }
        );

        ImageCaptureConfig imageCaptureConfig=new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(mainActivity.getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap=new ImageCapture(imageCaptureConfig);

        v.findViewById(R.id.imgCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                File file=new File(mainActivity.getDatabasePath(Environment.DIRECTORY_PICTURES) + "CameraX"+contador);

                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg="Guardado en "+file.getAbsolutePath();
                        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg="Fallo " + message;
                        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();

                        if(cause!=null){
                            cause.printStackTrace();
                        }
                    }
                });
            }
        });

        CameraX.bindToLifecycle(this,preview,imgCap);
    }

    private void updateTransfrom() {
        Matrix mx=new Matrix();
        float w=textureView.getMeasuredWidth();
        float h=textureView.getMeasuredHeight();

        float cx=w/2f;
        float cy=h/2f;

        int rotationDgr;
        int rotation=(int) textureView.getRotation();

        switch (rotation){
            case Surface.ROTATION_0:
                rotationDgr=0;
                break;
            case Surface.ROTATION_90:
                rotationDgr=90;
                break;
            case Surface.ROTATION_180:
                rotationDgr=180;
                break;
            case Surface.ROTATION_270:
                rotationDgr=270;
                break;
            default:
                return;
        }
        mx.postRotate((float)rotationDgr,cx,cy);
        textureView.setTransform(mx);
    }

    private boolean allPermissionGranted() {
        for(String permission : REQUIRED_PERMISSONS){
            if(ContextCompat.checkSelfPermission(mainActivity,permission)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}