package com.islamistudio.moviedb.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.islamistudio.moviedb.MainActivity
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.ui.MovieAdapter
import com.islamistudio.moviedb.core.ui.RecyclerViewAdapterDelegate
import com.islamistudio.moviedb.databinding.FragmentSearchBinding
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
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            (activity as MainActivity).hideNavBar(true)

            binding.rvMovie.setHasFixedSize(true)
            binding.edtSearch.addTextChangedListener(object : TextWatcher {
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
                    val movieList = arrayListOf<Movie>()
                    it.map { movie ->
                        movieList.add(movie)
                    }
                    showList(movieList)
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
                val action = SearchFragmentDirections.actionSearchFragmentToDetailMovieFragment(t)
                findNavController().navigate(action)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}