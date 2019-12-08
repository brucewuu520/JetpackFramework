package org.library.glide;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

public abstract class ViewBackgroundTarget<Z> extends CustomViewTarget<View, Z> implements Transition.ViewAdapter {

    /**
     * Constructor that defaults {@code waitForLayout} to {@code false}.
     *
     * @param view
     */
    public ViewBackgroundTarget(@NonNull View view) {
        super(view);
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {
        setResource(null);
        setDrawable(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        setResource(null);
        setDrawable(errorDrawable);
    }

    @Override
    public void onResourceReady(@NonNull Z resource, @Nullable Transition<? super Z> transition) {
        setResource(resource);
    }

    @Nullable
    @Override
    public Drawable getCurrentDrawable() {
        return view.getBackground();
    }

    @Override
    public void setDrawable(Drawable drawable) {
        view.setBackground(drawable);
    }

    protected abstract void setResource(@Nullable Z resource);
}
