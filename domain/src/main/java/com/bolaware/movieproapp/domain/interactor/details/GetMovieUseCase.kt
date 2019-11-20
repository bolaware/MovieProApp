package com.bolaware.movieproapp.domain.interactor.details

import com.bolaware.movieproapp.domain.executor.PostExecutionThread
import com.bolaware.movieproapp.domain.interactor.SingleUseCase
import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.repository.IMoviesProRepository
import io.reactivex.Single
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val repository: IMoviesProRepository,
                                          postExecutionThread: PostExecutionThread
) : SingleUseCase<Movie, GetMovieUseCase.Params>(postExecutionThread) {


    override fun buildUseCaseSingle(params: Params?): Single<Movie> {
        if(params == null) throw UnsupportedOperationException("params cant be null")
        return repository.getMovie(params.imdbId)
    }

    data class Params(val imdbId :  String)

}