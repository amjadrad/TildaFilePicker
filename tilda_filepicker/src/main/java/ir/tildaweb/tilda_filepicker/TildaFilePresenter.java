package ir.tildaweb.tilda_filepicker;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.List;

import ir.tildaweb.tilda_filepicker.enums.FileMimeType;
import ir.tildaweb.tilda_filepicker.models.FileModel;

public class TildaFilePresenter {

    private String TAG = this.getClass().getName();
    private String[] projection = {MediaStore.MediaColumns.DATA};

    private OnDataPreparedListener onDataPreparedListener;
    private Context context;

    public TildaFilePresenter(Context context, OnDataPreparedListener onDataPreparedListener) {
        this.onDataPreparedListener = onDataPreparedListener;
        this.context = context;
    }


    public void requestImages() {
        List<FileModel> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        assert cursor != null;
        if (cursor.moveToLast()) {
            do {
                String absolutePath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                FileModel model = new FileModel();
                model.setPath(absolutePath);
                model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_IMAGE);
                list.add(model);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        onDataPreparedListener.onResponseImages(list);
    }

    public void requestFiles() {
        List<FileModel> list = new ArrayList<>();
        // only pdf
        String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=? or " + MediaStore.Files.FileColumns.MIME_TYPE + "=? or " + MediaStore.Files.FileColumns.MIME_TYPE + "=? or " + MediaStore.Files.FileColumns.MIME_TYPE + "=? or " + MediaStore.Files.FileColumns.MIME_TYPE + "=? or " + MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String mimeTypePDF = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
        String mimeTypeDocx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx");
        String mimeTypeZip = MimeTypeMap.getSingleton().getMimeTypeFromExtension("zip");
        String mimeTypeRar = MimeTypeMap.getSingleton().getMimeTypeFromExtension("rar");
        String mimeTypeText = MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt");
        String mimeTypeHtml = MimeTypeMap.getSingleton().getMimeTypeFromExtension("html");
        String[] selectionArgsPdf = new String[]{mimeTypePDF, mimeTypeDocx, mimeTypeZip, mimeTypeRar, mimeTypeText, mimeTypeHtml};

        Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), projection, selectionMimeType, selectionArgsPdf, null);
        assert cursor != null;
        if (cursor.moveToLast()) {
            do {
                String absolutePath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                FileModel model = new FileModel();
                model.setPath(absolutePath);
                model.setTitle(absolutePath.substring(absolutePath.lastIndexOf('/') + 1));
                String mimeType = absolutePath.substring(absolutePath.lastIndexOf('.') + 1);
                switch (mimeType) {
                    case "pdf": {
                        model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_PDF);
                        break;
                    }
                    case "zip": {
                        model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_ZIP);
                        break;
                    }
                    case "rar": {
                        model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_RAR);
                        break;
                    }
                    case "txt": {
                        model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_TEXT);
                        break;
                    }
                    case "html": {
                        model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_HTML);
                        break;
                    }
                    case "docx": {
                        model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_DOCX);
                        break;
                    }
                    default: {
                        model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_NONE);
                    }

                }
                list.add(model);
                Log.d(TAG, "requestFiles: " + model.getFileMimeType().name() + " :::::: " + absolutePath);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        onDataPreparedListener.onResponseFiles(list);
    }

    public void requestMusics() {
        List<FileModel> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        assert cursor != null;
        if (cursor.moveToLast()) {
            do {
                String absolutePath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                FileModel model = new FileModel();
                model.setTitle(absolutePath.substring(absolutePath.lastIndexOf('/') + 1));
                model.setPath(absolutePath);
                model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_MUSIC);
                list.add(model);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        onDataPreparedListener.onResponseMusics(list);
    }

    public void requestVideos() {
        List<FileModel> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        assert cursor != null;
        if (cursor.moveToLast()) {
            do {
                String absolutePath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                FileModel model = new FileModel();
                model.setPath(absolutePath);
                model.setFileMimeType(FileMimeType.FILE_MIME_TYPE_VIDEO);
                list.add(model);
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        onDataPreparedListener.onResponseVideos(list);
    }

    public interface OnDataPreparedListener {

        void onResponseImages(List<FileModel> list);

        void onResponseFiles(List<FileModel> list);

        void onResponseMusics(List<FileModel> list);

        void onResponseVideos(List<FileModel> list);

    }

}
