package github.vege19.githublibrary.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.githublibrary.api.ApiInterface
import github.vege19.githublibrary.models.RepositoryModel
import github.vege19.githublibrary.utils.encodeAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoriesFragmentViewModel @Inject constructor(private val apiInterface: ApiInterface): ViewModel() {

    private val progressLoader = MutableLiveData<Boolean>()
    fun getProgressLoader(): LiveData<Boolean> = progressLoader

    private val repositoriesResponse = MutableLiveData<List<RepositoryModel>>()
    fun getRepositoriesResponse(): LiveData<List<RepositoryModel>> = repositoriesResponse
    fun getRepositories(username: String, password: String) {
        GlobalScope.launch {
            try {
                val response = apiInterface.getRepos(encodeAuth(username, password)).execute()
                if (response.isSuccessful) {
                    progressLoader.postValue(false)
                    repositoriesResponse.postValue(response.body())
                    progressLoader.postValue(true)
                } else {
                    repositoriesResponse.postValue(null)
                    Log.d("debug", response.message())
                    progressLoader.postValue(true)
                }
            } catch (t: Throwable) {

            }
        }
    }
}