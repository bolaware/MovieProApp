package com.bolaware.movieproapp


import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.bolaware.movieproapp.di.helpers.ViewModelFactory
import com.bolaware.movieproapp.presentation.MainViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_movies_details.view.*
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class MoviesDetailsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var vm : MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postponeEnterTransition()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.slide_right)
        }

        AndroidSupportInjection.inject(this)
        vm = ViewModelProviders.of(requireActivity(), factory)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers(view)

        (requireActivity() as MainActivity).setSupportActionBar(view.toolbar1)
        (requireActivity() as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val name = arguments?.getString(EXTRA_TRANSITION_NAME)
            view.detailsIv.transitionName = name
        }
    }


    private fun setUpObservers(rootView : View){
        vm.movieSummaryLd.observe(this, Observer {
            loadBasicDetails(it.title, it.posterUrl)
        })

        vm.movieLd.observe(this, Observer {
            it?.let {
                rootView.progressBar.visibility = View.GONE
                rootView.ratingBar.rating = it.getRating()
                rootView.ratingTV.text = it.imdbRating.toString()
                rootView.runtimeTV.text = it.runtime
                rootView.genreTV.text = it.genre.substringBefore(",")
                rootView.moviesPlotTV.text = it.plot
                rootView.personnelRV.layoutManager = LinearLayoutManager(requireContext(), androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
                rootView.personnelRV.adapter = MovieActorsAdapter(it.actors.split(",").subList(0, 3))
            }
        })
    }

    private fun loadBasicDetails(title : String, posterUrl : String){
        view?.let {rootView ->
            Picasso.get()
                .load(posterUrl)
                .noFade()
                .placeholder(getRandomDrawbleColor())
                .into(rootView.detailsIv, object : Callback{
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                }
            })
            rootView.titleTV.text = title
        }
    }
}
