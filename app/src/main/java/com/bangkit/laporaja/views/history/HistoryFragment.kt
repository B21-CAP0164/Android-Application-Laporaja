package com.bangkit.laporaja.views.history

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.databinding.FragmentHistoryBinding
import com.bangkit.laporaja.viewmodels.HistoryViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity
    private val viewModel by viewModel<HistoryViewModel>()
    private lateinit var viewAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        viewAdapter = HistoryAdapter()

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = viewAdapter
            setHasFixedSize(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)

        val account = GoogleSignIn.getLastSignedInAccount(activity)
        val userId = account?.id.toString()

        lifecycleScope.launch(Dispatchers.Default) {
            viewAdapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Report) {
                    showReportDetail(data)
                }
            })

            viewModel.getUserHistory(userId).collectLatest { item ->
                if (!item.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        viewAdapter.setReports(item)

                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.listEmpty.visibility = View.GONE
                            binding.rvHistory.visibility = View.VISIBLE
                            binding.shimmerReportHistory.visibility = View.GONE
                        }, 300)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.listEmpty.visibility = View.VISIBLE
                            binding.shimmerReportHistory.visibility = View.GONE
                        }, 300)
                    }
                }
            }
        }
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

    private fun showReportDetail(report: Report) {
        val toReportDetail =
            HistoryFragmentDirections.actionNavigationHistoryToReportDetailFragment(report)
        view?.findNavController()?.navigate(toReportDetail)
    }
}