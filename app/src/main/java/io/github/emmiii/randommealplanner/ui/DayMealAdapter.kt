package io.github.emmiii.randommealplanner.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.emmiii.randommealplanner.databinding.ItemDayMealBinding
import io.github.emmiii.randommealplanner.model.DayMeal

class DayMealAdapter(
    private val items: MutableList<DayMeal>,
    private val onPinClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<DayMealAdapter.DayMealViewHolder>() {

    inner class DayMealViewHolder(
        val binding: ItemDayMealBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayMealViewHolder {
        val binding = ItemDayMealBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DayMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayMealViewHolder, position: Int) {
        val item = items[position]

        holder.binding.dayText.text = item.day
        holder.binding.mealText.text = item.meal ?: "No meal selected"

        // Uppdatera knapptext beroende på isPinned
        holder.binding.pinButton.text = if (item.isPinned) "📌" else "📍"

        // Klick på pin
        holder.binding.pinButton.setOnClickListener {
            onPinClicked(position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<DayMeal>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}



