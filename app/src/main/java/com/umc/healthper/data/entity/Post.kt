package com.umc.healthper.data.entity

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName(value = "type") var type : String,
    @SerializedName(value = "title") var title : String,
    @SerializedName(value = "content") var content : String
)

data class modiPost(
    @SerializedName(value = "title") var title : String,
    @SerializedName(value = "content") var content : String
)

/** {
    "type": "string",
    "title": "string",
    "content": "string"
}
 */