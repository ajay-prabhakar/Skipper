package chromicle.mvmm.data.network.responses

import chromicle.mvmm.data.db.entitives.User

/**
 *Created by Chromicle on 27/8/19.
 */
data class AuthResponses(
    val isSuccessful : Boolean?,
    val message : String?,
    val user : User
) {
}