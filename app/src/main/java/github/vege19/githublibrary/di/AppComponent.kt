package github.vege19.githublibrary.di

import dagger.Component
import github.vege19.githublibrary.controllers.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class, ViewModelModule::class])
interface AppComponent {

    // Declare injections //

    fun inject(mainActivity: MainActivity)

}