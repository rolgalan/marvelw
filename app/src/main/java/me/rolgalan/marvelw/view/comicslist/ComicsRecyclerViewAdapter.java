package me.rolgalan.marvelw.view.comicslist;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.rolgalan.marvelw.R;
import me.rolgalan.marvelw.model.Comic;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

public class ComicsRecyclerViewAdapter
        extends RecyclerView.Adapter<ComicsRecyclerViewAdapter.ComicViewHolder> {

    private final OnComicListInteractionListener parent;
    private final List<Comic> list;

    public ComicsRecyclerViewAdapter(List<Comic> items,
                                     OnComicListInteractionListener interactionListener) {

        this.list = items;
        this.parent = interactionListener;
    }

    private Comic getItem(int position) {

        if (list == null || position < 0 || position > getItemCount()) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_list_item, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ComicViewHolder holder, final int position) {

        holder.setData(getItem(position), parent);
    }

    @Override
    public int getItemCount() {

        return list != null ? list.size() : 0;
    }

    public interface OnComicListInteractionListener {

        void onComicInteraction(Comic comic, int position);
    }

    static class ComicViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        @BindView(R.id.image)
        ImageView mImageView;
        @BindView(R.id.content)
        TextView mContentView;
        private Comic mItem;

        ComicViewHolder(View view) {

            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        private static void loadImage(Context context, ImageView image, String imgUrl) {

            if (imgUrl != null && !imgUrl.isEmpty()) {
                Glide.with(context)
                        .load(imgUrl)
                        .fitCenter()
                        .placeholder(getGenderlessSuperPlaceholder())
                        .crossFade()
                        .into(image);
            }
        }

        private static @DrawableRes int getGenderlessSuperPlaceholder() {
            /*
                Creative Commons placeholder resources
                https://commons.wikimedia.org/wiki/File:Placeholder_female_superhero_c.png
                https://commons.wikimedia.org/wiki/File:Placeholder_male_superhero_c.png
             */
            return System.currentTimeMillis() % 2 == 0
                   ? R.drawable.placeholder_hero
                   : R.drawable.placeholder_heroine;
        }

        void setData(Comic item, final OnComicListInteractionListener parent) {

            this.mItem = item;
            updateViews();
            mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (parent != null) {
                        parent.onComicInteraction(mItem, getAdapterPosition());
                    }
                }
            });
        }

        private void updateViews() {

            mContentView.setText(mItem.getTitle());
            loadImage(mView.getContext(), mImageView, mItem.getImagePathThumbnail());
        }

        @Override
        public String toString() {

            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {

            super(itemView);
        }
    }
}
