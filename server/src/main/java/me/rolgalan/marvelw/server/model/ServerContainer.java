package me.rolgalan.marvelw.server.model;

import java.util.List;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

public class ServerContainer<T> {
    int offset;
    int limit;
    int total;
    int count;
    List<T> results;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public List<T> getResults() {
        return results;
    }
}