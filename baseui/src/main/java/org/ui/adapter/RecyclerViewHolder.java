package org.ui.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private final SparseArrayCompat<View> mViews;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArrayCompat<>();
    }

    private <V extends View> V findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        //noinspection unchecked
        return (V) view;
    }

    public View findView(int viewId) {
        return findViewById(viewId);
    }

    public AppCompatTextView findTextView(int viewId) {
        return (AppCompatTextView) findViewById(viewId);
    }

    public AppCompatImageView findImageView(int viewId) {
        return (AppCompatImageView) findViewById(viewId);
    }

    public AppCompatImageButton findImageButton(int viewId) {
        return (AppCompatImageButton) findViewById(viewId);
    }

    public Button findButton(int viewId) {
        return (Button) findViewById(viewId);
    }

    public void setBackground(int resId) {
        itemView.setBackgroundResource(resId);
    }

    public void setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
    }

    public void setText(int viewId, CharSequence text) {
        TextView textView = findViewById(viewId);
        textView.setText(text);
    }

    public void setText(int viewId, int resId) {
        TextView textView = findViewById(viewId);
        textView.setText(resId);
    }

    public void setClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
    }
}
