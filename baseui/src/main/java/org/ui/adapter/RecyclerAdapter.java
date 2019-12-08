package org.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.ui.listener.OnItemClickListener;
import org.ui.listener.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * base RecyclerView.Adapter
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements DataIO<T> {

    private final ArrayList<T> items;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public RecyclerAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public RecyclerAdapter(Context context, List<T> items) {
        this.items = new ArrayList<>(items);
        this.inflater = LayoutInflater.from(context);
    }

    public RecyclerAdapter(Context context, T... items) {
        this.items = new ArrayList<>(items.length);
        Collections.addAll(this.items, items);
        this.inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RecyclerViewHolder viewHolder = new RecyclerViewHolder(inflater.inflate(getItemLayoutId(viewType), parent, false));
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(viewHolder.itemView, viewHolder.getLayoutPosition());
            });
        }
        if (onItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(view -> {
                onItemLongClickListener.onItemLongClick(viewHolder.itemView, viewHolder.getLayoutPosition());
                return true;
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        bindData(holder, position, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void add(T item) {
        final int position = items.size();
        items.add(item);
        notifyItemInserted(position);
    }

    public void add(int index, T item) {
        items.add(index, item);
        notifyItemInserted(index);
    }

    @Override
    public void addAll(Collection<T> collection) {
        if (null == collection)
            return;
        final int positionStart = items.size();
        items.addAll(collection);
        notifyItemRangeInserted(positionStart, collection.size());
    }

    @Override
    public void addAll(@NonNull T[] t) {
        final int positionStart = items.size();
        Collections.addAll(items, t);
        notifyItemRangeInserted(positionStart, t.length);
    }

    @Override
    public void addAll(int index, Collection<T> collection) {
        if (null == collection)
            return;
        items.addAll(index, collection);
        notifyItemRangeInserted(index, collection.size());
    }

    @Override
    public void remove(T item) {
        final int position = items.indexOf(item);
        if (-1 == position)
            return;
        items.remove(item);
        notifyItemRemoved(position);
    }

    @Override
    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void clear() {
        if (items.isEmpty())
            return;
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public ArrayList<T> getAll() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @LayoutRes
    protected abstract int getItemLayoutId(int viewType);

    protected abstract void bindData(RecyclerViewHolder holder, int position, T t);
}
