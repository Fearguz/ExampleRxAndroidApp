package pl.ptprogramming.rxexampleapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Scheduler

class PostsViewModelFactory(private val scheduler: Scheduler) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = modelClass
        .getConstructor(Scheduler::class.java)
        .newInstance(scheduler)
}