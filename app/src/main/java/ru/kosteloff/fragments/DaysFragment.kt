package ru.kosteloff.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kosteloff.MainActivity
import ru.kosteloff.R
import ru.kosteloff.adapters.DayAdapter
import ru.kosteloff.data.DayModel
import ru.kosteloff.data.ExerciseModel
import ru.kosteloff.databinding.FragmentDaysBinding
import ru.kosteloff.utils.FragmentManager
import ru.kosteloff.utils.MainViewModel

class DaysFragment : Fragment(), DayAdapter.Listener {

    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    fun initRecyclerView() {
        val dayAdapter = DayAdapter(this@DaysFragment)
        binding.recyclerViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        binding.recyclerViewDays.adapter = dayAdapter
        dayAdapter.submitList(forEachDaysExercises())
    }

    fun forEachDaysExercises(): ArrayList<DayModel> {
        val list = ArrayList<DayModel>()
        resources.getStringArray(R.array.day_exercise).forEach {
            list.add(DayModel(it, false))
        }
        return list
    }

    private fun fillExerciseList(dayModel: DayModel) {
        val temporaryList = ArrayList<ExerciseModel>()
        dayModel.exercises.split(",").forEach {
            val exercisesList = resources.getStringArray(R.array.exercise)
            val exercises = exercisesList[it.toInt()]
            val exercisesArray = exercises.split("|")
            temporaryList.add(
                ExerciseModel(
                    exercisesArray[0],
                    exercisesArray[1],
                    exercisesArray[2]
                )
            )
        }
        model.mutableListExercise.value = temporaryList
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(dayModel: DayModel) {
        fillExerciseList(dayModel)
        FragmentManager.setFragment(
            ExercisesListFragment.newInstance(),
            activity as AppCompatActivity
        )
    }
}