package me.rolgalan.marvelw.view.comicslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.rolgalan.marvelw.R;
import me.rolgalan.marvelw.model.Comic;
import me.rolgalan.marvelw.model.ComicsList;
import me.rolgalan.marvelw.view.comicdetails.ComicDetailActivity;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

public class ComicsListActivity extends AppCompatActivity
        implements ComicsListView, ComicsRecyclerViewAdapter.OnComicListInteractionListener,
        ComicsRecyclerViewAdapter.LoadMoreListener {

    private final int columnCount = 2;

    @BindView(R.id.comic_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //Cant' use Butterknife since these elements are in the footer of the adapter's recyclerview
    private Button loadMoreButton;
    private ProgressBar spinner;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ComicListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        checkTwoPane();

        presenter = new ComicListPresenter(this);

        setupRecyclerView(recyclerView);

        presenter.loadData();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        presenter = null;
    }

    @Override
    public void setLoadMoreButon(Button button, ProgressBar spinner) {

        this.loadMoreButton = button;
        this.spinner = spinner;
    }

    @Override
    public void loadMore() {

        presenter.loadMore();
    }

    @Override
    public void onLoadingMore() {

        if (loadMoreButton != null) {
            loadMoreButton.setEnabled(false);
        }
    }

    @Override
    public void onRequestEnded() {

        hideProgress();
        if (loadMoreButton != null) {
            loadMoreButton.setEnabled(true);
        }
    }

    @Override
    public void showProgress() {

        if (spinner != null) {
            spinner.setVisibility(View.VISIBLE);
            spinner.animate();
        }
    }

    @Override
    public void hideProgress() {

        if (spinner != null) {
            spinner.setVisibility(View.GONE);
            spinner.clearAnimation();
        }
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDataList(ComicsList data) {

        if (data.getOffset() == 0 || recyclerView.getAdapter()==null) {
            recyclerView.setAdapter(new ComicsRecyclerViewAdapter(data, this, this));
        } else {

            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onComicInteraction(Comic comic, int position) {

        showMessage("Selected " + comic.getTitle());
        startActivity(ComicDetailActivity.createIntent(this, position));
    }

    public boolean isTwoPane() {

        return mTwoPane;
    }

    private void checkTwoPane() {

        if (findViewById(R.id.comic_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        if (columnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
        }

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int total = linearLayoutManager.getItemCount();
                int lastVisibleItemCount = linearLayoutManager.findLastVisibleItemPosition();
                //Log.i(TAG, "Total " + total + " - lastVisibleItemCount: " + lastVisibleItemCount);

                if (total > 0) {
                    //Load new page when 9 elements left for ending
                    //Be careful when changing number of items displayed in the grid. This number always must be 1+N*columnCount
                    if ((total - 9) <= lastVisibleItemCount) {
                        presenter.loadMore();
                    }
                }
            }
        });
    }
}
