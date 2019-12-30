package pl.ptprogramming.rxexampleapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.koin.core.context.startKoin
import org.koin.dsl.module
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.koin.core.context.stopKoin

val testModule = module {
    factory<PostsApi> { PostsApiMock() }
}

class ExampleUnitTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            startKoin { modules(testModule) }
        }

        @AfterClass
        @JvmStatic
        fun close() {
            stopKoin()
        }
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun givenPostsApiMockWhenRetrieveAllPostsThenVerify() {
        val viewModel = PostsViewModel(Schedulers.single())
        val observer: Observer<List<Post>> = mock()
        viewModel.listOfPosts.observeForever(observer)

        val expectedPosts = listOf(Post("post"))
        assert(viewModel.listOfPosts.value == expectedPosts)
    }
}
