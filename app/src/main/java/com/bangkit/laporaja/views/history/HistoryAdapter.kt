package com.bangkit.laporaja.views.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.laporaja.R
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.databinding.ItemHistoryListBinding
import com.bangkit.laporaja.utils.ShimmerDrawableInit
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listReport = ArrayList<Report>()

    interface OnItemClickCallback {
        fun onItemClicked(data: Report)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setReports(list: List<Report>?) {
        if (list == null) return

        this.listReport.clear()
        this.listReport.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemHistoryListBinding =
            ItemHistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(itemHistoryListBinding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val report = listReport[position]

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listReport[holder.adapterPosition])
        }

        holder.bind(report)
    }

    override fun getItemCount(): Int = listReport.size

    inner class HistoryViewHolder(private val binding: ItemHistoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(report: Report) {
            binding.tvLocation.text = report.location
            binding.tvReportIdList.text = binding.tvReportIdList.context.resources.getString(R.string.id_laporan, report.id.toString())

            Glide.with(binding.imgReport.context)
                .load(report.photo)
                .apply(
                    RequestOptions().override(400, 400).placeholder(ShimmerDrawableInit.shimmerDrawable)
                        .error(R.drawable.ic_broken_image_black)
                )
                .into(binding.imgReport)
        }
    }
}