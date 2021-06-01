package com.bangkit.laporaja.views.history

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R
import com.bangkit.laporaja.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity

        setHasOptionsMenu(true)
        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting_menu) {

        }
        return super.onOptionsItemSelected(item)
    }
}