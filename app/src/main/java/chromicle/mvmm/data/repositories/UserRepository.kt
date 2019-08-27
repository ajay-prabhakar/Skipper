package chromicle.mvmm.data.repositories

import chromicle.mvmm.data.network.MYApi
import chromicle.mvmm.data.network.responses.AuthResponses
import retrofit2.Response

/**
 *Created by Chromicle on 15/7/19.
 */
class UserRepository {
    suspend fun userLogin(email: String, password: String): Response<AuthResponses> {
        return MYApi().userLogin(email, password)
    }
}