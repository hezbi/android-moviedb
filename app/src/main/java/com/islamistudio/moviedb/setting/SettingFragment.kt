package com.islamistudio.moviedb.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.islamistudio.moviedb.MainActivity
import com.islamistudio.moviedb.R
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.ui.RecyclerViewAdapterDelegate
import com.islamistudio.moviedb.databinding.FragmentFavoriteBinding
import com.islamistudio.moviedb.databinding.FragmentSettingBinding
import com.islamistudio.moviedb.favorite.FavoriteFragmentDirections

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding
        get() = _binding!!

    private var isDarkMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            (activity as MainActivity).setSupportActionBar(binding.toolbar)
            (activity as MainActivity).supportActionBar?.title =
                getString(R.string.menu_bottom_setting)

            binding.swSettingTheme.setOnCheckedChangeListener { buttonView, isChecked ->
                isDarkMode = isChecked
                val delayTime = 200
                run {
                    buttonView.postDelayed(
                        {
                            if (isDarkMode) {
                                (activity as MainActivity).delegate.localNightMode =
                                    AppCompatDelegate.MODE_NIGHT_YES
                            } else {
                                (activity as MainActivity).delegate.localNightMode =
                                    AppCompatDelegate.MODE_NIGHT_NO
                            }
                        }, delayTime.toLong()
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}