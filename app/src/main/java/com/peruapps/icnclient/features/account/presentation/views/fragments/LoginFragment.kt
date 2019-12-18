package com.peruapps.icnclient.features.account.presentation.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import com.peruapps.icnclient.BR

import com.peruapps.icnclient.R
import com.peruapps.icnclient.databinding.FragmentLoginBinding
import com.peruapps.icnclient.features.account.presentation.viewmodel.LoginViewModel
import com.peruapps.icnclient.features.account.presentation.views.LoginNavigator
import com.peruapps.icnclient.features.reservations.presentation.views.ReservationActivity
import com.peruapps.icnclient.features.forgot_password.presentation.ForgotPasswordFragment
import com.peruapps.icnclient.helpers.NavigationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import android.content.Intent
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.peruapps.icnclient.features.register.presentation.fragments.CreateAccountFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginNavigator {

    private lateinit var binding: FragmentLoginBinding
    private val model: LoginViewModel by viewModel()

    private lateinit var loginButton: LoginButton
    private lateinit var callbackManager: CallbackManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callbackManager = CallbackManager.Factory.create()

        login_fbbutton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))

            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    val request = GraphRequest.newMeRequest(result!!.accessToken) { `object`, response ->
                        try {
                            //here is the data that you want
                            val email = `object`.get("email").toString()
                            val name = `object`.get("name").toString()
//                            Log.d("fb_result", `object`.toString())
                            model.facebookSignIn(email, name)

                        } catch (e: Exception) {
                            e.printStackTrace()
//                            dismissDialogLogin()
                        }
                    }

                    val parameters = Bundle()
                    parameters.putString("fields", "name,email,id,picture.type(large)")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    Log.d("fb_result", "cancel")
                }

                override fun onError(error: FacebookException?) {
                    Log.d("fb_result", error.toString())
                }
            })
        }

        model.setNavigator(this)
        subscribeLiveData()
        binding.setVariable(BR.viewModel, model)
    }

    override fun redirectAfterLogin() {
        NavigationHelper.redirectTo(activity!!, ReservationActivity::class.java, true)
    }

    override fun showForgotPasswordView() {
        NavigationHelper.changeFragment(fragmentManager!!,
            R.id.main_container,
            ForgotPasswordFragment(),
            "ForgotPasswordFragment")
    }

    private fun subscribeLiveData() {
        model.showError.observe(this, Observer {
            if  (it != "") {
                Toast.makeText(context!!, it , Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun showCreateAccountView(email: String, name: String) {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction!!.setCustomAnimations(
            R.anim.transition_slide_right_in,
            R.anim.transition_slide_left_out,
            android.R.anim.slide_in_left,
            R.anim.transition_slide_right_out
        )

        fragmentTransaction.replace(
            R.id.main_container,
            CreateAccountFragment.setData(name, email),
            "CreateAccountFragment"
        )
        fragmentTransaction.addToBackStack("LoginFragment")
        fragmentTransaction.commit()
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
