package chromicle.mvmm.data.network

import android.provider.ContactsContract
import android.telecom.Call
import chromicle.mvmm.data.network.responses.AuthResponses
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *Created by Chromicle on 13/7/19.
 */

interface MYApi {

    @FormUrlEncoded
    @POST("login")

    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponses>

    companion object {
        operator fun invoke(): MYApi {
            return Retrofit.Builder().baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create()).build().create(MYApi::class.java)
        }
    }
}