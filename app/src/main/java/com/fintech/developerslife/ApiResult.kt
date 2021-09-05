package com.fintech.developerslife

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResult(
    @SerializedName("id")
    @Expose
    val id: Long,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("votes")
    @Expose
    val votes: Int,

    @SerializedName("author")
    @Expose
    val author: String,

    @SerializedName("date")
    @Expose
    val date: String,

    @SerializedName("gifURL")
    @Expose
    val gifURL: String,

    @SerializedName("gifSize")
    @Expose
    val gifSize: Long,

    @SerializedName("previewURL")
    @Expose
    val previewURL: String,

    @SerializedName("videoURL")
    @Expose
    val videoURL: String,

    @SerializedName("videoPath")
    @Expose
    val videoPath: String,

    @SerializedName("videoSize")
    @Expose
    val videoSize: Long,

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("width")
    @Expose
    val width: Int,

    @SerializedName("height")
    @Expose
    val height: Int,

    @SerializedName("commentsCount")
    @Expose
    val commentsCount: Int,

    @SerializedName("fileSize")
    @Expose
    val fileSize: Long,

    @SerializedName("canVote")
    @Expose
    val canVote: Boolean
)
