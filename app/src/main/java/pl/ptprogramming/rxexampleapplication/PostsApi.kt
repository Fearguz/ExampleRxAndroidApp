package pl.ptprogramming.rxexampleapplication

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val POSTS_BASE_URL = "https://jsonplaceholder.typicode.com"

interface PostsApi
{
    @GET("/posts")
    fun allPosts(): Observable<List<Post>>

    companion object Factory {
        fun create(baseUrl: String): PostsApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(PostsApi::class.java)
    }
}