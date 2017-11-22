package me.rolgalan.marvelw.view.comicdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.rolgalan.marvelw.R;
import me.rolgalan.marvelw.model.Comic;
import me.rolgalan.marvelw.provider.DataInterface;
import me.rolgalan.marvelw.provider.DataProvider;
import me.rolgalan.marvelw.view.utils.ImageUtils;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

public class ComicDetailActivity extends AppCompatActivity
        implements ComicDetailViewPager.OnPageSelectedListener {

    private static final String ARG_ITEM_POSITION = "ARG_ITEM_POSITION";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header_image)
    ImageView image;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout appBarLayout;
    @BindView(R.id.detail_viewpager)
    ComicDetailViewPager viewPager;

    public static Intent createIntent(Context context, int position) {

        Intent intent = new Intent(context, ComicDetailActivity.class);
        intent.putExtra(ARG_ITEM_POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        ButterKnife.bind(this);

        setupActionBar();

        int position = getIntent().getIntExtra(ARG_ITEM_POSITION, -1);

        //Starts our custom viewpager with the selected item to be loaded
        setViewPager(position);
        loadComicOnToolbar(position);
    }

    @Override
    public void onPageSelected(int position) {

        loadComicOnToolbar(position);
    }

    private void loadComicOnToolbar(int pos) {

        DataProvider.getInstance().getComicByPosition(pos, new DataInterface<Comic>() {

            @Override
            public void onReceived(Comic data) {

                setCurrentMarvelComic(data);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void setCurrentMarvelComic(Comic comic) {

        ImageUtils.loadImageLandscape(this, image, comic.getImagePathThumbnail());
        appBarLayout.setTitle(comic.getTitle());
    }

    private void setViewPager(int startingItem) {

        viewPager.init(startingItem, getSupportFragmentManager(), this);
    }

    private void setupActionBar() {

        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
