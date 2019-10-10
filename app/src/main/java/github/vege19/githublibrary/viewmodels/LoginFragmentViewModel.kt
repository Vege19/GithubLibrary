package github.vege19.githublibrary.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.githublibrary.api.ApiInterface
import github.vege19.githublibrary.models.UserModel
import github.vege19.githublibrary.utils.encodeAuth
import github.vege19.githublibrary.utils.showToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(private val apiInterface: ApiInterface): ViewModel() {

    private val userResponse = MutableLiveData<UserModel>()
    fun getUserResponse(): LiveData<UserModel> = userResponse
    fun getUser(username: String, password: String) {
        //Retrofit call
        GlobalScope.launch {
            try {
                val response = apiInterface.getUser(encodeAuth(username, password)).execute()
                if (response.isSuccessful) {
                    userResponse.postValue(response.body())
                } else {
                    Log.d("debug", response.message())
                    userResponse.postValue(null)
                }

            } catch (t: Throwable) {
                userResponse.postValue(null)
                showToast(t.message.toString())
            }
        }
    }

}