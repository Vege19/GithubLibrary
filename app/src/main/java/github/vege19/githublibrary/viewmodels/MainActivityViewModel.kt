package github.vege19.githublibrary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.githublibrary.api.ApiInterface
import github.vege19.githublibrary.models.DefaultModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val apiInterface: ApiInterface) :
    ViewModel() {

    private val data = MutableLiveData<DefaultModel>()
    fun getData(): LiveData<DefaultModel> {
        return data
    }

    fun generateData() {
        GlobalScope.launch {
            try {
                val response = apiInterface.getPokemons().await()
                data.postValue(response)

            } catch (t: Throwable) {
                data.postValue(null)

            }
        }
    }

}