package ir.tildaweb.tildafilepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ir.tildaweb.tilda_filepicker.TildaFilePicker;
import ir.tildaweb.tilda_filepicker.models.FileModel;

public class MainActivity extends AppCompatActivity implements TildaFilePicker.OnTildaFileSelectListener {

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
                TildaFilePicker tildaFilePicker = new TildaFilePicker(MainActivity.this);
                tildaFilePicker.setOnTildaFileSelectListener(list -> {});
                tildaFilePicker.show();
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

    @Override
    public void onFilePicks(List<FileModel> list) {
        for (FileModel model : list) {
            Log.d(TAG, "onFilePicks: " + model.getPath());
        }
    }
}