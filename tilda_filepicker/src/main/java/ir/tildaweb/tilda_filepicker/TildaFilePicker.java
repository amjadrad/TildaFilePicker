package ir.tildaweb.tilda_filepicker;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import ir.tildaweb.tilda_filepicker.adapter.AdapterFiles;
import ir.tildaweb.tilda_filepicker.adapter.AdapterImages;
import ir.tildaweb.tilda_filepicker.adapter.AdapterMusics;
import ir.tildaweb.tilda_filepicker.adapter.AdapterVideos;
import ir.tildaweb.tilda_filepicker.databinding.BottomSheetSelectFilesBinding;
import ir.tildaweb.tilda_filepicker.enums.FileType;
import ir.tildaweb.tilda_filepicker.models.FileModel;

public class TildaFilePicker extends BottomSheetDialogFragment implements TildaFilePresenter.OnDataPreparedListener, View.OnClickListener, AdapterImages.ImageListener, AdapterMusics.MusicListener, AdapterVideos.VideoListener, AdapterFiles.FileListener {

    private String TAG = this.getClass().getName();
    private BottomSheetSelectFilesBinding binding;
    private TildaFilePresenter tildaFilePresenter;
    private AdapterImages adapterImages;
    private AdapterVideos adapterVideos;
    private AdapterMusics adapterMusics;
    private AdapterFiles adapterFiles;
    private AttachType currentAttachType;
    private OnTildaFileSelectListener onTildaFileSelectListener;
    private FileType[] showJustTypes = {FileType.FILE_TYPE_ALL};
    private final Context context;
    private boolean isSingleChoice = false;

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
        this.context = context;
    }

    public TildaFilePicker(@NonNull Context context, FileType[] showJustTypes) {
        this.context = context;
        this.showJustTypes = showJustTypes;
    }

    public void setSingleChoice() {
        this.isSingleChoice = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ModalBottomSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setUp(context);
    }

    private View setUp(Context context) {
        binding = BottomSheetSelectFilesBinding.inflate(getLayoutInflater());
        tildaFilePresenter = new TildaFilePresenter(context, this);
        binding.cardViewNavGallery.setOnClickListener(this);
        binding.cardViewNavFile.setOnClickListener(this);
        binding.cardViewNavMusic.setOnClickListener(this);
        binding.cardViewNavVideo.setOnClickListener(this);
        binding.btnConfirm.setOnClickListener(this);

        binding.recyclerViewImages.setLayoutManager(new GridLayoutManager(context, 3));
        adapterImages = new AdapterImages(context, isSingleChoice);
        adapterImages.setImageListener(this);
        binding.recyclerViewImages.setAdapter(adapterImages);

        binding.recyclerViewVideos.setLayoutManager(new GridLayoutManager(context, 3));
        adapterVideos = new AdapterVideos(context);
        adapterVideos.setVideoListener(this);
        binding.recyclerViewVideos.setAdapter(adapterVideos);

        binding.recyclerViewMusics.setLayoutManager(new LinearLayoutManager(context));
        adapterMusics = new AdapterMusics(context);
        adapterMusics.setMusicListener(this);
        binding.recyclerViewMusics.setAdapter(adapterMusics);


        binding.recyclerViewFiles.setLayoutManager(new LinearLayoutManager(context));
        adapterFiles = new AdapterFiles(context);
        adapterFiles.setFileListener(this);
        binding.recyclerViewFiles.setAdapter(adapterFiles);

        binding.cardViewNavFile.setVisibility(View.GONE);
        binding.cardViewNavGallery.setVisibility(View.GONE);
        binding.cardViewNavMusic.setVisibility(View.GONE);
        binding.cardViewNavVideo.setVisibility(View.GONE);
        for (FileType type : showJustTypes) {
            if (type == FileType.FILE_TYPE_ALL) {
                binding.cardViewNavFile.setVisibility(View.VISIBLE);
                binding.cardViewNavGallery.setVisibility(View.VISIBLE);
                binding.cardViewNavMusic.setVisibility(View.VISIBLE);
                binding.cardViewNavVideo.setVisibility(View.VISIBLE);
                binding.cardViewNavGallery.callOnClick();
                break;
            }
            if (type == FileType.FILE_TYPE_IMAGE) {
                binding.cardViewNavGallery.setVisibility(View.VISIBLE);
                binding.cardViewNavGallery.callOnClick();
            } else if (type == FileType.FILE_TYPE_MUSIC) {
                binding.cardViewNavMusic.setVisibility(View.VISIBLE);
                binding.cardViewNavMusic.callOnClick();
            } else if (type == FileType.FILE_TYPE_VIDEO) {
                binding.cardViewNavVideo.setVisibility(View.VISIBLE);
                binding.cardViewNavVideo.callOnClick();
            } else if (type == FileType.FILE_TYPE_FILE) {
                binding.cardViewNavFile.setVisibility(View.VISIBLE);
                binding.cardViewNavFile.callOnClick();
            }
        }
        if (showJustTypes.length == 1 && showJustTypes[0] != FileType.FILE_TYPE_ALL) {
            binding.cardViewNavFile.setVisibility(View.GONE);
            binding.cardViewNavGallery.setVisibility(View.GONE);
            binding.cardViewNavMusic.setVisibility(View.GONE);
            binding.cardViewNavVideo.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    public void setOnTildaFileSelectListener(OnTildaFileSelectListener onTildaFileSelectListener) {
        this.onTildaFileSelectListener = onTildaFileSelectListener;
    }

    @Override
    public void onResponseImages(List<FileModel> list) {
        adapterImages.clearAll();
        adapterImages.addItems(list);
        if (list.size() == 0) {
            binding.linearNoGallery.setVisibility(View.VISIBLE);
        } else {
            binding.linearNoGallery.setVisibility(View.GONE);
        }
        binding.linearNoFile.setVisibility(View.GONE);
        binding.linearNoMusic.setVisibility(View.GONE);
        binding.linearNoVideo.setVisibility(View.GONE);
    }

    @Override
    public void onResponseFiles(List<FileModel> list) {
        adapterFiles.clearAll();
        adapterFiles.addItems(list);
        if (list.size() == 0) {
            binding.linearNoFile.setVisibility(View.VISIBLE);
            binding.linearNoGallery.setVisibility(View.GONE);
        } else {
            binding.linearNoGallery.setVisibility(View.GONE);
            binding.linearNoFile.setVisibility(View.GONE);
        }
        binding.linearNoMusic.setVisibility(View.GONE);
        binding.linearNoVideo.setVisibility(View.GONE);
    }

    @Override
    public void onResponseMusics(List<FileModel> list) {
        adapterMusics.clearAll();
        adapterMusics.addItems(list);
        if (list.size() == 0) {
            binding.linearNoMusic.setVisibility(View.VISIBLE);
            binding.linearNoGallery.setVisibility(View.GONE);
            binding.linearNoFile.setVisibility(View.GONE);
        } else {
            binding.linearNoGallery.setVisibility(View.GONE);
            binding.linearNoFile.setVisibility(View.GONE);
            binding.linearNoMusic.setVisibility(View.GONE);
        }
        binding.linearNoVideo.setVisibility(View.GONE);
    }

    @Override
    public void onResponseVideos(List<FileModel> list) {
        adapterVideos.clearAll();
        adapterVideos.addItems(list);
        if (list.size() == 0) {
            binding.linearNoVideo.setVisibility(View.VISIBLE);
            binding.linearNoGallery.setVisibility(View.GONE);
            binding.linearNoFile.setVisibility(View.GONE);
            binding.linearNoMusic.setVisibility(View.GONE);
        } else {
            binding.linearNoGallery.setVisibility(View.GONE);
            binding.linearNoFile.setVisibility(View.GONE);
            binding.linearNoMusic.setVisibility(View.GONE);
            binding.linearNoVideo.setVisibility(View.GONE);
        }
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
                    dismiss();
                    break;
                case ATTACHE_TYPE_FILE:
                    onTildaFileSelectListener.onFilePicks(adapterFiles.getAllSelectedItems());
                    dismiss();
                    break;
                case ATTACHE_TYPE_MUSIC:
                    onTildaFileSelectListener.onFilePicks(adapterMusics.getAllSelectedItems());
                    dismiss();
                    break;
                case ATTACHE_TYPE_VIDEO:
                    onTildaFileSelectListener.onFilePicks(adapterVideos.getAllSelectedItems());
                    dismiss();
                    break;
            }
        }
    }


    private void changeSelectedAttachType(AttachType attachType) {
        this.currentAttachType = attachType;
        binding.linearAfterSelect.setVisibility(View.GONE);
        switch (attachType) {
            case ATTACHE_TYPE_GALLERY: {
                binding.recyclerViewImages.setVisibility(View.VISIBLE);
                binding.recyclerViewMusics.setVisibility(View.GONE);
                binding.recyclerViewVideos.setVisibility(View.GONE);
                binding.recyclerViewFiles.setVisibility(View.GONE);
                binding.cardViewNavGallery.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorGalleryDark));
                binding.imageViewNavGallery.setColorFilter(ContextCompat.getColor(context, R.color.colorWhite));
                binding.tvNavGallery.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                binding.cardViewNavFile.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorFileLight));
                binding.imageViewNavFile.setColorFilter(ContextCompat.getColor(context, R.color.colorFileDark));
                binding.tvNavFile.setTextColor(ContextCompat.getColor(context, R.color.colorFileDark));
                binding.cardViewNavMusic.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorMusicLight));
                binding.imageViewNavMusic.setColorFilter(ContextCompat.getColor(context, R.color.colorMusicDark));
                binding.tvNavMusic.setTextColor(ContextCompat.getColor(context, R.color.colorMusicDark));
                binding.cardViewNavVideo.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorVideoLight));
                binding.imageViewNavVideo.setColorFilter(ContextCompat.getColor(context, R.color.colorVideoDark));
                binding.tvNavVideo.setTextColor(ContextCompat.getColor(context, R.color.colorVideoDark));
                break;
            }
            case ATTACHE_TYPE_FILE: {

                binding.recyclerViewFiles.setVisibility(View.VISIBLE);
                binding.recyclerViewImages.setVisibility(View.GONE);
                binding.recyclerViewMusics.setVisibility(View.GONE);
                binding.recyclerViewVideos.setVisibility(View.GONE);
                binding.cardViewNavFile.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorFileDark));
                binding.imageViewNavFile.setColorFilter(ContextCompat.getColor(context, R.color.colorWhite));
                binding.tvNavFile.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                binding.cardViewNavGallery.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorGalleryLight));
                binding.imageViewNavGallery.setColorFilter(ContextCompat.getColor(context, R.color.colorGalleryDark));
                binding.tvNavGallery.setTextColor(ContextCompat.getColor(context, R.color.colorGalleryDark));
                binding.cardViewNavMusic.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorMusicLight));
                binding.imageViewNavMusic.setColorFilter(ContextCompat.getColor(context, R.color.colorMusicDark));
                binding.tvNavMusic.setTextColor(ContextCompat.getColor(context, R.color.colorMusicDark));
                binding.cardViewNavVideo.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorVideoLight));
                binding.imageViewNavVideo.setColorFilter(ContextCompat.getColor(context, R.color.colorVideoDark));
                binding.tvNavVideo.setTextColor(ContextCompat.getColor(context, R.color.colorVideoDark));
                break;
            }
            case ATTACHE_TYPE_MUSIC: {
                binding.recyclerViewMusics.setVisibility(View.VISIBLE);
                binding.recyclerViewImages.setVisibility(View.GONE);
                binding.recyclerViewVideos.setVisibility(View.GONE);
                binding.recyclerViewFiles.setVisibility(View.GONE);
                binding.cardViewNavMusic.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorMusicDark));
                binding.imageViewNavMusic.setColorFilter(ContextCompat.getColor(context, R.color.colorWhite));
                binding.tvNavMusic.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                binding.cardViewNavGallery.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorGalleryLight));
                binding.imageViewNavGallery.setColorFilter(ContextCompat.getColor(context, R.color.colorGalleryDark));
                binding.tvNavGallery.setTextColor(ContextCompat.getColor(context, R.color.colorGalleryDark));
                binding.cardViewNavFile.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorFileLight));
                binding.imageViewNavFile.setColorFilter(ContextCompat.getColor(context, R.color.colorFileDark));
                binding.tvNavFile.setTextColor(ContextCompat.getColor(context, R.color.colorFileDark));
                binding.cardViewNavVideo.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorVideoLight));
                binding.imageViewNavVideo.setColorFilter(ContextCompat.getColor(context, R.color.colorVideoDark));
                binding.tvNavVideo.setTextColor(ContextCompat.getColor(context, R.color.colorVideoDark));
                break;
            }
            case ATTACHE_TYPE_VIDEO: {
                binding.recyclerViewVideos.setVisibility(View.VISIBLE);
                binding.recyclerViewMusics.setVisibility(View.GONE);
                binding.recyclerViewImages.setVisibility(View.GONE);
                binding.recyclerViewFiles.setVisibility(View.GONE);
                binding.cardViewNavVideo.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorVideoDark));
                binding.imageViewNavVideo.setColorFilter(ContextCompat.getColor(context, R.color.colorWhite));
                binding.tvNavVideo.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                binding.cardViewNavGallery.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorGalleryLight));
                binding.imageViewNavGallery.setColorFilter(ContextCompat.getColor(context, R.color.colorGalleryDark));
                binding.tvNavGallery.setTextColor(ContextCompat.getColor(context, R.color.colorGalleryDark));
                binding.cardViewNavFile.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorFileLight));
                binding.imageViewNavFile.setColorFilter(ContextCompat.getColor(context, R.color.colorFileDark));
                binding.tvNavFile.setTextColor(ContextCompat.getColor(context, R.color.colorFileDark));
                binding.cardViewNavMusic.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorMusicLight));
                binding.imageViewNavMusic.setColorFilter(ContextCompat.getColor(context, R.color.colorMusicDark));
                binding.tvNavMusic.setTextColor(ContextCompat.getColor(context, R.color.colorMusicDark));
                break;
            }

        }
    }

    @Override
    public void onImageSelectedSizeChanged(int selectedItemsSize) {
//        if (singleChoice) {
//            binding.btnConfirm.callOnClick();
//        } else {
        binding.tvNumberSelectedItems.setText(String.format("%s: %s", "تعداد تصاویر انتخاب شده", selectedItemsSize));
        if (selectedItemsSize == 0) {
            binding.linearAfterSelect.setVisibility(View.GONE);
        } else {
            binding.linearAfterSelect.setVisibility(View.VISIBLE);
        }
//        }
    }

    @Override
    public void onMusicSelectedSizeChanged(int selectedItemsSize) {
        binding.tvNumberSelectedItems.setText(String.format("%s: %s", "تعداد موزیک انتخاب شده", selectedItemsSize));
        if (selectedItemsSize == 0) {
            binding.linearAfterSelect.setVisibility(View.GONE);
        } else {
            binding.linearAfterSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onVideoSelectedSizeChanged(int selectedItemsSize) {
        binding.tvNumberSelectedItems.setText(String.format("%s: %s", "تعداد ویدئو انتخاب شده", selectedItemsSize));
        if (selectedItemsSize == 0) {
            binding.linearAfterSelect.setVisibility(View.GONE);
        } else {
            binding.linearAfterSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFileSelectedSizeChanged(int selectedItemsSize) {
        binding.tvNumberSelectedItems.setText(String.format("%s: %s", "تعداد فایل انتخاب شده", selectedItemsSize));
        if (selectedItemsSize == 0) {
            binding.linearAfterSelect.setVisibility(View.GONE);
        } else {
            binding.linearAfterSelect.setVisibility(View.VISIBLE);
        }
    }


    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, null);
    }
}