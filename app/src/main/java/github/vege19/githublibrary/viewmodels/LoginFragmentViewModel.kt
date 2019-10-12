package github.vege19.githublibrary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.githublibrary.api.ApiInterface
import github.vege19.githublibrary.models.UserModel
import github.vege19.githublibrary.utils.encodeAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(private val apiInterface: ApiInterface): ViewModel() {

    var responseMessage = ""

    private val userResponse = MutableLiveData<UserModel>()
    fun getUserResponse(): LiveData<UserModel> = userResponse
    fun getUser(base64Auth: String) {
        //Retrofit call
        GlobalScope.launch {
            try {
                val response = apiInterface.getUser(base64Auth).execute()
                if (response.isSuccessful) {
                    userResponse.postValue(response.body())
                } else {
                    userResponse.postValue(null)
                    responseMessage = response.message()
                }

            } catch (t: Throwable) {
                userResponse.postValue(null)
            }
        }
    }

}