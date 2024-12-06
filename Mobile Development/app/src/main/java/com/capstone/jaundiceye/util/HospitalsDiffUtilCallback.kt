package com.capstone.jaundiceye.util

import androidx.recyclerview.widget.DiffUtil
import com.capstone.jaundiceye.data.remote.responses.HospitalsResponseItem

object HospitalsDiffUtilCallback : DiffUtil.ItemCallback<HospitalsResponseItem>() {
    override fun areItemsTheSame(oldItem: HospitalsResponseItem, newItem: HospitalsResponseItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HospitalsResponseItem, newItem: HospitalsResponseItem): Boolean {
        return oldItem == newItem
    }
}