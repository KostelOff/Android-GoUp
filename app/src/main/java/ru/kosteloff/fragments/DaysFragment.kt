package ru.kosteloff.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kosteloff.R
import ru.kosteloff.adapters.DayAdapter
import ru.kosteloff.data.DayModel
import ru.kosteloff.data.ExerciseModel
import ru.kosteloff.databinding.FragmentDaysBinding
import ru.kosteloff.utils.DialogManager
import ru.kosteloff.utils.FragmentManager
import ru.kosteloff.utils.MainViewModel

class DaysFragment : Fragment(), DayAdapter.Listener {
    private lateinit var dayAdapter: DayAdapter
    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels()
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.schedule)
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_reset) {
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.message_attention_total,
                object : DialogManager.Listener {
                    override fun onclick() {
                        model.pref?.edit()?.clear()?.apply()
                        dayAdapter.submitList(forEachDaysExercises())
                    }
                }
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        dayAdapter = DayAdapter(this@DaysFragment)
        binding.recyclerViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        binding.recyclerViewDays.adapter = dayAdapter
        dayAdapter.submitList(forEachDaysExercises())
    }

    private fun forEachDaysExercises(): ArrayList<DayModel> {
        val list = ArrayList<DayModel>()
        var restDaysCounter = 0
        resources.getStringArray(R.array.day_exercise).forEach {
            model.currentDay++
            val exercisesCounter = it.split(",").size
            list.add(DayModel(it, model.getExerciseCount() == exercisesCounter, 0))
        }

        binding.progressBarInFragmentDays.max = list.size
        list.forEach {
            if (it.isDone) restDaysCounter++
        }
        updateRestDaysUI(list.size - restDaysCounter, list.size)
        return list
    }

    private fun updateRestDaysUI(restDays: Int, total: Int) {
        val textConstructorForRest = getString(R.string.days_left)
        binding.restDaysInFragmentDays.text = textConstructorForRest
        binding.countRestFay.text = restDays.toString()
        binding.progressBarInFragmentDays.progress = total - restDays
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
                    exercisesArray[2],
                    false
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
        if (!dayModel.isDone) {
            fillExerciseList(dayModel)
            model.currentDay = dayModel.dayNumber
            FragmentManager.setFragment(
                ExercisesListFragment.newInstance(),
                activity as AppCompatActivity
            )
        } else {

            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.message_attention_day,
                object : DialogManager.Listener {
                    override fun onclick() {
                        model.prefSave(dayModel.dayNumber.toString(), 0)
                        dayAdapter.submitList(forEachDaysExercises())
                        FragmentManager.setFragment(
                            ExercisesListFragment.newInstance(),
                            activity as AppCompatActivity
                        )
                    }
                }
            )
        }
    }
}