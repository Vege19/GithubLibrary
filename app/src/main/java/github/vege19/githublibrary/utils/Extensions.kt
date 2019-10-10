package github.vege19.githublibrary.utils

import android.widget.Toast
import github.vege19.githublibrary.App

fun showToast(message: String) {
    Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show()
}