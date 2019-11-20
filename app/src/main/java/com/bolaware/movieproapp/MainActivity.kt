package com.bolaware.movieproapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.bolaware.movieproapp.di.helpers.ViewModelFactory
import com.bolaware.movieproapp.presentation.MainViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

const val INITIAL_SEARCH_TERM = "Game"
const val BING_API_KEY = "bb5e3ea0df6545368524be3024c7422b"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> { super.onOptionsItemSelected(item) }
        }
    }
}



