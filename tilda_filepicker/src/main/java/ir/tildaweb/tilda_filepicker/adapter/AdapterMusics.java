package ir.tildaweb.tilda_filepicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import ir.tildaweb.tilda_filepicker.R;
import ir.tildaweb.tilda_filepicker.models.FileModel;


public class AdapterMusics extends RecyclerView.Adapter<AdapterMusics.ViewHolder> {

    private String TAG = getClass().getName();
    private List<FileModel> list;
    private Context context;
    private MusicListener musicListener;
    private int selectedItemsSize = 0;

    public interface MusicListener {
        void onMusicSelectedSizeChanged(int selectedItemsSize);
    }

    public void setMusicListener(MusicListener musicListener) {
        this.musicListener = musicListener;
    }

    public AdapterMusics(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FileModel model = list.get(position);

        holder.tvTitle.setText(String.format("%s", model.getTitle()));


        if (model.isSelected()) {
            holder.coordinateSelectItem.setVisibility(View.VISIBLE);
        } else {
            holder.coordinateSelectItem.setVisibility(View.GONE);
        }
//
        holder.itemView.setOnClickListener(v -> {
            if (model.isSelected()) {
                model.setSelected(false);
                selectedItemsSize--;
            } else {
                selectedItemsSize++;
                model.setSelected(true);
            }
            musicListener.onMusicSelectedSizeChanged(selectedItemsSize);
            notifyItemChanged(position);

        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private CoordinatorLayout coordinateSelectItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
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
