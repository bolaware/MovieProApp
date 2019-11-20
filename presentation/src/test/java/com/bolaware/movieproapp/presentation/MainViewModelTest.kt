package com.bolaware.movieproapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bolaware.movieproapp.domain.interactor.details.GetMovieUseCase
import com.bolaware.movieproapp.domain.interactor.search.SearchMoviesUseCase
import com.bolaware.movieproapp.domain.model.MovieSummary
import com.nhaarman.mockito_kotlin.*
import konveyor.base.randomBuild
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @Mock lateinit var searchMoviesUseCase: SearchMoviesUseCase
    @Mock lateinit var getMovieUseCase: GetMovieUseCase
    @Mock lateinit var getMoviesSubscriber: MainViewModel.GetMoviesSubscriber
    @Mock lateinit var getMovieSubscriber: MainViewModel.GetMovieSubscriber

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(searchMoviesUseCase, getMovieUseCase)
    }

    @Test
    fun `test searchTerm() calls searchMoviesUseCase`(){
        val searchTerm : String = "King"
        mainViewModel.searchMovie(searchTerm)
        verify(searchMoviesUseCase).execute(any(), any())
    }

    @Test
    fun `test searchTerm() doesnt call searchMoviesUseCase when search term is empty`(){
        val searchTerm = ""
        mainViewModel.searchMovie(searchTerm)
        verify(searchMoviesUseCase, never()).execute(any(), any())
    }

    @Test
    fun `test searchTerm() calls loader`(){
        val searchTerm : String = "King"
        mainViewModel.searchMovie(searchTerm)
        Assert.assertEquals(mainViewModel.progressIndicatorLd.value, true)
    }


    @Test
    fun `test searchTerm() param is passed correctly`(){
        val observerCaptor = argumentCaptor<MainViewModel.GetMoviesSubscriber>()
        val paramsCaptor = argumentCaptor<SearchMoviesUseCase.Params>()

        val searchTerm = "Lion"

        mainViewModel.searchMovie(searchTerm)

        verify(searchMoviesUseCase).execute(observerCaptor.capture(), paramsCaptor.capture())

        Assert.assertEquals(searchTerm, observerCaptor.firstValue.currentSearchTerm)
        Assert.assertEquals(searchTerm, paramsCaptor.firstValue.term)
    }

    @Test
    fun `test getMovieDetails() calls getMovieUseCase`(){
        val movieSummary = randomBuild<MovieSummary>()
        mainViewModel.getMovieDetails(movieSummary)
        verify(getMovieUseCase).execute(any(), any())
    }

    @Test
    fun `test getMovieDetails() clears previously selected viewModel`(){
        val movieSummary = randomBuild<MovieSummary>()
        mainViewModel.getMovieDetails(movieSummary)
        Assert.assertEquals(mainViewModel.movieLd.value, null)
        Assert.assertEquals(mainViewModel.movieSummaryLd.value, movieSummary)
    }


    @Test
    fun `test getMovieDetails() param is passed correctly`(){
        val paramsCaptor = argumentCaptor<GetMovieUseCase.Params>()

        val movieSummary = randomBuild<MovieSummary>()

        mainViewModel.getMovieDetails(movieSummary)

        verify(getMovieUseCase).execute(any(), paramsCaptor.capture())

        Assert.assertEquals(movieSummary.imdbID, paramsCaptor.firstValue.imdbId)
    }

}