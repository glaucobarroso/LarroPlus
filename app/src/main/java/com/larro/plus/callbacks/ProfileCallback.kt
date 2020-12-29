package com.larro.plus.callbacks

import com.facebook.GraphRequest
import com.facebook.GraphResponse
import org.json.JSONObject

class ProfileCallback : GraphRequest.Callback {
    private val NAME_TAG = "name"
    private val ID_TAG = "id"
    private val EMAIL_TAG = "email"
    var id : String = ""
    var name : String = ""
    var email : String = ""

    override fun onCompleted(response: GraphResponse?) {
        val jsonObj : JSONObject? = response?.jsonObject
        if (jsonObj != null) {
            name = jsonObj.getString(NAME_TAG)
            id = jsonObj.getString(ID_TAG)
            email = jsonObj.getString(EMAIL_TAG)
        }
    }
}