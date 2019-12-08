package org.library.glide;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewGroupTarget extends ViewBackgroundTarget<BitmapDrawable> {

    public ViewGroupTarget(@NonNull View view) {
        super(view);
    }

    @Override
    protected void setResource(@Nullable BitmapDrawable resource) {
        view.setBackground(resource);
    }
}
