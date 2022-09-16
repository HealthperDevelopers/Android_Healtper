package com.umc.healthper.data.remote

import com.google.gson.annotations.SerializedName

data class APostResponse(
    @SerializedName(value = "postId") var postId : Int,
    @SerializedName(value = "postType") var postType : String,
    @SerializedName(value = "writer") var writer : WriterInfo,
    @SerializedName(value = "title") var title : String,
    @SerializedName(value = "content") var content : String,
    @SerializedName(value = "postStatus") var postStatus : String,
    @SerializedName(value = "createdAt") var createdAt : String,
    @SerializedName(value = "comments") var comments : List<Comments>
    )

data class Comments (
    @SerializedName(value = "commentId") var commentId : Int,
    @SerializedName(value = "writer") var writer : WriterInfo,
    @SerializedName(value = "content") var content : String,
    @SerializedName(value = "status") var status : String,
    @SerializedName(value = "createdAt") var createdAt : String,
    @SerializedName(value = "children") var children : List<Children>
    )

data class Children (
    @SerializedName(value = "commentId") var commentId : Int,
    @SerializedName(value = "writer") var writer : WriterInfo,
    @SerializedName(value = "content") var content : String,
    @SerializedName(value = "status") var status : String,
    @SerializedName(value = "createdAt") var createdAt : String
        )
/**
{
    "postId": 0,
    "postType": "NORMAL",
    "writer": {
        "memberId": 0,
        "nickName": "string",
        "status": "NORMAL"
    },
    "title": "string",
    "content": "string",
    "postStatus": "NORMAL",
    "createdAt": "2022-08-25T05:48:52.666Z",
        "comments": [
            {
                "commentId": 0,
                "writer": {
                "memberId": 0,
                "nickName": "string",
                "status": "NORMAL"
                },
            "content": "string",
            "status": "NORMAL",
            "createdAt": "2022-08-25T05:48:52.666Z",
            "children": [
                {
                "commentId": 0,
                "writer": {
                    "memberId": 0,
                    "nickName": "string",
                    "status": "NORMAL"
                    },
                "content": "string",
                "status": "NORMAL",
                "createdAt": "2022-08-25T05:48:52.666Z"
                }
            ]
        }
    ]
}
        */