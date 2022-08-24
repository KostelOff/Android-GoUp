package ru.kosteloff.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ru.kosteloff.R
import ru.kosteloff.databinding.FragmentWaitBinding
import ru.kosteloff.utils.FragmentManager
import ru.kosteloff.utils.TimeUtils

const val COUNT_DOWN_TIME = 6_000L

class WaitFragment : Fragment() {

    lateinit var timer: CountDownTimer
    lateinit var binding: FragmentWaitBinding
    private var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.max = COUNT_DOWN_TIME.toInt()
        startTimer()
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.get_ready)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(COUNT_DOWN_TIME, 1) {
            override fun onTick(p0: Long) {
                binding.timer.text = TimeUtils.getTime(p0)
                binding.progressBar.progress = p0.toInt()
            }

            override fun onFinish() {
                FragmentManager.setFragment(
                    ExerciseOneFragment.newInstance(),
                    activity as AppCompatActivity
                )
            }
        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WaitFragment()
    }
}