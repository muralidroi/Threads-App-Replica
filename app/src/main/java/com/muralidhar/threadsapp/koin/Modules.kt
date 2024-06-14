package com.muralidhar.threadsapp.koin

import androidx.room.Room
import com.muralidhar.threadsapp.repo.UserRepository
import com.muralidhar.threadsapp.repo.UserRepositoryImpl
import com.muralidhar.threadsapp.room.Database
import com.muralidhar.threadsapp.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


fun injectFeature() = loadFeatures

private val loadFeatures by lazy {
    loadKoinModules(listOf(viewModelModule, repositoryImplModule, userAppModule, userRepoModule))
}


val userRepoModule = module {
    factory<UserRepository> { UserRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { UserViewModel(userRepo = get()) }
}


val repositoryImplModule = module {
    factory { UserRepositoryImpl(userDao = get()) }
}

val userAppModule = module {
    // Room Database
    single {
        Room.databaseBuilder(androidApplication(), Database::class.java, "threadDb").build()
    }

    // DAO
    single { get<Database>().getUsersDao() }

    viewModelOf(::UserViewModel)
}