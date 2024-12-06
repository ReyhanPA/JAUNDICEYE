package com.capstone.jaundiceye.ui.recommendation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.jaundiceye.R
import com.capstone.jaundiceye.data.remote.responses.HospitalsResponseItem
import com.capstone.jaundiceye.databinding.ItemRecommendationBinding
import com.capstone.jaundiceye.util.HospitalsDiffUtilCallback

class RecommendationAdapter : ListAdapter<HospitalsResponseItem, RecommendationAdapter.RecommendationViewHolder>(HospitalsDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val hospital = getItem(position)
        holder.bind(hospital)
    }

    class RecommendationViewHolder(private val binding: ItemRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hospital: HospitalsResponseItem){
            val city = hospital.city
            val province = hospital.province
            val address = binding.root.context.getString(R.string.default_address_format, city, province)
            Glide.with(binding.root.context)
                .load(hospital.imageUrl)
                .into(binding.imageHospital)
            binding.textHospitalName.text = hospital.name
            binding.textHospitalAddress.text = address
        }
    }
}