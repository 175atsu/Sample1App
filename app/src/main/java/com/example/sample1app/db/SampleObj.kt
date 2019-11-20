package com.example.sample1app.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SampleObj: RealmObject() {
    @PrimaryKey
    var id : Int? = null

    var name = ""
    var logoImage = ""
}