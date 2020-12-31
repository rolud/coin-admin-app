package com.flockware.coinadmin

import com.flockware.coinadmin.data.database.getDatabase
import com.flockware.coinadmin.data.repositories.getCategoryRepository
import com.flockware.coinadmin.data.repositories.getTransactionRepository
import com.flockware.coinadmin.ui.category.AddCategoryViewModel
import com.flockware.coinadmin.ui.main.InsertPinViewModel
import com.flockware.coinadmin.ui.main.MainViewModel
import com.flockware.coinadmin.ui.settings.SetPinViewModel
import com.flockware.coinadmin.ui.transaction.AddTransactionViewModel
import com.flockware.coinadmin.utils.SessionManager
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {

    single { getDatabase(androidApplication()) }

}

val repositoriesModule = module {
    single { getCategoryRepository(get()) }
    single { getTransactionRepository(get()) }
}

val sessionModule = module {
    single { SessionManager(androidContext()) }
}

val viewModelsModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { AddCategoryViewModel(get()) }
    viewModel { AddTransactionViewModel(get(), get()) }

    viewModel { InsertPinViewModel(get()) }
    viewModel { SetPinViewModel(get()) }
}