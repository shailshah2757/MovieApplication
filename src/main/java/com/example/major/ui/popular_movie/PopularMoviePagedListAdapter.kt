package com.example.major.ui.popular_movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.major.R
import com.example.major.data.api.POSTER_BASE_URL
import com.example.major.data.repository.NetworkState
import com.example.major.data.repository.Status
import com.example.major.data.valueObject.Movie
import com.example.major.databinding.MovieListItemBinding
import com.example.major.databinding.NetworkStateItemBinding
import com.example.major.ui.single_movie_details.SingleMovie

@Suppress("DEPRECATION")
class PopularMoviePagedListAdapter(public val context: Context) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {


    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStateViewHolder).bind(networkState)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var binding: MovieListItemBinding

        fun bind(movie: Movie?, context: Context) {

            binding = MovieListItemBinding.bind(itemView)

            binding.cvMovieTitle.text = movie?.title
            binding.cvMovieReleaseDate.text = movie?.releaseDate

            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context).load(moviePosterURL).into(binding.cvIvPoster)

            itemView.setOnClickListener {
                val intent = Intent(context, SingleMovie::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }
        }
    }

    class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var binding1: NetworkStateItemBinding


        fun bind(networkState: NetworkState?) {

            binding1 = NetworkStateItemBinding.bind(itemView)

            if (networkState != null && networkState == NetworkState.LOADING) {
                binding1.progressBar.visibility == View.VISIBLE;
            } else {
                binding1.progressBar.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                binding1.errorMessage.visibility == View.VISIBLE;
                binding1.errorMessage.text = networkState.msg;
            } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                binding1.errorMessage.visibility == View.VISIBLE;
                binding1.errorMessage.text = networkState.msg;
            } else {
                binding1.errorMessage.visibility == View.GONE;
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

}

   




