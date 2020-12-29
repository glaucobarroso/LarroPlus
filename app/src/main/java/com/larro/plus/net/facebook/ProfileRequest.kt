package com.larro.plus.net.facebook

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod

class ProfileRequest {

    private val ME_ENDPOINT = "/me"

    fun makeProfileRequest(callback : GraphRequest.Callback) {
        val params = Bundle()
        params.putString("fields", "picture,name,id,email,permissions")
        GraphRequest(AccessToken.getCurrentAccessToken(), ME_ENDPOINT, params, HttpMethod.GET,
            callback).executeAsync()
    }
}