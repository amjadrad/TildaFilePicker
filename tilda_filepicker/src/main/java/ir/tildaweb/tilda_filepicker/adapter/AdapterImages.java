package ir.tildaweb.tilda_filepicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ir.tildaweb.tilda_filepicker.R;
import ir.tildaweb.tilda_filepicker.dialog.DialogShowPicture;
import ir.tildaweb.tilda_filepicker.models.FileModel;


public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHolder> {

    private String TAG = getClass().getName();
    private List<FileModel> list;
    private Context context;
    private ImageListener imageListener;
    private int selectedItemsSize = 0;


    public interface ImageListener {
        void onImageSelectedSizeChanged(int selectedItemsSize);
    }

    public void setImageListener(ImageListener imageListener) {
        this.imageListener = imageListener;
    }

    public AdapterImages(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FileModel model = list.get(position);

        Glide.with(context)
                .load(model.getPath())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .into(holder.imageView);

        if (model.isSelected()) {
            holder.viewSelectStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_badge_enable));
            holder.imageViewSelect.setVisibility(View.VISIBLE);
        } else {
            holder.viewSelectStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_badge_disable));
            holder.imageViewSelect.setVisibility(View.GONE);
        }

        holder.coordinateSelectItem.setOnClickListener(v -> {
            if (model.getSelectionNumber() > 0) {
                model.setSelectionNumber(0);
                model.setSelected(false);
                selectedItemsSize--;
            } else {
                selectedItemsSize++;
                model.setSelected(true);
                model.setSelectionNumber(1);
            }
            imageListener.onImageSelectedSizeChanged(selectedItemsSize);
            notifyItemChanged(position);
        });

        holder.itemView.setOnClickListener(v -> {
            DialogShowPicture dialogShowPicture = new DialogShowPicture(context, model.getPath());
            dialogShowPicture.show();
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView imageView;
        private AppCompatImageView imageViewSelect;
        private View viewSelectStatus;
        private CoordinatorLayout coordinateSelectItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageViewSelect = itemView.findViewById(R.id.imageViewSelect);
            viewSelectStatus = itemView.findViewById(R.id.viewSelectStatus);
            coordinateSelectItem = itemView.findViewById(R.id.coordinateSelectItem);
        }

    }

    public void addItems(List<FileModel> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    public void clearAll() {
        this.list.clear();
        this.selectedItemsSize = 0;
        notifyDataSetChanged();
    }


    public List<FileModel> getAllSelectedItems() {
        List<FileModel> tmp = new ArrayList<>();
        for (FileModel model : list) {
            if (model.isSelected()) {
                tmp.add(model);
            }
        }
        return tmp;
    }

}
