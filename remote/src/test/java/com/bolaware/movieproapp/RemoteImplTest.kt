package com.bolaware.movieproapp

import com.bolaware.movieproapp.data.model.MoviesResponse
import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.model.MovieSummary
import com.bolaware.movieproapp.remote.MovieProService
import com.bolaware.movieproapp.remote.RemoteImpl
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


class RemoteImplTest{
    lateinit var remoteImpl: RemoteImpl

    @Mock lateinit var movieProService: MovieProService

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        remoteImpl = RemoteImpl(movieProService)
    }

    @Test
    fun `test getMovie() completes`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()

        `stub service's fecthMovie()`(imdb, returnedMovie)
        val testObserver = remoteImpl.getMovie(imdb).test()

        testObserver.assertComplete()
    }

    @Test
    fun `test service fecthMovie() is called`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()

        `stub service's fecthMovie()`(imdb, returnedMovie)
        remoteImpl.getMovie(imdb)

        verify(movieProService).fecthMovie(any(), any())
    }


    @Test
    fun `test getMovie() that correct param is passed`(){
        val paramArgumentCaptor = argumentCaptor<String>()

        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()

        `stub service's fecthMovie()`(imdb, returnedMovie)
        remoteImpl.getMovie(imdb)

        verify(movieProService).fecthMovie(paramArgumentCaptor.capture(), paramArgumentCaptor.capture())

        assertEquals(imdb, paramArgumentCaptor.firstValue)
    }

    @Test
    fun `test getMovie() that correct response is returned`(){
        val imdb = randomBuild<String>()
        val returnedMovie = randomBuild<Movie>()

        `stub service's fecthMovie()`(imdb, returnedMovie)
        val testObserver = remoteImpl.getMovie(imdb).test()

        verify(movieProService).fecthMovie(any(), any())


        testObserver.assertValue(returnedMovie)
    }


    @Test
    fun `test searchMovies() completes`(){
        val searchTerm = randomBuild<String>()
        val returnedMovieResponse = generateMovieResponse(true)

        `stub service's searchMovies()`(searchTerm, returnedMovieResponse)
        val testObserver = remoteImpl.searchMovies(searchTerm).test()

        testObserver.assertComplete()
    }

    @Test
    fun `test service searchMovies() completes`(){
        val searchTerm = randomBuild<String>()
        val returnedMovieResponse = generateMovieResponse(true)

        `stub service's searchMovies()`(searchTerm, returnedMovieResponse)
        remoteImpl.searchMovies(searchTerm)

        verify(movieProService).searchMovies(any(), any(), any())
    }




    @Test
    fun `test searchMovies() that correct param is passed`(){
        val paramArgumentCaptor = argumentCaptor<String>()

        val searchTerm = randomBuild<String>()
        val returnedMovieResponse = generateMovieResponse(true)

        `stub service's searchMovies()`(searchTerm, returnedMovieResponse)
        remoteImpl.searchMovies(searchTerm)

        verify(movieProService).searchMovies(paramArgumentCaptor.capture(), any(), any())

        assertEquals(searchTerm, paramArgumentCaptor.firstValue)
    }


    @Test
    fun `test searchMovies() that correct response is returned when response is successful`(){
        val searchTerm = randomBuild<String>()
        val returnedMovieResponse = generateMovieResponse(true)

        `stub service's searchMovies()`(searchTerm, returnedMovieResponse)
        val testObserver = remoteImpl.searchMovies(searchTerm).test()

        verify(movieProService).searchMovies(any(), any(), any())


        testObserver.assertValue(returnedMovieResponse.movies)
    }


    @Test
    fun `test searchMovies() that correct response is returned when response is failed`(){
        val searchTerm = randomBuild<String>()
        val returnedMovieResponse = generateMovieResponse(false)

        `stub service's searchMovies()`(searchTerm, returnedMovieResponse)
        val testObserver = remoteImpl.searchMovies(searchTerm).test()

        verify(movieProService).searchMovies(any(), any(), any())


        testObserver.assertValue(emptyList())
    }

    fun generateMovieResponse(successful: Boolean) : MoviesResponse{
        var response = successful
        var totalResult = randomBuild<String>()
        var movies = listOf(randomBuild<MovieSummary>(), randomBuild())

        return MoviesResponse(movies, totalResult, response)
    }


    fun `stub service's fecthMovie()`(imdb : String, movie : Movie){
        whenever(movieProService.fecthMovie(imdb)).thenReturn(Single.just(movie))
    }


    fun `stub service's searchMovies()`(searchTerm : String, movieResponse: MoviesResponse){
        whenever(movieProService.searchMovies(searchTerm)).thenReturn(Single.just(movieResponse))
    }
}