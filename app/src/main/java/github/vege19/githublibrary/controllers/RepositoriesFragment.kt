package github.vege19.githublibrary.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import github.vege19.githublibrary.App
import github.vege19.githublibrary.R
import github.vege19.githublibrary.utils.StartFlow
import github.vege19.githublibrary.viewmodels.RepositoriesFragmentViewModel
import javax.inject.Inject

class RepositoriesFragment : Fragment(), StartFlow {

    //Injecting viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[RepositoriesFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        startFlow()

    }

    override fun startFlow() {
        App.getComponent().inject(this)
    }

}
