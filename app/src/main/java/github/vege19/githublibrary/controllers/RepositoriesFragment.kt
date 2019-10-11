package github.vege19.githublibrary.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import github.vege19.githublibrary.App
import github.vege19.githublibrary.R
import github.vege19.githublibrary.models.RepositoryModel
import github.vege19.githublibrary.utils.*
import github.vege19.githublibrary.viewmodels.RepositoriesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_repositories.*
import kotlinx.android.synthetic.main.item_repo.view.*
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
        repositoriesObserver()
        progressObserver()
        viewModel.getRepositories(getPreference().getString(Const.USERNAME_KEY, "")!!,
            getPreference().getString(Const.PASSWORD_KEY, "")!!)
    }

    private fun repositoriesObserver() {
        viewModel.getRepositoriesResponse().observe(this, Observer {
            if (it != null) {
                repositoriesRv.layoutManager = LinearLayoutManager(context)
                repositoriesRv.adapter = repositoriesAdapter(it)
            } else {
                showToast(viewModel.responseMessage)
            }
        })
    }

    private fun progressObserver() {
        viewModel.getProgressLoader().observe(this, Observer {
            repositoriesProgressBar.visibility = if (it == true) View.VISIBLE else View.GONE
        })
    }

    private fun repositoriesAdapter(list: List<RepositoryModel>): GenericAdapter<RepositoryModel> {
        return  GenericAdapter(R.layout.item_repo, list, fun (_, view, item, _) {
            view.repoNameTxt.text = item.full_name
            view.repoOwnerTxt.text = item.owner?.login
            view.ownerAvatarImg.setGlideImage(requireContext(),
                item.owner?.avatar_url!!)
            if (!item.private!!) {
                view.repoAccessibilityImg.setImageResource(R.drawable.ic_lock_open_24px)
            }
        })
    }

}
