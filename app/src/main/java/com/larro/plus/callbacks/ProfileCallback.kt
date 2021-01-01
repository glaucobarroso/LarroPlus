package com.larro.plus.callbacks

import android.os.Handler
import android.util.Log
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.larro.plus.activities.PROFILE_MSG
import org.json.JSONObject

class ProfileCallback(private var uiHandler : Handler?) : GraphRequest.Callback {
    private val NAME_TAG = "name"
    private val ID_TAG = "id"
    private val EMAIL_TAG = "email"
    var id : String = ""
    var name : String = ""
    var email : String = ""

    override fun onCompleted(response: GraphResponse?) {
        val threadName = Thread.currentThread().name
        Log.i("THREAD", "onCompleted name = $threadName")
        val jsonObj : JSONObject? = response?.jsonObject
        if (jsonObj != null) {
            name = jsonObj.getString(NAME_TAG)
            id = jsonObj.getString(ID_TAG)
            email = jsonObj.getString(EMAIL_TAG)
        }
        uiHandler?.sendEmptyMessage(PROFILE_MSG)
    }
}