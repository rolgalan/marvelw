package me.rolgalan.marvelw.provider.cache;

import android.support.v4.util.LongSparseArray;

import me.rolgalan.marvelw.model.Identifiable;
import me.rolgalan.marvelw.model.PaginableList;

/**
 * Created by Roldán Galán on 20/11/2017.
 * <p>
 * Encapsulate common operations for a simple cache.
 */

public abstract class Cache<K extends Identifiable, L extends PaginableList<K>> {

    private final L items = initList();
    /*
    This field could be a normal Map<Long,Comic>, but a LongSparseArray is used for memory optimization.
    The Hashmap and the SparseArray have very comparable performance up to the 10K size.
    Source: https://www.javacodegeeks.com/2012/07/android-performance-tweaking-parsearray.html
    Since SparseArray uses primitive long for the key, there is no need to allocate a Long object,
    and then uses less memory.
     */
    private final LongSparseArray<K> itemMap = initMap();

    private LongSparseArray<K> initMap() {

        return new LongSparseArray<K>();
    }

    protected abstract L initList();

    public void addItem(K item) {

        items.add(item);
        itemMap.put(item.getId(), item);
    }

    public void setOtherList(L list) {

        if (list.getOffset() == 0) {
            clear();
        }

        items.setTotalPossibleSize(list.getTotalPossibleSize());
        items.setOffset(list.getOffset());

        for (K t : list) {
            addItem(t);
        }
    }

    public L getItems() {

        return items;
    }

    public K getItemById(long id) {

        return itemMap.get(id);
    }

    public K getItemByPosition(int index) {

        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public int getCurrentOffset() {

        return items.getOffset();
    }

    public int getSize(){
        return items.size();
    }

    public void clear() {

        items.clear();
        itemMap.clear();
    }
}
