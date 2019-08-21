package com.kuanquan.networklibrary.test

data class DataModel(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)

data class DataResult(
    val error: Boolean,
    val results: List<DataModel>
//    val results: DataModel
)