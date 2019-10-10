package github.vege19.githublibrary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import github.vege19.githublibrary.controllers.LoginFragment
import github.vege19.githublibrary.utils.ViewModelFactory
import github.vege19.githublibrary.utils.ViewModelKey
import github.vege19.githublibrary.viewmodels.LoginFragmentViewModel
import github.vege19.githublibrary.viewmodels.MainActivityViewModel
import github.vege19.githublibrary.viewmodels.RepositoriesFragmentViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    // Declare viewModels //

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun mainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    abstract fun loginFragmentViewModel(viewModel: LoginFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepositoriesFragmentViewModel::class)
    abstract fun repositoriesFragmentViewModel(viewModel: RepositoriesFragmentViewModel): ViewModel

}