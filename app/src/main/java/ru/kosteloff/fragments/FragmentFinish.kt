package ru.kosteloff.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.kosteloff.R
import ru.kosteloff.databinding.FragmentFinishBinding

class FragmentFinish : Fragment() {
    private var actionBar: ActionBar? = null
    lateinit var binding: FragmentFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.GoUp)
        binding.imageFinish.setImageResource(R.drawable.otdyh)
        binding.buttonFinish.setOnClickListener {
            ru.kosteloff.utils.FragmentManager.setFragment(
                DaysFragment.newInstance(),
                activity as AppCompatActivity
            )
            binding.textView3.text = getString(R.string.the_exercises_are_completed)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentFinish()
    }
}