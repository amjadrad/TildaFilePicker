package ir.tildaweb.tildafilepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.List;

import ir.tildaweb.tilda_filepicker.TildaFilePicker;
import ir.tildaweb.tilda_filepicker.enums.FileType;
import ir.tildaweb.tilda_filepicker.models.FileModel;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getName();
    private Button btn;
    private int STORAGE_PERMISSION = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            if (isStoragePermissionGranted()) {
                TildaFilePicker tildaFilePicker = new TildaFilePicker(MainActivity.this, new FileType[]{FileType.FILE_TYPE_ALL});
                tildaFilePicker.setSingleChoice();
                tildaFilePicker.setOnTildaFileSelectListener(list -> Log.d(TAG, "onCreate: " + list.get(0).getPath()));
                tildaFilePicker.show(getSupportFragmentManager());
            }
        });
    }

    public boolean isStoragePermissionGranted() {
        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            return false;
        }
        return true;
    }

}