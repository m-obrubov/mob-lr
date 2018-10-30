package ru.obrubov.laba8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView preview;

    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preview = findViewById(R.id.surfaceView);

        File pictures = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        photoFile = new File(pictures, "myphoto.jpg");
        surfaceHolder = preview.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try
                {
                    camera.setPreviewDisplay(holder);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                Camera.Size previewSize = camera.getParameters().getPreviewSize();
                float aspect = (float) previewSize.width / previewSize.height;

                int previewSurfaceWidth = preview.getWidth();
                int previewSurfaceHeight = preview.getHeight();

                ViewGroup.LayoutParams lp = preview.getLayoutParams();

                // здесь корректируем размер отображаемого preview, чтобы не было искажений

                if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
                {
                    // портретный вид
                    camera.setDisplayOrientation(90);
                    lp.height = previewSurfaceHeight;
                    lp.width = (int) (previewSurfaceHeight / aspect);
                }
                else
                {
                    // ландшафтный
                    camera.setDisplayOrientation(0);
                    lp.width = previewSurfaceWidth;
                    lp.height = (int) (previewSurfaceWidth / aspect);
                }
                preview.setLayoutParams(lp);
                camera.startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)
        {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public void onSavePicture(View view) {
        camera.takePicture(null, null, new PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                try(FileOutputStream fos = new FileOutputStream(photoFile)) {
                    fos.write(data);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    camera.startPreview();
                }

                try(FileInputStream fis = new FileInputStream(photoFile)) {
                    fis.read(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}