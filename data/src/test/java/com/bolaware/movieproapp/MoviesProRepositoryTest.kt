package com.bolaware.movieproapp

import com.bolaware.movieproapp.data.MoviesProRepository
import com.bolaware.movieproapp.data.source.RemoteDataSource
import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.model.MovieSummary
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


class MoviesProRepositoryTest {
    lateinit var moviesProRepository: MoviesProRepository

    @Mock lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        moviesProRepository = MoviesProRepository(remoteDataSource)
    }

    @Test
    fun `test getMovie() completes`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()

        `stub remoteDataSource's fecthMovie()`(imdb, returnedMovie)
        val testObserver = moviesProRepository.getMovie(imdb).test()

        testObserver.assertComplete()
    }


    @Test
    fun `test service fecthMovie() is called`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()

        `stub remoteDataSource's fecthMovie()`(imdb, returnedMovie)
        moviesProRepository.getMovie(imdb)

        verify(remoteDataSource).getMovie(any())
    }

    @Test
    fun `test getMovie() that correct param is passed`(){
        val paramArgumentCaptor = argumentCaptor<String>()

        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()

        `stub remoteDataSource's fecthMovie()`(imdb, returnedMovie)
        moviesProRepository.getMovie(imdb)

        verify(remoteDataSource).getMovie(paramArgumentCaptor.capture())

        Assert.assertEquals(imdb, paramArgumentCaptor.firstValue)
    }

    @Test
    fun `test getMovie() that correct response is returned`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()

        `stub remoteDataSource's fecthMovie()`(imdb, returnedMovie)
        val testObserver = moviesProRepository.getMovie(imdb).test()

        verify(remoteDataSource).getMovie(any())


        testObserver.assertValue(returnedMovie)
    }


    @Test
    fun `test searchMovie() completes`(){
        val searchTerm = randomBuild<String>()
        val returnedMovies = listOf<MovieSummary>(randomBuild(), randomBuild())

        `stub remoteDataSource's searchMovies()`(searchTerm, returnedMovies)
        val testObserver = moviesProRepository.searchMovies(searchTerm).test()

        testObserver.assertComplete()
    }


    @Test
    fun `test service searchMovie() is called`(){
        val searchTerm = randomBuild<String>()
        val returnedMovies = listOf<MovieSummary>(randomBuild(), randomBuild())

        `stub remoteDataSource's searchMovies()`(searchTerm, returnedMovies)
        moviesProRepository.searchMovies(searchTerm)

        verify(remoteDataSource).searchMovies(any())
    }

    @Test
    fun `test searchMovies() that correct param is passed`(){
        val paramArgumentCaptor = argumentCaptor<String>()

        val searchTerm = randomBuild<String>()
        val returnedMovies = listOf<MovieSummary>(randomBuild(), randomBuild())

        `stub remoteDataSource's searchMovies()`(searchTerm, returnedMovies)
        moviesProRepository.searchMovies(searchTerm)

        verify(remoteDataSource).searchMovies(paramArgumentCaptor.capture())

        Assert.assertEquals(searchTerm, paramArgumentCaptor.firstValue)
    }

    @Test
    fun `test searchMovies() that correct response is returned`(){
        val searchTerm = randomBuild<String>()
        val returnedMovies = listOf<MovieSummary>(randomBuild(), randomBuild())

        `stub remoteDataSource's searchMovies()`(searchTerm, returnedMovies)
        val testObserver = moviesProRepository.searchMovies(searchTerm).test()

        verify(remoteDataSource).searchMovies(any())


        testObserver.assertValue(returnedMovies)
    }


    fun `stub remoteDataSource's fecthMovie()`(imdb : String, movie : Movie){
        whenever(remoteDataSource.getMovie(imdb)).thenReturn(Single.just(movie))
    }

    fun `stub remoteDataSource's searchMovies()`(searchTerm : String, movies : List<MovieSummary>){
        whenever(remoteDataSource.searchMovies(searchTerm)).thenReturn(Single.just(movies))
    }




}
