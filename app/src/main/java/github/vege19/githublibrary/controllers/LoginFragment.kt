package github.vege19.githublibrary.controllers

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.infinum.goldfinger.Goldfinger
import github.vege19.githublibrary.App
import github.vege19.githublibrary.R
import github.vege19.githublibrary.utils.*
import github.vege19.githublibrary.viewmodels.LoginFragmentViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.lang.Exception


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
    private lateinit var goldFinger: Goldfinger
    private lateinit var base64Auth: String
    private val bundle = Bundle()

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
        userFingerprintChk.isChecked = getPreference().getBoolean(Const.FINGERPRINT_ENABLED_KEY, false)
        //If fingerprint is enabled and there is user saved, show prompt
        if (userFingerprintChk.isChecked &&
            !getPreference().getString(Const.USERNAME_KEY, "")!!.isEmpty() &&
            !getPreference().getString(Const.PASSWORD_KEY, "")!!.isEmpty()) {
            fingerAuth()
        } else {
            userFingerprintChk.isChecked = false
        }
        buildDialog()
        setClickListeners()
        getUserObserver()
    }

    private fun setClickListeners() {
        loginBtn.setOnClickListener {
            if (userFingerprintChk.isChecked) {
                //Save user credentials in preferences
                setPreference().putString(Const.USERNAME_KEY, inputUserTxt.text.toString()).commit()
                setPreference().putString(Const.PASSWORD_KEY, inputPassTxt.text.toString()).commit()
                login()
            } else {
                //Delete preferences
                setPreference().remove(Const.USERNAME_KEY).commit()
                setPreference().remove(Const.PASSWORD_KEY).commit()
                login()
            }
        }

        userFingerprintChk.setOnClickListener {
            //Save checkbox state
            if (userFingerprintChk.isChecked) {
                setPreference().putBoolean(Const.FINGERPRINT_ENABLED_KEY, true).commit()
            } else {
                setPreference().putBoolean(Const.FINGERPRINT_ENABLED_KEY, false).commit()
            }
        }
    }

    private fun login(isFingerprintAuth: Boolean = false) {
        //User inputs validations
        if (isFingerprintAuth) {
            username = getPreference().getString(Const.USERNAME_KEY, "")!!
            pass = getPreference().getString(Const.PASSWORD_KEY, "")!!
            Log.d("Debug", "$username $pass")
        } else {
            username = inputUserTxt.text.toString()
            pass = inputPassTxt.text.toString()

            if (username.isEmpty()) {
                inputUserLyt.error = getString(R.string.login_empty_error)
                return
            }

            if (pass.isEmpty()) {
                inputPassLyt.error = getString(R.string.login_empty_error)
                return
            }

        }

        base64Auth = encodeAuth(username, pass)
        bundle.putString(Const.BASE64_AUTH_KEY, base64Auth)
        viewModel.getUser(base64Auth)
        dialog.show() // Show loading dialog
    }

    private fun getUserObserver() {
        viewModel.getUserResponse().observe(this, Observer {
            if (it != null) {
                //Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
                //Save user data in bundle
                bundle.putSerializable(Const.USER_OBJECT_KEY, it)
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
            bundle,
            NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment,
                    true).build())
    }

    private fun fingerAuth() {
        //Initialize
        goldFinger = Goldfinger.Builder(requireContext()).build()

        //Check hardware
        if (goldFinger.hasFingerprintHardware()) {
            if (goldFinger.hasEnrolledFingerprint()) {
                //Build prompt
                val params = Goldfinger.PromptParams.Builder(requireActivity())
                    .title("Identifiquese")
                    .negativeButtonText("Cancelar")
                    .build()

                //Make auth in another thread
                Handler().post {
                    goldFinger.authenticate(params, object: Goldfinger.Callback {
                        override fun onError(e: Exception) {
                            //showToast(e.message.toString())
                        }

                        override fun onResult(result: Goldfinger.Result) {
                            if (result.type() == Goldfinger.Type.SUCCESS) {
                                login(true)
                            } else {
                                //showToast("Error")
                            }
                        }

                    })
                }

            }
        }
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
