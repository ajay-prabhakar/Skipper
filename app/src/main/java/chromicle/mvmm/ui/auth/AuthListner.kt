package chromicle.mvmm.ui.auth

/**
 *Created by Chromicle on 12/7/19.
 */
interface AuthListner {

    fun onStrarted()
    fun onSuccess()
    fun onFailure(message: String)
}