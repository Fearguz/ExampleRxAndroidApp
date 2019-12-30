package pl.ptprogramming.rxexampleapplication

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class PostsViewModel(private val observerScheduler: Scheduler) : ViewModel(), KoinComponent
{
    private val postsApi: PostsApi by inject()
    private lateinit var subscription: Disposable

    val listVisibility = MutableLiveData<Int>()
    val listOfPosts = MutableLiveData<List<Post>>()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        subscription = postsApi.allPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(observerScheduler)
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .subscribe( { onSuccess(it) }, { onError() } )
    }

    private fun onStart() {
        listVisibility.value = View.GONE
    }

    private fun onFinish() {
        listVisibility.value = View.VISIBLE
    }

    private fun onSuccess(posts: List<Post>) {
        listOfPosts.value = posts
    }

    private fun onError() {}

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}