package exercise.com.architecturecomponentspixabay.util;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class BindingUtil {
    /**
     * Load Image into ImageView with Glide
     *
     * @param imageView      View - ImageView
     * @param imageReference Sting - Image Url
     */
    @BindingAdapter("app:loadImage")
    public static void loadImage(final ImageView imageView, String imageReference) {
        if (TextUtils.isEmpty(imageReference)) return;
        Glide.with(imageView.getContext())
                .load(imageReference)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        onPalette(Palette.from(((BitmapDrawable) resource).getBitmap()).generate());
                        return false;
                    }

                    void onPalette(Palette palette) {
                        if (null != palette) {
                            ViewGroup parent = (ViewGroup) imageView.getParent().getParent();
                            CardView cardView = (CardView) parent;
                            int colorFrom = Color.WHITE;
                            int colorTo = palette.getDarkVibrantColor(Color.GRAY);
                            int duration = 250;
                            ObjectAnimator.ofObject(cardView, "cardBackgroundColor", new ArgbEvaluator(), colorFrom, colorTo)
                                    .setDuration(duration)
                                    .start();
                        }
                    }
                })
                .into(imageView);
    }
}
