package com.staskokos.weatherdev.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.staskokos.weatherdev.data.models.Hour
import com.staskokos.weatherdev.databinding.LayoutHourBinding

class WeatherAdapter : ListAdapter<Hour, WeatherAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(private val binding: LayoutHourBinding,) : RecyclerView.ViewHolder(binding.root){
        fun bind(hour: Hour) {
            val text =
                "${hour.time.substring(11, 13)}:00 --- ${hour.temp_c} C, чувствуется как ${hour.feelslike_c} C\n" +
                "Условия: ${hour.condition.text}\n" +
                "Скорость ветра: ${hour.wind_kph} км/ч\n" +
                "Шанс того, что будет дождь: ${hour.chance_of_rain}%\n" +
                "Облачность: ${hour.cloud}%"

            binding.tvTime.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutHourBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(hour = getItem(position))
    }
}