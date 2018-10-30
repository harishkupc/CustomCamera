package in.mycam;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    public static final int PERMISSIONS_STORAGE_CAMERA = 1001;
    public int permsCount = 0;

    //check Camera and Storage Permission
    public List<String> isPermissionNeeded() {

        List<String> list = new ArrayList<>();

        int per1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int per2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int per3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        if (per1 != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.CAMERA);
        }
        if (per2 != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (per3 != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.RECORD_AUDIO);
        }
        return list;
    }

    //Request Camera and Storage Permission
    public void requestImagePermission(List<String> list) {
        List<String> tempList = new ArrayList<>();
        for (String perm : list) {
            if (ActivityCompat.checkSelfPermission(this, perm) !=
                    PackageManager.PERMISSION_GRANTED) {
                tempList.add(perm);
            }
        }

        permsCount = tempList.size();
        String[] perms = new String[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            perms[i] = tempList.get(i);
        }
        ActivityCompat.requestPermissions(this, perms, PERMISSIONS_STORAGE_CAMERA);
    }

    //Camera and Storage permission alert on denied permission
    public void showPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Please accept all permissions")
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    List<String> list = isPermissionNeeded();
                    if (list.size() > 0) {
                        requestImagePermission(list);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setCancelable(false)
                .show();
    }
}
