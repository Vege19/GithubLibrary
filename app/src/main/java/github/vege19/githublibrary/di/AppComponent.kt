package github.vege19.githublibrary.di

import dagger.Component
import github.vege19.githublibrary.controllers.LoginFragment
import github.vege19.githublibrary.controllers.MainActivity
import github.vege19.githublibrary.controllers.RepositoriesFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, ViewModelModule::class])
interface AppComponent {

    // Declare injections //

    fun inject(mainActivity: MainActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(repositoriesFragment: RepositoriesFragment)
}