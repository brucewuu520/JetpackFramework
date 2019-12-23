package org.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.collection.SparseArrayCompat;

/**
 * 适配器辅助类
 */
public final class ViewHolder {

    private final View convertView;
    private final SparseArrayCompat<View> mViews;

    public ViewHolder(View convertView) {
        this.convertView = convertView;
        this.mViews = new SparseArrayCompat<>();
    }

    public View getConvertView() {
        return convertView;
    }

    private <V extends View> V findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        //noinspection unchecked
        return (V) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public AppCompatTextView getText(int viewId) {
        return (AppCompatTextView) findViewById(viewId);
    }

    public AppCompatImageView getImage(int viewId) {
        return (AppCompatImageView) findViewById(viewId);
    }

    public void setBackground(int resId) {
        convertView.setBackgroundResource(resId);
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