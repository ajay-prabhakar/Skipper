package chromicle.mvmm.ui.auth

import androidx.lifecycle.LiveData

/**
 *Created by Chromicle on 12/7/19.
 */
interface AuthListner {

    fun onStrarted()
    fun onSuccess(loginResponse: LiveData<String>)
    fun onFailure(message: String)
}