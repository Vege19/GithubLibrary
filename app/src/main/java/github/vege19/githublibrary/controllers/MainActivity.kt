package github.vege19.githublibrary.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import github.vege19.githublibrary.App
import github.vege19.githublibrary.R
import github.vege19.githublibrary.utils.StartFlow
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

    override fun startFlow() {
        App.getComponent().inject(this)
        getData()
    }

    private fun getData() {
        viewModel.getData().observe(this, Observer {
            if (it != null) {
                for (i in it.results) {
                    Log.d("Data", i.name)
                }
            } else {
                Log.d("Data", "Empty")
            }
        })

        viewModel.generateData()
    }

}
