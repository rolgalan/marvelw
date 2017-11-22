package me.rolgalan.marvelw.view.comicslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.rolgalan.marvelw.R;
import me.rolgalan.marvelw.model.Comic;
import me.rolgalan.marvelw.view.comicdetails.ComicDetailActivity;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

public class ComicsListActivity extends AppCompatActivity
        implements ComicsListView, ComicsRecyclerViewAdapter.OnComicListInteractionListener {

    private final int mColumnCount = 2;

    @BindView(R.id.comic_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

        setupRecyclerView(recyclerView);

        presenter = new ComicListPresenter(this);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        presenter = null;
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

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }

        //TODO implement pagination with scroll
    }

    public boolean isTwoPane() {

        return mTwoPane;
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDatasetChanged() {

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setDataList(List<Comic> list) {

        recyclerView.setAdapter(new ComicsRecyclerViewAdapter(list, this));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onComicInteraction(Comic comic, int position) {

        showMessage("Selected " + comic.getTitle());
        startActivity(ComicDetailActivity.createIntent(this, position));
    }
}
