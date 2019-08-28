package chromicle.mvmm.ui.auth

import chromicle.mvmm.data.db.entitives.User

/**
 *Created by Chromicle on 12/7/19.
 */
interface AuthListner {

    fun onStrarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}