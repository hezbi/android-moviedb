package com.islamistudio.moviedb.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.islamistudio.moviedb.MainActivity
import com.islamistudio.moviedb.R
import com.islamistudio.moviedb.core.data.Resource
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.ui.MovieGridAdapter
import com.islamistudio.moviedb.core.ui.RecyclerViewAdapterDelegate
import com.islamistudio.moviedb.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            (activity as MainActivity).hideNavBar(false)
            (activity as MainActivity).setSupportActionBar(binding.toolbar)
            (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)

            loadData()
            binding.rvMovie.setHasFixedSize(true)
            binding.swpRefresh.setOnRefreshListener {
                loadData()
            }
        }

    }

    private fun loadData() {
        viewModel.movie().observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is com.islamistudio.moviedb.core.data.Resource.Loading -> {
                        binding.progressBarView.root.visibility = View.VISIBLE
                        binding.swpRefresh.isRefreshing = false
                    }
                    is com.islamistudio.moviedb.core.data.Resource.Success -> {
                        binding.progressBarView.root.visibility = View.GONE
                        showGridView(it.data!!)
                    }
                    is com.islamistudio.moviedb.core.data.Resource.Error -> {
                        binding.progressBarView.root.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showGridView(list: List<Movie>) {
        binding.rvMovie.layoutManager = GridLayoutManager(requireContext(), 2)
        val movieGridAdapter = MovieGridAdapter()
        movieGridAdapter.list = list
        binding.rvMovie.adapter = movieGridAdapter

        movieGridAdapter.notifyDataSetChanged()
        movieGridAdapter.delegate = object : RecyclerViewAdapterDelegate<Movie> {
            override fun onClick(t: Movie) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(t)
                findNavController().navigate(action)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_search -> {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}