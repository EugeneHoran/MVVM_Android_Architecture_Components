package exercise.com.architecturecomponentspixabay.images;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import exercise.com.architecturecomponentspixabay.R;
import exercise.com.architecturecomponentspixabay.databinding.FragmentImageBinding;
import exercise.com.architecturecomponentspixabay.util.EndlessRecyclerOnScrollListener;
import exercise.com.architecturecomponentspixabay.util.ItemDecorationImageColumns;

public class ImageFragment extends Fragment {

    public static ImageFragment newInstance() {
        return new ImageFragment();
    }

    private ImageViewModel imageViewModel;
    public ImageRecyclerAdapter adapter;
    public ItemDecorationImageColumns itemDecoration;
    private FragmentImageBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
        adapter = new ImageRecyclerAdapter();
        itemDecoration = new ItemDecorationImageColumns(getResources().getDimensionPixelSize(R.dimen.space_12), 2);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerUserList.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                imageViewModel.loadImages();
            }
        });
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case ImageRecyclerAdapter.HOLDER_HIT:
                        return 1;
                    case ImageRecyclerAdapter.HOLDER_PROGRESS:
                        return 2;
                    default:
                        return -1;
                }
            }
        });
        binding.recyclerUserList.setLayoutManager(gridLayoutManager);
        binding.setFragment(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        imageViewModel.initImages();
        observeObjectList(imageViewModel);
    }

    private void observeObjectList(ImageViewModel imageViewModel) {
        imageViewModel.getObjectMutableLiveData().observe(this, new Observer<List<Object>>() {
            @Override
            public void onChanged(@Nullable List<Object> hits) {
                Parcelable recyclerViewState = binding.recyclerUserList.getLayoutManager().onSaveInstanceState();
                adapter.addObjectList(hits);
                binding.recyclerUserList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            }
        });
    }
}
