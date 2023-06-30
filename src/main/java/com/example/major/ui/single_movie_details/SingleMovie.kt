package com.example.major.ui.single_movie_details
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.major.R
import com.example.major.data.api.POSTER_BASE_URL
import com.example.major.data.api.TheMovieDBClient
import com.example.major.data.api.TheMovieDatabaseInterface
import com.example.major.data.repository.NetworkState
import com.example.major.data.repository.Status
import com.example.major.data.valueObject.MovieDetails
import com.example.major.databinding.ActivityMainBinding
import com.example.major.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.Locale


class SingleMovie : AppCompatActivity() {

    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var binding: ActivitySingleMovieBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService: TheMovieDatabaseInterface = TheMovieDBClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)

        val movieId: Int = intent.getIntExtra("id", 1)
        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.progressBar.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            binding.textError.visibility = if (it.status == Status.FAILED) View.VISIBLE else View.GONE
        })
    }

    @SuppressLint("SetTextI18n")
    fun bindUI(it: MovieDetails) {

        binding.movieTitle.text = it.title
        binding.movieTagline.text = it.tagline
        binding.movieReleaseDate.text = it.releaseDate
        binding.movieRating.text = it.rating.toString()
        binding.movieRuntime.text = it.runtime.toString() + "minutes"
        binding.movieOverview.text = it.overview

        val formatCurrency: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        binding.movieBudget.text = formatCurrency.format(it.budget)
        binding.movieRevenue.text = formatCurrency.format(it.revenue)

        val moviePosterUrl: String = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(binding.ivMoviePoster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieDetailsRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}


