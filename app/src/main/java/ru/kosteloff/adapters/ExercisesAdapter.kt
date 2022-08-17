package ru.kosteloff.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.droidsonroids.gif.GifDrawable
import ru.kosteloff.R
import ru.kosteloff.data.ExerciseModel
import ru.kosteloff.databinding.ExercisesListItemBinding

class ExercisesAdapter() :
    ListAdapter<ExerciseModel, ExercisesAdapter.ExercisesHolder>(Comparator()) {

    class ExercisesHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ExercisesListItemBinding.bind(view)

        fun setData(exerciseModel: ExerciseModel) {

            binding.titleExercisesInExercisesListItem.text = exerciseModel.name
            binding.counterInExercisesListItem.text = exerciseModel.time
            binding.imageInExercisesListItem.setImageDrawable(
                GifDrawable(
                    binding.root.context.assets,
                    exerciseModel.image
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercises_list_item, parent, false)
        return ExercisesHolder(view)
    }

    override fun onBindViewHolder(holder: ExercisesHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class Comparator() : DiffUtil.ItemCallback<ExerciseModel>() {
        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }
    }
}