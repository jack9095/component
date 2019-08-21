package com.kuanquan.networklibrary.test

data class DataModel(
    val type: String,
    val url: String
)

data class DataResult(
    val results: List<DataModel>
)