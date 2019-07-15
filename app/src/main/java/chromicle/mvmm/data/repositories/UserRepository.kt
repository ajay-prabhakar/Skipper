package chromicle.mvmm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import chromicle.mvmm.data.network.MYApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *Created by Chromicle on 15/7/19.
 */
class UserRepository {
    fun userLogin(email: String, password : String) : LiveData<String>{
        val loginResponse = MutableLiveData<String>()

        MYApi().userLogin(email,password).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loginResponse.value = t.message;

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    loginResponse.value = response.body()?.string();

                }
                else{
                    loginResponse.value = response.errorBody()?.string()
                }

            }

        })
        return loginResponse
    }
}