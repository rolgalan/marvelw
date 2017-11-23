package me.rolgalan.marvelw.provider.cache;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Roldán Galán on 22/11/2017.
 */
public class ComicsCacheTest {

    @Test
    public void listConstructor() throws Exception {
        Cache cache = new ComicsCache();
        cache.initList();
        assertNotNull(cache.getItems());
    }
}