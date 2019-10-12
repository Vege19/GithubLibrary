package github.vege19.githublibrary.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import github.vege19.githublibrary.App
import github.vege19.githublibrary.R

fun showToast(message: String) {
    Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show()
}

fun encodeAuth(username: String, password: String): String {
    val base = "$username:$password"
    return "Basic ${Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP)}"
}

@SuppressLint("CommitPrefEdits")
fun setPreference(): SharedPreferences.Editor {
    val sharedPreferences = App.getContext().getSharedPreferences(
        App.getContext().getString(R.string.preference_file_key)
        , Context.MODE_PRIVATE
    )
    return sharedPreferences.edit()
}

fun getPreference(): SharedPreferences {
    return App.getContext().getSharedPreferences(
        App.getContext().getString(R.string.preference_file_key)
        , Context.MODE_PRIVATE
    )
}

fun ImageView.setGlideImage(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

suspend fun xd() {

}
