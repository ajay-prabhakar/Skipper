package chromicle.mvmm.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import chromicle.mvmm.R
import chromicle.mvmm.data.db.entitives.User
import chromicle.mvmm.utils.snackbar
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
        root_layout.snackbar("Login Started")
    }

    override fun onSuccess(user: User) {
        root_layout.snackbar("${user.name} is logged in")
    }

    override fun onFailure(message: String) {
        progress_bar.visibility = View.GONE
        root_layout.snackbar(message)
    }
}
