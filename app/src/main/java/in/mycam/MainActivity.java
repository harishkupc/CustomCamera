package in.mycam;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.mycam.fragments.ImageFragment;
import in.mycam.fragments.OutputFragment;
import in.mycam.fragments.VideoFragment;

public class MainActivity extends BaseActivity {

    public static final String IMAGE = "IMAGE";
    public static final String VIDEO = "VIDEO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> list = isPermissionNeeded();
            if (list.size() > 0) {
                requestImagePermission(list);
            } else {
                setCameraPreview();
            }
        } else {
            setCameraPreview();
        }
    }

    public void setCameraPreview() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, VideoFragment.newInstance())
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_STORAGE_CAMERA) {
            List<Integer> acceptedList = new ArrayList<>();
            for (int permission : grantResults) {
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    acceptedList.add(permission);
                }
            }
            if (permsCount == acceptedList.size()) {
                setCameraPreview();
            } else {
                showPermissionDialog();
            }
        }
    }

    public void outputImage(String mFile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, OutputFragment.newInstance(mFile, IMAGE))
                .commit();
    }

    public void outputVideo(String path) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, OutputFragment.newInstance(path, VIDEO))
                .commit();
    }

    public void returnFile(String mFile) {
        finish();
    }
}
