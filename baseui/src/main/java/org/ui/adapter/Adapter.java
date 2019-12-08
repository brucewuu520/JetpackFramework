package org.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * base @ListView and @GridView' Adapter
 */
public abstract class Adapter<T> extends BaseAdapter implements DataIO<T> {

    private ArrayList<T> items;
    private LayoutInflater inflater;

    public Adapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public Adapter(Context context, List<T> items) {
        this.items = new ArrayList<>(items);
        this.inflater = LayoutInflater.from(context);
    }

    public Adapter(Context context, @NonNull T[] items) {
        this.items = new ArrayList<>(items.length);
        Collections.addAll(this.items, items);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(getItemLayoutId(), parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        bindData(holder, position, items.get(position));

        return convertView;
    }

    @Override
    public void add(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(Collection<T> collection) {
        if (null == collection)
            return;
        items.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(@NonNull T[] t) {
        Collections.addAll(items, t);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(int index, Collection<T> collection) {
        items.addAll(index, collection);
        notifyDataSetChanged();
    }

    @Override
    public void remove(T item) {
        items.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        if (items.isEmpty())
            return;
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public ArrayList<T> getAll() {
        return items;
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @LayoutRes
    abstract int getItemLayoutId();

    abstract void bindData(ViewHolder holder, int position, T t);
}
