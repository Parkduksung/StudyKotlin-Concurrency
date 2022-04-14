package com.example.practice1.api

import com.google.gson.annotations.SerializedName

data class KakaoBookSearchResponse(
    @SerializedName("documents") val bookItems: List<BookItem>,
    @SerializedName("meta") val meta: Meta
)


data class BookItem(
    @SerializedName("contents") val contents: String,
    @SerializedName("sale_price") val sale_price: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("title") val title: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("datetime") val datetime: String
)

data class Meta(
    @SerializedName("is_end") val is_end: Boolean,
    @SerializedName("pageable_count") val pageable_count: Int,
    @SerializedName("total_count") val total_count: Int
)
