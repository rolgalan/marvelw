package me.rolgalan.marvelw.view.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Roldán Galán on 22/11/2017.
 * <p>
 * Adapter to include a footer view at the end of a RecyclerView.
 * Based on https://github.com/lopspower/HFRecyclerView
 * <p>
 * Since we're using a GridManager, implement 2 differnet footers (one per grid)
 * <p>
 */

public abstract class FooterRecyclerViewAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SECOND_FOOTER = 2;
    private static final int TYPE_FIRST_FOOTER = 1;

    private List<T> list;

    public FooterRecyclerViewAdapter(List<T> items) {

        this.list = items;
    }

    protected abstract RecyclerView.ViewHolder getItemView(LayoutInflater inflater,
                                                           ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getSecondFooterView(LayoutInflater inflater,
                                                                   ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getFirstFooterView(LayoutInflater inflater,
                                                                  ViewGroup parent);

    protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position,
                                                 T data);

    protected abstract void onBindSecondFooterViewHolder(RecyclerView.ViewHolder holder,
                                                         int position);

    protected abstract void onBindFirstFooterViewHolder(RecyclerView.ViewHolder holder,
                                                        int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            return getItemView(inflater, parent);
        } else if (viewType == TYPE_SECOND_FOOTER) {
            return getSecondFooterView(inflater, parent);
        } else if (viewType == TYPE_FIRST_FOOTER) {
            return getFirstFooterView(inflater, parent);
        }
        throw new RuntimeException(
                "there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_SECOND_FOOTER:
                onBindSecondFooterViewHolder(holder, position);
                break;
            case TYPE_FIRST_FOOTER:
                onBindFirstFooterViewHolder(holder, position);
                break;
            case TYPE_ITEM:
            default:
                onBindItemViewHolder(holder, position, list.get(position));
        }
    }

    @Override
    public int getItemCount() {

        return list.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (isSecondPositionFooter(position)) {
            return TYPE_SECOND_FOOTER;
        } else if (isFirstPositionFooter(position)) {
            return TYPE_FIRST_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isSecondPositionFooter(int position) {

        return position == getItemCount() - 1;
    }

    private boolean isFirstPositionFooter(int position) {

        return position == getItemCount() - 2;
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {

            super(itemView);
        }
    }
}
