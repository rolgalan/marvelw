package me.rolgalan.marvelw.view.comicdetails;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import me.rolgalan.marvelw.provider.DataProvider;
import me.rolgalan.marvelw.view.utils.ZoomOutPageTransformer;

/**
 * Created by Roldán Galán on 21/11/2017.
 * <p>
 * ViewPager implementing swipe pagination
 */

public class ComicDetailViewPager extends ViewPager {

    public ComicDetailViewPager(Context context) {

        super(context);
    }

    public ComicDetailViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    void init(int startingItem, FragmentManager fragmentManager,
              final OnPageSelectedListener listener) {

        PagerAdapter pagerAdapter = new CharacterDetailsPagerAdapter(fragmentManager);
        setAdapter(pagerAdapter);
        setPageTransformer(true, new ZoomOutPageTransformer());

        if (listener != null) {
            addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                    //Not required
                }

                @Override
                public void onPageSelected(int position) {

                    listener.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    //Not required
                }
            });
        }

        setCurrentItem(startingItem);
    }

    public interface OnPageSelectedListener {

        void onPageSelected(int position);
    }

    private class CharacterDetailsPagerAdapter extends FragmentStatePagerAdapter {

        CharacterDetailsPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            ComicDetailFragment fragment = new ComicDetailFragment();
            fragment.setArguments(ComicDetailFragment.createBundleForPosition(position));
            return fragment;
        }

        @Override
        public int getCount() {

            return DataProvider.getInstance().getComicsCachedSize();
        }
    }
}
