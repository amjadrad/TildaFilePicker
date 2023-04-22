package ir.tildaweb.tildafilepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.List;

import ir.tildaweb.tilda_filepicker.TildaFilePicker;
import ir.tildaweb.tilda_filepicker.enums.FileType;
import ir.tildaweb.tilda_filepicker.models.FileModel;

public class MainActivity extends AppCompatActivity {

    private String TAG = this.getClass().getName();
    private AppCompatButton btn;
    private AppCompatImageView imageView;
    private int STORAGE_PERMISSION = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        imageView = findViewById(R.id.img);
        btn.setOnClickListener(v -> {
            if (checkFilesPermission(this, 1)) {
                TildaFilePicker tildaFilePicker = new TildaFilePicker(MainActivity.this, new FileType[]{FileType.FILE_TYPE_ALL});
                tildaFilePicker.setSingleChoice();
                tildaFilePicker.setOnTildaFileSelectListener(list -> {
                    Log.d(TAG, "onCreate: " + list.get(0).getPath());
                    imageView.setImageBitmap(BitmapFactory.decodeFile(list.get(0).getPath()));
                });
                tildaFilePicker.show(getSupportFragmentManager());
            }
        });
    }

    protected boolean checkFilesPermission(Activity activity, int REQUEST_CODE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_VIDEO}, REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        }
    }

}