package com.bangkit.laporaja.views.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.laporaja.MainActivity
import com.bangkit.laporaja.R
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.databinding.FragmentHomeBinding
import com.bangkit.laporaja.utils.ShimmerDrawableInit
import com.bangkit.laporaja.viewmodels.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var viewAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        viewAdapter = HomeAdapter()

        binding.rvRecentReports.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = viewAdapter
            setHasFixedSize(true)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        val personName = acct?.givenName
        val text = resources.getString(R.string.hello_string, personName)
        binding.tvHello.text = text

        binding.tvJumlah.background = ShimmerDrawableInit.shimmerDrawable
        binding.tvJumlah.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )

        setHasOptionsMenu(true)
        val toolbar = binding.topAppBar
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.supportActionBar?.title = " "

        lifecycleScope.launchWhenCreated {
            viewModel.getUserReportsCount(acct?.id as String).collectLatest {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.tvJumlah.background = null
                    binding.tvJumlah.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                    binding.tvJumlah.text = it.toString()
                }, 300)
            }

            viewAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Report) {
                    showReportDetail(data)
                }
            })

            viewModel.getRecentReports().collectLatest { item ->
                if (!item.isNullOrEmpty()) {
                    viewAdapter.setReports(item)

                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.rvRecentReports.visibility = View.VISIBLE
                        binding.shimmerReportHome.visibility = View.GONE
                    }, 300)
                }
            }
        }

        binding.cameraButton.setOnClickListener {
            showCamera()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentActivity.showBottomBar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting_menu) {
            val toSetting = HomeFragmentDirections.actionNavigationHomeToSettingsFragment()
            view?.findNavController()?.navigate(toSetting)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCamera() {
        val toCamera =
            HomeFragmentDirections.actionNavigationHomeToNavigationCamera()
        view?.findNavController()?.navigate(toCamera)
    }

    private fun showReportDetail(report: Report) {
        val toReportDetail =
            HomeFragmentDirections.actionNavigationHomeToReportDetailFragment(report)
        view?.findNavController()?.navigate(toReportDetail)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}