package com.islamistudio.moviedb.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.islamistudio.moviedb.MainActivity
import com.islamistudio.moviedb.R
import com.islamistudio.moviedb.core.utils.PreferenceHelper
import com.islamistudio.moviedb.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            (activity as MainActivity).setSupportActionBar(binding.toolbar)
            (activity as MainActivity).supportActionBar?.title =
                getString(R.string.menu_bottom_setting)

            binding.swSettingTheme.isChecked = PreferenceHelper.getSetting(requireContext())
            binding.swSettingTheme.setOnCheckedChangeListener { buttonView, isChecked ->
                PreferenceHelper.saveSetting(requireContext(), isChecked)
                val delayTime = 200
                run {
                    buttonView.postDelayed(
                        {
                            if (PreferenceHelper.getSetting(requireContext())) {
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


