package com.pam.spotassets.model

data class WordlistResponseModel(
    val package_id: String,
    val raw_wordlist: List<String>,
    val wordlist: List<String>
)