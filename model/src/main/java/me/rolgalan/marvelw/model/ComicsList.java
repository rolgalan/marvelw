package me.rolgalan.marvelw.model;

import java.util.List;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public interface ComicsList extends List<Comic> {

    int getTotalPossibleSize();

    void setTotalPossibleSize(int totalPossibleSize);

    int getOffset();

    void setOffset(int offset);

    boolean hasMoreItemsToLoad();
}
