package com.islamistudio.moviedb.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.islamistudio.moviedb.MainActivity
import com.islamistudio.moviedb.R
import com.islamistudio.moviedb.core.data.Resource
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.ui.MovieAdapter
import com.islamistudio.moviedb.core.ui.RecyclerViewAdapterDelegate
import com.islamistudio.moviedb.databinding.FragmentSearchBinding
import com.islamistudio.moviedb.home.HomeFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    private val movieAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            (activity as MainActivity).hideNavBar(true)
            (activity as MainActivity).setSupportActionBar(binding.toolbar)
            (activity as MainActivity).supportActionBar?.title =
                getString(R.string.menu_main_search)

            binding.rvMovie.setHasFixedSize(true)

            binding.edPlace.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    lifecycleScope.launch {
                        viewModel.queryChannel.send(s.toString())
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })

            viewModel.searchResult.observe(viewLifecycleOwner, {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBarView.root.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBarView.root.visibility = View.GONE
                            showList(it.data!!)
                        }
                        is Resource.Error -> {
                            binding.progressBarView.root.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                        }
                    }
                }
            })

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showList(list: List<Movie>) {
        binding.rvMovie.layoutManager = LinearLayoutManager(context)
        binding.rvMovie.setHasFixedSize(true)
        movieAdapter.list = list
        binding.rvMovie.adapter = movieAdapter

        movieAdapter.notifyDataSetChanged()
        movieAdapter.delegate = object : RecyclerViewAdapterDelegate<Movie> {
            override fun onClick(t: Movie) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(t)
                findNavController().navigate(action)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        if (activity != null) {
            (activity as MainActivity).hideNavBar(false)
        }
    }

}