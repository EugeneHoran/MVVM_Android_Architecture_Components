package exercise.com.architecturecomponentspixabay.images;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import exercise.com.architecturecomponentspixabay.data.PixabayApi;
import exercise.com.architecturecomponentspixabay.model.Hit;
import exercise.com.architecturecomponentspixabay.model.Pixabay;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ImageViewModel extends ViewModel {
    private static String TAG = ImageViewModel.class.getSimpleName();

    private CompositeDisposable disposables = new CompositeDisposable();
    private List<Object> objectList = new ArrayList<>();
    private MutableLiveData<List<Object>> objectMutableLiveData;

    private boolean isDataInitiated = false;
    private Integer page;

    public ImageViewModel() {
    }

    MutableLiveData<List<Object>> getObjectMutableLiveData() {
        if (objectMutableLiveData == null) {
            objectMutableLiveData = new MutableLiveData<>();
        }
        return objectMutableLiveData;
    }

    void initImages() {
        if (!isDataInitiated) {
            page = 0;
            objectList.add(Boolean.TRUE);
            getObjectMutableLiveData().setValue(objectList);
            loadImages();
        }
    }

    void loadImages() {
        if (page == null) {
            return;
        }
        isDataInitiated = true;
        disposables.add(PixabayApi.provideImageRestServices()
                .getImages(page + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Pixabay>() {
                    @Override
                    public void onSuccess(Pixabay pixabay) {
                        handleList(pixabay.getHits());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }
                }));
    }

    private void handleList(List<Hit> hits) {
        if (hits.size() != 0) {
            ++page;
        } else {
            page = null;
        }
        if (objectList.get(objectList.size() - 1) instanceof Boolean) {
            objectList.remove(objectList.size() - 1);
        }
        objectList.addAll(hits);
        if (page != null) {
            objectList.add(Boolean.TRUE);
        }
        getObjectMutableLiveData().setValue(objectList);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
