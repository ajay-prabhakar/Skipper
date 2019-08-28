package chromicle.mvmm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import chromicle.mvmm.data.repositories.UserRepository
import chromicle.mvmm.utils.Coroutines
import retrofit2.Response

/**
 *Created by Chromicle on 12/7/19.
 */

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListner: AuthListner? = null

    fun OnLoginButtonClick(view: View) {
        authListner?.onStrarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListner?.onFailure("Invalid email or password")
        }

        Coroutines.main {

            val response =UserRepository().userLogin(email!!,password!!);
            if (response.isSuccessful){
                authListner?.onSuccess(response.body()?.user!!)
            }else{
                authListner?.onFailure("Error Code : ${response.code()}")
            }
        }

    }

}