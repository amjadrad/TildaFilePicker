package ir.tildaweb.tilda_filepicker.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import ir.tildaweb.tilda_filepicker.R;


public class DialogShowPicture {

    private String TAG = this.getClass().getName();
    private AlertDialog alertDialog;

   private PhotoView photoView;

    public DialogShowPicture(Context context, String picture) {
        this.alertDialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_show_picture, null);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setView(view);
        photoView = view.findViewById(R.id.photoView);

        Glide.with(context).load(picture).into(photoView);

    }

    public void show() {
        this.alertDialog.show();
    }

    public void dismiss() {
        this.alertDialog.dismiss();
    }


}
