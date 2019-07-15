package chromicle.mvmm.ui.auth

import chromicle.mvmm.R
import chromicle.mvmm.utils.toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), AuthListner {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: chromicle.mvmm.databinding.ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListner = this

    }

    override fun onStrarted() {
        progress_bar.visibility = View.VISIBLE
        toast("Login Started")
    }

    override fun onSuccess(loginResponse: LiveData<String>) {
        loginResponse.observe(this, Observer {
            progress_bar.visibility = View.GONE
            toast(it)
        })
    }

    override fun onFailure(message: String) {
        progress_bar.visibility = View.GONE
        toast(message)
    }
}
