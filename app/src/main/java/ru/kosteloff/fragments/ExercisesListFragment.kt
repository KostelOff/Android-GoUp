package ru.kosteloff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kosteloff.R
import ru.kosteloff.adapters.ExercisesAdapter
import ru.kosteloff.databinding.FragmentExercisesListBinding
import ru.kosteloff.utils.FragmentManager
import ru.kosteloff.utils.MainViewModel

class ExercisesListFragment : Fragment() {
    lateinit var binding: FragmentExercisesListBinding
    private lateinit var adapter: ExercisesAdapter
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExercisesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExercisesAdapter()
        model.mutableListExercise.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initExercisesAdapter() {
        adapter = ExercisesAdapter()
        binding.recyclerViewInFragmentExercisesList.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewInFragmentExercisesList.adapter = adapter
        binding.buttonStart.setOnClickListener {
            FragmentManager.setFragment(WaitFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesListFragment()
    }
}
