package chromicle.mvmm.ui.auth

import chromicle.mvmm.R
import chromicle.mvmm.utils.toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders


class LoginActivity : AppCompatActivity(), AuthListner {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: chromicle.mvmm.databinding.ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListner = this

    }

    override fun onStrarted() {
        toast("Login Started")
    }

    override fun onSuccess() {
        toast("Login Success")
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}
