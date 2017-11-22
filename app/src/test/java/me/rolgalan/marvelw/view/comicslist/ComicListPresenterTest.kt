package me.rolgalan.marvelw.view.comicslist

import com.nhaarman.mockito_kotlin.*
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import me.rolgalan.marvelw.model.implementation.MarvelComicList
import me.rolgalan.marvelw.provider.DataProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.util.reflection.Whitebox
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * Created by Roldán Galán on 22/11/2017.
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(DataProvider::class)
class ComicListPresenterTest {

    private lateinit var view: ComicsListView
    private lateinit var presenter: ComicListPresenter

    @Before
    @Throws(Exception::class)
    fun setUp() {

        view = mock()
        presenter = ComicListPresenter(view)

        //Mock DataProvider to do nothing when called
        val dataProvider: DataProvider = mock()
        doNothing().whenever(dataProvider).getCharacterComics(presenter)
        PowerMockito.mockStatic(DataProvider::class.java)
        whenever(DataProvider.getInstance()).thenReturn(dataProvider)
    }

    @Test
    @Throws(Exception::class)
    fun afterInstantiation() {
        //isLoading must be false after instantiation
        assertFalse(Whitebox.getInternalState(presenter, "isLoading") as Boolean)
    }

    @Test
    @Throws(Exception::class)
    fun loadData() {
        //DataProvider.getInstance().getCharacterComics() should test itself that works properly
        //So here we only check that the isLoading field changes when requesting data

        assertFalse(Whitebox.getInternalState(presenter, "isLoading") as Boolean)

        presenter.loadData()

        assertTrue(Whitebox.getInternalState(presenter, "isLoading") as Boolean)
    }

    @Test
    @Throws(Exception::class)
    fun onReceived() {

        Whitebox.setInternalState(presenter, "isLoading", true)
        val list = MarvelComicList(0, 0)
        presenter.onReceived(list)

        verify(view).setDataList(list)
        //After a successful response, isLoading must change to false
        assertFalse(Whitebox.getInternalState(presenter, "isLoading") as Boolean)
    }

    @Test
    @Throws(Exception::class)
    fun onError() {
        Whitebox.setInternalState(presenter, "isLoading", true)
        val error = "Error message"
        presenter.onError(error)

        verify(view).showMessage(error)
        //After an error, isLoading must change to false
        assertFalse(Whitebox.getInternalState(presenter, "isLoading") as Boolean)
    }

    @Test
    @Throws(Exception::class)
    fun loadMore_whenIsLoading_False() {
        //isLoading is false after instantiation, so nothing to set here
        presenter.loadMore()
        verify(view).onLoadingMore()
        verify(view).showProgress()
    }

    @Test
    @Throws(Exception::class)
    fun loadMore_whenIsLoading_True() {
        Whitebox.setInternalState(presenter, "isLoading", true)
        presenter.loadMore()
        verify(view, never()).onLoadingMore()
        verify(view, never()).showProgress()
    }
}