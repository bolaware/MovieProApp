package com.bolaware.movieproapp

import com.bolaware.movieproapp.domain.executor.PostExecutionThread
import com.bolaware.movieproapp.domain.interactor.search.SearchMoviesUseCase
import com.bolaware.movieproapp.domain.model.MovieSummary
import com.bolaware.movieproapp.domain.repository.IMoviesProRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import konveyor.base.randomBuild
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.UnsupportedOperationException

class SearchMovieUseCaseTest {
    lateinit var searchMoviesUseCase : SearchMoviesUseCase

    @Mock
    lateinit var repository: IMoviesProRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        searchMoviesUseCase = SearchMoviesUseCase(repository, postExecutionThread)
    }

    @Test
    fun `test buildUseCaseSingle() completes`(){
        val searchTerm = randomBuild<String>()
        val returnedMovies = listOf<MovieSummary>(randomBuild(), randomBuild())
        val searchMoviesUseCaseParam = SearchMoviesUseCase.Params(searchTerm)
        `stub repo's searchMovies()`(searchTerm, returnedMovies)

        val testObserver = searchMoviesUseCase.buildUseCaseSingle(searchMoviesUseCaseParam).test()

        testObserver.assertComplete()
    }

    @Test
    fun `test repo getMovie() is called`(){
        val searchTerm = randomBuild<String>()
        val returnedMovies = listOf<MovieSummary>(randomBuild(), randomBuild())
        val searchMoviesUseCaseParam = SearchMoviesUseCase.Params(searchTerm)
        `stub repo's searchMovies()`(searchTerm, returnedMovies)

        searchMoviesUseCase.buildUseCaseSingle(searchMoviesUseCaseParam)

        verify(repository).searchMovies(any())
    }


    @Test(expected = UnsupportedOperationException::class)
    fun `test buildUseCaseSingle() throws error when null param is passed`(){
        searchMoviesUseCase.buildUseCaseSingle(null)
    }

    @Test
    fun `test buildUseCaseSingle() that correct param is passed`(){
        val paramArgumentCaptor = argumentCaptor<String>()

        val searchTerm = randomBuild<String>()
        val returnedMovies = listOf<MovieSummary>(randomBuild(), randomBuild())
        val searchMoviesUseCaseParam = SearchMoviesUseCase.Params(searchTerm)
        `stub repo's searchMovies()`(searchTerm, returnedMovies)

        searchMoviesUseCase.buildUseCaseSingle(searchMoviesUseCaseParam)

        verify(repository).searchMovies(paramArgumentCaptor.capture())

        Assert.assertEquals(searchTerm, paramArgumentCaptor.firstValue)
    }

    @Test
    fun `test buildUseCaseSingle() that correct response is returned`(){
        val searchTerm = randomBuild<String>()
        val returnedMovies = listOf<MovieSummary>(randomBuild(), randomBuild())
        val searchMoviesUseCaseParam = SearchMoviesUseCase.Params(searchTerm)
        `stub repo's searchMovies()`(searchTerm, returnedMovies)

        val testObserver = searchMoviesUseCase.buildUseCaseSingle(searchMoviesUseCaseParam).test()

        testObserver.assertValue(returnedMovies)
    }

    fun `stub repo's searchMovies()`(searchTerm : String, movies : List<MovieSummary>){
        whenever(repository.searchMovies(searchTerm)).thenReturn(Single.just(movies))
    }
}