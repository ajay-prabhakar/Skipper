package chromicle.mvmm.data.repositories

import chromicle.mvmm.data.network.MYApi
import chromicle.mvmm.data.network.responses.AuthResponses
import chromicle.mvmm.data.network.responses.SafeApiRequest
import retrofit2.Response

/**
 *Created by Chromicle on 15/7/19.
 */
class UserRepository : SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponses {

        return apiRequest {
            MYApi().userLogin(email, password)
        }
    }
}