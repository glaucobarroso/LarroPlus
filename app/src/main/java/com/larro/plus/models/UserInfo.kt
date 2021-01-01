package com.larro.plus.models

data class UserInfo(
    val id : String,
    val name : String,
    val points : Int,
    val level : String,
    val nextLevel : String,
    val nextLevelPoints : Int)
