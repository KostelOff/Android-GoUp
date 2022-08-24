package ru.kosteloff.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kosteloff.R
import ru.kosteloff.data.DayModel
import ru.kosteloff.databinding.DaysListItemBinding

class DayAdapter(var listener: Listener) :
    ListAdapter<DayModel, DayAdapter.DayHolder>(Comparator()) {

    class DayHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var binding = DaysListItemBinding.bind(view)

        fun setData(dayModel: DayModel, listener: Listener) {

            val resourceAccessToDay =
                binding.root.context.getString(R.string.day) + " ${adapterPosition + 1}"

            binding.nameInDaysListItem.text = resourceAccessToDay

            val counterExercise = binding.root.context.getString(R.string.exercises) + " " +
                    dayModel.exercises.split(",").size.toString()

            binding.counterInDaysListItem.text = counterExercise
            binding.checkBoxDaysListItem.isChecked = dayModel.isDone
            itemView.setOnClickListener { listener.onClick(dayModel.copy(dayNumber = adapterPosition + 1)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.days_list_item, parent, false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class Comparator : DiffUtil.ItemCallback<DayModel>() {
        override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun onClick(dayModel: DayModel)
    }
}