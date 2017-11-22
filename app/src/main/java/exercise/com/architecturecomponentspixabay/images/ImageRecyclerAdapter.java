package exercise.com.architecturecomponentspixabay.images;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import exercise.com.architecturecomponentspixabay.databinding.RecyclerImageItemBinding;
import exercise.com.architecturecomponentspixabay.databinding.RecyclerProgressItemBinding;
import exercise.com.architecturecomponentspixabay.model.Hit;
import exercise.com.architecturecomponentspixabay.util.ImageDifferCallback;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int HOLDER_HIT = 0;
    static final int HOLDER_PROGRESS = 1;
    public List<Object> objectList = new ArrayList<>();

    ImageRecyclerAdapter() {
    }

    void addObjectList(List<Object> objectList) {
        final ImageDifferCallback diffCallback = new ImageDifferCallback(this.objectList, objectList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.objectList.clear();
        this.objectList.addAll(objectList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof Hit) {
            return HOLDER_HIT;
        } else if (objectList.get(position) instanceof Boolean) {
            return HOLDER_PROGRESS;
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HOLDER_HIT:
                return new ImageViewHolder(RecyclerImageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case HOLDER_PROGRESS:
                return new ProgressViewHolder(RecyclerProgressItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.bindView();
        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.bindView();
        }
        holder.itemView.setTag(this);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        private RecyclerImageItemBinding binding;

        ImageViewHolder(RecyclerImageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindView() {
            Hit hit = (Hit) objectList.get(getAdapterPosition());
            binding.setHit(hit);
        }
    }


    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        private RecyclerProgressItemBinding binding;

        ProgressViewHolder(RecyclerProgressItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindView() {
        }
    }
}
