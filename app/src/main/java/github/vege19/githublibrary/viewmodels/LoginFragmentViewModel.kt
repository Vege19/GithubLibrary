package github.vege19.githublibrary.viewmodels

import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.vege19.githublibrary.api.ApiInterface
import github.vege19.githublibrary.models.UserModel
import github.vege19.githublibrary.utils.showToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(private val apiInterface: ApiInterface): ViewModel() {

    private val userResponse = MutableLiveData<UserModel>()
    fun getUserResponse() = userResponse
    fun getUser(username: String, password: String) {
        //Encode auth
        val base = "$username:$password"
        val encodeAuth = "Basic ${Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP)}"

        //Retrofit call
        GlobalScope.launch {
            try {
                val response = apiInterface.getUser(encodeAuth).execute()
                if (response.isSuccessful) {
                    userResponse.postValue(response.body())
                } else {
                    userResponse.postValue(null)
                }

            } catch (t: Throwable) {
                userResponse.postValue(null)
                showToast(t.message.toString())
            }
        }
    }

}