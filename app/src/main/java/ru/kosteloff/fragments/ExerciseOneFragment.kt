package ru.kosteloff.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.droidsonroids.gif.GifDrawable
import ru.kosteloff.R
import ru.kosteloff.data.ExerciseModel
import ru.kosteloff.databinding.ExerciseOneBinding
import ru.kosteloff.utils.FragmentManager
import ru.kosteloff.utils.MainViewModel
import ru.kosteloff.utils.TimeUtils

class ExerciseOneFragment : Fragment() {

    lateinit var binding: ExerciseOneBinding
    private var listExercises: ArrayList<ExerciseModel>? = null
    private var counterExercises = 0
    private var currentDay = 0
    private var actionBar: ActionBar? = null
    private var timer: CountDownTimer? = null
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = model.currentDay
        counterExercises = model.getExerciseCount()
        actionBar = (activity as AppCompatActivity).supportActionBar
        model.mutableListExercise.observe(viewLifecycleOwner) {
            listExercises = it
            nextExercise()
        }
        binding.buttonNext.setOnClickListener {
            timer?.cancel()
            nextExercise()
        }
    }

    private fun nextExercise() {
        if (counterExercises < listExercises?.size!!) {
            val exercise = listExercises?.get(counterExercises++) ?: return
            showExercise(exercise)
            setExerciseType(exercise)
            showGifNextExercise()
        } else {
            counterExercises++
            timer?.cancel()
            FragmentManager.setFragment(FragmentFinish.newInstance(), activity as AppCompatActivity)
        }
    }

    private fun showExercise(exercise: ExerciseModel) {
        binding.imageInCardViewHigh.setImageDrawable(
            GifDrawable(
                binding.root.context.assets,
                exercise.image
            )
        )
        binding.titleInCardViewMiddle.text = exercise.name
        val titleActionBar = "Exercises: $counterExercises/${listExercises?.size}"
        actionBar?.title = titleActionBar
    }

    private fun setExerciseType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            binding.timerInCardViewMiddle.text = exercise.time
        } else {
            startTimer(exercise)
        }
    }

    private fun startTimer(exercise: ExerciseModel) {
        binding.progressBar.max = exercise.time.toInt() * 1000
        timer = object : CountDownTimer(exercise.time.toLong() * 1000, 1) {
            override fun onTick(p0: Long) {
                binding.timerInCardViewMiddle.text = TimeUtils.getTime(p0)
                binding.progressBar.progress = p0.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }
        }.start()
    }

    private fun showGifNextExercise() {
        if (counterExercises < listExercises?.size!!) {
            val exercise = listExercises?.get(counterExercises) ?: return
            binding.imageInCardViewBellow.setImageDrawable(
                GifDrawable(
                    binding.root.context.assets,
                    exercise.image
                )
            )
            binding.textInCardViewBellow.text = exercise.name
        } else {
            binding.textInCardViewBellow.text = getString(R.string.well_done)
            binding.imageInCardViewBellow.setImageResource(R.drawable.otdyh)
        }
    }

    override fun onDetach() {
        super.onDetach()
        model.prefSave(currentDay.toString(), counterExercises - 1)
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseOneFragment()
    }
}