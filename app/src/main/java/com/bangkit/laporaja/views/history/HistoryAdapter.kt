package com.bangkit.laporaja.views.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.laporaja.R
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.databinding.ItemHistoryListBinding
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
        private val shimmer =
            Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1000) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

        // This is the placeholder for the imageView
        private val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        fun bind(report: Report) {
            binding.tvLocation.text = report.location
            binding.tvReportIdList.text = binding.tvReportIdList.context.resources.getString(R.string.id_laporan, report.id.toString())

            Glide.with(binding.imgReport.context)
                .load(report.photo)
                .apply(
                    RequestOptions().override(400, 400).placeholder(shimmerDrawable)
                        .error(R.drawable.ic_broken_image_black)
                )
                .into(binding.imgReport)
        }
    }
}