package com.example.legodataapp.data

data class LegoSet(
    var last_modified_dt: String = "",
    var name: String = "",
    var num_parts: Int = 0,
    var set_img_url: String = "",
    var set_num: String = "",
    var set_url: String = "",
    var theme_id: Int = 0,
    var year: Int = 0
) {
    constructor() : this("", "", 0, "", "", "", 0, 0)
}
