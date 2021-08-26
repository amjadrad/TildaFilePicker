package ir.tildaweb.tilda_filepicker;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import ir.tildaweb.tilda_filepicker.adapter.AdapterFiles;
import ir.tildaweb.tilda_filepicker.adapter.AdapterImages;
import ir.tildaweb.tilda_filepicker.adapter.AdapterMusics;
import ir.tildaweb.tilda_filepicker.adapter.AdapterVideos;
import ir.tildaweb.tilda_filepicker.models.FileModel;

public class TildaFilePicker extends BottomSheetDialog implements TildaFilePresenter.OnDataPreparedListener, View.OnClickListener, AdapterImages.ImageListener, AdapterMusics.MusicListener, AdapterVideos.VideoListener, AdapterFiles.FileListener {

    private String TAG = this.getClass().getName();
    private TildaFilePresenter tildaFilePresenter;
    private AdapterImages adapterImages;
    private AdapterVideos adapterVideos;
    private AdapterMusics adapterMusics;
    private AdapterFiles adapterFiles;
    private AttachType currentAttachType;

    private RecyclerView recyclerViewImages;
    private RecyclerView recyclerViewVideos;
    private RecyclerView recyclerViewMusics;
    private RecyclerView recyclerViewFiles;

    private CardView cardViewNavGallery;
    private CardView cardViewNavFile;
    private CardView cardViewNavMusic;
    private CardView cardViewNavVideo;
    private TextView tvNavGallery;
    private TextView tvNavFile;
    private TextView tvNavMusic;
    private TextView tvNavVideo;
    private AppCompatImageView imageViewNavGallery;
    private AppCompatImageView imageViewNavFile;
    private AppCompatImageView imageViewNavMusic;
    private AppCompatImageView imageViewNavVideo;
    private TextView tvNumberSelectedItems;
    private AppCompatButton btnConfirm;
    private LinearLayout linearAfterSelect;
    private OnTildaFileSelectListener onTildaFileSelectListener;

    private enum AttachType {
        ATTACHE_TYPE_GALLERY,
        ATTACHE_TYPE_FILE,
        ATTACHE_TYPE_MUSIC,
        ATTACHE_TYPE_VIDEO
    }

    public interface OnTildaFileSelectListener {
        void onFilePicks(List<FileModel> list);
    }


    public TildaFilePicker(@NonNull Context context) {
        super(context);
        setContentView(R.layout.bottom_sheet_select_files);
        tildaFilePresenter = new TildaFilePresenter(context, this);

        //Find views
        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        recyclerViewVideos = findViewById(R.id.recyclerViewVideos);
        recyclerViewMusics = findViewById(R.id.recyclerViewMusics);
        recyclerViewFiles = findViewById(R.id.recyclerViewFiles);
        cardViewNavGallery = findViewById(R.id.cardViewNavGallery);
        cardViewNavFile = findViewById(R.id.cardViewNavFile);
        cardViewNavMusic = findViewById(R.id.cardViewNavMusic);
        cardViewNavVideo = findViewById(R.id.cardViewNavVideo);
        tvNavGallery = findViewById(R.id.tvNavGallery);
        tvNavFile = findViewById(R.id.tvNavFile);
        tvNavMusic = findViewById(R.id.tvNavMusic);
        tvNavVideo = findViewById(R.id.tvNavVideo);
        imageViewNavGallery = findViewById(R.id.imageViewNavGallery);
        imageViewNavFile = findViewById(R.id.imageViewNavFile);
        imageViewNavMusic = findViewById(R.id.imageViewNavMusic);
        imageViewNavVideo = findViewById(R.id.imageViewNavVideo);
        tvNumberSelectedItems = findViewById(R.id.tvNumberSelectedItems);
        btnConfirm = findViewById(R.id.btnConfirm);
        linearAfterSelect = findViewById(R.id.linearAfterSelect);

        cardViewNavGallery.setOnClickListener(this);
        cardViewNavFile.setOnClickListener(this);
        cardViewNavMusic.setOnClickListener(this);
        cardViewNavVideo.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);


        recyclerViewImages.setLayoutManager(new GridLayoutManager(context, 3));
        adapterImages = new AdapterImages(context);
        adapterImages.setImageListener(this);
        recyclerViewImages.setAdapter(adapterImages);

        recyclerViewVideos.setLayoutManager(new GridLayoutManager(context, 3));
        adapterVideos = new AdapterVideos(context);
        adapterVideos.setVideoListener(this);
        recyclerViewVideos.setAdapter(adapterVideos);

        recyclerViewMusics.setLayoutManager(new LinearLayoutManager(context));
        adapterMusics = new AdapterMusics(context);
        adapterMusics.setMusicListener(this);
        recyclerViewMusics.setAdapter(adapterMusics);


        recyclerViewFiles.setLayoutManager(new LinearLayoutManager(context));
        adapterFiles = new AdapterFiles(context);
        adapterFiles.setFileListener(this);
        recyclerViewFiles.setAdapter(adapterFiles);


        cardViewNavGallery.callOnClick();

    }

    public void setOnTildaFileSelectListener(OnTildaFileSelectListener onTildaFileSelectListener) {
        this.onTildaFileSelectListener = onTildaFileSelectListener;
    }

    @Override
    public void onResponseImages(List<FileModel> list) {
        adapterImages.clearAll();
        adapterImages.addItems(list);
    }

    @Override
    public void onResponseFiles(List<FileModel> list) {
        adapterFiles.clearAll();
        adapterFiles.addItems(list);
    }

    @Override
    public void onResponseMusics(List<FileModel> list) {
        adapterMusics.clearAll();
        adapterMusics.addItems(list);
    }

    @Override
    public void onResponseVideos(List<FileModel> list) {
        adapterVideos.clearAll();
        adapterVideos.addItems(list);
    }

    @Override
    public void onClick(View v) {
        //Nav
        if (v.getId() == R.id.cardViewNavGallery) {
            tildaFilePresenter.requestImages();
            changeSelectedAttachType(AttachType.ATTACHE_TYPE_GALLERY);
        } else if (v.getId() == R.id.cardViewNavFile) {
            tildaFilePresenter.requestFiles();
            changeSelectedAttachType(AttachType.ATTACHE_TYPE_FILE);
        } else if (v.getId() == R.id.cardViewNavMusic) {

            tildaFilePresenter.requestMusics();
            changeSelectedAttachType(AttachType.ATTACHE_TYPE_MUSIC);
        } else if (v.getId() == R.id.cardViewNavVideo) {
            tildaFilePresenter.requestVideos();
            changeSelectedAttachType(AttachType.ATTACHE_TYPE_VIDEO);
        } else if (v.getId() == R.id.btnConfirm) {
            switch (currentAttachType) {
                case ATTACHE_TYPE_GALLERY:
                    onTildaFileSelectListener.onFilePicks(adapterImages.getAllSelectedItems());
                    cancel();
                    break;
                case ATTACHE_TYPE_FILE:
                    onTildaFileSelectListener.onFilePicks(adapterFiles.getAllSelectedItems());
                    cancel();
                    break;
                case ATTACHE_TYPE_MUSIC:
                    onTildaFileSelectListener.onFilePicks(adapterMusics.getAllSelectedItems());
                    cancel();
                    break;
                case ATTACHE_TYPE_VIDEO:
                    onTildaFileSelectListener.onFilePicks(adapterVideos.getAllSelectedItems());
                    cancel();
                    break;
            }
        }


    }


    private void changeSelectedAttachType(AttachType attachType) {
        this.currentAttachType = attachType;
        linearAfterSelect.setVisibility(View.GONE);
        switch (attachType) {
            case ATTACHE_TYPE_GALLERY: {

                recyclerViewImages.setVisibility(View.VISIBLE);
                recyclerViewMusics.setVisibility(View.GONE);
                recyclerViewVideos.setVisibility(View.GONE);
                recyclerViewFiles.setVisibility(View.GONE);

                cardViewNavGallery.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorGalleryDark));
                imageViewNavGallery.setColorFilter(getContext().getResources().getColor(R.color.colorWhite));
                tvNavGallery.setTextColor(getContext().getResources().getColor(R.color.colorWhite));

                cardViewNavFile.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorFileLight));
                imageViewNavFile.setColorFilter(getContext().getResources().getColor(R.color.colorFileDark));
                tvNavFile.setTextColor(getContext().getResources().getColor(R.color.colorFileDark));

                cardViewNavMusic.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorMusicLight));
                imageViewNavMusic.setColorFilter(getContext().getResources().getColor(R.color.colorMusicDark));
                tvNavMusic.setTextColor(getContext().getResources().getColor(R.color.colorMusicDark));

                cardViewNavVideo.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorVideoLight));
                imageViewNavVideo.setColorFilter(getContext().getResources().getColor(R.color.colorVideoDark));
                tvNavVideo.setTextColor(getContext().getResources().getColor(R.color.colorVideoDark));

                break;
            }
            case ATTACHE_TYPE_FILE: {

                recyclerViewFiles.setVisibility(View.VISIBLE);
                recyclerViewImages.setVisibility(View.GONE);
                recyclerViewMusics.setVisibility(View.GONE);
                recyclerViewVideos.setVisibility(View.GONE);

                cardViewNavFile.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorFileDark));
                imageViewNavFile.setColorFilter(getContext().getResources().getColor(R.color.colorWhite));
                tvNavFile.setTextColor(getContext().getResources().getColor(R.color.colorWhite));

                cardViewNavGallery.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorGalleryLight));
                imageViewNavGallery.setColorFilter(getContext().getResources().getColor(R.color.colorGalleryDark));
                tvNavGallery.setTextColor(getContext().getResources().getColor(R.color.colorGalleryDark));

                cardViewNavMusic.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorMusicLight));
                imageViewNavMusic.setColorFilter(getContext().getResources().getColor(R.color.colorMusicDark));
                tvNavMusic.setTextColor(getContext().getResources().getColor(R.color.colorMusicDark));

                cardViewNavVideo.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorVideoLight));
                imageViewNavVideo.setColorFilter(getContext().getResources().getColor(R.color.colorVideoDark));
                tvNavVideo.setTextColor(getContext().getResources().getColor(R.color.colorVideoDark));


                break;
            }
            case ATTACHE_TYPE_MUSIC: {

                recyclerViewMusics.setVisibility(View.VISIBLE);
                recyclerViewImages.setVisibility(View.GONE);
                recyclerViewVideos.setVisibility(View.GONE);
                recyclerViewFiles.setVisibility(View.GONE);


                cardViewNavMusic.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorMusicDark));
                imageViewNavMusic.setColorFilter(getContext().getResources().getColor(R.color.colorWhite));
                tvNavMusic.setTextColor(getContext().getResources().getColor(R.color.colorWhite));

                cardViewNavGallery.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorGalleryLight));
                imageViewNavGallery.setColorFilter(getContext().getResources().getColor(R.color.colorGalleryDark));
                tvNavGallery.setTextColor(getContext().getResources().getColor(R.color.colorGalleryDark));

                cardViewNavFile.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorFileLight));
                imageViewNavFile.setColorFilter(getContext().getResources().getColor(R.color.colorFileDark));
                tvNavFile.setTextColor(getContext().getResources().getColor(R.color.colorFileDark));

                cardViewNavVideo.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorVideoLight));
                imageViewNavVideo.setColorFilter(getContext().getResources().getColor(R.color.colorVideoDark));
                tvNavVideo.setTextColor(getContext().getResources().getColor(R.color.colorVideoDark));


                break;
            }
            case ATTACHE_TYPE_VIDEO: {

                recyclerViewVideos.setVisibility(View.VISIBLE);
                recyclerViewMusics.setVisibility(View.GONE);
                recyclerViewImages.setVisibility(View.GONE);
                recyclerViewFiles.setVisibility(View.GONE);

                cardViewNavVideo.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorVideoDark));
                imageViewNavVideo.setColorFilter(getContext().getResources().getColor(R.color.colorWhite));
                tvNavVideo.setTextColor(getContext().getResources().getColor(R.color.colorWhite));

                cardViewNavGallery.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorGalleryLight));
                imageViewNavGallery.setColorFilter(getContext().getResources().getColor(R.color.colorGalleryDark));
                tvNavGallery.setTextColor(getContext().getResources().getColor(R.color.colorGalleryDark));

                cardViewNavFile.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorFileLight));
                imageViewNavFile.setColorFilter(getContext().getResources().getColor(R.color.colorFileDark));
                tvNavFile.setTextColor(getContext().getResources().getColor(R.color.colorFileDark));

                cardViewNavMusic.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorMusicLight));
                imageViewNavMusic.setColorFilter(getContext().getResources().getColor(R.color.colorMusicDark));
                tvNavMusic.setTextColor(getContext().getResources().getColor(R.color.colorMusicDark));


                break;
            }

        }
    }

    @Override
    public void onImageSelectedSizeChanged(int selectedItemsSize) {

        tvNumberSelectedItems.setText(String.format("%s: %d", "تعداد تصاویر انتخاب شده", selectedItemsSize));
        if (selectedItemsSize == 0) {
            linearAfterSelect.setVisibility(View.GONE);
        } else {
            linearAfterSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMusicSelectedSizeChanged(int selectedItemsSize) {
        tvNumberSelectedItems.setText(String.format("%s: %d", "تعداد موزیک انتخاب شده", selectedItemsSize));
        if (selectedItemsSize == 0) {
            linearAfterSelect.setVisibility(View.GONE);
        } else {
            linearAfterSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onVideoSelectedSizeChanged(int selectedItemsSize) {
        tvNumberSelectedItems.setText(String.format("%s: %d", "تعداد ویدئو انتخاب شده", selectedItemsSize));
        if (selectedItemsSize == 0) {
            linearAfterSelect.setVisibility(View.GONE);
        } else {
            linearAfterSelect.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onFileSelectedSizeChanged(int selectedItemsSize) {
        tvNumberSelectedItems.setText(String.format("%s: %d", "تعداد فایل انتخاب شده", selectedItemsSize));
        if (selectedItemsSize == 0) {
            linearAfterSelect.setVisibility(View.GONE);
        } else {
            linearAfterSelect.setVisibility(View.VISIBLE);
        }
    }

}

