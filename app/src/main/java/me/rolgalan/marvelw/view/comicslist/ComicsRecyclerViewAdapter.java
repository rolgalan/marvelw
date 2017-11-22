package me.rolgalan.marvelw.view.comicslist;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.rolgalan.marvelw.R;
import me.rolgalan.marvelw.model.Comic;
import me.rolgalan.marvelw.view.utils.FooterRecyclerViewAdapter;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

public class ComicsRecyclerViewAdapter extends FooterRecyclerViewAdapter<Comic> {

    private final OnComicListInteractionListener parent;
    private final LoadMoreListener listenerLoadMore;

    public ComicsRecyclerViewAdapter(List<Comic> items,
                                     OnComicListInteractionListener interactionListener) {

        this(items, interactionListener, null);

    }

    public ComicsRecyclerViewAdapter(List<Comic> items,
                                     OnComicListInteractionListener interactionListener,
                                     LoadMoreListener listenerLoadMore) {

        super(items);
        this.parent = interactionListener;
        this.listenerLoadMore = listenerLoadMore;
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_list_item, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder getSecondFooterView(LayoutInflater inflater,
                                                          ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_more, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder getFirstFooterView(LayoutInflater inflater,
                                                         ViewGroup parent) {
        //Just an empty space, since we're interested in displaying the loader on the right
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.empty_view, parent, false);
        return new EmptyViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position,
                                        Comic data) {

        ((ComicViewHolder) holder).setData(data, parent);
    }

    @Override
    protected void onBindSecondFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

        listenerLoadMore.setLoadMoreButon(((FooterViewHolder) holder).getButton(),
                ((FooterViewHolder) holder).getProgressBar());
    }

    @Override
    protected void onBindFirstFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        //not required
    }

    /**
     * The view using this adapter should implement this interface to get control of the button
     * and spinner while and at the end of each request.
     */
    public interface LoadMoreListener {

        void loadMore();

        void setLoadMoreButon(Button button, ProgressBar spinner);
    }

    /**
     * The parent have to implement this interface to control what happens when the user taps
     * in any item of the adapter.
     */
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

        private static @DrawableRes
        int getGenderlessSuperPlaceholder() {
            /*
                Instead of using a placeholder showing a man, use two different placeholders with
                a man and a woman ;)

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

    class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loadmore_button)
        Button button;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        FooterViewHolder(View view) {

            super(view);
            ButterKnife.bind(this, view);
        }

        Button getButton() {

            return button;
        }

        ProgressBar getProgressBar() {

            return progressBar;
        }

        @OnClick(R.id.loadmore_button)
        void onButtonClicked() {

            button.setEnabled(false);
            if (listenerLoadMore != null) {
                listenerLoadMore.loadMore();
            }
        }
    }
}
