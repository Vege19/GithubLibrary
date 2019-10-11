package github.vege19.githublibrary.controllers

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import github.vege19.githublibrary.App
import github.vege19.githublibrary.R
import github.vege19.githublibrary.utils.*
import github.vege19.githublibrary.viewmodels.LoginFragmentViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : Fragment(), StartFlow {

    //Injecting viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LoginFragmentViewModel::class.java]
    }

    private lateinit var username: String
    private lateinit var pass: String
    private lateinit var dialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startFlow()

    }

    override fun startFlow() {
        App.getComponent().inject(this)
        buildDialog()
        setClickListeners()
        getUserObserver()
    }

    private fun setClickListeners() {
        loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        //User inputs validations
        username = inputUsernameEt.text.toString()
        pass = inputPassEt.text.toString()

        if (username.isEmpty()) {
            inputUsernameEt.error = getString(R.string.login_empty_error)
            return
        }

        if (pass.isEmpty()) {
            inputPassEt.error = getString(R.string.login_empty_error)
            return
        }

        viewModel.getUser(username, pass)
        dialog.show() // Show loading dialog
    }

    private fun getUserObserver() {
        viewModel.getUserResponse().observe(this, Observer {
            if (it != null) {
                //Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
                //Save user credentials in preferences
                setPreference().putString(Const.USERNAME_KEY, username).commit()
                setPreference().putString(Const.PASSWORD_KEY, pass).commit()
                startRepositoriesFragment() //Nav to next screen
                dialog.dismiss() //Close loading dialog
            } else {
                showToast(viewModel.responseMessage)
                dialog.dismiss() //Close loading dialog
            }
        })
    }

    private fun startRepositoriesFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_repositoriesFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment,
                    true).build())
    }

    private fun buildDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.dialog_loading)
        }
        dialog = builder.create()
    }

}
