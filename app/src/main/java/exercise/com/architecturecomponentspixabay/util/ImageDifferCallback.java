package exercise.com.architecturecomponentspixabay.util;

import android.support.v7.util.DiffUtil;

import java.util.List;

import exercise.com.architecturecomponentspixabay.model.Hit;

/**
 * Created by Eugene on 11/21/2017.
 */

public class ImageDifferCallback extends DiffUtil.Callback {

    private final List<Object> oldList;
    private final List<Object> newList;

    public ImageDifferCallback(List<Object> oldList, List<Object> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (oldList.get(oldItemPosition) instanceof Hit && newList.get(newItemPosition) instanceof Hit) {
            Hit oldHit = (Hit) oldList.get(oldItemPosition);
            Hit newHit = (Hit) newList.get(newItemPosition);
            return oldHit.getId().equals(newHit.getId());
        } else {
            return false;
        }
    }

    /**
     * List Sizes
     */
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

}
