package com.larro.plus.net

import com.larro.plus.models.UserInfo

class GenericRequest {

    fun getUserInfo(id : String) : UserInfo {
        // TODO
        return UserInfo("id", "sample", 100)
    }
}