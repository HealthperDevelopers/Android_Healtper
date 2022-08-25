package com.umc.healthper.data.entity

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName(value = "postType") var postType : String,
    @SerializedName(value = "title") var title : String,
    @SerializedName(value = "content") var content : String
)

/** {
    "postType": "string",
    "title": "string",
    "content": "string"
}
 */