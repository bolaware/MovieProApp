package com.bolaware.movieproapp.domain.interactor.search

import com.bolaware.movieproapp.domain.model.MovieSummary
import com.bolaware.movieproapp.domain.repository.IMoviesProRepository
import com.bolaware.movieproapp.domain.executor.PostExecutionThread
import com.bolaware.movieproapp.domain.interactor.SingleUseCase
import io.reactivex.Single
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val repository: IMoviesProRepository,
                                              postExecutionThread: PostExecutionThread
) : SingleUseCase<List<MovieSummary>, SearchMoviesUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseSingle(params: Params?): Single<List<MovieSummary>> {
        if(params == null) throw UnsupportedOperationException("params cant be null")
        return repository.searchMovies(params.term)
    }

    data class Params(val term : String)
}