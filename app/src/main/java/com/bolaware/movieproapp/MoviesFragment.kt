package com.bolaware.movieproapp


import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bolaware.movieproapp.di.helpers.ViewModelFactory
import com.bolaware.movieproapp.presentation.MainViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController

const val EXTRA_TRANSITION_NAME = "extra trans name"

class MoviesFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var vm : MainViewModel

    lateinit var shareElementImageView : ImageView

    lateinit var rootView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(!::rootView.isInitialized){
            AndroidSupportInjection.inject(this)
            vm = ViewModelProviders.of(requireActivity(), factory)[MainViewModel::class.java]

            return inflater.inflate(R.layout.fragment_main, container, false)
        }

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!::rootView.isInitialized){
            rootView = view

            setUpView(view)

            setUpObservers(view)

            initAMovieSearch()
        }
    }


    private fun setUpView(rootView : View){
        val snapHelper = androidx.recyclerview.widget.PagerSnapHelper()
        rootView.moviesRV.onFlingListener = null
        snapHelper.attachToRecyclerView(rootView.moviesRV)

        rootView.moviesRV.layoutManager = CenterZoomLayoutManager(requireContext(), androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        rootView.moviesRV.adapter = MoviesAdapter(mutableListOf()){movie, imageView ->
            vm.getMovieDetails(movie)

            shareElementImageView = imageView

            val transitionName =  ViewCompat.getTransitionName(shareElementImageView)?: ""

            val extras = FragmentNavigatorExtras(
                imageView to transitionName
            )

            val action = MoviesFragmentDirections.actionSearchScrenToDetail(movie.title)

            findNavController().navigate(action, extras)
        }

        rootView.searchET.setOnEditorActionListener( object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || event?.action == KeyEvent.ACTION_DOWN
                    && event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    vm.searchMovie(rootView.searchET.text.toString())
                    return true
                }
                return false
            }
        })
    }

    private fun setUpObservers(rootView: View){
        vm.moviesLd.observe(this, Observer {
            it?.let {
                (rootView.moviesRV.adapter as MoviesAdapter).updateMovies(it)
                requireActivity().hideKeyboard()
            }
        })

        vm.progressIndicatorLd.observe(this, Observer {
            it?.let {
                if(it){
                    rootView.progressBar.visibility = View.VISIBLE
                    rootView.moviesRV.visibility = View.GONE
                } else {
                    rootView.progressBar.visibility = View.GONE
                    rootView.moviesRV.visibility = View.VISIBLE
                }
            }
        })

        vm.errorLd.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initAMovieSearch(){
        vm.searchMovie(INITIAL_SEARCH_TERM)
    }
}
