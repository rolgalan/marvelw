package me.rolgalan.marvelw.view.comicdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.rolgalan.marvelw.R;
import me.rolgalan.marvelw.model.Comic;
import me.rolgalan.marvelw.provider.DataInterface;
import me.rolgalan.marvelw.provider.DataProvider;

/**
 * Created by Roldán Galán on 21/11/2017.
 * <p>
 * * A fragment representing a single Comic detail screen.
 * This fragment is currently displayed in the {@link ComicDetailViewPager}, which is contained
 * inside {@link ComicDetailActivity}
 * <p>
 * TODO: Load into ComicListActivity wider devices
 * TODO: Do we need enough data with the character comics response? Maybe is required to ask more info to the server
 */

public class ComicDetailFragment extends Fragment implements DataInterface<Comic> {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    private static final String ARG_ITEM_ID = "ARG_ITEM_ID";
    private static final String ARG_ITEM_POSITION = "ARG_ITEM_POSITION";

    @BindView(R.id.detail_description)
    TextView description;
    @BindView(R.id.detail_title)
    TextView title;
    @BindView(R.id.detail_characters)
    TextView characters;
    @BindView(R.id.detail_image)
    ImageView image;

    private Comic comic;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ComicDetailFragment() {

    }

    public static Bundle createBundleForId(long id) {

        return createBundle(id, false);
    }

    public static Bundle createBundleForPosition(int position) {

        return createBundle(position, true);
    }

    private static Bundle createBundle(long arg, boolean isPosition) {

        Bundle arguments = new Bundle();
        if (isPosition) {
            arguments.putInt(ARG_ITEM_POSITION, (int) arg);
        } else {
            arguments.putLong(ARG_ITEM_ID, arg);
        }

        ComicDetailFragment fragment = new ComicDetailFragment();
        fragment.setArguments(arguments);
        return arguments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.comic_detail, container, false);

        ButterKnife.bind(this, rootView);
        loadData();
        updateViews();

        return rootView;
    }

    private void loadData() {

        Bundle arguments = getArguments();
        if (arguments.containsKey(ARG_ITEM_ID)) {
            DataProvider.getInstance().getComic(getArguments().getLong(ARG_ITEM_ID), this);
        } else if (arguments.containsKey(ARG_ITEM_POSITION)) {
            DataProvider.getInstance()
                    .getComicByPosition(getArguments().getInt(ARG_ITEM_POSITION), this);
        }
    }

    public void updateViews() {

        boolean isTwoPane = false;
        title.setVisibility(isTwoPane ? View.VISIBLE : View.GONE);
        image.setVisibility(isTwoPane ? View.VISIBLE : View.GONE);
        if (comic != null) {

            if (!isTwoPane) {
                title.setText(comic.getTitle());
                description.setText(comic.getDescription());

                final String chars = comic.getCharactersString();
                characters.setVisibility(chars.isEmpty() ? View.GONE : View.VISIBLE);
                //TODO: Instead of using getCharactersString, handle it here and make characters clickable
                characters.setText("Characters in this comic: " + chars);

                //TODO show other comic relevant fields?
            }
        } else {
            //loadImageLandscape(context, image, character.getImagePathLandscape());
        }
    }

    @Override
    public void onReceived(Comic data) {

        this.comic = data;
        updateViews();
    }

    @Override
    public void onError(String error) {

        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
