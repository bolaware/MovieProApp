package com.bolaware.movieproapp


import com.bolaware.movieproapp.domain.executor.PostExecutionThread
import com.bolaware.movieproapp.domain.interactor.details.GetMovieUseCase
import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.repository.IMoviesProRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import konveyor.base.randomBuild
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.UnsupportedOperationException


class GetMovieUseCaseTest{
    lateinit var getMovieUseCase: GetMovieUseCase

    @Mock lateinit var repository: IMoviesProRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        getMovieUseCase = GetMovieUseCase(repository, postExecutionThread)
    }

    @Test
    fun `test buildUseCaseSingle() completes`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()
        val getMovieUseCaseParam = GetMovieUseCase.Params(imdb)

        `stub repo's getMovie()`(imdb, returnedMovie)
        val testObserver = getMovieUseCase.buildUseCaseSingle(getMovieUseCaseParam).test()

        testObserver.assertComplete()
    }

    @Test
    fun `test repo getMovie() is called`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()
        val getMovieUseCaseParam = GetMovieUseCase.Params(imdb)

        `stub repo's getMovie()`(imdb, returnedMovie)
        getMovieUseCase.buildUseCaseSingle(getMovieUseCaseParam)

        verify(repository).getMovie(any())
    }


    @Test(expected = UnsupportedOperationException::class)
    fun `test buildUseCaseSingle() throws error when null param is passed`(){
        getMovieUseCase.buildUseCaseSingle(null)
    }

    @Test
    fun `test buildUseCaseSingle() that correct param is passed`(){
        val paramArgumentCaptor = argumentCaptor<String>()

        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()
        val getMovieUseCaseParam = GetMovieUseCase.Params(imdb)

        `stub repo's getMovie()`(imdb, returnedMovie)
        getMovieUseCase.buildUseCaseSingle(getMovieUseCaseParam)

        verify(repository).getMovie(paramArgumentCaptor.capture())

        assertEquals(imdb, paramArgumentCaptor.firstValue)
    }

    @Test
    fun `test buildUseCaseSingle() that correct response is returned`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()
        val getMovieUseCaseParam = GetMovieUseCase.Params(imdb)

        `stub repo's getMovie()`(imdb, returnedMovie)
        val testObserver = getMovieUseCase.buildUseCaseSingle(getMovieUseCaseParam).test()


        testObserver.assertValue(returnedMovie)
    }

    fun `stub repo's getMovie()`(imdb : String, movie : Movie){
        whenever(repository.getMovie(imdb)).thenReturn(Single.just(movie))
    }
}