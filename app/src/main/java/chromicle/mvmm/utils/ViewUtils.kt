package chromicle.mvmm.utils

import android.content.Context
import android.widget.Toast

/**
 *Created by Chromicle on 13/7/19.
 */

fun Context.toast(message: String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}