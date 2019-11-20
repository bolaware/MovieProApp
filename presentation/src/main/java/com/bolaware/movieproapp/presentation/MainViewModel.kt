package com.bolaware.movieproapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bolaware.movieproapp.domain.interactor.details.GetMovieUseCase
import com.bolaware.movieproapp.domain.interactor.search.SearchMoviesUseCase
import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.model.MovieSummary
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val searchMovieUseCase : SearchMoviesUseCase,
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel(){

    private val disposable = CompositeDisposable()

    lateinit var getMoviesSubscriber: GetMoviesSubscriber
    lateinit var getMovieSubscriber: GetMovieSubscriber

    val moviesLd = MutableLiveData<List<MovieSummary>>()
    val movieLd = MutableLiveData<Movie>()
    val movieSummaryLd = MutableLiveData<MovieSummary>()
    val errorLd = MutableLiveData<String>()
    val progressIndicatorLd = MutableLiveData<Boolean>()

    lateinit var lastSerachTerm : String

    fun searchMovie(searchTerm : String){
        if(searchTerm.isNotEmpty()){
            lastSerachTerm = searchTerm

            getMoviesSubscriber = GetMoviesSubscriber(searchTerm)

            disposable.add(getMoviesSubscriber)

            progressIndicatorLd.value = true

            searchMovieUseCase.execute(getMoviesSubscriber, SearchMoviesUseCase.Params(searchTerm))
        }
    }


    fun getMovieDetails(movie: MovieSummary){
        movieLd.value = null
        movieSummaryLd.value = movie

        getMovieSubscriber = GetMovieSubscriber()

        disposable.add(getMovieSubscriber)

        getMovieUseCase.execute(getMovieSubscriber, GetMovieUseCase.Params(movie.imdbID))
    }


    inner class GetMovieSubscriber : DisposableSingleObserver<Movie>(){
        override fun onSuccess(t: Movie) {
            progressIndicatorLd.value = false
            movieLd.value = t
        }

        override fun onError(e: Throwable) {
            progressIndicatorLd.value = false
            errorLd.value = e.localizedMessage
        }
    }


    inner class GetMoviesSubscriber(val currentSearchTerm : String) : DisposableSingleObserver<List<MovieSummary>>(){
        override fun onSuccess(t: List<MovieSummary>) {
            if(currentSearchTerm != lastSerachTerm){
                //do nothing
            } else if(t.isNotEmpty()) {
                progressIndicatorLd.value = false
                moviesLd.value = t
            } else {
                errorLd.value = "No matching movies"
            }
        }

        override fun onError(e: Throwable) {
            progressIndicatorLd.value = false
            errorLd.value = e.localizedMessage
        }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

}