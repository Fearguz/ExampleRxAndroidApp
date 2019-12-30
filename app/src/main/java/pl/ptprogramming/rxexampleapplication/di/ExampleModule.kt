package pl.ptprogramming.rxexampleapplication.di

import org.koin.dsl.module
import pl.ptprogramming.rxexampleapplication.POSTS_BASE_URL
import pl.ptprogramming.rxexampleapplication.PostsApi

val exampleModule = module {
    single { PostsApi.create(POSTS_BASE_URL) }
}