package github.vege19.githublibrary.controllers

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import github.vege19.githublibrary.App
import github.vege19.githublibrary.R
import github.vege19.githublibrary.utils.Const
import github.vege19.githublibrary.utils.StartFlow
import github.vege19.githublibrary.utils.getPreference
import github.vege19.githublibrary.viewmodels.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), StartFlow {

    //Injecting viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFlow()

    }

    @SuppressLint("CommitPrefEdits")
    override fun startFlow() {
        App.getComponent().inject(this)

        //Remove preferences
        /*if (!getPreference().getString(Const.USERNAME_KEY, "").isNullOrEmpty() ||
            !getPreference().getString(Const.PASSWORD_KEY, "").isNullOrEmpty()
        ) {
            getPreference().edit().remove(Const.USERNAME_KEY).apply()
            getPreference().edit().remove(Const.PASSWORD_KEY).apply()
        } */

    }

}
