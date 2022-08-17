package ru.kosteloff.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kosteloff.data.ExerciseModel

class MainViewModel : ViewModel() {
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
}