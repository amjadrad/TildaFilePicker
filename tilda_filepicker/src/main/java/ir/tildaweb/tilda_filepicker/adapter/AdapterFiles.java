package ir.tildaweb.tilda_filepicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.tildaweb.tilda_filepicker.R;
import ir.tildaweb.tilda_filepicker.models.FileModel;


public class AdapterFiles extends RecyclerView.Adapter<AdapterFiles.ViewHolder> {

    private String TAG = getClass().getName();
    private List<FileModel> list;
    private Context context;
    private FileListener fileListener;
    private int selectedItemsSize = 0;

    public interface FileListener {
        void onFileSelectedSizeChanged(int selectedItemsSize);
    }

    public void setFileListener(FileListener fileListener) {
        this.fileListener = fileListener;
    }

    public AdapterFiles(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_file, parent, false);
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

        switch (model.getFileMimeType()) {
            case FILE_MIME_TYPE_NONE:
                holder.imageViewMimeType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_folder));
                break;
            case FILE_MIME_TYPE_PDF:
                holder.imageViewMimeType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pdf));
                break;
            case FILE_MIME_TYPE_DOCX:
                holder.imageViewMimeType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_word));
                break;
            case FILE_MIME_TYPE_RAR:
                holder.imageViewMimeType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_rar));
                break;
            case FILE_MIME_TYPE_ZIP:
                holder.imageViewMimeType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_zip));
                break;
            case FILE_MIME_TYPE_HTML:
                holder.imageViewMimeType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_html));
                break;
            case FILE_MIME_TYPE_TEXT:
                holder.imageViewMimeType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_txt));
                break;
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
            fileListener.onFileSelectedSizeChanged(selectedItemsSize);
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
        private AppCompatImageView imageViewMimeType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            coordinateSelectItem = itemView.findViewById(R.id.coordinateSelectItem);
            imageViewMimeType = itemView.findViewById(R.id.imageViewMimeType);
        }

    }

    public void addItems(List<FileModel> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    public void addItem(FileModel item) {
        this.list.add(0, item);
        notifyItemInserted(0);
    }


    public void updateItem(int id, FileModel item) {
        int i = 0;
        for (FileModel model : list) {
//            if (model.getId() == id) {
//                model.setTitle(item.getTitle());
//                notifyItemChanged(i);
//                break;
//            }
            i++;
        }
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
