package com.umc.healthper.data.entity

import com.google.gson.annotations.SerializedName

data class Comment (
    @SerializedName(value = "postId") var postId : Int = 0,
    @SerializedName(value = "content") var content : String = ""
)

data class ChildComment (
    @SerializedName(value = "postId") var postId : Int = 0,
    @SerializedName(value = "parentId") var parentId : Int = 0,
    @SerializedName(value = "content") var content : String = ""
)