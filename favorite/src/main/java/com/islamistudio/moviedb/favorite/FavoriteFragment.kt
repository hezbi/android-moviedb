package com.islamistudio.moviedb.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.islamistudio.moviedb.MainActivity
import com.islamistudio.moviedb.R
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.ui.MovieAdapter
import com.islamistudio.moviedb.core.ui.RecyclerViewAdapterDelegate
import com.islamistudio.moviedb.databinding.FragmentFavoriteBinding
import com.islamistudio.moviedb.di.favoriteModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding

    private val movieAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        if (activity != null) {

            (activity as MainActivity).hideNavBar(false)
            (activity as MainActivity).setSupportActionBar(binding?.toolbar)
            (activity as MainActivity).supportActionBar?.title = getString(R.string.menu_bottom_favorite)

            movieAdapter.delegate = object : RecyclerViewAdapterDelegate<Movie> {
                override fun onClick(t: Movie) {
                    val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailMovieFragment(t)
                    findNavController().navigate(action)
                }
            }
            loadData()
            binding?.rvMovie?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    private fun loadData() {
        viewModel.movie.observe(viewLifecycleOwner, {
            movieAdapter.list = it
            movieAdapter.notifyDataSetChanged()
            binding?.viewEmpty?.root?.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unloadKoinModules(favoriteModule)
        binding?.appBarLayout?.removeAllViewsInLayout()
        binding?.rvMovie?.adapter = null
        _binding = null
    }
}