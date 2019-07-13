package chromicle.mvmm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel

/**
 *Created by Chromicle on 12/7/19.
 */

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListner:AuthListner? = null

    fun OnLoginButtonClick(view: View){
        authListner?.onStrarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()){

            authListner?.onFailure("Invalid email or password")
            return
        }

        authListner?.onSuccess()


    }

}