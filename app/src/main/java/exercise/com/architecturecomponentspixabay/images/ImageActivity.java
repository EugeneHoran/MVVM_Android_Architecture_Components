package exercise.com.architecturecomponentspixabay.images;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import exercise.com.architecturecomponentspixabay.R;
import exercise.com.architecturecomponentspixabay.databinding.ActivityImageBinding;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG_IMAGE_FRAGMENT = "tag_image_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image);
        setSupportActionBar(binding.toolbar);
        ImageFragment imageFragment = (ImageFragment) getSupportFragmentManager().findFragmentByTag(TAG_IMAGE_FRAGMENT);
        if (imageFragment == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, ImageFragment.newInstance(), TAG_IMAGE_FRAGMENT).commit();
        }
    }
}
