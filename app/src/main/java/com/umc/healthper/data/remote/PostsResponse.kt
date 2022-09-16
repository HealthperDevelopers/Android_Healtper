package com.umc.healthper.data.remote

import com.google.gson.annotations.SerializedName
import java.util.*

data class PostsResponse (
    @SerializedName(value = "content") val content : List<Contents>
)

data class Contents (
    @SerializedName(value = "postId") val postId : Int,
    @SerializedName(value = "postType") val postType : String,
    @SerializedName(value = "writer") val writer : WriterInfo,
    @SerializedName(value = "title") val title : String,
    @SerializedName(value = "likeCount") var likeCount : Int,
    @SerializedName(value = "commentCount") var commentCount : Int,
    @SerializedName(value = "createdAt") val createdAt : String
        )

data class WriterInfo (
    @SerializedName(value = "memberId") val memberId : Int,
    @SerializedName(value = "nickName") val nickName : String,
    @SerializedName(value = "status") val status : String
    )

/** example
 * {
        "content":
        [
            {
                "postId": 0,
                "postType": "NORMAL",
                "writer": {
                    "memberId": 0,
                    "nickName": "string",
                    "status": "NORMAL"
                },
                "title": "string",
                "likeCount": 0,
                "commentCount": 0,
                "createdAt": "2022-08-25T02:55:15.278Z"
            }
        ]
    }
 */