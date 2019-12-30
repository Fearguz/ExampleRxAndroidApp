package pl.ptprogramming.rxexampleapplication

import io.reactivex.Observable

class PostsApiMock : PostsApi
{
    override fun allPosts(): Observable<List<Post>> = Observable.just(listOf(Post("post")))
}